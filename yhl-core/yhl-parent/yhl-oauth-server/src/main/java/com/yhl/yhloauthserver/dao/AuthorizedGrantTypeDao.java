package com.yhl.yhloauthserver.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.yhloauthserver.entity.AuthorizedGrantType;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizedGrantTypeDao extends BaseDao<AuthorizedGrantType,String> {
}
