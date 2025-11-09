package com.hncu.config;

import cn.hutool.core.util.ObjectUtil;
import com.hncu.constant.AuthConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author caimeisahng
 * @Date 2025/11/9 17:45
 * @Version 1.0
 * feigin拦截器
 * 作用:解决服务之间调用没有token的问题
 * 浏览器 -> 服务a -> 服务b
 *
 * 定时器(无token) -> A服务
 */
@Component
public class FeignInterception implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        //获取当前请求上下文中的值
        ServletRequestAttributes requestAttributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //判断是否有值
        if (ObjectUtil.isNotNull(requestAttributes) ) {
            // 获取请求对象
            HttpServletRequest request = requestAttributes.getRequest();
            //判断是否有值
            if (ObjectUtil.isNotNull(request)) {
                //获取当前请求头中的token值，传递到下一个请求对象中请求头中
                String authorization = request.getHeader(AuthConstants.AUTHORIZATION);
                requestTemplate.header(AuthConstants.AUTHORIZATION,authorization);
                return;
            }

        }
        //没有的情况下给一个固定值
        requestTemplate.header(AuthConstants.AUTHORIZATION,AuthConstants.BEARER + "login_token602fe644-95e2-4c89-9e9b-73b044beca9c");
    }
}
