package com.hncu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hncu.domain.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户标识查询菜单权限集合
     * @param loginUserId
     * @return
     */
    Set<SysMenu> selectUserMenuListByUserId(Long loginUserId);
}