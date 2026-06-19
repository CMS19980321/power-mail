package com.hncu.feign.sentinel;

import com.hncu.domain.MemberAddr;
import com.hncu.feign.OrderMemberFeign;
import com.hncu.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author caimeisahng
 * @Date 2026/6/15 4:38
 * @Version 1.0
 */

@Component
@Slf4j
public class OrderMemberFeignSentinel implements OrderMemberFeign {
    @Override
    public Result<MemberAddr> getMemberAddrById(Long addrId) {
        log.error("远程接口调用失败:根据收货地址标识查询查询收获地址信息");
        return null;
    }

    @Override
    public Result<String> getNickNameByOpenId(String openId) {
        log.error("远程接口调用失败:根据会员openId查询会员昵称");
        return null;
    }
}
