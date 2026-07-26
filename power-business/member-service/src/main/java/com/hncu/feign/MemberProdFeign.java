package com.hncu.feign;

import com.hncu.domain.Prod;
import com.hncu.feign.sentinel.MemberProdFSentinel;
import com.hncu.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

/**
 * @Author caimeisahng
 * @Date 2026/7/26 20:05
 * @Version 1.0
 * 会员业务模块调用商品业务模块feign接口
 */

@FeignClient(value = "product-service",fallback = MemberProdFSentinel.class)
public interface MemberProdFeign {
    @GetMapping("prod/prod/getProdListByIds")
    Result<List<Prod>> getProdListByIds(@RequestParam List<Long> prodIdList);

}
