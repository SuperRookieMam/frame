package com.yhl.base.baseService;

import com.yhl.base.baseEntity.BaseEntity;
import com.yhl.base.component.dto.ResultDto;

import java.io.Serializable;

public interface BaseService<T extends BaseEntity<ID>,ID extends Serializable> {

    /**
     * 自定义接口
     * */
    public <T> ResultDto findById(ID id);
    /**
     * 根据一个实体插入
     * */
    public <T> ResultDto insertByEntity(T entity);

    /**
     * 批量插入
     * */
    public <T> ResultDto insertByList(T[] entitys);


    /**
     * 根据参数自定义查询
     * *//*
    public <T> ResultDto findByParams(Params params);
    *//**
     * 根据条件查询条数
     * *//*
    public ResultDto findCountByParams(Params params);
    *//**
     * 分页查询
     * *//*
    public <T> ResultDto findPageByParams(Params params);
    *//**
     *接受类型不顶 ，但是又不能用泛型，到时强转吧
     * *//*
    public<T1> ResultDto findByHql(String hql, Class<T1> clazz);
    *//**
     *接受类型不顶 ，但是又不能用泛型，到时强转吧
     * *//*
    public<T1>  ResultDto  findBysql(String sql, Class<T1> clazz);

    *//**
     * 根据实体跟新
     * *//*
    public<T> ResultDto updateByEntity(T entity);
    *//**
     *根据Hql跟新
     * *//*
    public ResultDto updateByHql(String Hql);
    *//**
     *根据sql跟新
     * *//*
    public ResultDto updateBysql(String sql);

    public<T> ResultDto deleteByEntity(T entity);

    public ResultDto deleteByIds(ID[] ids);*/
}
