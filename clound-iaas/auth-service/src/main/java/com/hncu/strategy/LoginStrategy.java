package com.hncu.strategy;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Author caimeisahng
 * @Date 2025/10/4 18:12
 * @Version 1.0
 * 登录策略接口
 */
public interface LoginStrategy {

    /**
     * 正真的处理登录的接口
     * @param name
     * @return
     */
    UserDetails realLogin(String name);
}
