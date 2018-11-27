package com.yhl.base.condition;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class PrimaryCondition implements Condition {

    @Value("${datasource.primary.driverClassName}")
    private  String driver;
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        if (driver!=null){
            return true;
        }
        return false;
    }
}
