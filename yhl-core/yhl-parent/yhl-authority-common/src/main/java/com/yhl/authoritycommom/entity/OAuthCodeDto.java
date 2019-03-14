package com.yhl.authoritycommom.entity;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OAuthCodeDto implements Serializable {

    private  String code;

    private String authentication;
}
