package com.hncu.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.Member;
import com.hncu.mapper.MemberMapper;
import com.hncu.service.MemberService;
import com.hncu.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService{

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Boolean modifyMemberInfoByOpenId(Member member) {
        String openId = AuthUtils.getMemberOpenId();
        return memberMapper.update(member,new LambdaUpdateWrapper<Member>()
                .eq(Member::getOpenId,openId)
        ) > 0;
    }
}
