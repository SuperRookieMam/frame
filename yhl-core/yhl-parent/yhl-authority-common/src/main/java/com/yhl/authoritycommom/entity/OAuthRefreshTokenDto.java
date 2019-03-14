package com.yhl.authoritycommom.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

@Getter
@Setter
public class OAuthRefreshTokenDto implements   OAuth2RefreshToken {


    private String tokenId;

    private String token;


    private String authentication;

    @Override
    public String getValue() {
        return tokenId;
    }
}
