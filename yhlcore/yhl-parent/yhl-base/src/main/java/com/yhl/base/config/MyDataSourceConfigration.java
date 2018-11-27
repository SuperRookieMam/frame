package com.yhl.base.config;

import com.yhl.base.condition.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 配置了5个数据源,支持同时使用5中不同的数据库，
 * 如果还需好更多，再加吧，我想应该够了
 * */
@Configuration
public class MyDataSourceConfigration {

   @Bean(name = "primaryDataSource")
   @Primary
   @Qualifier("primaryDataSource")
   @ConfigurationProperties(prefix = "datasource.primary")
   @Conditional(PrimaryCondition.class)   //  才会加载这个Bean
   public DataSource primaryDataSource(){
       return DataSourceBuilder.create().build();
   }

   @Bean(name = "secondDataSource")
   @Qualifier("secondDataSource")
   @ConfigurationProperties(prefix = "datasource.second")
   @Conditional(SecondCondition.class)   //  才会加载这个Bean
   public DataSource secondDataSource(){
      return DataSourceBuilder.create().build();
   }
   @Bean(name = "thirdCondition")
   @Primary
   @Qualifier("thirdCondition")
   @ConfigurationProperties(prefix = "datasource.third")
   @Conditional(ThirdCondition.class)   //  才会加载这个Bean
   public DataSource thirdDataSource(){
      return DataSourceBuilder.create().build();
   }
   @Bean(name = "fourDataSource")
   @Primary
   @Qualifier("fourDataSource")
   @ConfigurationProperties(prefix = "datasource.four")
   @Conditional(FourConditon.class)   //  才会加载这个Bean
   public DataSource fourDataSource(){
      return DataSourceBuilder.create().build();
   }
   @Bean(name = "fiveDataSource")
   @Primary
   @Qualifier("fiveDataSource")
   @ConfigurationProperties(prefix = "datasource.five")
   @Conditional(FiveCondition.class)   //  才会加载这个Bean
   public DataSource fiveDataSource(){
      return DataSourceBuilder.create().build();
   }
}
