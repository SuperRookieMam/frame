package com.yhl.Interapt;

import com.yhl.dao.BaseDao;
import com.yhl.util.InterceptUtil;
import com.yhl.util.MyClassUtil;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.beans.PropertyDescriptor;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Intercepts({
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class , ResultHandler.class }),
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
    @Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class}),
    @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
public class MyMybatisInterrupt implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
         Object target=invocation.getTarget();
        //拦截 ParameterHandler 的 setParameters 方法 动态设置参数
         if (target instanceof ParameterHandler ){
             DefaultParameterHandler defaultParameterHandler =(DefaultParameterHandler)target;
             MappedStatement mappedStatement =(MappedStatement)MyClassUtil.getFieldByName(defaultParameterHandler,"mappedStatement");
             String statementId =mappedStatement.getId();
             if (!InterceptUtil.isInInterceptMethod(statementId)){
                return invocation.proceed();
             }
             String className =statementId.substring(0,statementId.lastIndexOf("."));
             Class daoClazz = MyClassUtil.getClassForName(className);
             Class superClazz=  daoClazz.getSuperclass();
             if (!BaseDao.class.getName().equals(superClazz.getName())){
                 return invocation.proceed();
             }
             Class paramClass = MyClassUtil.getFirstClass(superClazz);
             EntityTable entityTable = EntityHelper.getEntityTable(paramClass);
             String tableName=entityTable.getName();
             BoundSql boundSql =(BoundSql)MyClassUtil.getFieldByName(defaultParameterHandler,"boundSql");
             Object parameterObject =(BoundSql)MyClassUtil.getFieldByName(defaultParameterHandler,"boundSql");
             List<ParameterMapping> parameterMappings =boundSql.getParameterMappings();
             List<String> paramNames =new ArrayList<>();
             for (ParameterMapping parameterMapping : parameterMappings) {
                     paramNames.add(parameterMapping.getProperty());

             }
             Map<String, Object> paramMap = new MapperMethod.ParamMap<>();
             if (parameterObject == null) {
                 paramMap.put("tableName", 1L);
                 parameterObject = paramMap;
                 // 单参数 将 参数转为 map
             } else if (ClassUtils.isPrimitiveOrWrapper(paramMap.getClass())
                     || String.class.isAssignableFrom(paramMap.getClass())
                     || Number.class.isAssignableFrom(paramMap.getClass())) {
                 if (paramMap.size() == 1) {
                     paramMap.put(paramNames.iterator().next(), parameterObject);
                     paramMap.put("tableName", 1L);
                     parameterObject = paramMap;
                 }
             } else {
                 if (parameterObject instanceof Map) {
                     ((Map) parameterObject).putIfAbsent("tableName", 1L);
                 } else {
                     //就是根据反射获取 使用spring的  执行方法
                     PropertyDescriptor ps = BeanUtils.getPropertyDescriptor(parameterObject.getClass(), "tableName");
                     if (ps != null && ps.getReadMethod() != null && ps.getWriteMethod() != null) {
                         Object value = ps.getReadMethod().invoke(parameterObject);
                         if (value == null) {
                             ps.getWriteMethod().invoke(parameterObject, 1L);
                         }
                     }
                 }
             }
//           改写的参数设置到原parameterHandler对象
             MyClassUtil.setFieldByName(defaultParameterHandler,"parameterObject",parameterObject);
             defaultParameterHandler.setParameters((PreparedStatement) invocation.getArgs()[0]);
         // 拦截 Executor 的 createCacheKey 方法，pageHelper插件会拦截 query 方法，调用此方法，提前将参数设置到参数集合中
             // 拦截 Executor 的 update 方法 生成sql前将 tenantId 设置到实体中
         }else if (target instanceof Executor){

         }else if (target instanceof ResultSetHandler){

         }



        return invocation.proceed();
    }

    @Override
    public Object plugin(Object arg) {
        return Plugin.wrap(arg, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
