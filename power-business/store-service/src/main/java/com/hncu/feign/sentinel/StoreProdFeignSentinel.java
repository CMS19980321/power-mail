package com.hncu.feign.sentinel;

import com.hncu.domain.Prod;
import com.hncu.feign.StoreProdFeign;
import com.hncu.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author caimeisahng
 * @Date 2026/5/16 19:57
 * @Version 1.0
 */

@Component
@Slf4j
public class StoreProdFeignSentinel implements StoreProdFeign {

    @Override
    public Result<List<Prod>> getProdListByIds(List<Long> prodIdList) {
        log.error("根据商品id查询商品对象集合");
        return null;
    }
}
