package com.hncu.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.constant.ProductConstant;
import com.hncu.domain.ProdTag;
import com.hncu.mapper.ProdTagMapper;
import com.hncu.service.ProdTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Service
@CacheConfig(cacheNames = "com.hncu.service.impl.ProdTagServiceImpl")
public class ProdTagServiceImpl extends ServiceImpl<ProdTagMapper, ProdTag> implements ProdTagService {

    @Autowired
    private ProdTagMapper prodTagMapper;


    @Override
    @Caching(evict = {
            @CacheEvict(key = ProductConstant.PROD_TAG_NORMAL_KEY),
            @CacheEvict(key = ProductConstant.WX_PROD_TAG)
    })
    public Boolean saveProdTag(ProdTag prodTag) {
        prodTag.setCreateTime(new Date());
        prodTag.setUpdateTime(new Date());
        return prodTagMapper.insert(prodTag) > 0;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(key = ProductConstant.PROD_TAG_NORMAL_KEY),
            @CacheEvict(key = ProductConstant.WX_PROD_TAG)
    })
    public Boolean modifyProdTag(ProdTag prodTag) {
        prodTag.setUpdateTime(new Date());
        return prodTagMapper.updateById(prodTag) > 0;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(key = ProductConstant.PROD_TAG_NORMAL_KEY),
            @CacheEvict(key = ProductConstant.WX_PROD_TAG)
    })
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @Cacheable(key = ProductConstant.PROD_TAG_NORMAL_KEY)
    public List<ProdTag> queryProdTagList() {

        return prodTagMapper.selectList(new LambdaQueryWrapper<ProdTag>()
                .eq(ProdTag::getStatus,1)
                .orderByDesc(ProdTag::getSeq)
        );
    }

    @Override
    @Cacheable(key = ProductConstant.WX_PROD_TAG)
    public List<ProdTag> queryWxProdTagList() {
        return prodTagMapper.selectList(new LambdaQueryWrapper<ProdTag>()
                .eq(ProdTag::getStatus,1)
                .orderByDesc(ProdTag::getSeq)
        );
    }
}
