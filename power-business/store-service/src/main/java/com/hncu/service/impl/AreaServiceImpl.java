package com.hncu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.constant.StoreConstants;
import com.hncu.domain.Area;
import com.hncu.mapper.AreaMapper;
import com.hncu.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@CacheConfig(cacheNames = "com.hncu.service.impl.AreaServiceImpl")
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService{

    @Autowired
    private AreaMapper areaMapper;


    @Override
    @Cacheable(key = StoreConstants.ALL_AREA_KEY)
    public List<Area> queryAllAreaList() {
        return areaMapper.selectList(null);
    }
}
