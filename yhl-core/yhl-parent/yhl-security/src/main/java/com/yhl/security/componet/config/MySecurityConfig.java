package com.yhl.security.componet.config;

import com.yhl.security.componet.filter.Myfilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
/**
 * 一个此类初始化一个filter链条
 * */
@Configuration
@EnableWebSecurity
public class MySecurityConfig  extends WebSecurityConfigurerAdapter{


    /**
     *  重写这个方法主要是自定义url 的自定义拦截规则
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
          /*多HttpSecurity配置时必须设置这个，除最后一个外，
          因为不设置的话默认匹配所有，就不会执行到下面的HttpSecurity了*/
          http.antMatcher("/**");//设置最顶顶级的路径
          http.authorizeRequests()//获取子路径链条，设置子路径的行为
              .antMatchers("/").permitAll()//这些子路径允许通过，不验证（不是通过）
              .antMatchers("/").access("hasRole('ADMIN') and hasRole('DBA')")
              .anyRequest().authenticated();//其他路径，设置为已通过经验证状态
        http.addFilter(new Myfilter());//添加自定义filter 还可以分前中后添加
        /**CSRF（Cross-site request forgery）跨站请求伪造，
         * 由于目标站无token/referer限制，
         * 导致攻击者可以用户的身份完成操作达到各种目的。
         * 根据HTTP请求方式，CSRF利用方式可分为两种
         * */
        http.csrf().disable();
        //Http.ExceptionHandling 命名空间包含与捕获、处理和记录异常相关的类。
        http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        //验证出错登陆地址
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
        //将此http应用与那个配置
        // http.apply(new OAuth2ClientAuthenticationConfigurer(oauth2SsoFilter()));
    }

    /**
     * 比如说自定义的验证规则，注入倒此链条当中
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    /**
     *AuthenticationManager是一个接口
     *ProviderManager是AuthenticationManager的实现类;
     *ProviderManager有一个List<AuthenticationProvider> providers成员变量。
     * 认证是通过AuthenticationManager的authenticate函数实现的。
     * 也就是通过AuthenticationManager实现类ProviderManager的authenticate函数认证，
     * ProviderManager的authenticate函数会轮训ProviderManager的List<AuthenticationProvider> providers成员变量，
     * 如果该providers中如果有一个AuthenticationProvider的supports函数返回true，
     * 那么就会调用该AuthenticationProvider的authenticate函数认证，如果认证成功则整个认证过程结束。
     * 如果不成功，则继续使用下一个合适的AuthenticationProvider进行认证，只要有一个认证成功则为认证成功。
     *
     * 如果需要自定义验证规则貌似可以在这里做些什么
     * */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();

        return manager;
    }
}
