package com.yhl.oauth2.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauth2.entity.Organization;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationDao extends BaseDao<Organization,String> {
}
