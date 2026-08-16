package com.hncu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.domain.Notice;
import com.hncu.model.Result;
import com.hncu.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author caimeisahng
 * @Date 2026/5/3 19:53
 * @Version 1.0
 * 公共业务管理控制层
 */

@Api(tags = "公告业务接口管理")
@RequestMapping("shop/notice")
@RestController
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     *
     * @param current 当前页
     * @param size 每页显示条数
     * @param title 标题
     * @param status 状态
     * @param isTop 是否置顶
     * @return
     */
    @ApiOperation("多条件分页查询公告列表")
    @GetMapping("page")
    @PreAuthorize("hasAuthority('shop:notice:page')")
    public Result<Page<Notice>> loadNoticePage(@RequestParam Long current,
                                               @RequestParam Long size,
                                               @RequestParam(required = false) String title,
                                               @RequestParam(required = false) Integer status,
                                               @RequestParam(required = false) Integer isTop
    ){
        //创建分页对象
        Page<Notice> page = new Page<>(current, size);
        //多条件分页查询公告列表
        page = noticeService.page(page,new LambdaQueryWrapper<Notice>()
                .eq(ObjectUtil.isNotNull(status),Notice::getStatus,status)
                .eq(ObjectUtil.isNotEmpty(isTop),Notice::getIsTop,isTop)
                .like(StringUtils.hasText(title),Notice::getTitle,title)
                .orderByDesc(Notice::getCreateTime)
        );

        return Result.success(page);
    }


    @ApiOperation("新增公告")
    @PostMapping("")
    @PreAuthorize("hasAuthority('shop:notice:page')")
    public Result<String> saveNotice(@RequestBody Notice notice){
        Boolean saved = noticeService.saveNotice(notice);

        return Result.handle(saved);
    }

    /**
     * 根据标识查询公告详情
     * @param noticeId 公告id
     * @return
     */
    @ApiOperation("根据标识查询公告详情")
    @GetMapping("info/{noticeId}")
    @PreAuthorize("hasAuthority('shop:notice:info')")
    public Result<Notice> loadNoticeInfo(@PathVariable Long noticeId){
        Notice notice = noticeService.getById(noticeId);
        return Result.success(notice);
    }

    /**
     * 修改公告内容
     * @param notice 公告对象
     * @return
     */
    @ApiOperation("修改公告内容")
    @PutMapping("")
    @PreAuthorize("hasAuthority('shop:notice:update')")
    public Result<String> modifyNotice(@RequestBody Notice notice){
        Boolean modified = noticeService.modifyNotice(notice);
        return Result.handle(modified);
    }

    @ApiOperation("根据公告标识删除公告")
    @DeleteMapping("{noticeId}")
    @PreAuthorize("hasAuthority('shop:notice:delete')")
    public Result<String> removeNotice(@PathVariable Long noticeId){
        boolean removed = noticeService.removeById(noticeId);
        return Result.handle(removed);
    }

    //////////微信小程序数据接口///////////

    /**
     * 查询小程序置顶公告列表
     * @return
     */
    @ApiOperation("查询小程序置顶公告列表")
    @GetMapping("topNoticeList")
    public Result<List<Notice>> loadWxTopNoticeList(){
        List<Notice> notices = noticeService.queryWxTopNoticeList();
        return Result.success(notices);
    }

    /**
     * 查询小程序所有公告列表
     * @return
     */
    @ApiOperation("查询小程序所有公告列表")
    @GetMapping("noticeList")
    public Result<List<Notice>> loadWXAllNoticeList(){
        List<Notice> notices = noticeService.queryWxAllNoticeList();
        return Result.success(notices);
    }

    /**
     * 根据标识查询公告详情
     * @param noticeId
     * @return
     */
    @ApiOperation("根据标识查询公告详情")
    @GetMapping("detail/{noticeId}")
    public Result<Notice> loadWxNoticeInfo(@PathVariable Long noticeId){
        Notice notice = noticeService.getById(noticeId);
        return Result.success(notice);
    }


}
