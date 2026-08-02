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
     * 新增收货地址
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


    /**
     * 查询会员收货地址详情
     * @param addrId 收货地址Id
     * @return
     */
    @ApiOperation("查询会员收货地址详情")
    @GetMapping("addrInfo/{addrId}")
    public Result<MemberAddr> loadMemberAddrInfo(@PathVariable Long addrId){
        MemberAddr addr = memberAddrService.getById(addrId);
        return Result.success(addr);
    }

    /**
     * 修改会员收货地址信息
     * 收货地址对象
     * @return
     */
    @ApiOperation("修改会员收货地址信息")
    @PutMapping("")
    public Result<String> modifyMemberAddrInfo(@RequestBody MemberAddr memberAddr){
        String openId = AuthUtils.getMemberOpenId();
        Boolean modified = memberAddrService.modifyMemberAddrInfo(memberAddr, openId);
        return Result.handle(modified);
    }

    /**
     * 删除会员收货地址
     * @param addrId
     * @return
     */
    @ApiOperation("删除会员收货地址")
    @DeleteMapping("deleteAddr/{addrId}")
    public Result<String> removeMemberAddr(@PathVariable Long addrId){
        String openId = AuthUtils.getMemberOpenId();
        Boolean removed = memberAddrService.removeMemberAddrById(addrId, openId);
        return Result.handle(removed);
    }

    @ApiOperation("会员设置默认收货地址")
    @PutMapping("defaultAddr/{newAddrId}")
    public Result<String> modifyMemberDefaultAddr(@PathVariable Long newAddrId){
        String openId = AuthUtils.getMemberOpenId();
        Boolean modified = memberAddrService.modifyMemberDefaultAddr(openId, newAddrId);
        return Result.handle(modified);
    }




    ///////////////////////// feign接口 /////////////////////////////

    @GetMapping("getMemberAddrById")
    Result<MemberAddr> getMemberAddrById(@RequestParam Long addrId){
        MemberAddr addr = memberAddrService.getById(addrId);
        return Result.success(addr);
    };
}
