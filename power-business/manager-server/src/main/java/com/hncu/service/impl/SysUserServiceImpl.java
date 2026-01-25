package com.hncu.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.SysUser;
import com.hncu.domain.SysUserRole;
import com.hncu.mapper.SysUserMapper;
import com.hncu.mapper.SysUserRoleMapper;
import com.hncu.service.SysUserRoleService;
import com.hncu.service.SysUserService;
import com.hncu.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 1.新增管理员
     * 2.新增管理员与角色的关系，方法中涉及多个数据库操作使用
     *
     * @param sysUser
     * @return
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveSysUser(SysUser sysUser) {
        //新增管理员
        sysUser.setCreateUserId(AuthUtils.getLoginUserId());
        sysUser.setCreateTime(new Date());
        sysUser.setShopId(1L); //店铺id目前只有一个
        int i = sysUserMapper.insert(sysUser);
        if (i > 0) {
            //获取管理员标识
            Long userId = sysUser.getUserId();
            //新增管理员与角色的关系
            //获取管理员的角色id的集合
            List<Long> roleIdList = sysUser.getRoleIdList();
            //创建管理员与角色的关系集合
            ArrayList<SysUserRole> sysUserRoleList = new ArrayList<>();
            //判断是否有值
            if (CollectionUtil.isNotEmpty(roleIdList) && roleIdList.size() != 0){
                // 循环遍历角色id的结合
                for (Long roleId : roleIdList) {
                    //创建管理员与角色的关系
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(userId);
                    sysUserRole.setRoleId(roleId);
                    //注意:不推荐在循环中操作数据库
                    //sysUserRoleMapper.insert(sysUserRole);
                    sysUserRoleList.add(sysUserRole);
                }
                //批量添加管理员与角色的关系
                sysUserRoleService.saveBatch(sysUserRoleList);
            }

        }
        return i;
    }

    @Override
    public SysUser querySysUserInfoByUserId(Long id) {
        //根据用户标识查询管理员信息
        SysUser sysUser = sysUserMapper.selectById(id);
        //根据用户标识查询管理员与角色的关系合集
        List<SysUserRole> sysUserRoleLIst = sysUserRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, id));

        //判断集合是否有值
        if (CollectionUtil.isNotEmpty(sysUserRoleLIst) && sysUserRoleLIst.size() != 0) {
            // 从管理员与角色关系集合中获取角色id集合
            /*
            * 创建流 (stream())：将集合转换为流进行操作
              中间操作 (map(SysUserRole::getId))：对流中的每个元素进行转换
               终止操作 (collect(Collectors.toList()))：将流转换回集合
            * */
            List<Long> roleIdList = sysUserRoleLIst.stream()
                    .map(SysUserRole::getRoleId)
                    .collect(Collectors.toList());
            sysUser.setRoleIdList(roleIdList);
        }

        return sysUser;
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
