package com.yhl.oauth2.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauth2.entity.YhlUserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface YhlUserDetailsDao extends BaseDao<YhlUserDetails,String> {
}
