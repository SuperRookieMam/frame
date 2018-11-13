package com.yhl.base.dao;

public interface BaseDao {

    public  <T> T findById(String Id);

    public <T>  T findByWhere();

}
