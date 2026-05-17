package com.hncu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author caimeisahng
 * @Date 2026/5/17 20:50
 * @Version 1.0
 */

@SpringBootApplication
@EnableDiscoveryClient
public class MemberServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberServiceApplication.class,args);
    }
}
