package com.yhl.baseOrm.config;

import com.yhl.baseOrm.config.factory.BaseDaoFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
@EnableJpaRepositories(
        basePackages = {"com.**.dao"},
        entityManagerFactoryRef="entityManagerFactory",//注意这个名字不能乱取，妈的坑了我一天
        transactionManagerRef="transactionManagerPrimary",
        repositoryFactoryBeanClass = BaseDaoFactoryBean.class
)//最后一个时dao包扫描什么包
@AutoConfigureAfter(MyConfig.class)
public class JpaConfig {
    @Autowired
    @Qualifier("datasource")
    private DataSource dataSource;
    //实体包的位置
    private  String[] packagepath={"com.**.entity","com.**.model"};
    // hibernate 配置



    @Bean(name = "entityManagerFactory")
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
       /* properties.setProperty("base-package", "com.**.jpaDao");
        properties.setProperty("factory-class", BaseDaoFactoryBean.class.getName());
        properties.setProperty("entity-manager-factory-ref","localContainerEntityManagerFactoryBean");
        properties.setProperty("transactionManagerRef","jpaTransactionManager");*/
        return properties;
    }
    @Bean(name = "transactionManagerPrimary")
    public PlatformTransactionManager transactionManagerPrimary(/*EntityManagerFactoryBuilder builder*/) {
        /* return new JpaTransactionManager(entityManagerFactory(builder).getObject());*/
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory ().getObject());
        return txManager;
    }
}
