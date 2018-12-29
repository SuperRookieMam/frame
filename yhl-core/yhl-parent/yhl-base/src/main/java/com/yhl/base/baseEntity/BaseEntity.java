package com.yhl.base.baseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 添加一些数据库都有的公共字段或者做一些
 * **/
/*@Entity
@Table(name="BaseEntity")*/

/**
 * 1.Single Table Strategy 单表策略,一张表包含基类与子类的所有数据,
 * 很多情况下都是采用这样的冗余设计,
 * 通过一个discriminator来区分
 * 2.Table Per Class Strategy ,
 * 每个子类对应一张表,每张表都拥有基类的属性，
 * 基类不会生成表。
 * 3..Join Strategy ,仍然是每个子类对应一张表，
 * 但此表中不包含基类的属性,
 * 仅仅是此子类的扩展属性,共享基类的属性
 * */
/*@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "BaseEntity", discriminatorType = DiscriminatorType.STRING)
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")*/
@MappedSuperclass
public class BaseEntity<ID> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private ID id;


    @Column(name = "create_time",updatable = false,columnDefinition ="datetime comment '创建时间'")
    private String createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    @Column(name = "modify_time",columnDefinition ="datetime comment '修改时间'")
    private String modifyTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());


    @Column(name = "create_user",updatable = false,columnDefinition ="varchar(255) comment '创建者'")
    private String createUser;


    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
