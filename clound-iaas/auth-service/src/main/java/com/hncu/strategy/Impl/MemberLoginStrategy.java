package com.hncu.strategy.Impl;

import com.hncu.constant.AuthConstants;
import com.hncu.strategy.LoginStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * 商城购物系统具体实现策略
 * @Author caimeisahng
 * @Date 2025/10/4 18:23
 * @Version 1.0
 */
@Service(AuthConstants.MEMBER_LOGIN)
public class MemberLoginStrategy implements LoginStrategy {
    @Override
    public UserDetails realLogin(String name) {
        return null;
    }
}
