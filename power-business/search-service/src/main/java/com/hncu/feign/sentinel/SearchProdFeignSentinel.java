package com.hncu.feign.sentinel;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.domain.ProdTagReference;
import com.hncu.feign.SearchProdFeign;
import com.hncu.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author caimeisahng
 * @Date 2026/8/23 22:32
 * @Version 1.0
 */

@Component
@Slf4j
public class SearchProdFeignSentinel implements SearchProdFeign {

    @Override
    public Result<Page<ProdTagReference>> getProdTagReferencePageTagId(Long current, Long size, Long tagId) {
        log.error("远程调用失败:根据分组标签id分页查询商品与分组标签关系");
        return null;
    }
}
