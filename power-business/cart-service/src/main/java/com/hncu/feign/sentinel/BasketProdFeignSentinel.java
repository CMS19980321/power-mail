package com.hncu.feign.sentinel;

import com.hncu.domain.Sku;
import com.hncu.feign.BasketProdFeign;
import com.hncu.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author caimeisahng
 * @Date 2026/10/17 20:52
 * @Version 1.0
 */

@Component
@Slf4j
public class BasketProdFeignSentinel implements BasketProdFeign {
    @Override
    public Result<List<Sku>> getSKuListBySkuIds(List<Long> skuIds) {
        log.error("远程调用:根据skuId结合查询上面sku对象集合失败");
        return null;
    }
}
