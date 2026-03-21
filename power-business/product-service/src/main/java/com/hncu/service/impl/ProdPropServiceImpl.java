package com.hncu.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.ProdProp;
import com.hncu.mapper.ProdPropMapper;
import com.hncu.mapper.ProdPropValueMapper;
import com.hncu.service.ProdPropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "com.hncu.service.impl.ProdPropServiceImpl")
public class ProdPropServiceImpl extends ServiceImpl<ProdPropMapper, ProdProp> implements ProdPropService {

    @Autowired
    private ProdPropMapper prodPropMapper;

    @Autowired
    private ProdPropValueMapper prodPropValueMapper;


    @Override
    public Page<ProdProp> queryProdSpecPage(Long current, Long size, String propName) {
        return null;
    }

    @Override
    public Boolean saveProdSpec(ProdProp prodProp) {
        return null;
    }

    @Override
    public Boolean modifyProdSpec(ProdProp prodProp) {
        return null;
    }

    @Override
    public Boolean removeProdSpecByPropId(Long propId) {
        return null;
    }

    @Override
    public List<ProdProp> queryProdPropList() {
        return null;
    }
}
