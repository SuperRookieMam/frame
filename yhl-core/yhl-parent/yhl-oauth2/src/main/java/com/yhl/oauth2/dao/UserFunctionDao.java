package com.yhl.oauth2.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauth2.entity.UserFunction;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFunctionDao extends BaseDao<UserFunction,String> {
}
