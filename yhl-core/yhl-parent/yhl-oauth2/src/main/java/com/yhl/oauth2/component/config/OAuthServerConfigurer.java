package com.yhl.oauth2.component.config;

import com.yhl.oauth2.service.MyClientDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
@EnableAuthorizationServer
public class OAuthServerConfigurer   extends AuthorizationServerConfigurerAdapter {
  @Autowired
  private   MyClientDetailService myClientDetailService;

    /**
     * ClientDetailsServiceConfigurer：用来配置客户端详情服务（ClientDetailsService），
     * 获取客户端的详细信息，可以继承ClientDetailsService 来自定义管理
     * 注意client实体对应的属性
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 通过自定义的service来存储客户端信息
        clients.withClientDetails(myClientDetailService);
    }

}
