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
    @Transactional(value ="transactionManagerPrimary")
    public <T> ResultDto updateByselecteParam(UpdateParam updateParams, SelecteParam selecteParam, int flushSize){
        int number =  baseDao.updateByselecteParam( updateParams,selecteParam, flushSize);
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

    @Override
    @Transactional(value ="transactionManagerPrimary")
    public ResultDto deleteById(ID id) {
        baseDao.deleteById(id);
        return ResultDto.success(null);
    }

    @Override
    @Transactional(value ="transactionManagerPrimary")
    public ResultDto deleteBySelectParam(SelecteParam selecteParam) {
        return ResultDto.success(baseDao.deleteBySelectParam(selecteParam));
    }
}
