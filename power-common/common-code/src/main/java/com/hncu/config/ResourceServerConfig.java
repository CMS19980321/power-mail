package com.hncu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hncu.constant.BusinessEnum;
import com.hncu.constant.HttpConstant;
import com.hncu.constant.ResourceConstants;
import com.hncu.model.Result;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author caimeisahng
 * @Date 2025/11/2 20:28
 * @Version 1.0
 * 资源服务器配置类
 * 二次验证必要性:通过服务ip与端口直接访问服务，跳过网关，不路由到auth,这时的请求可能未携带token等
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 在Spring Security中，csrf() 方法用于配置跨站请求伪造（Cross-Site Request Forgery，CSRF）的防护机制。
         * CSRF是一种攻击，攻击者通过诱导用户访问恶意网站，利用用户在目标网站上的已认证状态，执行非预期的操作。
         *
         * disable() 方法用于禁用CSRF防护。禁用CSRF防护意味着应用程序将不再自动检查请求中的CSRF令牌，
         * 这可能会使应用程序更容易受到CSRF攻击，但在某些情况下，例如API服务，可能会选择禁用CSRF防护，
         * 因为它通常不适用于这种场景。
         */
        //关闭跨站请求伪造
        http.csrf().disable();
        /**
         * 禁用CORS通常是因为应用程序不需要支持跨域请求，或者通过其他方式处理了跨域问题。
         */
        //关闭跨域请求
        http.cors().disable();
        //关闭session策略
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //配置处理携带token但权限不足的请求
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint()) //二次处理没有携带token的请求
                .accessDeniedHandler(accessDeniedHandler()); //处理携带token但是权限不足的请求
        //配置其他的请求
        http.authorizeRequests()
                .antMatchers(ResourceConstants.RESOURCE_ALLOW_URLS)
                .permitAll()
                .anyRequest().authenticated();

    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return (request, response, authException) -> {
            //设置响应头的信息
            response.setContentType(HttpConstant.APPLICATION_JSON);
            request.setCharacterEncoding(HttpConstant.UTF_8);

            //创建项目庭院响应结果对象并输出
            Result<Object> fail = Result.fail(BusinessEnum.UN_AUTHORIZATION);
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(fail);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();

        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return (request, response, accessDeniedException) -> {
            //设置响应头的信息
            response.setContentType(HttpConstant.APPLICATION_JSON);
            request.setCharacterEncoding(HttpConstant.UTF_8);

            //创建项目庭院响应结果对象并输出
            Result<Object> result = Result.fail(BusinessEnum.ACCESS_DENY_FAIL);
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(request);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();

        };
    }
}
