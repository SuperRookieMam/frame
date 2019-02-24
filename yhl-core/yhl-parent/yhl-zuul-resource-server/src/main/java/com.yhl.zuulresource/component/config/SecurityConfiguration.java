package com.yhl.zuulresource.component.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2SsoProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerTokenServicesConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableConfigurationProperties(OAuth2SsoProperties.class)//读取这个类的配置
@Import({ ResourceServerTokenServicesConfiguration.class })//导入
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

}
