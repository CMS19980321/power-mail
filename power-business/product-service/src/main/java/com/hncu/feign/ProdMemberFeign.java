package com.hncu.feign;

import com.hncu.domain.Member;
import com.hncu.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author caimeisahng
 * @Date 2026/10/3 17:46
 * @Version 1.0
 * 产品业务模块调用会员业务模块:feign接口
 */
@FeignClient(value = "product-service")
@Component
public interface ProdMemberFeign {
    @GetMapping("p/user/getMemberListByOpenIds")
    Result<List<Member>> getMemberListByOpenIds(@RequestParam List<String> openIds);
}
