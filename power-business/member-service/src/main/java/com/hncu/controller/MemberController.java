package com.hncu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hncu.domain.Member;
import com.hncu.model.Result;
import com.hncu.service.MemberService;
import com.hncu.util.AuthUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author caimeisahng
 * @Date 2026/6/28 20:32
 * @Version 1.0
 * 微信小程序会员业务管理控制层
 */

@Api(tags = "微信小程序会员业务管理控制层")
@RequestMapping("p/user")
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;


    /**
     * 更新会员头像与昵称
     * @param member
     * @return
     */
    @ApiOperation("更新会员头像与昵称")
    @PutMapping("setUserInfo")
    public Result<String> mofigyMemberInfo(@RequestBody Member member){
        Boolean modified = memberService.modifyMemberInfoByOpenId(member);
        return Result.handle(modified);
    }

    /**
     * 查询会员是否绑定手机号码
     * @return
     */
    @ApiOperation("查询会员是否绑定手机号")
    @GetMapping("isBindPhone")
    public Result<Boolean> loadMemberOpenId(){
        //获取会员的openId
        String memberOpenId = AuthUtils.getMemberOpenId();
        //根据openId获取会员详情
        Member member = memberService.getOne(new LambdaQueryWrapper<Member>()
                .eq(Member::getOpenId, memberOpenId)
        );

        return Result.success(StringUtils.hasText(member.getUserMobile()));
    }
}
