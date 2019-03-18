package com.yhl.base.baseService;

import com.yhl.base.baseEntity.BaseEntity;
import com.yhl.base.component.dto.ResultDto;
import com.yhl.baseorm.component.constant.UpdateFields;
import com.yhl.baseorm.component.constant.WhereCondition;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

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
    public <T> ResultDto insertByList(List<T>  entitys);

    /**
     * 根据实体跟新
     * */
    public<T> ResultDto updateByUpdateFields(UpdateFields updateFields);

    @Transactional(value ="transactionManagerPrimary")
    <T2> ResultDto updateByEntity(T2 entity);

    /**
     * 根据实体跟新
     * */
    public<T> ResultDto updateByUpdateFields(UpdateFields[] updateFieldss,int flusSize);

    public <T> ResultDto updateByWhereCondition(UpdateFields updateFields, WhereCondition whereCondition, int flushSize);
    /**
     * 根据参数自定义查询
     * */
    public <T> ResultDto findByParams(WhereCondition whereCondition);
    /**
     * 根据条件查询条数
     * */
    public ResultDto findCountByParams(WhereCondition whereCondition);
    /**
     * 分页查询
     * */
    public <T> ResultDto findPageByParams(WhereCondition whereCondition);

    public ResultDto deleteById(ID id);

    public ResultDto deleteByWhereCondition(WhereCondition whereCondition);
}
