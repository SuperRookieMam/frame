package com.yhl.yhlsecuritycommon.service.Impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 如果资源服务器和Oauth不在一个服务器上 就使用远程的来获取
 * */
public class RemoteTokenStoreResourceServerTokenServices implements ResourceServerTokenServices, InitializingBean {

    private TokenStore tokenStore = null;

    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }
    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        return null;
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        return null;
    }
}
