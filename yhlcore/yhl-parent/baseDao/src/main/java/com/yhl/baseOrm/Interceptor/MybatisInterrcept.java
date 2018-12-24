package com.yhl.baseOrm.Interceptor;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

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
/*@Intercepts({
        @Signature(
                type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class, ParameterHandler.class, ResultSetHandler.class}
        )
})*/
public class MybatisInterrcept implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
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
