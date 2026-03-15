package com.hncu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.SysMenu;
import com.hncu.mapper.SysMenuMapper;
import com.hncu.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@CacheConfig(cacheNames = "com.hncu.service.impl.SysMenuServiceImpl")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public Set<SysMenu> queryUserMenuListByUserId(Long loginUserId) {
        return null;
    }

    @Override
    public List<SysMenu> queryAllSysMenuList() {
        return null;
    }

    @Override
    public Boolean saveSysMenu(SysMenu sysMenu) {
        return null;
    }

    @Override
    public Boolean modifySysMenu(SysMenu sysMenu) {
        return null;
    }

    @Override
    public Boolean removeSysMenuById(Long menuId) {
        return null;
    }
}
