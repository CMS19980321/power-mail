package com.hncu.service.impl;

import com.hncu.mapper.MemberMapper;
import com.hncu.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 *
 */
@Service
public class SendServiceImpl implements SendService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void sendPhoneMsg(Map<String, Object> map) {

    }

    @Override
    public Boolean saveMsgPhone(Map<String, Object> map) {
        return null;
    }
}
