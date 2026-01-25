package com.hncu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.constant.ManagerConstants;
import com.hncu.domain.SysRole;
import com.hncu.mapper.SysRoleMapper;
import com.hncu.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "com.hncu.service.impl.SysRoleServiceImpl")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 查询的是系统中所有的角色数据(全量查询)
     * 全量查询需要将数据存放到缓存中
     * @return
     */

    @Override
    @Cacheable(key=ManagerConstants.SYS_ALL_ROLE_KEY)
    public List<SysRole> querySysRoleList() {
        return sysRoleMapper.selectList(new LambdaQueryWrapper<SysRole>()
                .orderByDesc(SysRole::getCreateTime));
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
