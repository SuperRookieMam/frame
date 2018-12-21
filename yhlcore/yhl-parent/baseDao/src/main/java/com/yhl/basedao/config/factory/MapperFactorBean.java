package com.yhl.basedao.config.factory;

import org.mybatis.spring.mapper.MapperFactoryBean;

import java.io.Serializable;

public class MapperFactorBean<T,ID extends Serializable> extends MapperFactoryBean<T>  {

    public MapperFactorBean(Class<T> entityClass ){
        super(entityClass);
    }





}
