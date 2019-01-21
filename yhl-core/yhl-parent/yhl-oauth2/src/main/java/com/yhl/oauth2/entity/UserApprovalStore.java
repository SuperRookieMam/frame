package com.yhl.oauth2.entity;

import com.yhl.base.baseEntity.BaseEntity;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.oauth2.provider.approval.Approval;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * 自定义token的存储方式
 * oauth2实现的有：
 * InMemoryApprovalStore: 基于内存存储
 * TokenApprovalStore:基于token实现，将token存储在token_acess_token表中
 * 一般的只有使用TokenStoreUserApprovalHandeler 才会使用此方式
 * JdbcApprovalStore:基于scope实现，将token存储子在oauth_proval表中
 * 一般的只有使用provalStoreUserApprovalHandeler 才会使用此方式
 * */
@Entity
@Table(name = "user_approval_store")
public class UserApprovalStore extends BaseEntity<String> implements Serializable {
   private static final long serialVersionUID = 138296080858479529L;
    @Column(name = "user_id")
    private String userId;


    @Column(name = "client_id")
    private String clientId;


    @Column(name = "scope")
    private String scope;


    @Column(name = "status")
    @Enumerated(EnumType.STRING) //枚举类型？
    private Approval.ApprovalStatus status;

    @Column(name = "expires_at")
    private Date expiresAt;

    @Column(name = "last_update_at")
    @LastModifiedDate
    private ZonedDateTime lastUpdatedAt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Approval.ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(Approval.ApprovalStatus status) {
        this.status = status;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public ZonedDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(ZonedDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
