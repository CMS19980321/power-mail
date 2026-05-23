package com.hncu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.domain.Member;
import com.hncu.model.Result;
import com.hncu.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author caimeisahng
 * @Date 2026/5/23 19:59
 * @Version 1.0
 * 后台管理系统维护会员控制层
 */

@Api(tags = "后台管理系统维护会员接口管理")
@RequestMapping("admin/user")
@RestController
public class SysMemberController {
    @Autowired
    private MemberService memberService;

    /**
     *
     * @param current 页码
     * @param size 每页显示条数
     * @param nickName 会员昵称
     * @param status 会员状态
     * @return
     */
    @ApiOperation("多条件分页查询会员")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('admin:user:page')")
    public Result<Page<Member>> loadMemberPage(@RequestParam Long current,
                                               @RequestParam Long size,
                                               @RequestParam(required = false) String nickName,
                                               @RequestParam(required = false) Integer status){
        Page<Member> page = new Page<>(current, size);
        page = memberService.page(page,new LambdaQueryWrapper<Member>()
                .eq(ObjectUtil.isNotNull(status),Member::getStatus,status)
                .like(StringUtils.hasText(nickName),Member::getNickName,nickName)
                .orderByDesc(Member::getCreateTime)
        );

        return Result.success(page);
    }


    /**
     * 根据标识查询会员信息
     * @return id 会员id
     */
    @ApiOperation("根据标识查询会员信息")
    @GetMapping("info/{id}")
    @PreAuthorize("hasAuthority('admin:user:info')")
    public Result<Member> loadMemberInfo(@PathVariable Long id){
        Member member = memberService.getOne(new LambdaQueryWrapper<Member>()
                .select(Member::getId, Member::getOpenId, Member::getPic, Member::getNickName,Member::getStatus)
                .eq(Member::getId, id)
        );
        return Result.success(member);
    }


}
