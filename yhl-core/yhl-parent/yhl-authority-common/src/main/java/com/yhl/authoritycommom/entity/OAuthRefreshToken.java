package com.yhl.authoritycommom.entity;

import org.springframework.security.oauth2.common.OAuth2RefreshToken;

public interface OAuthRefreshToken  extends  OAuth2RefreshToken {


    public String getValue();

    public String getTokenId();

    public void setTokenId(String tokenId);

    public String getToken() ;

    public void setToken(String token);

    public String getAuthentication() ;

    public void setAuthentication(String authentication) ;
}
