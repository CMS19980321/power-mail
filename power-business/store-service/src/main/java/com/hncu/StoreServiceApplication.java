package com.hncu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author caimeisahng
 * @Date 2026/4/19 20:00
 * @Version 1.0
 * 门店业务模块启动类
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@EnableFeignClients
public class StoreServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StoreServiceApplication.class,args);
    }
}
