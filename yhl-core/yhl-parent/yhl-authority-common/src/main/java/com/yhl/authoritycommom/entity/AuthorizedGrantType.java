package com.yhl.authoritycommom.entity;

import java.io.Serializable;

public interface AuthorizedGrantType extends Serializable {

    public String getClientId() ;

    public void setClientId(String clientId);

    public String getGrantType();

    public void setGrantType(String grantType);
}
