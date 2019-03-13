package com.yhl.authoritycommom.entity;

import java.io.Serializable;

public interface ResourceServer  extends Serializable{

    public String getName() ;

    public void setName(String name) ;

    public String getRegisterUrl() ;

    public void setRegisterUrl(String registerUrl);

    public String getRemark() ;

    public void setRemark(String remark) ;

    public Integer getIsUse() ;
    public void setIsUse(Integer isUse) ;
}
