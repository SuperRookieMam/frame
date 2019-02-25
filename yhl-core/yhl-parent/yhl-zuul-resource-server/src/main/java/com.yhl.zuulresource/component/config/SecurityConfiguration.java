package com.yhl.zuulresource.component.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2SsoProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerTokenServicesConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableConfigurationProperties(OAuth2SsoProperties.class)//读取这个类的配置
@Import({ ResourceServerTokenServicesConfiguration.class })//导入
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private FilterSecurityInterceptor filterSecurityInterceptor;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests().anyRequest().authenticated();
        // 指定相关资源的权限校验过滤器
        http.addFilterBefore(filterSecurityInterceptor, FilterSecurityInterceptor.class);
        http.csrf().disable();
        http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
      // http.apply(new OAuth2ClientAuthenticationConfigurer(oauth2SsoFilter()));
    }
    /**
     * 看源码的意思时说，继承这个类，子类只用实现一个方法就可以完成 SecurityConfigurer 的默认配置
     * A base class for {@link SecurityConfigurer} that allows subclasses to only implement
     *  * the methods they are interested in. It also provides a mechanism for using the
     *  * {@link SecurityConfigurer} and when done gaining access to the {@link SecurityBuilder}
     *  * that is being configured.
     * */
    private static class OAuth2ClientAuthenticationConfigurer
            extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

        private OAuth2ClientAuthenticationProcessingFilter filter;

        OAuth2ClientAuthenticationConfigurer(
                OAuth2ClientAuthenticationProcessingFilter filter) {
            this.filter = filter;
        }

        @Override
        public void configure(HttpSecurity builder) throws Exception {
            OAuth2ClientAuthenticationProcessingFilter ssoFilter = this.filter;
            //设置请求成功后 对session的处理,如果要自定义我想可以自定义SessionAuthenticationStrategy
            ssoFilter.setSessionAuthenticationStrategy(
                    builder.getSharedObject(SessionAuthenticationStrategy.class));
            builder.addFilterAfter(ssoFilter,
                    AbstractPreAuthenticatedProcessingFilter.class);
        }

    }


}
