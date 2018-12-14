package com.yhl.Interapt;

import com.yhl.dao.BaseDao;
import com.yhl.util.InterceptUtil;
import com.yhl.util.MyClassUtil;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.sql.PreparedStatement;
import java.util.*;

/**
 * 此拦截器值拦截参数设置的时候，替换Basedao中InterceputCont.METHOD_NAME指定的放
 * */
@Intercepts({@Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class})})
public class ParamIntercept implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        DefaultParameterHandler defaultParameterHandler =(DefaultParameterHandler)invocation.getTarget();
        PreparedStatement preparedStatement =(PreparedStatement)invocation.getArgs()[0];

        //如果不时要拦截的方法不做处理
        MappedStatement mappedStatement =(MappedStatement) MyClassUtil.getFieldByName(defaultParameterHandler,"mappedStatement");
        String statementId =mappedStatement.getId();
        if(!InterceptUtil.isInInterceptMethod(statementId)){
           return invocation.proceed();
        }
        //如果没有继承basedao 不做任何处理
        Class SuperClazz=MyClassUtil.getSuperClass(statementId.substring(0,statementId.lastIndexOf(".")));
        if (!BaseDao.class.getName().equals(SuperClazz.getName())){
            return invocation.proceed();
        }
         // 获取泛型Dao对应实体的表名和列名
         Map<String,Object> tableMap = getTabelNameAndCOlumnByClazz(SuperClazz);





        return null;
    }

    @Override
    public Object plugin(Object arg) {
        return Plugin.wrap(arg, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
    /**
     * 需要查询的对应的表名和列名
     * @param  clazz 参数化baseDao
     * */
    public  Map<String,Object> getTabelNameAndCOlumnByClazz(Class clazz){
        Map<String,Object> map =new HashMap<>();
        Class paramClass = MyClassUtil.getFirstClass(clazz);
        EntityTable entityTable = EntityHelper.getEntityTable(paramClass);
        String tableName=entityTable.getName();
        map.put("tableName",tableName);







        map.put("columns","");
        return  map;
    }

       public  String getColumnStr(Class clazz,EntityTable entityTable){
       Map<String,Object> map = MyClassUtil.getClassBaseFieldAndRefenceField(clazz);

          return null;
       }

        public  String getColumnMapStr(Map<String,Object> map,EntityTable entityTable,String type){
            Set<EntityColumn> set = entityTable.getEntityClassPKColumns();
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            String columnStr="";
                while (iterator.hasNext()){
                    Map.Entry<String, Object> entry =    iterator.next();
                    String key = entry.getKey();
                    if ("serialVersionUID".equals(key)){
                        continue;
                    }
                    Iterator<EntityColumn> columnIterator =  set.iterator();
                    EntityColumn entityColumn =null;
                    while (columnIterator.hasNext()){
                         entityColumn =  columnIterator.next();
                        if (entityColumn.getProperty().equals(key)){
                            break;
                        }
                    }
                    Map<String, Object> valueMap=(Map<String, Object>) entry.getValue();
                     //如果没有关系就是基础类
                     if ("base".equals(valueMap.get("relation").toString()))  {
                         if (type.equals("select")){
                             columnStr +=entityColumn.getColumn()+" as "+entityTable.getName()+"__"+entityColumn.getProperty();
                         }
                     }else if ("oneToMany".equals(valueMap.get("relation").toString())){//如果时一对多


                     }else if ("manyToOne".equals(valueMap.get("relation").toString())){

                     }




                }
            return null;
        }
}
