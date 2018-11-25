package com.yhl.base.condition;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.List;

public class HibernateCondition implements Condition {
    @Value("${myOrm.list}")
    private List<String> list;

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equalsIgnoreCase("hibernate")){
                return  true;
            }
        }
        return false;
    }
}
