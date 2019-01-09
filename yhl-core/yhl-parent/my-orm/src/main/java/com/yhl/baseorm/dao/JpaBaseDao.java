package com.yhl.baseorm.dao;


import com.yhl.baseorm.component.constant.PageInfo;
import com.yhl.baseorm.component.constant.SelecteParam;
import com.yhl.baseorm.component.constant.UpdateParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 *继承勒JpaRepository的接口，findN那些所有方法，
 *JpaSpecificationExecutor 的执行器所有方法
 * */
@NoRepositoryBean
public interface JpaBaseDao<T,ID extends Serializable>  extends JpaRepository<T,ID>, JpaSpecificationExecutor<T> {

    /**
     * 自定义接口
     * */
    public <T> T findById(ID id);

    public <T> int updateByselecteParam(UpdateParam updateParams, SelecteParam selecteParam, int flushSize);

    /**
     * 根据参数自定义查询
     * */
    public <T> List<T> findByParams(SelecteParam selecteParam);

    /**
     * 根据一个实体插入
     * */
    public <T> T insertByEntity(T entity);
    /**
     * 批量插入
     * */
    public <T> int insertByList(List<T>  entitys);
    /**
     * 根据实体跟新
     * */
    public<T> T updateByUpdateParam(UpdateParam updateParams);


    /**
     * 根据实体跟新
     * @param  updateParams 要跟新的字段值
     * @param  flushSize 多少条刷新一次
     * */
    public<T> int updateByUpdateParams(UpdateParam[] updateParams,int flushSize);
    /**
     * 根据条件查询条数
     * */
    public long findCountByParams(SelecteParam selecteParam);


    public <T> PageInfo<T> findPageByParams(SelecteParam selecteParam);


    public void deleteById(ID id);

    public int deleteBySelectParam(SelecteParam selecteParam);

    /**
     * JpaRepository的接口
     * List<T> findAll();
     * List<T> findAll(Sort var1);
     * List<T> findAll(Iterable<ID> var1);
     * <S extends T> List<S> save(Iterable<S> var1);
     * void flush();
     * T saveAndFlush(T var1);
     * void deleteInBatch(Iterable<T> var1);
     * void deleteAllInBatch();
     * T getOne(ID var1);
     * */
    /**
     * JpaSpecificationExecutor 的接口
     * T findOne(Specification<T> var1);
     * List<T> findAll(Specification<T> var1);
     * Page<T> findAll(Specification<T> var1, Pageable var2);
     * List<T> findAll(Specification<T> var1, Sort var2);
     * int count(Specification<T> var1);
     * */
}
