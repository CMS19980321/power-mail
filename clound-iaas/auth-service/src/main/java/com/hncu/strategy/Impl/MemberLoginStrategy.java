package com.hncu.strategy.Impl;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hncu.config.WxParamConfig;
import com.hncu.constant.AuthConstants;
import com.hncu.domain.LoginMember;
import com.hncu.mapper.LoginMemberMapper;
import com.hncu.model.SecurityUser;
import com.hncu.strategy.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 商城购物系统具体实现策略
 * @Author caimeisahng
 * @Date 2025/10/4 18:23
 * @Version 1.0
 */
@Service(AuthConstants.MEMBER_LOGIN)
public class MemberLoginStrategy implements LoginStrategy {

    @Autowired
    private WxParamConfig wxParamConfig;
    @Autowired
    private LoginMemberMapper loginMemberMapper;
    @Override
    public UserDetails realLogin(String username) {
        //调用微信接口服务器中固定:登录凭证校验接口(appId,appSecret,code)
        //String url = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        //调用get方法调用登录凭证校验接口
        //// 通过HTTP GET请求获取指定URL的响应内容，并转换为JSON字符串
        /*
        String.format() 方法的主要作用是格式化字符串。它根据指定的格式字符串和参数，生成一个新的格式化后的字符串。
        具体特点如下：
        占位符替换：使用 %s、%d、%f 等占位符，将后续的参数按顺序替换到对应位置。
        类型安全与可读性：相比简单的字符串拼接（+），它在处理复杂模板时更清晰、易维护。
        常见用法：
                %s：字符串
                %d：整数
                %f：浮点数
                */
        String realUrl = String.format(wxParamConfig.getUrl(), wxParamConfig.getAppid(), wxParamConfig.getSecret(), username);
        String jsonStr = HttpUtil.get(realUrl);
        //判断响应是否有值
        if (!StringUtils.hasText(jsonStr)) {
            throw new InternalAuthenticationServiceException("登录异常，请重试");
        }
        //使用fastJson将登录凭证校验接口响应的json格式字符串转换为json对象
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        //获取openid
        String openid = jsonObject.getString("openid");
        //判断是否有值
        if (!StringUtils.hasText(openid)) {
            throw new InternalAuthenticationServiceException("登录异常，请重试");
        }
        //根据会员openid查询会员对象
        LoginMember loginMember = loginMemberMapper.selectOne(new LambdaQueryWrapper<LoginMember>()
                .eq(LoginMember::getOpenId,openid)
        );
        //判断会员是否存在
        if (ObjectUtil.isNull(loginMember)) {
            //会员不存在
            //注册:创建会员对象到我们的微信小程序用户体系内
            loginMember = registerMember(openid);
        }
        //判断会员账户的状态
        if (!loginMember.getStatus().equals(1)) {
            throw new InternalAuthenticationServiceException("账号异常，请联系平台工作人员");
        }
        //会员账号状态正常
        //会员存在，返回Security框架认识的安全用户对象SecurityUser
        SecurityUser securityUser = new SecurityUser();
        //加密后的密码: $2a$10$3Ve707cMNiK8Y8znOJiHIuFLtHikEl.J8tMp6J9uZtwxFjy8VJRbC

        securityUser.setUserId(loginMember.getId().longValue());
        securityUser.setLoginType(AuthConstants.MEMBER_LOGIN);
        securityUser.setUsername(openid);
        securityUser.setStatus(loginMember.getStatus());
        securityUser.setPassword(wxParamConfig.getPwd());
        securityUser.setOpenid(openid);



        return securityUser;
    }

    private LoginMember registerMember(String openid) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String ip = request.getRemoteAddr();
        LoginMember loginMember = new LoginMember();
        loginMember.setOpenId(openid);
        loginMember.setStatus(1);
        loginMember.setCreateTime(new Date());
        loginMember.setUpdateTime(new Date());
        loginMember.setUserLasttime(new Date());
        loginMember.setUserLastip(ip);
        loginMember.setUserRegip(ip);
        //如果存在积分业务
        loginMember.setScore(0);

        //新增会员
        loginMemberMapper.insert(loginMember);

        return loginMember;


    }


}
