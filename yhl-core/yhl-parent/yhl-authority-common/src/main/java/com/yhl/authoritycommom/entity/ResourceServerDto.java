package com.yhl.authoritycommom.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class ResourceServerDto implements Serializable{

    private static final long serialVersionUID = 2329425171066511234L;
    private  String name;

    private  String registerUrl;

    private  String remark;

    private Integer isUse= 1;
}
