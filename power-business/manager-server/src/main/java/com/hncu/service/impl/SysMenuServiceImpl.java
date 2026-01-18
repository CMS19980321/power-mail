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
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "com.powernode.service.impl.SysMenuServiceImpl")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public Set<SysMenu> queryUserMenuListByUserId(Long loginUserId) {
        Set<SysMenu> menus = sysMenuMapper.selectUserMenuListByUserId(loginUserId);
        //将菜单权限集合的数据转换为树结构(即:数据结构应该为层级关系)，方便前端处理
        return transformTree(menus,0L);
    }

    /**
     * 集合转换为树状结构
     *   1.已知菜单深度 <= 2'
     *   2.未知菜单深度
     * @param menus
     * @param pid
     * @return
     */
    private Set<SysMenu> transformTree(Set<SysMenu> menus, long pid) {
        // 从菜单集合中获取根节点的集合
        /*
        * 1.Stream 创建: menus.stream() 创建了一个流（Stream），
        * 这个流允许我们对集合中的元素进行一系列的操作，比如过滤、映射、收集等。
        * 2.过滤操作: .filter(m -> m.getParentId().equals(pid)) 是一个中间操作，用于过滤流中的元素。
        * 这里的过滤条件是 SysMenu对象的 parentId属性等于给定的 pid。
        * 这意味着我们筛选出所有父菜单ID为 pid 的子菜单，形成一个根菜单的集合。
        * 如果 pid 是 0，则筛选出所有顶级菜单
        * 3.收集操作: .collect(Collectors.toSet()) 是一个终端操作，
        * 用于将流中的元素收集到一个集合中。这里使用了 Collectors.toSet() 方法，
        * 将过滤后的元素收集到一个 Set<sysmenu> 中。Set 是一个不允许有重复元素的集合
        *
        * */
        //已知菜单的深度是2
        /*Set<SysMenu> roots = menus
                .stream()
                .filter(m -> m.getParentId().equals(pid))
                .collect(Collectors.toSet());

        //遍历循环根节点
        roots.forEach(root -> {
            //从菜单集合中过滤出它的父节点值和当前根节点id一致的菜单集合
            Set<SysMenu> child = menus
                    .stream()
                    .filter(m -> m.getParentId().equals(root.getMenuId()))
                    .collect(Collectors.toSet());
            root.setList(child);

        });

*/
        //菜单深度未知
        //获取根节点为pid的节点集合
        Set<SysMenu> roots = menus
                .stream()
                .filter(m -> m.getParentId().equals(pid))
                .collect(Collectors.toSet());

        //循环遍历调用自己，找到当前节点的子节点集合
        //遍历结束条件，当前节点子节点集合为空
        roots.forEach(r -> r.setList(transformTree(menus,r.getMenuId())));

        return roots;
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
