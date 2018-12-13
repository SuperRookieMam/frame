package com.yhl.Interapt;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * 此拦截器值拦截参数设置的时候，替换Basedao中InterceputCont.METHOD_NAME指定的放
 * */
@Intercepts({@Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class})})
public class ParamIntercept implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        DefaultParameterHandler defaultParameterHandler =(DefaultParameterHandler)invocation.getTarget();
        PreparedStatement preparedStatement =(PreparedStatement)invocation.getArgs()[0];


        return null;
    }

    @Override
    public Object plugin(Object arg) {
        return Plugin.wrap(arg, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
