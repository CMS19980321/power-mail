package com.hncu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

/**
 * @Author caimeisahng
 * @Date 2025/10/26 20:18
 * @Version 1.0
 * redis缓存配置类
 */

@Configuration
public class RedisCacheConfig {

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(){
        //创建redis缓存配置类
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        //进行Reids配置
        redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()))
                .entryTtl(Duration.ofDays(7))  //统一设置redis过期时间为7天
                .disableCachingNullValues(); //redis的value值禁止空值
        return  redisCacheConfiguration;
    }
}
