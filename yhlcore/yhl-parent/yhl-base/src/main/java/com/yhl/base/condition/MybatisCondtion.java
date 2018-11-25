package com.yhl.base.condition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.List;

public class MybatisCondtion implements Condition {
    @Value("${myOrm.list}")
    private List<String> list;

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
           //Thread thread =  Thread.currentThread();
           //获取当先县城下的CLASS目录下
           //String url = thread.getContextClassLoader().getResource("").getPath();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equalsIgnoreCase("mybatis")){
                    return  true;
                }
            }
        return false;
    }
}
