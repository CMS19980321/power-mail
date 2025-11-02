package com.hncu.fatory;

import com.hncu.strategy.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
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


    /**
     * 这段代码的主要功能是通过 Spring 自动装配机制，将所有 LoginStrategy 类型的 Bean 收集到一个 Map 中，
     * 以便 LoginStrategyFactory 可以根据不同的登录类型来获取相应的策略对象。
     * 这样做不仅简化了对象的管理和获取过程，还提高了代码的可扩展性和灵活性，
     * 使得不同的登录策略可以轻松地添加和维护。
     */
    @Autowired
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
