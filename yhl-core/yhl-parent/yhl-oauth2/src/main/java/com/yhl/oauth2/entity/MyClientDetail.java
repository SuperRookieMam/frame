package com.yhl.oauth2.entity;

import com.yhl.base.baseEntity.BaseEntity;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 自定义客户端信息
 * */
@Entity
@Table(name ="my_client_detail")
public class MyClientDetail extends BaseEntity<String>implements Persistable<String>, Serializable {

    private static final long serialVersionUID = -6186893015772300645L;

    @Column(name = "client_id", nullable = false, length = 50)
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "name")
    private String name;

    //读写权限
    //@CollectionTable：给出关联表格信息。表格Employee_Phone的外键Employee指向
    // entity对应表格的EmployeeId列。
    //@Column 读取CollectionTable中的scope列信息
    // @OrderColumn：以Priority为排序，
    // 存放到集合（List）中。表格中的priority或是List的index，即0,1,2,...
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "scope")
    //@OrderColumn(name="Priority")  要排序可以这样处理
    @CollectionTable(name = "my_clinet_scop", joinColumns = {
            @JoinColumn(name = "client_id")
    })
    private Set<String> scope;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "grant_type")
    @CollectionTable(name = "authorized_grant_type", joinColumns = {
            @JoinColumn(name = "client_id")
    })
    private Set<String> authorizedGrantTypes;


    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "register_url")
    @CollectionTable(name = "authorized_grant_type", joinColumns = {
            @JoinColumn(name = "client_id")
    })
    private Set<String> registeredRedirectUri;


    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "resource_id_")
    @CollectionTable(name = "client_resource_id", joinColumns = {
            @JoinColumn(name = "client_id")
    })
    private Set<String> resourceIds;


    // token 存货时间
    @Column(name = "access_token_validity_seconds")
    private Integer accessTokenValiditySeconds;
    //token时间
    @Column(name = "refresh_token_validity_seconds")
    private Integer refreshTokenValiditySeconds;

    @Column(name = "auto_approve", nullable = false)
    private boolean autoApprove = false;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Set<String> getScope() {
        return scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public Set<String> getRegisteredRedirectUri() {
        return registeredRedirectUri;
    }

    public void setRegisteredRedirectUri(Set<String> registeredRedirectUri) {
        this.registeredRedirectUri = registeredRedirectUri;
    }

    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public boolean isAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(boolean autoApprove) {
        this.autoApprove = autoApprove;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    @Override
    public String getId() {
        return this.clientId;
    }
}
