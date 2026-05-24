package com.hncu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.SysLog;
import com.hncu.mapper.SysLogMapper;
import com.hncu.service.SysLogService;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

}
