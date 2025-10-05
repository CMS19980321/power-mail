package com.hncu.strategy.Impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hncu.constant.AuthConstants;
import com.hncu.domain.LoginSysUser;
import com.hncu.mapper.LoginSysUserMapper;
import com.hncu.model.SecurityUser;
import com.hncu.strategy.LoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;


/**
 * 商城后台管理系统登录策略具体实现
 * @Author caimeisahng
 * @Date 2025/10/4 18:18
 * @Version 1.0
 */
@Service(AuthConstants.SYS_USER_LOGIN)
public class SysUserLoginStrategy implements LoginStrategy {

    @Autowired
    private LoginSysUserMapper loginSysUserMapper;
    @Override
    public UserDetails realLogin(String username) {
        //根据用户名称查询用户对象
        /*LoginSysUser loginSysUser = loginSysUserMapper.selectOne(new QueryWrapper<LoginSysUser>().
                eq("username",username));*/

        LoginSysUser loginSysUser = loginSysUserMapper.selectOne(new LambdaQueryWrapper<LoginSysUser>()
                .eq(LoginSysUser::getUsername,username)
        );

        /**
         * Hutool是一个备受欢迎的Java工具库，提供了一系列简单易用的工具，包括但不限于缓存、数据库操作、日期时间工具、文件操作、
         * 加解密工具、HTTP工具、JSON工具、反射工具、XML工具等，非常全面。可以大大减少我们的开发时间和成本。
         */
        if (ObjectUtil.isNotEmpty(loginSysUser)) {
            //根据用户标识查询用户的权限集合
            Set<String> perms = loginSysUserMapper.selectPermsByUserId(loginSysUser.getUserId());

            SecurityUser securityUser = SecurityUser.builder()
                    .userId(loginSysUser.getUserId())
                    .password(loginSysUser.getPassword())
                    .shopId(loginSysUser.getShopId())
                    .status(loginSysUser.getStatus())
                    .loginType(AuthConstants.SYS_USER_LOGIN)
                    .build();

            //权限空值判断
            if (CollectionUtil.isNotEmpty(perms) && perms.size() != 0) {
                securityUser.setPerms(perms);
            }
            return securityUser;
        }



        return null;
    }
}
