package com.hncu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author caimeisahng
 * @Date 2025/10/3 16:10
 * @Version 1.0
 * 认证授权服务启动类
 */

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class,args);
    }
}
