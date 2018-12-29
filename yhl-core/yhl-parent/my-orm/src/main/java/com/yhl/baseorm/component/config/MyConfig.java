package com.yhl.baseorm.component.config;


import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * 数据源配置
 * */
@Configuration
@Component
public class MyConfig {

    @Primary
    @Bean("datasource")
    @ConfigurationProperties("spring.datasource")
    public DataSource getMysqlDatasource(){
        return DataSourceBuilder.create().build();
    }
}
