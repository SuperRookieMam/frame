package com.yhl.base.component.condition;

import com.yhl.base.component.config.ConfigProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.List;

public class MybatisCondtion implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        ConfigProperties configProperties =new ConfigProperties();
        List<String> list =configProperties.getLi();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).contains("mybatis")){
                    return  true;
                }
            }
        return false;
    }
}
