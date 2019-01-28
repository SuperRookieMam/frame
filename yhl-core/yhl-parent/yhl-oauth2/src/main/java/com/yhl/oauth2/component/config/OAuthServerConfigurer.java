package com.yhl.oauth2.component.config;

import com.yhl.oauth2.service.MyClientDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

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
  /**
   * AuthorizationServerSecurityConfigurer ：
   * 用来配置令牌端点(Token Endpoint)的安全约束.
   * */
  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    super.configure(security);
    // 通常情况下,Spring Security获取token的认证模式是基于http basic的,
    // 也就是client的client_id和client_secret是通过http的header或者url模式传递的，
    // 也就是通过http请求头的 Authorization传递，具体的请参考http basic
    // 或者http://client_id:client_secret@server/oauth/token的模式传递的
    // 当启用这个配置之后，server可以从表单参数中获取相应的client_id和client_secret信息
    // 默认情况下，checkToken的验证时denyAll的，需要手动开启
    security.checkTokenAccess("isAuthenticated()"); // 开启/oauth/check_token验证端口认证权限访问
    security.allowFormAuthenticationForClients();//允许对客户机进行表单身份验证
    //security.tokenKeyAccess("permitAll()");// 开启/oauth/token_key验证端口无权限访问
  }


}
