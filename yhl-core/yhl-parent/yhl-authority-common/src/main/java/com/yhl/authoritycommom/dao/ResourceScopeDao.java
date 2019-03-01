package com.yhl.authoritycommom.dao;

import com.yhl.authoritycommom.entity.Resource;
import com.yhl.base.baseDao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceScopeDao extends BaseDao<Resource,String> {
}
