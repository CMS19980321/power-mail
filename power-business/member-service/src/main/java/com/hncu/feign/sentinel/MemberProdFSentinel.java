package com.hncu.feign.sentinel;

import com.hncu.domain.Prod;
import com.hncu.feign.MemberProdFeign;
import com.hncu.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author caimeisahng
 * @Date 2026/7/26 20:12
 * @Version 1.0
 */

@Component
@Slf4j
public class MemberProdFSentinel implements MemberProdFeign {
    @Override
    public Result<List<Prod>> getProdListByIds(List<Long> prodIdList) {
        log.error("远程调用失败,根据商品Id集合查询商品对象集合");
        return null;
    }
}
