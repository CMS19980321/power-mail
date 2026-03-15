package com.hncu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.SysRole;
import com.hncu.mapper.SysRoleMapper;
import com.hncu.mapper.SysRoleMenuMapper;
import com.hncu.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "com.hncu.service.impl.SysRoleServiceImpl")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private com.hcnu.service.SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;


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
