package com.yhl.base.condition;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class FourConditon implements Condition {
    @Value("${datasource.four.driverClassName}")
    private  String driver;
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        if (driver!=null){
            return true;
        }
        return false;
    }
}