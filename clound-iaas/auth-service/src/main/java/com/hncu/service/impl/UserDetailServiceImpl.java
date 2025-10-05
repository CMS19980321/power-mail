package com.hncu.service.impl;

import com.hncu.constant.AuthConstants;
import com.hncu.fatory.LoginStrategyFactory;
import com.hncu.strategy.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author caimeisahng
 * @Date 2025/10/3 20:45
 * @Version 1.0
 *
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private LoginStrategyFactory loginStrategyFactory;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //从请求头中获取登录类型
        String loginType = request.getHeader(AuthConstants.LOGIN_TYPE);

        if (!StringUtils.hasText(loginType)) {
            throw new InternalAuthenticationServiceException("登录类型不匹配异常");
        }

        //通过登录策略工厂获取具体的策略对象
        LoginStrategy instance = loginStrategyFactory.getInstance(loginType);
        return instance.realLogin(username);
    }
}
