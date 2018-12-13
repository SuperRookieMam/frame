package com.yhl.Interapt;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.util.Properties;


/**
 * type = ParameterHandler,ResultSetHandler,StatementHandler,Executor
 * 猜测对应的 参数封装，返回封装，链接会话,执行的时候
 * method = 拦截的方法名
 * args = 方法的参数
 *Executor的执行大概是这样的流程：
 * Executor->Plugin->Interceptor->Invocation
 * Executor.Method->Plugin.invoke->Interceptor.intercept->Invocation.proceed->method.invoke
 *
 * 如果拦截的是同一个目标方法，那么 yy拦截器将先执行。
 * 可拦截的目标方法有以下(大致的先后顺序):
 *   Executor
 * (update, query, flushStatements,
 *  commit,  rollback, getTransaction,
 *  close, isClosed)
 *   ParameterHandler
 * (getParameterObject, setParameters)
 *
 *   StatementHandler
 * (prepare, parameterize, batch, update, query)
 *   ResultSetHandler
 * (handleResultSets, handleOutputParameters)
 *  */
@Intercepts({
        @Signature(
                type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class, ParameterHandler.class, ResultSetHandler.class}
        )
})
public class MybatisInterrcept  implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final  Object[] args =invocation.getArgs();
        final  MappedStatement mappedStatement=(MappedStatement)args[0];
        final  ParameterHandler parameterHandler=(ParameterHandler)args[0];
        final  ResultSetHandler resultSetHandler=(ResultSetHandler)args[0];
        Executor executor=(Executor)invocation.getTarget();
        /*final  Object parameter=args[1];
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
        map.put("columns",columnStr);*/


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
