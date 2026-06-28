package com.hncu.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author caimeisahng
 * @Date 2026/6/28 11:41
 * @Version 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "wx")
@RefreshScope
@Data
public class WxParamConfig {


    //微信小程序appId
    private String appid;
    //微信小程序密钥security
    private String secret;
    //登录凭证校验接口url
    private String url;
    //微信小程序登录密码(固定为:wechat)
    private String pwd;
}
