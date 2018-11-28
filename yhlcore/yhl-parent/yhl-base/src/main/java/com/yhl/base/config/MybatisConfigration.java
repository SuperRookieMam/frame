package com.yhl.base.config;


import com.yhl.base.condition.MybatisCondtion;
import com.yhl.base.condition.PrimaryCondition;
import com.yhl.base.condition.SecondCondition;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Conditional(MybatisCondtion.class)
@MapperScan(basePackages = {"com.**.dao","com.**.mapper"})
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
}
