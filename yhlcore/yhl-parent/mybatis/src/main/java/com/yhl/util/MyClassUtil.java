package com.yhl.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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


}
