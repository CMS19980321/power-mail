package com.hncu.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.domain.Category;
import com.hncu.domain.Prod;
import com.hncu.domain.ProdTagReference;
import com.hncu.feign.sentinel.SearchProdFeignSentinel;
import com.hncu.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author caimeisahng
 * @Date 2026/8/23 22:25
 * @Version 1.0
 * 搜索业务模块调用产品业务模块:feign接口
 */

@FeignClient(value = "product-service",fallback = SearchProdFeignSentinel.class)
@Component
public interface SearchProdFeign {
    @GetMapping("prod/prodTag/getProdTagReferencePageByTagId")
    Result<Page<ProdTagReference>> getProdTagReferencePageTagId(@RequestParam Long current,
                                                                @RequestParam Long size,
                                                                @RequestParam Long tagId
                                                                );

    @GetMapping("prod/prod/getProdListByIds")
    Result<List<Prod>> getProdListByIds(@RequestParam List<Long> prodIdList);

    @GetMapping("prod/category/getCategoryListByParentId")
    Result<List<Category>> getCategoryListByParentId(@RequestParam Long parentId);
}
