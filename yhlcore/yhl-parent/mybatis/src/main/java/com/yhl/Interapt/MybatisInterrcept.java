package com.yhl.Interapt;

import com.yhl.dao.BaseDao;
import com.yhl.util.MyClassUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.Map;
import java.util.Properties;


/**
 * type = ParameterHandler,ResultSetHandler,StatementHandler,Executor
 * 猜测对应的 参数封装，返回封装，链接会话,执行的时候
 * method = 拦截的方法名
 * args = 方法的参数
 *Executor的执行大概是这样的流程：
 * Executor->Plugin->Interceptor->Invocation
 * Executor.Method->Plugin.invoke->Interceptor.intercept->Invocation.proceed->method.invoke
 * */
@Intercepts({
        @Signature(
                type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class}
        )
})
public class MybatisInterrcept  implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final  Object[] args =invocation.getArgs();
        final  MappedStatement mappedStatement=(MappedStatement)args[0];
        final  Object parameter=args[1];
        //获取被拦截的对象
        Object target = invocation.getTarget();
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("拦截的对象："+target.getClass().getName());
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("是否继承基类："+(target instanceof BaseDao));
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        // 没有继承basedao不拦截，不进行下面的操作
            if (!(target instanceof BaseDao)){
                return invocation.proceed();
            }
          //获取参数耍的泛型类,判断操作的那张表
         Class clazz = target.getClass();
         Class entityClass =  MyClassUtil.getFirstClass(clazz);
          //获取实体表的信息，注意要这个的话一定要用tk。mapper,spring的没的！
         EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
         String tableName =entityTable.getName();
         //获取实体的columns
         String[] columns =entityTable.getKeyColumns();
         String columnStr ="";
         for (int i = 0; i <columns.length ; i++) {
             columnStr +=columns[i]+",";
         }
        columnStr =columnStr.endsWith(",")?columnStr.substring(0,columnStr.length()-1):columnStr;
        //只拦截Map的参数，因为自己做的baseDao接收的参数都是map的
         Map<String,Object> map =null;
         if (parameter instanceof Map){
            map = (Map<String, Object>) parameter;
         }
         if (map ==null){
             throw  new  RuntimeException("参数为空");
         }
        map.put("tableName",tableName);
        map.put("columns",columnStr);


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
