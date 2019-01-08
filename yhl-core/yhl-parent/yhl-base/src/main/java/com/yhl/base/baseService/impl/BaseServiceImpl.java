package com.yhl.base.baseService.impl;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.base.baseEntity.BaseEntity;
import com.yhl.base.baseService.BaseService;
import com.yhl.base.component.dto.ResultDto;
import com.yhl.baseorm.component.constant.PageInfo;
import com.yhl.baseorm.component.constant.SelecteParam;
import com.yhl.baseorm.component.constant.UpdateParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public class BaseServiceImpl<T extends BaseEntity<ID>,ID extends Serializable> implements BaseService<T, ID> {
    
    @Autowired
    BaseDao<T,ID> baseDao;
    
    @Override
    public <T> ResultDto findById(ID id) {
        T entity= (T) baseDao.findById(id);
        return ResultDto.success(entity);
    }
    @Override
    public <T> ResultDto insertByEntity(T entity) {
        entity=(T)baseDao.insertByEntity(entity);
        baseDao.flush();
        return ResultDto.success(entity);
    }

    @Override
    @Transactional(value ="transactionManagerPrimary")
    public <T> ResultDto insertByList(List<T>  entitys) {
        return ResultDto.success(baseDao.insertByList(entitys));
    }

    @Override
    @Transactional(value ="transactionManagerPrimary")
    public <T> ResultDto updateByUpdateParam(UpdateParam updateParams) {
        T entity = (T) baseDao.updateByUpdateParam(updateParams);
        return ResultDto.success(entity) ;
    }
    @Override
    @Transactional(value ="transactionManagerPrimary")
    public <T> ResultDto updateByUpdateParams(UpdateParam[] updateParams,int flusSize) {
        int number =  baseDao.updateByUpdateParams(updateParams,flusSize);
        return ResultDto.success(number) ;
    }

    @Override
    public <T> ResultDto  findByParams(SelecteParam selecteParam) {
       List<T> list = baseDao.findByParams(selecteParam);
        return ResultDto.success(list);
    }

    @Override
    public ResultDto findCountByParams(SelecteParam selecteParam) {
        return ResultDto.success(baseDao.findCountByParams(selecteParam));
    }

    @Override
    public <T> ResultDto findPageByParams(SelecteParam selecteParam) {
        PageInfo pageInfo=baseDao.findPageByParams(selecteParam);
        return ResultDto.success(pageInfo);
    }

    /*
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
*/}
