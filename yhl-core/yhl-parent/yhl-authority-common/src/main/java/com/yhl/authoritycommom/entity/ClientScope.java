package com.yhl.authoritycommom.entity;

import java.io.Serializable;


public interface ClientScope  extends Serializable {

     String getClientId() ;

     void setClientId(String clientId);

     String getScope();

     void setScope(String scope);
}
