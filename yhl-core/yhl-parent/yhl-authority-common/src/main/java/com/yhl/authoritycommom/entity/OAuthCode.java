package com.yhl.authoritycommom.entity;


import java.io.Serializable;

public interface OAuthCode   extends Serializable {

    public String getCode() ;

    public void setCode(String code);

    public String getAuthentication() ;

    public void setAuthentication(String authentication);
}
