package com.hncu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.IndexImg;
import com.hncu.mapper.IndexImgMapper;
import com.hncu.service.IndexImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "com.hncu.service.impl.IndexImgServiceImpl")
public class IndexImgServiceImpl extends ServiceImpl<IndexImgMapper, IndexImg> implements IndexImgService{

    @Autowired
    private IndexImgMapper indexImgMapper;



    @Override
    public Boolean saveIndexImg(IndexImg indexImg) {
        return null;
    }

    @Override
    public IndexImg queryIndexImgInfoById(Long imgId) {


        return null;
    }

    @Override
    public Boolean modifyIndexImg(IndexImg indexImg) {
        return null;
    }

    @Override
    public Boolean removeIndexImgByIds(List<Long> imgIds) {
        return null;
    }

    @Override
    public List<IndexImg> queryWxIndexImgList() {
        return null;
    }
}
