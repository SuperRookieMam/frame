package com.yhl.authoritycommom.dao;

import com.yhl.authoritycommom.entity.UserInfo;
import com.yhl.base.baseDao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDao extends BaseDao<UserInfo,String> {
}
