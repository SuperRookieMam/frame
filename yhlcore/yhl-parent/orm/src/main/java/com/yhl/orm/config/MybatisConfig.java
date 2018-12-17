package com.yhl.orm.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.yhl.dao"},sqlSessionFactoryRef = "sqlSessionFactory")
@EnableJpaRepositories(
        entityManagerFactoryRef="localContainerEntityManagerFactoryBean",
        transactionManagerRef="jpaTransactionManager",
        basePackages= {"com.**.dao"})//最后一个时dao包扫描什么包
public class MybatisConfig {
    @Autowired
    @Qualifier("datasource")
    private DataSource dataSource;
    @Autowired
    private JpaProperties jpaProperties;
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
    @Bean(name = "entityManager")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactory(builder).getObject().createEntityManager();
    }

    @Bean(name = "localContainerEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory (EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .properties(getVendorProperties())
                .packages(packagepath)
                .persistenceUnit("erpPersistenceUnit")
                .build();
    }
    private Map getVendorProperties() {
        return jpaProperties.getHibernateProperties(dataSource);
    }
    @Bean(name = "jpaTransactionManager")
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactory(builder).getObject());
    }
}
