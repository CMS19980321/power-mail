package com.hncu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.SysRole;
import com.hncu.mapper.SysRoleMapper;
import com.hncu.service.SysRoleService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "com.powernode.service.impl.SysRoleServiceImpl")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Override
    public List<SysRole> querySysRoleList() {
        return null;
    }

    @Override
    public Boolean saveSysRole(SysRole sysRole) {
        return null;
    }

    @Override
    public SysRole querySysRoleInfoByRoleId(Long roleId) {
        return null;
    }

    @Override
    public Boolean modifySysRole(SysRole sysRole) {
        return null;
    }

    @Override
    public Boolean removeSysRoleListByIds(List<Long> roleIdList) {
        return null;
    }
}
