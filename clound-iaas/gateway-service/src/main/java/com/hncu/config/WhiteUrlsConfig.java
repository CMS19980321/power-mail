package com.hncu.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author caimeisahng
 * @Date 2025/9/21 10:53
 * @Version 1.0
 */


/*
* @ConfigurationProperties: 可以将配置文件（如 application.properties 或 application.yml）中的属性绑定到一个
* Java 对象的字段上。
* @RefreshScope:Spring Cloud 提供的一个注解，主要作用是使被注解的 bean 能够在运行时刷新其配置
* ，而不必重启整个应用程序。
*
* */


@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "gateway.white")
@RefreshScope
@Data
public class WhiteUrlsConfig {
    /*
    * 放行的路径的集合
    * */
    private List<String> allowsUrls;

}
