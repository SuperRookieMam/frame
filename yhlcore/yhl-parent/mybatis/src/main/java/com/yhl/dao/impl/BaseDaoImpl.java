package com.yhl.dao.impl;

import com.yhl.dao.BaseDao;
import com.yhl.util.EntityClassUtil;
import com.yhl.util.MyClassUtil;
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
    private  SqlSessionTemplate sqlSessionTemplate;

    private  String nameSpace="com.yhl.dao.BaseMapper";
    private  String[] nameFunction={".selectListByWhere"};

    @Override
    public <T> T findById(Map<String,Object> map) {

        return null;
    }

    @Override
    public <T> List<T> findByParams(Map<String,Object> map) {
        List<Map<String,Object>> list =  sqlSessionTemplate.selectList(nameSpace+nameFunction[0],map);
        Class clazz = MyClassUtil.getFirstClass(this.getClass());
        return EntityClassUtil.initEntityList(list,clazz);
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
    public int updateByEntity(Map<String,Object> map) {
        return 0;
    }

    @Override
    public  int updateByList(Map<String,Object> map) {
        return 0;
    }

    @Override
    public int updateBySql(String sql) {
        return 0;
    }

    @Override
    public int deleteById(Map<String,Object> map) {
        return 0;
    }

    @Override
    public int deleteByList(Map<String,Object> map) {
        return 0;
    }

    @Override
    public int deleteBySql(String sql) {
        return 0;
    }
}
