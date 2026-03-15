package com.hncu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.SysUser;
import com.hncu.mapper.SysUserMapper;
import com.hncu.mapper.SysUserRoleMapper;
import com.hncu.service.SysUserRoleService;
import com.hncu.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysUserRoleService sysUserRoleService;


    @Override
    public Integer saveSysUser(SysUser sysUser) {
        return null;
    }

    @Override
    public SysUser querySysUserInfoByUserId(Long id) {
        return null;
    }

    @Override
    public Integer modifySysUserInfo(SysUser sysUser) {
        return null;
    }

    @Override
    public Boolean removeSysUserListByUserIds(List<Long> userIds) {
        return null;
    }
}
