package com.hncu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hncu.domain.Member;


public interface MemberService extends IService<Member> {


    /**
     * 更新会员的头像和昵称
     *
     * @param member
     * @return
     */
    Boolean modifyMemberInfoByOpenId(Member member);
}
