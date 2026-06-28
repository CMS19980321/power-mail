package com.hncu.controller;

import com.hncu.domain.Member;
import com.hncu.model.Result;
import com.hncu.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
}
