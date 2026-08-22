package com.hncu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.domain.Prod;
import com.hncu.model.Result;
import com.hncu.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author caimeisahng
 * @Date 2026/8/22 20:29
 * @Version 1.0
 * 搜索业务控制层
 */


@Api(tags = "搜索业务控制层次")
@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;

    @ApiOperation("根据分组标签分页查询商品")
    @GetMapping("prod/prodListByTagId")
    public Result<Page<Prod>> loadWxProdByPAgeTagId(@RequestParam Long current,
                                                    @RequestParam Long size,
                                                    @RequestParam Long tagId
                                                    ){
        Page<Prod> page = searchService.queryWxProdPageByTagId(current, size, tagId);
        return Result.success(page);
    }
}
