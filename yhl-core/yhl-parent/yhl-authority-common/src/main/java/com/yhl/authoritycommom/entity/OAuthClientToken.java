package com.yhl.authoritycommom.entity;

import java.io.Serializable;

public interface OAuthClientToken extends Serializable {

    public String getTokenId() ;

    public void setTokenId(String tokenId) ;

    public String getToken() ;

    public void setToken(String token) ;

    public String getAuthenticationId() ;

    public void setAuthenticationId(String authenticationId);

    public String getUserName() ;

    public void setUserName(String userName) ;

    public String getClientId() ;
    public void setClientId(String clientId) ;
}
