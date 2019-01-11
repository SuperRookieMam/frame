package com.yhl.oauth2.component.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public  class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.antMatcher("/**")// 定义当需要用户登录时候，转到的登录页面。
            .authorizeRequests()//每个子匹配器将会按照声明的顺序起作用。
                .antMatchers("/api/**","/dbk/**").permitAll()
                .antMatchers("/ye/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .anyRequest().authenticated(); //从上往依照顺序，其它的都验证通过
        //过滤器联调添加过滤器,注意重载方理论上可以无限添加  暂时还没有
        //http.addFilter();
        /**CSRF（Cross-site request forgery）跨站请求伪造，
         * 由于目标站无token/referer限制，导致攻击者可以用户的身份完成操作达到各种目的。
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


}
