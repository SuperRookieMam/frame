package com.yhl.authoritycommom.entity;

import org.springframework.security.core.userdetails.UserDetails;


public interface OAthUserDetailes extends UserDetails {
    public String getUserName() ;

    public void setUserName(String userName) ;

    public String getPassWord() ;

    public void setPassWord(String passWord);

    public boolean isExpired() ;

    public void setExpired(boolean expired) ;
    public boolean isLock() ;

    public void setLock(boolean lock) ;

    public String getCredentials() ;

    public void setCredentials(String credentials) ;

    public boolean isEnabled() ;

    public void setEnabled(boolean enabled);
}
