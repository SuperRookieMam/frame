package com.yhl.dao.impl;

import com.yhl.dao.BaseDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Component
public class BaseDaoImpl <T extends Serializable>  extends SqlSessionDaoSupport implements BaseDao<T> {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public <T> T findById(String id) {

        return null;
    }

    @Override
    public <T> List<T> findByParams(Map<String,Object> map) {
        return null;
    }

    @Override
    public long findeCountByParam(Map<String,Object> map) {
        return 0;
    }

    @Override
    public <T> List<T> findBySql(String sql) {
        return null;
    }

    @Override
    public long findCountBySql(String sql) {
        return 0;
    }

    @Override
    public <T> int updateByEntity(T entity) {
        return 0;
    }

    @Override
    public <T> int updateByList(List<T> list) {
        return 0;
    }

    @Override
    public int updateBySql(String sql) {
        return 0;
    }

    @Override
    public int deleteById(String id) {
        return 0;
    }

    @Override
    public int deleteByList(List<String> list) {
        return 0;
    }

    @Override
    public int deleteBySql(String sql) {
        return 0;
    }
}
