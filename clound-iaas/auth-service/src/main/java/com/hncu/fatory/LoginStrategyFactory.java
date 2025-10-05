package com.hncu.fatory;

import com.hncu.strategy.LoginStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author caimeisahng
 * @Date 2025/10/4 18:30
 * @Version 1.0
 * 登录策略工厂类
 */

@Component
public class LoginStrategyFactory {
    private Map<String, LoginStrategy> loginStrategyMap = new HashMap<>();


    /**
     * 根据用户的登录类型获取具体的登录策略
     * @param loginType
     * @return
     */

    public LoginStrategy getInstance(String loginType){
        return loginStrategyMap.get(loginType);
    }
}
