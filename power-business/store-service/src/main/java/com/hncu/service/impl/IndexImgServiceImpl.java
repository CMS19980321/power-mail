package com.hncu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.IndexImg;
import com.hncu.mapper.IndexImgMapper;
import com.hncu.service.IndexImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@CacheConfig(cacheNames = "com.hncu.service.impl.IndexImgServiceImpl")
public class IndexImgServiceImpl extends ServiceImpl<IndexImgMapper, IndexImg> implements IndexImgService{

    @Autowired
    private IndexImgMapper indexImgMapper;



    @Override
    public Boolean saveIndexImg(IndexImg indexImg) {
        indexImg.setShopId(1L);
        indexImg.setCreateTime(new Date());
        //获取关联类型
        Integer type = indexImg.getType();
        if (type == -1) {
            //轮播图未关联商品
            indexImg.setProdId(-1L);
        }
        return indexImgMapper.insert(indexImg) > 0;
    }

    @Override
    public IndexImg queryIndexImgInfoById(Long imgId) {
        //根据标识查询轮播图信息
        IndexImg indexImg = indexImgMapper.selectById(imgId);
        //获取轮播图关联类型
        Integer type = indexImg.getType();
        //判断关联商品
        if (type == -1) {
            //说明:轮播图已关联商品
            //获取关联商品的id
            Long prodId = indexImg.getProdId();

        }


        return indexImg;
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
