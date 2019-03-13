package com.yhl.authoritycommom.entity;

import java.io.Serializable;


public interface ResourceServerClient  extends  Serializable {

    public String getClientId() ;

    public void setClientId(String clientId) ;

    public String getResourceId() ;

    public void setResourceId(String resourceId);
}
