package com.yhl.base.service.impl;

import com.yhl.base.dao.BaseDao;
import com.yhl.base.dto.ResultDto;
import com.yhl.base.service.BaseService;
import com.yhl.baseOrm.constant.PageInfo;
import com.yhl.baseOrm.constant.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
@Service
public class BaseServiceImpl<T,ID extends Serializable> implements BaseService<T, ID> {
    
    @Autowired
    BaseDao<T,ID> baseDao;
    
    @Override
    public <T> ResultDto findById(ID id) {
        T entity= (T) baseDao.findById(id);
        return ResultDto.success(entity);
    }

    @Override
    public <T> ResultDto  findByParams(Params params) {
       List<T> list = baseDao.findByParams(params);
        return ResultDto.success(list);
    }

    @Override
    public ResultDto findCountByParams(Params params) {
        return ResultDto.success(baseDao.findCountByParams(params));
    }

    @Override
    public <T> ResultDto findPageByParams(Params params) {
        PageInfo pageInfo=baseDao.findPageByParams(params);
        return ResultDto.success(pageInfo);
    }

    @Override
    public <T1> ResultDto findByHql(String hql, Class<T1> clazz) {
        List<T1> list= baseDao. findBysql(hql,clazz);
        return ResultDto.success(list);
    }

    @Override
    public <T1> ResultDto findBysql(String sql, Class<T1> clazz) {
        List<T1> list =baseDao.findBysql(sql,clazz);
        return ResultDto.success(list);
    }

    @Override
    public <T> ResultDto insertByEntity(T entity) {
        entity=(T)baseDao.insertByEntity(entity);
        return ResultDto.success(entity);
    }

    @Override
    public <T> ResultDto insertByList(T[] entitys) {
        return ResultDto.success(baseDao.insertByList(entitys));
    }

    @Override
    public <T> ResultDto updateByEntity(T entity) {
       entity = (T) baseDao.updateByEntity(entity);
        return ResultDto.success(entity) ;
    }

    @Override
    public ResultDto updateByHql(String hql) {
        return ResultDto.success(baseDao.updateByHql(hql));
    }

    @Override
    public ResultDto updateBysql(String sql) {
        return ResultDto.success(baseDao.updateBysql(sql));
    }

    @Override
    public<T> ResultDto deleteByEntity(T entity) {
        baseDao.deleteByEntity(entity);
        return  ResultDto.success(null);
    }

    @Override
    public ResultDto deleteByIds(ID[] ids) {
        return ResultDto.success(baseDao.deleteByIds(ids,"id"));
    }
}
