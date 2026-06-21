package com.hncu.feign;

import com.hncu.domain.MemberAddr;
import com.hncu.feign.sentinel.OrderMemberFeignSentinel;
import com.hncu.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author caimeisahng
 * @Date 2026/6/15 4:26
 * @Version 1.0
 * 订单业务模块调用会员业务模块feign接口
 */

@FeignClient(value = "member-service",fallback = OrderMemberFeignSentinel.class)
@Component
public interface OrderMemberFeign {
    @GetMapping("p/address/getMemberAddrById")
    Result<MemberAddr> getMemberAddrById(@RequestParam Long addrId);

    @GetMapping("admin/user/getNickNameByOpenId")
    Result<String> getNickNameByOpenId(@RequestParam String openId);

}
