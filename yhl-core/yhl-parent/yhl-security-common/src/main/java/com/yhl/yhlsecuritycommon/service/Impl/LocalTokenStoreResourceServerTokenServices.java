package com.yhl.yhlsecuritycommon.service.Impl;

import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;
/**
 * 如果资源服务器和Oaut服务器在一个项目就用这个来获取Token
 * */
@Setter
public class LocalTokenStoreResourceServerTokenServices implements ResourceServerTokenServices, InitializingBean {

    private TokenStore tokenStore = null;

    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(tokenStore, "tokenStore must be set");
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
