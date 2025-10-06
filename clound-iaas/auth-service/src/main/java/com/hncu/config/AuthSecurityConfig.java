package com.hncu.config;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hncu.constant.AuthConstants;
import com.hncu.constant.BusinessEnum;
import com.hncu.constant.HttpConstant;
import com.hncu.model.LoginResult;
import com.hncu.model.Result;
import com.hncu.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.PrintWriter;
import java.time.Duration;
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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
            //从security框架中获取认证用户对象并转换我json格式字符串
            String userJsonStr = JSONObject.toJSONString(authentication.getPrincipal());
            //将token作为key,认证对象的json格式字符串最为value存入到redis中
            stringRedisTemplate.opsForValue().set(AuthConstants.LOGIN_TOKEN_PREFIX +
                    token,userJsonStr,
                    /**
                     * 在Java中，Duration 类是 java.time 包的一部分，用于表示两个时间点之间的时间量，以秒和纳秒为单位。
                     * 这个类是在Java 8中引入的，作为日期和时间API的一部分，旨在提供更清晰和易用的方式来处理日期和时间。
                     */
                    Duration.ofSeconds(AuthConstants.TOKEN_TIME));
            //封装一个登录统一结果对象
            LoginResult loginResult = new LoginResult(token, AuthConstants.TOKEN_TIME);

            //创建以恶搞响应结果对象
            Result<LoginResult> success = Result.success(loginResult);

            //返回结果
            /**
             * 这段代码的主要功能是在用户成功登录后，将登录结果以JSON格式返回给客户端(手动的方式：spring管理的类可以直接return)。
             * 它通过ObjectMapper将Java对象转换为JSON字符串，
             * 并使用PrintWriter将这个JSON字符串写入到HTTP响应中，
             * 以便客户端可以获取到登录结果。这一过程确保了登录信息以结构化、
             * 易于解析的方式传递给客户端，便于客户端进行后续的处理和存储。
             */
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(success);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();

        };
    }

    /**
     * 登录失败处理器
     * @return
     */

    public AuthenticationFailureHandler authenticationFailureHandler(){
        return (request, response, exception) -> {
            //设置响应头信息
            response.setContentType(HttpConstant.CONTENT_TYPE);
            response.setCharacterEncoding(HttpConstant.UTF_8);
            //创建统一响应结果对象
            Result<Object> result = new Result<>();
            result.setCode(BusinessEnum.OPERATION_FAIL.getCode());

            if (exception instanceof BadCredentialsException) {
                result.setMsg("用户名或者密码有误");
            } else if (exception instanceof AccountExpiredException) {
                result.setMsg("账号异常，请联系管理员");
            } else if (exception instanceof AccountStatusException) {
                result.setMsg("账号异常，请联系管理员");
            } else if (exception instanceof InternalAuthenticationServiceException) {
                result.setMsg(exception.getMessage());
            } else if (exception instanceof UsernameNotFoundException) {
                result.setMsg("用户不存在");
            } else {
                result.setMsg(BusinessEnum.OPERATION_FAIL.getDesc());
            }

        };
    }

    /**
     * 登出成功处理器
     * @return
     */
    public LogoutSuccessHandler logoutSuccessHandler(){
        return (request, response, authentication) -> {
            //设置响应头信息
            response.setContentType(HttpConstant.CONTENT_TYPE);
            response.setCharacterEncoding(HttpConstant.UTF_8);

            //从请求头获取token
            String authorization = request.getHeader(AuthConstants.AUTHORIZATION);
            String token = authorization.replaceFirst(AuthConstants.BEARER, "");

            //将当前Token从Redis中删除
            stringRedisTemplate.delete(AuthConstants.LOGIN_TOKEN_PREFIX + token);

            //创建统一响应结果对象
            Result<Object> success = Result.success(null);

            //手动返回结果到前端
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(success);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();


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
