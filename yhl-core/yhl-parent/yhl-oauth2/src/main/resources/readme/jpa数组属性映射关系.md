####基础类（String）存放在集合（有顺序的List）
    -- 这是一个基础的员工信息表，很简单，姓名和Id
    CREATE TABLE `Employee` (
      `EmployeeId` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
      `FirstName` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
      `LastName` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
      PRIMARY KEY (`EmployeeId`)
    ) ENGINE=InnoDB
     
    -- 员工的电话号码，一个员工可以有多个电话号码，其中Protrity用于在电话列表中的优先排序。
    CREATE TABLE `Employee_Phone` (
      `Employee` bigint(20) unsigned NOT NULL,
      `Priority` smallint(5) unsigned NOT NULL,
      `Number` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
      CONSTRAINT `Employee_Phone_Employee` FOREIGN KEY (`Employee`) 
         REFERENCES `Employee` (`EmployeeId`) ON DELETE CASCADE
    ) ENGINE=InnoDB
    @Entity
    public class Employee {
        private long id;
        private String firstName;
        private String lastName;
        //【1】设置List属性：将从Employee_Phone中获取员工的电话信息。
        private List<String> phoneNumbers = new ArrayList<>();
     
        //【2】从collection table中获取获取集合（本例子为List）的信息。
        // 2.1）@ElementCollection：在小例子中，页面分页显示时给出员工的姓名，也给出联系电话，
        //      因此在业务逻辑上获取entity对象的同时，就需要获取电话信息，采用FetchType.EAGER。
        // 2.2）@CollectionTable：给出关联表格信息。表格Employee_Phone的外键Employee指向
        //      entity对应表格的EmployeeId列。
        // 2.3）@Column@OrderColumn：读取CollectionTable中的Number列信息，以Priority为排序，
        //      存放到集合（List）中。表格中的priority或是List的index，即0,1,2,... 
        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable( name="Employee_Phone",
              joinColumns = { @JoinColumn(name = "Employee", referencedColumnName = "EmployeeId")})
        @OrderColumn(name="Priority")
        @Column(name="Number")
        public List<String> getPhoneNumbers() {
            return phoneNumbers;
        }  	
        ... ...
    }
    Embeddabel类作为集合（Set）
    员工还有地址信息，也是可以有多条。
    
    CREATE TABLE `Employee_Address` (
      `Employee` bigint(20) unsigned NOT NULL,
      `Street` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
      `City` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
      `State` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
      `Country` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
      `PostalCode_Code` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
      `PostalCode_Suffix` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,
      CONSTRAINT `Employee_Address_Employee` FOREIGN KEY (`Employee`) 
            REFERENCES `Employee` (`EmployeeId`) ON DELETE CASCADE
    ) ENGINE=InnoDB
    1）定义@Embeddable类
    // 在javadoc中对@ElementCollection的解释：
    // Specifies a collection of instances of a basic type or embeddable class。
    // 地址是个复杂的类，要能在entity中做为ElementCollection，这个类需要是Embeddable
    @Embeddable
    public class Address {
        private String street;
        private String city;
        private String state;
        private String country;
        private PostalCode postalCode;
        ... ...
    }
    2）获取@Embeddable类的集合，在entity中指定@Embeddable类属性对应的列
    @Entity
    public class Employee {
        //【1】假定这些信息没有先后顺序，存放在集合Set中。
        private Set<Address> addresses = new HashSet<>();
     
        //【2】从collection table中获取获取集合（本例为Set）的信息。
        /* - @ElementCollection：在小例子中，页面分页显示时给出员工的姓名，也给出联系电话，但并不提
             供地址信息，只有点员工详细查看时才提供。也就是只有需要的时候才获取Adress信息，这种情况
             采用FetchType.LAZY。
         - @Embeddable类如果是entity的属性，则必须在同一个表格（之前学习），如果是collection的
             一个元素，则可以在不同的表格（现在学习），这个表的缺省名字为entity表名_属性名，本例为
             Employee_Address，符合缺省定义，安全地，我们仍在@CollectionTable中设定。
           - @AttributeOverrides：本例子中列名和属性名是一致的，如果不一致，可以通过
             @AttributeOverrides来设定。当然也可以在@Embeddable类中属性采用@Column来指定。但是如
             果这个@Embeddable最终映射到两个或者以上表，列名不一样时，就需要在entity中指定。*/ 
        @ElementCollection(fetch = FetchType.LAZY)
        @CollectionTable(name="Employee_Address",
                         joinColumns ={@JoinColumn(name = "Employee", referencedColumnName = "EmployeeId")})
        @AttributeOverrides({ @AttributeOverride(name="street",column = @Column(name="Street")),	
                              @AttributeOverride(name = "city", column = @Column(name = "City")),
                              @AttributeOverride(name = "state", column = @Column(name = "State")),
                              @AttributeOverride(name = "country", column=@Column(name = "Country")) })
        public Set<Address> getAddresses() {
            return addresses;
        }	
        ... ...
    }
    FetchType.LAZY
    在这个例子中，Set<Address>采用了fetch = FetchType.LAZY，也就是代码中涉及该属性处理时，才会去数据库中获取。我们有可能会碰到下面的异常：
    
    org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: 
           cn.wei.flowingflying.chapter24.entities.Employee.addresses, could not initialize proxy - no Session
    我们如果要对entity进行处理，如果后续用使用到lazy的属性，就没有初始化，会报错。例如，需要save entity，即使没有对Adress的信息进行改动，但在Spring data中也是对完整信息进行处理。解决方式就是要确保整获完整的entity的数据。我们在Service中增加一个public Employee getEmployee(long id) 接口来替代Spring data中的findOne接口，用于获取完整的entity信息。
    
    @Inject EmployeeRepository employeeRepository;
     
    @Override
    //【1】加入到transactional中。这里读了两次，第一次是普通读（不获取LAZY属性）；第二次是获取LAZY属
    //     性。两次操作有管理，放在一个事务中。
    @Transactional
    public Employee getEmployee(long id) {
        Employee employee = this.employeeRepository.findOne(id);
        //【2】实现初始化，例如调用employee.getAddresses()进行某个操作，例如.size()，触发在数据库中
        //     获取相关的信息。
        employee.getAddresses().size(); 
        return employee;
    }
    映射到集合Map
    给员工数据增加一个扩展属性说明
    
    CREATE TABLE `Employee_Property` (
      `Employee` bigint(20) unsigned NOT NULL,
      `KeyName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
      `Value` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
      CONSTRAINT `Employee_Property_Employee` FOREIGN KEY (`Employee`) 
          REFERENCES `Employee` (`EmployeeId`) ON DELETE CASCADE
    ) ENGINE=InnoDB;
    @Entity
    public class Employee {
        //【1】这些扩展信息放在key-value对中。
        private Map<String, String> extraProperties = new HashMap<>();
     
        /*【2】从collection table中获取获取键值对（本例为Map）的信息。
           @Column和@MapKeyColumn分别代表CollectionTable中的value所在列和key所在列。如果是枚举做为
           值，则分别对应@Enumerated和@MapKeyEnumerated，如果是时间，则对应@Temporal和
           @MapKeyTemporal。不仅仅可以使用基础类，还可以通过convert转换为其他类型，或者是embeddable类 */
        @ElementCollection(fetch=FetchType.EAGER)
        @CollectionTable(name="Employee_Property",
                         joinColumns ={@JoinColumn(name = "Employee", referencedColumnName = "EmployeeId")})
        @Column(name = "Value")
        @MapKeyColumn(name = "KeyName")
        public Map<String, String> getExtraProperties() { ... }
        ... ...
        }