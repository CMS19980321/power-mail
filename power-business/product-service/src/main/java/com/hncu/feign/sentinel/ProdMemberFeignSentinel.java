package com.hncu.feign.sentinel;

import com.hncu.domain.Member;
import com.hncu.feign.ProdMemberFeign;
import com.hncu.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author caimeisahng
 * @Date 2026/10/3 17:54
 * @Version 1.0
 *
 */

@Component
@Slf4j
public class ProdMemberFeignSentinel implements ProdMemberFeign {
    @Override
    public Result<List<Member>> getMemberListByOpenIds(List<String> openIds) {
        log.error("程调用:根据会员openId查询会员对象集合失败");
        return null;
    }
}
