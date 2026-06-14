package com.hncu.feign;

import com.hncu.domain.MemberAddr;
import com.hncu.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author caimeisahng
 * @Date 2026/6/15 4:26
 * @Version 1.0
 * 订单业务模块调用会员业务模块feign接口
 */

@FeignClient(value = "member-service")
@Component
public interface OrderMemberFeign {
    @GetMapping("xx/xx/getMemberAddrById")
    Result<MemberAddr> getMemberAddrById(@RequestParam Long addrId);
}
