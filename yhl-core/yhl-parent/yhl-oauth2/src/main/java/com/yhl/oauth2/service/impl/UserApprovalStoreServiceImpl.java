package com.yhl.oauth2.service.impl;

import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.oauth2.entity.UserApprovalStore;
import com.yhl.oauth2.service.UserApprovalStoreService;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.stereotype.Service;

import java.util.Collection;

//那个客户段对应用户的权限域的存储
@Service
public class UserApprovalStoreServiceImpl extends BaseServiceImpl<UserApprovalStore,String> implements UserApprovalStoreService {

    @Override
    public boolean addApprovals(Collection<Approval> collection) {
        return false;
    }

    @Override
    public boolean revokeApprovals(Collection<Approval> collection) {
        return false;
    }

    @Override
    public Collection<Approval> getApprovals(String s, String s1) {
        return null;
    }
}
