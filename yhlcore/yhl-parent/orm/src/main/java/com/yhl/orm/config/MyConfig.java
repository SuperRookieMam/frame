package com.yhl.orm.config;


import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 数据源配置
 * */
@Configuration
public class MyConfig {

    @Primary
    @Bean("datasource")
    @ConfigurationProperties("spring.datasource")
    public DataSource getMysqlDatasource(){
        return DataSourceBuilder.create().build();
    }
}
