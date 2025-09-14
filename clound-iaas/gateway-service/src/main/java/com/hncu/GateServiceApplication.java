package com.hncu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author caimeisahng
 * @Date 2025/9/14 20:24
 * @Version 1.0
 * 启动类
 */


/*
* 从 Spring Cloud Edgware 版本开始，即使不显式添加 @EnableDiscoveryClient，
* 只要在类路径上有相应的发现客户端实现，Spring Cloud 也会自动配置服务发现功能。
*
* @EnableDiscoveryClient 是 Spring Cloud 提供的一个注解，用于启用服务发现客户端
*
* */
@SpringBootApplication
@EnableDiscoveryClient
public class GateServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateServiceApplication.class,args);
    }
}
