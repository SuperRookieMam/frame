package com.yhl.authoritycommom.entity;

import java.io.Serializable;


public interface ClientRegisteredRedirectUri extends Serializable {


    public String getClientId() ;

    public void setClientId(String clientId);

    public String getRedirectUri() ;

    public void setRedirectUri(String redirectUri);
}
