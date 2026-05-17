package com.hncu.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.MemberAddr;
import com.hncu.mapper.MemberAddrMapper;
import com.hncu.service.MemberAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
@CacheConfig(cacheNames = "com.hncu.service.impl.MemberAddrServiceImpl")
public class MemberAddrServiceImpl extends ServiceImpl<MemberAddrMapper, MemberAddr> implements MemberAddrService{

    @Autowired
    private MemberAddrMapper memberAddrMapper;

    @Override
    @Cacheable(key = "#openId")
    public List<MemberAddr> queryMemberAddrListByOpenId(String openId) {
        return null;
    }

    /**
     * 会员收货地址业务：
     * 1.会员必须得有一个默认收货地址
     *  如果会员新增的第1个收货地址应该为默认收货地址
     * @param memberAddr
     * @param openId
     * @return
     */
    @Override
    @CacheEvict(key = "#openId")
    public Boolean saveMemberAddr(MemberAddr memberAddr,String openId) {
        return null;
    }

    @Override
    @CacheEvict(key = "#openId")
    public Boolean modifyMemberAddrInfo(MemberAddr memberAddr, String openId) {
        return null;
    }

    @Override
    @CacheEvict(key = "#openId")
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeMemberAddrById(Long addrId, String openId) {

        return null;
    }

    @Override
    @CacheEvict(key = "#openId")
    @Transactional(rollbackFor = Exception.class)
    public Boolean modifyMemberDefaultAddr(String openId, Long newAddrId) {


        return null;
    }
}
