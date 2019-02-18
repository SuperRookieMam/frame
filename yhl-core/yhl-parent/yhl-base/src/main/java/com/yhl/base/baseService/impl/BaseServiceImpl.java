package com.yhl.base.baseService.impl;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.base.baseEntity.BaseEntity;
import com.yhl.base.baseService.BaseService;
import com.yhl.base.component.dto.ResultDto;
import com.yhl.baseorm.component.constant.PageInfo;
import com.yhl.baseorm.component.constant.UpdateFields;
import com.yhl.baseorm.component.constant.WhereCondition;
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
    public <T> ResultDto updateByUpdateFields(UpdateFields updateFields) {
        T entity = (T) baseDao.updateByUpdateFields(updateFields);
        return ResultDto.success(entity) ;
    }
    @Override
    @Transactional(value ="transactionManagerPrimary")
    public <T> ResultDto updateByUpdateFields(UpdateFields[] updateFieldss,int flusSize) {
        int number =  baseDao.updateByUpdateFields(updateFieldss,flusSize);
        return ResultDto.success(number) ;
    }
    @Override
    @Transactional(value ="transactionManagerPrimary")
    public <T> ResultDto updateByWhereCondition(UpdateFields updateFields, WhereCondition whereCondition, int flushSize){
        int number =  baseDao.updateByWhereCondition( updateFields,whereCondition, flushSize);
        return ResultDto.success(number) ;
    }
    @Override
    public <T> ResultDto  findByParams(WhereCondition whereCondition) {
       List<T> list = baseDao.findByParams(whereCondition);
        return ResultDto.success(list);
    }

    @Override
    public ResultDto findCountByParams(WhereCondition whereCondition) {
        return ResultDto.success(baseDao.findCountByWhereCondition(whereCondition));
    }

    @Override
    public <T> ResultDto findPageByParams(WhereCondition whereCondition) {
        PageInfo pageInfo=baseDao.findPageByParams(whereCondition);
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
    public ResultDto deleteByWhereCondition(WhereCondition selecteParam) {
        return ResultDto.success(baseDao.deleteByWhereCondition(selecteParam));
    }
}
