package com.yhl.oauth2.service;

import com.yhl.base.baseService.BaseService;
import com.yhl.oauth2.entity.UserApprovalStore;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;

public interface UserApprovalStoreService extends ApprovalStore,BaseService<UserApprovalStore,String>{

}
