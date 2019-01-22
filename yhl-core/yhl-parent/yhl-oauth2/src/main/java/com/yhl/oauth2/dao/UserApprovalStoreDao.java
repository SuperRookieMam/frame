package com.yhl.oauth2.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauth2.entity.UserApprovalStore;
import org.springframework.stereotype.Repository;

@Repository
public interface UserApprovalStoreDao extends BaseDao<UserApprovalStore,String> {
}
