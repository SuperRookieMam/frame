package com.test.dao;

import com.test.entity.MyTestEntity1;
import com.yhl.base.baseDao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface MyTestEntity1Dao extends BaseDao<MyTestEntity1,String> {
}
