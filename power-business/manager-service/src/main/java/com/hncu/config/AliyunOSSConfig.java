package com.hncu.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author caimeisahng
 * @Date 2026/3/8 20:14
 * @Version 1.0
 * 阿里云平台--对象存储OSS服务配置类
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
@ConfigurationProperties(prefix = "aliyun.oss")
@RefreshScope
@Data
public class AliyunOSSConfig {

    /*
    * 访问地址
    * */
    private String endpoint;
    /*
     * bucket名称
     * */
    private String bucketName;
    /*
     * 访问ID
     * */
    private String accessKeyId;
    /*
     * 访问密钥
     * */
    private String accessKeySecret;

}
