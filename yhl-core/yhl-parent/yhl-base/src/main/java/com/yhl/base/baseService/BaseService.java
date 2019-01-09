package com.yhl.base.baseService;

import com.yhl.base.baseEntity.BaseEntity;
import com.yhl.base.component.dto.ResultDto;
import com.yhl.baseorm.component.constant.SelecteParam;
import com.yhl.baseorm.component.constant.UpdateParam;

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
    public<T> ResultDto updateByUpdateParam(UpdateParam updateParams);
    /**
     * 根据实体跟新
     * */
    public<T> ResultDto updateByUpdateParams(UpdateParam[] updateParams,int flusSize);

    public <T> ResultDto updateByselecteParam(UpdateParam updateParams, SelecteParam selecteParam, int flushSize);
    /**
     * 根据参数自定义查询
     * */
    public <T> ResultDto findByParams(SelecteParam selecteParam);
    /**
     * 根据条件查询条数
     * */
    public ResultDto findCountByParams(SelecteParam selecteParam);
    /**
     * 分页查询
     * */
    public <T> ResultDto findPageByParams(SelecteParam selecteParam);

    public ResultDto deleteById(ID id);

    public ResultDto deleteBySelectParam(SelecteParam selecteParam);
}
