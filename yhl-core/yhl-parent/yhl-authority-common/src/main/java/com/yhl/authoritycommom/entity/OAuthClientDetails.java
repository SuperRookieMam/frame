package com.yhl.authoritycommom.entity;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface OAuthClientDetails extends   ClientDetails {


    public String getClientId() ;

    public void setClientId(String clientId) ;

    public Set<String> getResourceIds() ;

    public void setResourceIds(Set<String> resourceIds) ;

    public String getClientSecret() ;

    public void setClientSecret(String clientSecret);
    public Set<String> getScopes();

    public void setScopes(Set<String> scopes);

    public Set<String> getAuthorizedGrantTypes() ;

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) ;
    @Override
    public Set<String> getRegisteredRedirectUri();

    public void setRegisteredRedirectUri(Set<String> registeredRedirectUri) ;

    public Collection<GrantedAuthority> getAuthorities() ;

    public void setAuthorities(List<GrantedAuthority> authorities) ;


    public Integer getAccessTokenValiditySeconds();

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) ;


    public Integer getRefreshTokenValiditySeconds();

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) ;
    public Map<String, Object> getAdditionalInformation() ;
    public void setAdditionalInformation(Map<String, Object> additionalInformation);

    public boolean isAutoApprove();

    public void setAutoApprove(boolean autoApprove) ;

    public Integer getArchived() ;

    public void setArchived(Integer archived);

    public Integer getTrusted();

    public void setTrusted(Integer trusted) ;
}
