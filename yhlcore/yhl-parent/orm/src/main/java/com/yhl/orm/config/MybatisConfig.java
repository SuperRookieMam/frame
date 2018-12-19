package com.yhl.orm.config;

import com.yhl.orm.config.factory.BaseDaoFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@MapperScan(
            basePackages = {"com.**.mybatisDao"},
            sqlSessionFactoryRef = "sqlSessionFactory"
            )
@EnableJpaRepositories(
        basePackages= {"com.**.jpaDao"},
        entityManagerFactoryRef="localContainerEntityManagerFactoryBean",
        transactionManagerRef="jpaTransactionManager",
        repositoryFactoryBeanClass = BaseDaoFactoryBean.class
        )//最后一个时dao包扫描什么包
public class MybatisConfig {
    @Autowired
    @Qualifier("datasource")
    private DataSource dataSource;
    //xml位置
    private  String classpath="classpath*:mapping/**/*.xml";
    //实体包的位置
    private  String[] packagepath={"com.**.entity","com.**.model"};
    @Bean(name = "mysqlTransactionManager")
    @Primary
    public DataSourceTransactionManager mysqlTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("sqlSessionFactory")
    @Primary
    public SqlSessionFactory getSqlSessionFactory() throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver solver=new PathMatchingResourcePatternResolver();
        Resource[] resources =solver.getResources(classpath);
        bean.setMapperLocations(resources);
        return bean.getObject();
    }

    // hibernate 配置

    @Bean(name = "localContainerEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory () {
        HibernateJpaVendorAdapter jpaVendorAdapter=new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        //字符创建表结构
        jpaVendorAdapter.setGenerateDdl(true);
        //设置数据库类型
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        //factorybean
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaProperties(getVendorProperties());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        //永久层单元，如果时实体类就时设置的字符串加.
        factoryBean.setPersistenceUnitName("");
        //其实就是上面@实体类的包 的数组
        factoryBean.setPackagesToScan(packagepath);

        //下面时还可设置的，但是作用时什么不知道
       /* factoryBean.setJtaDataSource();
        factoryBean.setLoadTimeWeaver();
        factoryBean.setMappingResources();
        factoryBean.setPersistenceUnitManager();
        factoryBean.setPersistenceUnitPostProcessors();
        factoryBean.setPersistenceUnitRootLocation();
        factoryBean.setPersistenceXmlLocation();
        factoryBean.setResourceLoader();
        factoryBean.setSharedCacheMode();
        factoryBean.setValidationMode();
        factoryBean.setBeanName();
        factoryBean.setBootstrapExecutor();
        factoryBean.setEntityManagerFactoryInterface();
        factoryBean.setJpaDialect();
        factoryBean.afterPropertiesSet();
        factoryBean.setPersistenceProviderClass();*/
        return factoryBean;
    }
    private Properties getVendorProperties() {
       /* Map<String, Object> props = new HashMap<>();
        props.put("hibernate.use-new-id-generator-mappings", "true");
        props.put("hibernate.ddl-auto", "update");*/
        //jpaProperties.getHibernateProperties(DataSource)
        Properties properties = new Properties();
        properties.setProperty("hibernate.ddl-auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("base-package", "com.**.jpaDao");
        properties.setProperty("factory-class", BaseDaoFactoryBean.class.getName());
        properties.setProperty("entity-manager-factory-ref","localContainerEntityManagerFactoryBean");
        properties.setProperty("transactionManagerRef","jpaTransactionManager");
        return properties;
    }
    @Bean(name = "jpaTransactionManager")
    public PlatformTransactionManager transactionManagerPrimary(/*EntityManagerFactoryBuilder builder*/) {
        /* return new JpaTransactionManager(entityManagerFactory(builder).getObject());*/
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory ().getObject());
        return txManager;
    }

}
