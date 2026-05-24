package com.hncu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hncu.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}