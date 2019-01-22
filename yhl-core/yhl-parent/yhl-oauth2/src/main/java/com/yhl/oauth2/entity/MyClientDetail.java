package com.yhl.oauth2.entity;

import com.sun.security.ntlm.Client;
import com.yhl.base.baseEntity.BaseEntity;
import org.springframework.data.domain.Persistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * 自定义客户端信息
 * */
@Entity
@Table(name ="my_user_detail")
public class MyClientDetail extends BaseEntity<String>implements Persistable<String>, Serializable {

    private static final long serialVersionUID = -6186893015772300645L;

    @Column(name = "client_id", nullable = false, length = 50)
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "name")
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "scope_")
    @CollectionTable(name = "dm_client_scope_", joinColumns = {
            @JoinColumn(name = "client_")
    })
    private Set<String> scope;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "authorized_grant_type_")
    @CollectionTable(name = "dm_client_authorized_grant_type_", joinColumns = {
            @JoinColumn(name = "client_")
    })
    private Set<String> authorizedGrantTypes;


    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "registered_redirect_uri_")
    @CollectionTable(name = "dm_client_registered_redirect_uri_", joinColumns = {
            @JoinColumn(name = "client_")
    })
    private Set<String> registeredRedirectUri;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "resource_id_")
    @CollectionTable(name = "dm_client_resource_id_", joinColumns = {
            @JoinColumn(name = "client_")
    })
    private Set<String> resourceIds;

    @Column(name = "access_token_validity_seconds_")
    private Integer accessTokenValiditySeconds;

    @Column(name = "refresh_token_validity_seconds_")
    private Integer refreshTokenValiditySeconds;

    @Column(name = "auto_approve_", nullable = false)
    private boolean autoApprove = false;


    @Override
    public boolean isNew() {
        return false;
    }

    @Override
    public String getId() {
        return this.clientId;
    }
}
