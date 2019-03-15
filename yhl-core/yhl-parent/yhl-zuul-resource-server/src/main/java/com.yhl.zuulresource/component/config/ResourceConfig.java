package com.yhl.zuulresource.component.config;


import com.yhl.yhlsecuritycommon.componet.access.RequestAuthoritiesAccessDecisionVoter;
import com.yhl.yhlsecuritycommon.componet.access.RequestAuthoritiesFilterInvocationSecurityMetadataSource;
import com.yhl.yhlsecuritycommon.service.Impl.RemoteTokenStoreResourceServerTokenServices;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;


@EnableResourceServer
@EnableWebSecurity
public class ResourceConfig extends ResourceServerConfigurerAdapter  {

    /**
     * 配置对资源的保护模式
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 指定所有的资源都要被保护
        super.configure(http);
        // 增加自定义的资源授权过滤器
        http.addFilterBefore(interceptor(), FilterSecurityInterceptor.class);
        //自定义的请求规则
        http.requestMatcher(new BearerTokenRequestMatcher());
    }
    @Bean
    public FilterSecurityInterceptor interceptor() {
        FilterSecurityInterceptor interceptor = new FilterSecurityInterceptor();
        //自定一个投票系统
        List<AccessDecisionVoter<?>> voters = Collections.singletonList(new RequestAuthoritiesAccessDecisionVoter());
        AccessDecisionManager accessDecisionManager = new AffirmativeBased(voters);
        interceptor.setAccessDecisionManager(accessDecisionManager);
        interceptor.setSecurityMetadataSource(securityMetadataSource());
        return interceptor;
    }
    @Bean
    public FilterInvocationSecurityMetadataSource securityMetadataSource() {
        return new RequestAuthoritiesFilterInvocationSecurityMetadataSource();
    }

    //判断是不是来自资源服务器的请求,因为资源服务器可能会增加某个属性
    static class BearerTokenRequestMatcher implements RequestMatcher {
        //请求里面的header里面要有这个属性
        @Override
        public boolean matches(HttpServletRequest request) {
            return matchHeader(request) || matchParameter(request);
        }

        private boolean matchHeader(HttpServletRequest request) {
            String authHeader = request.getHeader("Authorization");
            return StringUtils.startsWithIgnoreCase(authHeader, OAuth2AccessToken.BEARER_TYPE);
        }
        private boolean matchParameter(HttpServletRequest request) {
            return !StringUtils.isEmpty(request.getParameter(OAuth2AccessToken.ACCESS_TOKEN));
        }
    }

    /**使用它来配置安全资源的访问规则。默认情况下，“/oauth/**”中的所有资源<i>not</i>
      *是受保护的(例如，没有给出关于作用域的特定规则)。你还会得到
     * */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        // 指定这是一个restful service,不会保存会话状态
        resources.resourceId("ZUUL");
        resources.stateless(true);

        // 这里指定通过token store来校验token
        // 当第三方服务通过access_token来访问服务时，直接从token_store中获取相关信息，而不用再发起远程调用请求
        resources.tokenServices(tokenStoreResourceServerTokenServices());
    }
    /**
     * @return
     */
    public ResourceServerTokenServices tokenStoreResourceServerTokenServices() {
       RemoteTokenStoreResourceServerTokenServices tokenService = new RemoteTokenStoreResourceServerTokenServices();
       //tokenService.setTokenStore(tokenStore());
        return  tokenService;
    }
  /*  *//**
     * 其实这个就是一个Dao层
     * *//*
    @Bean
    public TokenStore tokenStore() {
        //自定义的一个tokenstore 实现自定义的token管理
        return  new SecurityTokenStore();
    }*/


}
