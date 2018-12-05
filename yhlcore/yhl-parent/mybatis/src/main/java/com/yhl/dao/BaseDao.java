package com.yhl.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao<T extends Serializable> {

    /**
     * 根据id查询实体
     * @param id
     * @return  T 实体
     * */
    public <T> T findById(String id);

    /**
     * 根据条件查询实体
     * */
    public <T> List<T> findByParams(Map<String,Object> map);

    /**
     * 查询条数
     * */
    public long findeCountByParam(Map<String,Object> map);

    /**
     * 根据sql查询
     * */
    public <T> List<T>  findBySql(String sql);

    /**
     * 根据sql 查询条数
     * */
    public long findCountBySql(String sql);

    /**
     * 根据实体跟新
     * */
    public <T> int updateByEntity(T entity);

    /**
     * 批量跟新
     * */
    public  <T> int updateByList(List<T> list);

    /**
     * 根据sql跟新
     * */
    public int updateBySql(String sql);


    /**
     *根据id删除
     * */
    public  int deleteById(String id);

    /**
     * 根据ids 删除
     * */
    public int deleteByList(List<String> list);

    /**
     * 根据sql删除
     * */
    public  int deleteBySql(String sql);
}
