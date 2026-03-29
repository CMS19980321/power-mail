package com.hncu.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.ProdTag;
import com.hncu.mapper.ProdTagMapper;
import com.hncu.service.ProdTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@CacheConfig(cacheNames = "com.hncu.service.impl.ProdTagServiceImpl")
public class ProdTagServiceImpl extends ServiceImpl<ProdTagMapper, ProdTag> implements ProdTagService {

    @Autowired
    private ProdTagMapper prodTagMapper;


    @Override
    public Boolean saveProdTag(ProdTag prodTag) {
        prodTag.setCreateTime(new Date());
        prodTag.setUpdateTime(new Date());
        return prodTagMapper.insert(prodTag) > 0;
    }

    @Override
    public Boolean modifyProdTag(ProdTag prodTag) {
        prodTag.setUpdateTime(new Date());
        return prodTagMapper.updateById(prodTag) > 0;
    }

    @Override
    public List<ProdTag> queryProdTagList() {
        return null;
    }

    @Override
    public List<ProdTag> queryWxProdTagList() {
        return null;
    }
}
