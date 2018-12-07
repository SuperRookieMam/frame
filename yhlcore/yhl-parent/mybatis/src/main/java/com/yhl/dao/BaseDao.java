package com.yhl.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao<T extends Serializable> {

    /**
     * 根据id查询实体
     * @return  T 实体
     * */
    public <T> T findById(Map<String,Object> map);

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
    public  int updateByEntity(Map<String,Object> map);

    /**
     * 批量跟新
     * */
    public  int updateByList(Map<String,Object> map);

    /**
     * 根据sql跟新
     * */
    public int updateBySql(String sql);


    /**
     *根据id删除
     * */
    public  int deleteById(Map<String,Object> map);

    /**
     * 根据ids 删除
     * */
    public int deleteByList(Map<String,Object> map);

    /**
     * 根据sql删除
     * */
    public  int deleteBySql(String sql);
}
