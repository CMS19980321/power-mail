package com.hncu.feign;

import com.hncu.domain.Prod;
import com.hncu.feign.sentinel.StoreProdFeignSentinel;
import com.hncu.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author caimeisahng
 * @Date 2026/5/5 17:21
 * @Version 1.0
 * 门店业务模块调用商品业务模块feign接口
 */

@FeignClient(value = "product-service",fallback = StoreProdFeignSentinel.class)
@Component
public interface StoreProdFeign {



    @GetMapping("prod/prod/getProdListByIds")
    Result<List<Prod>> getProdListByIds(@RequestParam List<Long> prodIdList);
}
