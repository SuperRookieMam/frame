package com.yhl.oauth2.entity;

import com.sun.security.ntlm.Client;
import com.yhl.base.baseEntity.BaseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * 自定义客户信息
 * */
@Entity
@Table(name ="my_user_detail")
public class MyUserDetail extends BaseEntity<String> implements UserDetails, Serializable {


    private static final long serialVersionUID = -6186893015772300645L;

    @Column(name = "client_id", nullable = false, length = 50)
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "name")
    private String name;

    //详情请参照说明书
    @ElementCollection(fetch  = FetchType.EAGER)
    @Column(name = "scope")
    @CollectionTable(name = "my_user_detail", joinColumns = {
            @JoinColumn(name = "id")
    })
    private Set<String> scope;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
