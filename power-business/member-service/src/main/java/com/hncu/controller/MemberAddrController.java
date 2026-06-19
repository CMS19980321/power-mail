package com.hncu.controller;

import com.hncu.domain.MemberAddr;
import com.hncu.model.Result;
import com.hncu.service.MemberAddrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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




    ///////////////////////// feign接口 /////////////////////////////

    @GetMapping("getMemberAddrById")
    Result<MemberAddr> getMemberAddrById(@RequestParam Long addrId){
        MemberAddr addr = memberAddrService.getById(addrId);
        return Result.success(addr);
    };
}
