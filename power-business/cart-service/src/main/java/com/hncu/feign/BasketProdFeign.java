package com.hncu.feign;

import com.hncu.domain.Sku;
import com.hncu.feign.sentinel.BasketProdFeignSentinel;
import com.hncu.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author caimeisahng
 * @Date 2026/10/17 20:46
 * @Version 1.0
 * 购物车模块调用商品业务模块:Feign接口
 */

@FeignClient(value = "product-service",fallback = BasketProdFeignSentinel.class)
@Component
public interface BasketProdFeign {
    @GetMapping("prod/prod/getSkuListBySkuIDs")
    Result<List<Sku>> getSKuListBySkuIds(@RequestParam List<Long> skuIds);
}
