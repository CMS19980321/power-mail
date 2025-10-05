package com.hncu.config;

import com.hncu.constant.AuthConstants;
import com.hncu.constant.HttpConstant;
import com.hncu.service.impl.UserDetailServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author caimeisahng
 * @Date 2025/10/3 20:37
 * @Version 1.0
 * Security安全框架配置类
 */

@Configuration
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 设置Security安全框架走自己的认证流程
     * @param auth
     * @throws Exception
     */
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭跨站请求伪造
        http.cors().disable();
        //关闭跨域请求
        http.csrf().disable();
        //关闭session使用策略
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //配置登录信息
        http.formLogin()
                .loginProcessingUrl(AuthConstants.LOGIN_URL)  //设置登录URL
                .successHandler(authenticationSuccessHandler()) //设置登录成功处理器
                .failureHandler(authenticationFailureHandler()); //设置登录失败处理器


        //配置登出信息
        http.logout()
                .logoutUrl(AuthConstants.LOGOUT_URL) //设置登出url
                .logoutSuccessHandler(logoutSuccessHandler()); //设置登出成功处理器

        //设置所有请求都要进行身份认证
        http.authorizeRequests().anyRequest().authenticated();
    }

    /**
     * 登录成功处理器
     * @return
     */

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return (request, response, authentication) -> {
            //设置响应头信息
            response.setContentType(HttpConstant.CONTENT_TYPE);
            response.setCharacterEncoding(HttpConstant.UTF_8);
            //使用UUID当作token
            String token = UUID.randomUUID().toString();
            //从security框架中获取认证用户对象

        };
    }

    /**
     * 登录失败处理器
     * @return
     */

    public AuthenticationFailureHandler authenticationFailureHandler(){
        return (request, response, exception) -> {

        };
    }

    /**
     * 登出成功处理器
     * @return
     */
    public LogoutSuccessHandler logoutSuccessHandler(){
        return (request, response, authentication) -> {

        };
    }

    /**
     * 密码加密器配置
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
