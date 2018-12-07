package com.yhl.util;

import org.mybatis.spring.SqlSessionTemplate;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EntityClassUtil {

    /**
     * 初始化实体
     * */
    public static<T> T initEntity(Map<String,Object> map,Class<T> clazz){
          if (map==null||clazz==null){
              return  null;
            }
        T entity = null;
        try {
            entity = clazz.newInstance();
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()){
                String key =iterator.next();
                String methdName ="set"+firstWordUpCase(key);
                Method method = clazz.getMethod(methdName,map.get(key).getClass());
                method.invoke(entity,map.get(key));
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }
    public static<T> List<T> initEntityList(List<Map<String,Object>> list,Class<T> clazz){
            List<T> list1 =new ArrayList<>();
            if (list==null){
                return list1;
            }
            for (int i = 0; i <list.size() ; i++) {
               T entity= initEntity(list.get(i),clazz);
               list1.add(entity);
            }
        return list1;
    }
    /**
     * 第一个首字母大写
     * */
    public static String firstWordUpCase(String name){
        char[] charArray = name.toCharArray();
        if (charArray[0]>=97&&charArray[0]<=122){
            charArray[0]-=32;
            return  String.valueOf(charArray);
        }
        return name;
    }

    /**
     *根据TKmapper 获取表明字段
     * */
    public  static  String getTableName(SqlSessionTemplate sqlSessionTemplate,Class clazz){
       EntityTable entityTable = EntityHelper.getEntityTable(clazz);

        return "";
    }
}
