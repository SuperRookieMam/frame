package com.yhl.util;

import tk.mybatis.mapper.entity.EntityTable;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MyClassUtil<T> {

    /**
     * 获取参数化的泛型的第一个参数类型
     * */
    public static  Class  getFirstClass(Class clazz){
        return getSuperClassGenricType(clazz,0);
    }

    /**
    * 通过反射获取定义class时申明父类的单行的类型
    * */
   public static  Class getSuperClassGenricType(Class clazz, int index){
       //得到泛型父类
       Type genType =clazz.getGenericSuperclass();

       //判断泛型是否参数化
       //如果没有参数话不支持泛型，直接返回obj
       if (!(genType instanceof ParameterizedType)){
            return  Object.class;
       }
       Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >=params.length||index<0){
            throw  new  RuntimeException("你输入的索引数组越界了,数组长度:"+params.length+",你的索引值");
        }
        if (!(params[index] instanceof  Class)){
            return Object.class;
        }
       return (Class) params[index];
   }
    /**
     * 判断一个类是自定义类
     * @return  true 自定义类，false java核心类
     * */
   public  static  boolean   isCustomClass(Class clazz){
        //如果时java核心类clazz.getClassLoader()==null,类加载器的基础知识，自己取了解
        return clazz!=null&&clazz.getClassLoader()!=null;
   }
   /**
    * 根据字段名反射获取字段
    * @param  object 实例;
    * @param  name 字段名
    * @return  返回属性值
    * */
   public  static  Object getFieldByName(Object object,String name ){
       Class clazz =object.getClass();
       Object object1=null;
       try {
           Field field = clazz.getDeclaredField(name);
           field.setAccessible(true);
           object1 = field.get(object);
           field.setAccessible(false);
       } catch (Exception e) {
           e.printStackTrace();
       }
       return  object1;
   }
    /**
     * 根据字段名反射获取字段
     * @param  object 实例;
     * @param  name 字段名
     * @param  value 要设置的字段值
     * @return  返回属性值
     * */
    public  static  void setFieldByName(Object object,String name,Object value ){
        Class clazz =object.getClass();
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            field.set(object,value);
            field.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  Class getClassForName(String name){
       Class clazz=null;
       try {
           clazz = Class.forName(name);
       }catch (Exception e){
           e.printStackTrace();
       }
        return  clazz;
    }
    /**
     *获取父类
     * */
    public static  Class getSuperClass(String className){
        Class superClazz=null;
        Class clazz =  getClassForName(className);
        superClazz=  clazz.getSuperclass();
        return  superClazz;
    }

    /**
     * 获取一个类的所有字段：按照对应关系分
     * 基本字段，8大基础类，8大封装类
     * 一对多
     * 多对一
     * 此方法用于查询，所以如果时自定义类，
     * 没有添加对应关系的不返回
     * @param clazz
     * return Map<字段名字,字段属性>
     * */
    public  static Map<String,Map<String,Object>> getClassBaseFieldAndRefenceField(Class clazz, EntityTable entityTable){
//        //一个class对应一个map
//        Map<String, MyEntityColumn> objMap=new HashMap<>();
//        Field[] fields = clazz.getDeclaredFields();
//            for (int i = 0; i < fields.length; i++) {
//                Field field =fields[i];
//                Class fieldClazz = field.getType();
//                MyEntityColumn myEntityColumn =new MyEntityColumn();
//                myEntityColumn.setJavaType(fieldClazz);
//                myEntityColumn.setProperty(field.getName());
//                Set<EntityColumn> columns= entityTable.getEntityClassColumns();
//
//                //如果是用户自定义类，则要么时一对多，要么时多对多，要么时
//                if (isCustomClass(fieldClazz)){
//                    if (field.getAnnotation(ManyToOne.class)!=null){
//                        myEntityColumn.setRalation("manyToOne");
//                    }
//                    if (field.getAnnotation(Transient.class)!=null){
//                        myEntityColumn.setTransient(true);
//                    }
//
//
//                }
//
//
//
//                //一个fiel 对应一个map
//                Map<String,Object> map=new HashMap<>();
//               //如果时用户自定义的类，存在的对应关系有
//                Annotation[] annotations = field.getAnnotations();
//               if (isCustomClass(fieldClazz)){
//                   int m =0;
//                   for (int j = 0; j <annotations.length ; j++) {
//                        Class clzz = annotations[i].annotationType();
//                        //因为自定义的类多对一,一对一才是己引用自定义类,不用判断一对多
//                        if (clzz.isAssignableFrom(ManyToOne.class)){
//                            m=1;
//                            break;
//                        }
//                   }
//                   field.getAnnotation(ManyToOne.class);
//
//                   //如果存在多对一的关系，继续解析
//                   if (m==1){
//                       map.put("fieldClass",fieldClazz);
//                       map.put("relation","manyToOne");
//                       //防止循环引用递归死循环，如果已被解析过的不在解析
//                       if (!keyMap.containsKey(fieldClazz)){
//                           Map<String,Object>  fieldMap =  getClassBaseFieldAndRefenceField(fieldClazz,keyMap);
//                           map.put("refMsg",fieldMap);
//                           keyMap.put(fieldClazz,"");
//                       }
//                       objMap.put(field.getName(),map);
//                   }
//               }else {//如果时系统核心累
//                   int m =0;
//                   for (int j = 0; j <annotations.length ; j++) {
//                       Class clzz = annotations[i].annotationType();
//                       //因为自定义的类多对一,一对一才是己引用自定义类,不用判断一对多
//                       if (clzz.isAssignableFrom(OneToMany.class)){
//                           m=1;
//                           break;
//                       }
//                   }
//                     //如果时一对多的怎进入
//                     if (m==1){
//                         if (fieldClazz.isAssignableFrom(List.class)||fieldClazz.isAssignableFrom(Set.class)){
//                             Class paramClass = getSuperClassGenricType(fieldClazz, 0);
//                             map.put("relation","oneToMany");
//                             map.put("fieldClass",fieldClazz);
//                             map.put("paramClass",paramClass);
//
//                             if (isCustomClass(paramClass)&&!keyMap.containsKey(fieldClazz)){
//                                    Map<String,Object>  fieldMap =  getClassBaseFieldAndRefenceField(fieldClazz,keyMap);
//                                    map.put("refMsg",fieldMap);
//                                    keyMap.put(fieldClazz,"");
//                             }
//                         }
//                     }else {//如果没有多对一，则说明时java核心类，则说明类型是几大基本类型或或者包装类，
//                         map.put("relation","base");
//                         map.put("fieldClass",fieldClazz);
//                     }
//                   objMap.put(field.getName(),map);
//                }
//            }
     return null;
    }

    public  static Map<String,Object> getClassBaseFieldAndRefenceField(Class clazz){
        //一个class对应一个map
        Map<Class,Object> keyMap=new HashMap<>();
        return null;
    }

}
