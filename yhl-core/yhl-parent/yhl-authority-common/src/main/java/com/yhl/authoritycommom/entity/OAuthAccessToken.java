package com.yhl.authoritycommom.entity;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * 在项目中,主要操作oauth_access_token表的对象是JdbcTokenStore.java. 更多的细节请参考该类.
 * */
public interface OAuthAccessToken  extends  OAuth2AccessToken {


    public String getTokenId();

    public void setTokenId(String tokenId) ;

    public String getToken() ;

    public void setToken(String token);

    public String getAuthenticationId();

    public void setAuthenticationId(String authenticationId) ;

    public String getAuthentication() ;

    public void setAuthentication(String authentication);

    public String getClientId() ;

    public void setClientId(String clientId) ;

    public String getUserName() ;

    public void setUserName(String userName) ;

    public void setRefreshToken(String refreshToken) ;
}
