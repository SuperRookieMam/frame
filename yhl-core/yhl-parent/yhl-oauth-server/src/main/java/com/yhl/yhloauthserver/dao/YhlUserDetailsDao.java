package com.yhl.yhloauthserver.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.yhloauthserver.entity.YhlUserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface YhlUserDetailsDao extends BaseDao<YhlUserDetails,String> {
}
