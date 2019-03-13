package com.yhl.authoritycommom.entity;

import org.springframework.security.oauth2.provider.approval.Approval;

import java.io.Serializable;
import java.util.*;

public interface UserApproval  extends  Serializable{

    public String getUserId() ;

    public void setUserId(String userId);

    public String getClientId();

    public void setClientId(String clientId) ;

    public String getScope() ;

    public void setScope(String scope);

    public Approval.ApprovalStatus getStatus();

    public void setStatus(Approval.ApprovalStatus status) ;

    public Date getExpiresAt() ;

    public void setExpiresAt(Date expiresAt) ;

    public Date getLastUpdatedAt();

    public void setLastUpdatedAt(Date lastUpdatedAt);

}
