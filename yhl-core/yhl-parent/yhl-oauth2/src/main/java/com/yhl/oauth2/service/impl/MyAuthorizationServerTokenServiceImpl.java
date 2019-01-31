package com.yhl.oauth2.service.impl;

import com.yhl.oauth2.service.MyAuthorizationServerTokenService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.stereotype.Service;
/*
 *对token的刷新创建和获取
 * 如果使用这个的话就不用使用ownerdefaultToken，
 * 这时自定义token的增删改查实现的时候，参考ownerdefaultToken，
 * */
@Service
public class MyAuthorizationServerTokenServiceImpl implements MyAuthorizationServerTokenService {


    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication oAuth2Authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String s, TokenRequest tokenRequest) throws AuthenticationException {
        return null;
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication oAuth2Authentication) {
        return null;
    }
}
