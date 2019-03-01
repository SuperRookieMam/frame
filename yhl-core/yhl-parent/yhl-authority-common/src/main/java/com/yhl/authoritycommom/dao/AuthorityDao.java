package com.yhl.authoritycommom.dao;

import com.yhl.authoritycommom.entity.Authority;
import com.yhl.base.baseDao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityDao extends BaseDao<Authority,String> {
}
