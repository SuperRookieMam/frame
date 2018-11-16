package com.yhl.base.dao;

import com.yhl.base.dto.Params;

import java.util.List;

public interface BaseDao {

    public  <T> T findById(String Id);

    public <T> List<T> findByWhere(Params params);

    public <T> int  SelectCountByWhere(Params params);

    public <T> int save(T object);

    public <T> int update(T object);

    public <T> int deleteByWhere(Params params);

    public <T> List<T> findBysql(String sql,Params params);

    public <T> int findByHql(String sql,Params params);
}
