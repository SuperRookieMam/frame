package com.yhl.base.component.config;


import com.yhl.base.component.condition.MybatisCondtion;
import com.yhl.base.component.condition.PrimaryCondition;
import com.yhl.base.component.condition.SecondCondition;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Conditional(MybatisCondtion.class)
@MapperScan(basePackages = {"com.**.dao.mybatis"})
public class MybatisConfigration {


    @Primary
    @Bean(name = "primayDatasource")
    @Conditional(PrimaryCondition.class)
    @ConfigurationProperties(prefix = "datasource.primay")
    public DataSource getPrimayDataSource(){
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "secondDatasource")
    @Conditional(SecondCondition.class)
    @ConfigurationProperties(prefix = "datasource.second")
    public DataSource getSecondDataSource(){
        return DataSourceBuilder.create().build();
    }
//=======================================================================================
    /*
    * mybatis primary
    * */
    @Primary
    @Conditional(PrimaryCondition.class)
    @Bean(name = "primarySqlSessionFactory")
    public SqlSessionFactory getPrimarySqlSessionFactory(@Qualifier("primayDatasource") DataSource dataSource) throws  Exception{
        if (dataSource!=null){
            SqlSessionFactoryBean sqlSessionFactoryBean =new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            PathMatchingResourcePatternResolver solver =new PathMatchingResourcePatternResolver();
            Resource[] resources = solver.getResources("classpath*:mapper/**/*.xml");
            sqlSessionFactoryBean.setMapperLocations(resources);
            return sqlSessionFactoryBean.getObject();
          }
        return null;
    }
    @Primary
    @Conditional(PrimaryCondition.class)
    @Bean(name = "primarySqlSessionTemplate")
    public SqlSessionTemplate getPrimarySqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory){
         if (sqlSessionFactory!=null){
             return new SqlSessionTemplate(sqlSessionFactory);
         }
        return null;
    }

//=======================================================================================











}
