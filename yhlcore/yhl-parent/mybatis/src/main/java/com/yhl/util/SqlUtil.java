package com.yhl.util;

import com.alibaba.fastjson.JSONObject;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SqlUtil {
    private  static final JSONObject jsonObject=new JSONObject();

    /**
     * 根据实体和参数Map动态拼接Sql
     * 请使用Tkmapper,因为只有TKmapper EntityHelper
     * 里面的entitytable才是初始化的时候已经加载好
     * **/
    public  static <T> String getSingleSelectSql (Class<T> entityClass, Map<String,Object> map){
        StringBuffer sqlBuffer =new StringBuffer("SELECT ");
        EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        Iterator<EntityColumn> iterator = entityTable.getEntityClassPKColumns().iterator();
         while (iterator.hasNext()){
            EntityColumn entityColumn =iterator.next();
            sqlBuffer.append(entityColumn.getColumn()+" AS "+entityColumn.getProperty());
        }
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(entityTable.getName());

        return  null;
    }
    /**
     *
     * */
    public  static  String  getSqlCondition(Map<String,Object> map){
         StringBuffer stringBuffer =new StringBuffer();
         JSONObject jsonObject =getJsonObject();
         Set<String> set = jsonObject.keySet();
         Iterator<String> iterator=set.iterator();
             while (iterator.hasNext()){
                 String key =iterator.next();
                 Map<String,Object> map1 =(Map<String,Object>) map.get(key);


             }







            // 便利条件map
            /*
            Iterator<Map.Entry<String, Object>> iterator =  map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Object>  entry = iterator.next();
                String key = entry.getKey();
                Object obj =entry.getValue();
                //
                if (jsonObject.containsKey(key)){
                    String keyValue =jsonObject.getString(key);
                     if (obj instanceof  Map){
                         Map tempMap = (Map) obj;
                         Iterator iterator1 = tempMap.keySet().iterator();
                            while (iterator1.hasNext()){
                                String key1=iterator1.next().toString();




                            }


                     }

                 }
            }*/

        return  null;
    }

    public static JSONObject getJsonObject(){
        if (jsonObject.isEmpty()){
            jsonObject.put("likeMap","like");
            jsonObject.put("notLikeMap","not like");
            jsonObject.put("gtMap","&gt;");
            jsonObject.put("ltMap","&lt;");
            jsonObject.put("leMap","&lt;=");
            jsonObject.put("geMap","&gt;=");
            jsonObject.put("eqMap","=");
            jsonObject.put("notEqMap","!=");
            jsonObject.put("inMap","in");
            jsonObject.put("notInMap","not in");
            jsonObject.put("orMap","or");
        }
        return jsonObject;
    }


}
