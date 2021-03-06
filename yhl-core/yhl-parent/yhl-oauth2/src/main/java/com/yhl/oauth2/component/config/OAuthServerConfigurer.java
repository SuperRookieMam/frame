package com.yhl.oauth2.component.config;

import com.yhl.oauth2.service.MyAuthorizationServerTokenService;
import com.yhl.oauth2.service.MyClientDetailService;
import com.yhl.oauth2.service.UserApprovalStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;

@Configuration
@EnableAuthorizationServer
public class OAuthServerConfigurer   extends AuthorizationServerConfigurerAdapter {
  @Autowired
  private   MyClientDetailService myClientDetailService;
  @Autowired
  private UserApprovalStoreService userApprovalStoreService;
  @Autowired
  private MyAuthorizationServerTokenService myAuthorizationServerTokenService;
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
  /**
   * AuthorizationServerEndpointsConfigurer:
   * 用来配置授权（authorization）以及令牌（token） 的访问端点
   * 和令牌服务(token services)
   *
   * 配置授权类型（Grant Types）：
   * 授权是使用 AuthorizationEndpoint 这个端点来进行控制的，
   * 如果你不进行设置的话，默认是除了资源所有者密码（password）授权类型以外，支持其余所有标准授权类型的（RFC6749），
   *
   * AuthorizationServerEndpointsConfigurer属性，如下列表：
   *
   * authenticationManager：认证管理器，当你选择了资源所有者密码（password）授权类型的时候，
   * 请设置这个属性注入一个 AuthenticationManager 对象。
   *
   * userDetailsService：如果啊，你设置了这个属性的话，那说明你有一个自己的 UserDetailsService 接口的实现，
   * 或者你可以把这个东西设置到全局域上面去（例如 GlobalAuthenticationManagerConfigurer 这个配置对象），
   * 当你设置了这个之后，那么 "refresh_token" 即刷新令牌授权类型模式的流程中就会包含一个检查，
   * 用来确保这个账号是否仍然有效，假如说你禁用了这个账户的话。
   *
   * authorizationCodeServices：这个属性是用来设置授权码服务的（即 AuthorizationCodeServices 的实例对象），
   * 主要用于 "authorization_code" 授权码类型模式。
   *
   * implicitGrantService：这个属性用于设置隐式授权模式，用来管理隐式授权模式的状态。
   *
   * tokenGranter：这个属性就很牛B了，当你设置了这个东西（即 TokenGranter 接口实现），
   * 那么授权将会交由你来完全掌控，并且会忽略掉上面的这几个属性，
   * 这个属性一般是用作拓展用途的，
   * 即标准的四种授权模式已经满足不了你的需求的时候，才会考虑使用这个。
   * */
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      super.configure(endpoints);
    //自定义了对AccessToken的相关操作创建、刷新、获取
    endpoints.tokenServices(myAuthorizationServerTokenService);
    //对用户端，权限，作用于的控制
    endpoints.userApprovalHandler(userApprovalHandler());

  }

  @Bean
  public UserApprovalHandler userApprovalHandler() {
    // 存储用户的授权结果
    ApprovalStoreUserApprovalHandler handler = new ApprovalStoreUserApprovalHandler();
    handler.setApprovalStore(userApprovalStoreService);

    handler.setRequestFactory(requestFactory());
    return handler;
  }

  @Bean
  public OAuth2RequestFactory requestFactory() {
    return new DefaultOAuth2RequestFactory(myClientDetailService);
  }

}
