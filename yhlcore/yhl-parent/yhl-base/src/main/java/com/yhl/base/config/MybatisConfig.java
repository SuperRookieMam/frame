package com.yhl.base.config;

import com.yhl.base.condition.MybatisCondtion;
import com.yhl.base.condition.PrimaryCondition;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@Conditional(MybatisCondtion.class)
public class MybatisConfig {

    @Resource
    private  ConfigProperties configProperties;
    private  String  haveLoad=",";
    @Bean("primarySqlSessionFactorry")
    @Conditional(PrimaryCondition.class)
    public SqlSessionFactory getPrimarySqlSessionFactorry(){
        List<String> list =configProperties.getLi();
        for (int i = 0; i < list.size(); i++) {

         }
        return null;
    }




}
