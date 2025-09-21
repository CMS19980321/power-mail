package com.hncu.filter;

import com.hncu.config.WhiteUrlsConfig;
import com.hncu.constant.AuthConstants;
import com.hncu.constant.HttpConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @Author caimeisahng
 * @Date 2025/9/21 10:23
 * @Version 1.0
 */


/*
* 全局过滤器
* 前后端约定令牌存放的位置，请求头的Authorization bearer token
*
* */
@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private WhiteUrlsConfig whiteUrlsConfig;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 校验Token
     * 1.获取请求对象
     * 2.获取请求路径
     * 3..判断请求路径是否可以放行
     *    放行:不需要验证身份
     *    不放行:需要对其进行身份认证
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求对象
        ServerHttpRequest request = exchange.getRequest();
        //获取请求路径
        String path = request.getPath().toString();

        //判断当前路径是否存在白名单中
        if (whiteUrlsConfig.getAllowsUrls().contains(path)) {
            //存在:请求在白名单中，直接放行
            return chain.filter(exchange);
        }

        //请求路径不在白名单中，需要对其进行身份认证
        //从约定好的位置获取Authorization的值，值的格式为:bearer token

        //getFirst() 方法的作用是从请求头中获取指定键的第一个值。
        String authorization = request.getHeaders().getFirst(AuthConstants.AUTHORIZATION);

        //判断是否有值
        if (StringUtils.hasText(authorization)) {
            //从authorization的值中获取token
            String tokenValue = authorization.replaceFirst(AuthConstants.BEARER, " ");
            //判断token是否有值且是否在redis中存在
            if (StringUtils.hasText(tokenValue) && stringRedisTemplate.hasKey(AuthConstants.LOGIN_TOKEN_PREFIX + tokenValue)) {
                //身份验证通过
                return chain.filter(exchange);
            }

        }
        //流程到这里，说明验证身份不通过或者请求不合法
        log.error("拦截非法请求，时间:{}，请求Api路径:{}",new Date(),path);

        //获取响应对象
        ServerHttpResponse response = exchange.getResponse();
        //设置想要头的信息
        response.getHeaders().set(HttpConstant.CONTENT_TYPE,HttpConstant.APPLICATION_JSON);

        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
