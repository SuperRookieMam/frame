package com.yhl.authoritycommom.entity;

import com.yhl.authoritycommom.componet.util.SerializationUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

 @Getter
 @Setter
public class OAuthAccessTokenDto implements   OAuth2AccessToken {

    private String tokenId;

    private String token;

    private String authenticationId;

    private  String authentication;

    private String clientId;

    private  String userName;

    private  String  refreshToken;

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return new LinkedHashMap<>();
    }

    @Override
    public Set<String> getScope() {
        return null;
    }

    @Override
    public OAuth2RefreshToken getRefreshToken() {
        return StringUtils.isEmpty(refreshToken)?null:SerializationUtils.deserialize(refreshToken);
    }

    @Override
    public String getTokenType() {
        return null;
    }

    @Override
    public boolean isExpired() {
        OAuthAccessTokenDto oAuthAccessTokenDto =   SerializationUtils.deserialize(refreshToken);
        if (ObjectUtils.isEmpty(oAuthAccessTokenDto)){
            return true;
        }
        return oAuthAccessTokenDto.isExpired();
    }

    @Override
    public Date getExpiration() {
        OAuthAccessTokenDto oAuthAccessTokenDto =   SerializationUtils.deserialize(refreshToken);
        if (ObjectUtils.isEmpty(oAuthAccessTokenDto)){
            return new Date();
        }
        return oAuthAccessTokenDto.getExpiration();
    }

    @Override
    public int getExpiresIn() {
        OAuthAccessTokenDto oAuthAccessTokenDto =   SerializationUtils.deserialize(refreshToken);
        if (ObjectUtils.isEmpty(oAuthAccessTokenDto)){
            return (int)(new Date().getTime()/1000);
        }
        return oAuthAccessTokenDto.getExpiresIn();
    }

    @Override
    public String getValue() {
        return tokenId;
    }
}
