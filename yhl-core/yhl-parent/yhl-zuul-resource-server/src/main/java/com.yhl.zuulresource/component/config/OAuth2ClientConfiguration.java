package com.yhl.zuulresource.component.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhl.zuulresource.component.client.token.grant.code.AddAuthorizationCodeAccessTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.RedirectStrategy;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.TreeMap;

//作为客户端的配置
@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfiguration {

    /**
     * ObjectMapper提供了读取和写入JSON的功能，
     *从基本pojo(普通的旧Java对象)到基本pojo或从基本pojo到基本pojo或从基本pojo到基本pojo
     *一个通用的JSON树模型({@link JsonNode})，
     * */
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired  //这就是ClientContextFilter;
    private OAuth2ClientContextFilter oAuth2ClientContextFilter;

    //被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，
    // 并且只会被服务器调用一次，类似于Serclet的inti()方法。
    // 被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。

    //@PreDestroy说明 被@PreDestroy修饰的方法会在服务器卸载Servlet的时候运行，
    // 并且只会被服务器调用一次，类似于Servlet的destroy()方法。
    // 被@PreDestroy修饰的方法会在destroy()方法之后运行，
    // 在Servlet被彻底卸载之前。（详见下面的程序实践）
    @PostConstruct
    public void setFilter() {
        //初始化serverlet容器的时候就注册这个
        oAuth2ClientContextFilter.setRedirectStrategy(new RedirectStrategy() {
           //这里是如何重定向一个请求
            @Override
            public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url)
                    throws IOException {
                // 重新配置oauth2Context的重定向策略，不进行重定向，
                // 而是将重定向信息交由前端处理
                response.setStatus(HttpStatus.SEE_OTHER.value());
                TreeMap<String, String> parameterMap = new TreeMap<>();
                parameterMap.put("redirect_url", url);
                try (PrintWriter writer = response.getWriter()) {
                    writer.write(objectMapper.writeValueAsString(parameterMap));
                }
            }
        });
    }


    @Bean
    public UserInfoRestTemplateCustomizer userInfoRestTemplateCustomizer() {
        AccessTokenProviderChain provicerChain = new AccessTokenProviderChain(Arrays.<AccessTokenProvider>asList(
                // 更换原有的AuthorizationCodeAccessTokenProvider
                new AddAuthorizationCodeAccessTokenProvider(),
                new ImplicitAccessTokenProvider(),
                new ResourceOwnerPasswordAccessTokenProvider(),
                new ClientCredentialsAccessTokenProvider()));

        return new UserInfoRestTemplateCustomizer() {
            @Override
            public void customize(OAuth2RestTemplate template) {
                template.setAccessTokenProvider(provicerChain);
            }
        };
    }
}
