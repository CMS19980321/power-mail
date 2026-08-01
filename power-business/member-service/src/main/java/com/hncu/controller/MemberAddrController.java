package com.hncu.controller;

import com.hncu.domain.MemberAddr;
import com.hncu.model.Result;
import com.hncu.service.MemberAddrService;
import com.hncu.util.AuthUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author caimeisahng
 * @Date 2026/6/15 4:42
 * @Version 1.0
 * 会员收货地址控制层次
 */

@Api(tags = "会员收货地址接口管理")
@RestController
@RequestMapping("p/address")
public class MemberAddrController {

    @Autowired
    private MemberAddrService memberAddrService;

    /**
     * 查询会员的所有收货地址
     * @return
     */

    @ApiOperation("查询会员的所有收货地址")
    @GetMapping("list")
    public Result<List<MemberAddr>> loadMemberAddrList(){
        String openId = AuthUtils.getMemberOpenId();
        List<MemberAddr> memberAddrs = memberAddrService.queryMemberAddrListByOpenId(openId);
        return Result.success(memberAddrs);
    }

    /**
     *
     * @param memberAddr 会员收货地址对象
     * @return
     */
    @ApiOperation("新增收货地址")
    @PostMapping("")
    public Result<String> saveMemberAddr(@RequestBody MemberAddr memberAddr){
        String openId = AuthUtils.getMemberOpenId();
        Boolean saved = memberAddrService.saveMemberAddr(memberAddr, openId);
        return Result.handle(saved);
    }




    ///////////////////////// feign接口 /////////////////////////////

    @GetMapping("getMemberAddrById")
    Result<MemberAddr> getMemberAddrById(@RequestParam Long addrId){
        MemberAddr addr = memberAddrService.getById(addrId);
        return Result.success(addr);
    };
}
