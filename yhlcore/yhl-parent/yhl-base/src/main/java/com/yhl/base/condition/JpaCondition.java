package com.yhl.base.condition;

import com.yhl.base.config.ConfigProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.List;

public class JpaCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        ConfigProperties configProperties =new ConfigProperties();
        List<String> list =configProperties.getLi();
        for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equalsIgnoreCase("jpa")){
                    return  true;
                }
            }
        return false;
    }
}