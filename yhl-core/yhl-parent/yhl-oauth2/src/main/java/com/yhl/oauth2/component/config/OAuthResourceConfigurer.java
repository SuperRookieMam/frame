package com.yhl.oauth2.component.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@EnableResourceServer//
@Order(99)
@EnableWebSecurity
public class OAuthResourceConfigurer  extends ResourceServerConfigurerAdapter {


    /**
     * 一个资源服务（可以和授权服务在同一个应用中，当然也可以分离开成为两个不同的应用程序）
     * 提供一些受token令牌保护的资源，Spring OAuth提供者是通过Spring Security authentication filter
     * 即验证过滤器来实现的保护，你可以通过 @EnableResourceServer 注解到一个
     * @Configuration 配置类上，并且必须使用 ResourceServerConfigurer
     * 这个配置对象来进行配置（可以选择继承自 ResourceServerConfigurerAdapter 然后覆写其中的方法，
     * 参数就是这个对象的实例），下面是一些可以配置的属性：
     * */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        // 指定资源服务器的资源ID
        // 如果给某个授权指定了可也访问的资源ID，则仅能访问指定的资源，如果不给某个授权指定指定访问的资源ID，则代表可以访问所有资源
        resources.resourceId("USERS");
        resources.stateless(true); // 标记以指示在这些资源上仅允许基于令牌的身份验证
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
        // 仅仅将部分路径认定为资源
        http.requestMatchers()
                .antMatchers("/regions**/**",
                        "/user**/**",
                        "/roles**/**",
                        "/clients**/**",
                        "/tokens**/**");
    }
}
