package com.yhl.oauth2.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauth2.entity.AuthorizedGrantType;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizedGrantTypeDao extends BaseDao<AuthorizedGrantType,String> {
}
