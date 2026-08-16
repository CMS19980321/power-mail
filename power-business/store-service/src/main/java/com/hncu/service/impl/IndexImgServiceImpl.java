package com.hncu.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.constant.BusinessEnum;
import com.hncu.constant.StoreConstants;
import com.hncu.domain.IndexImg;
import com.hncu.domain.Prod;
import com.hncu.ex.handler.BusinessException;
import com.hncu.feign.StoreProdFeign;
import com.hncu.mapper.IndexImgMapper;
import com.hncu.model.Result;
import com.hncu.service.IndexImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@CacheConfig(cacheNames = "com.hncu.service.impl.IndexImgServiceImpl")
public class IndexImgServiceImpl extends ServiceImpl<IndexImgMapper, IndexImg> implements IndexImgService{

    @Autowired
    private IndexImgMapper indexImgMapper;

    @Autowired
    private StoreProdFeign storeProdFeign;



    @Override
    @CacheEvict(StoreConstants.WX_INDEX_IMG_KEY)
    public Boolean saveIndexImg(IndexImg indexImg) {
        indexImg.setShopId(1L);
        indexImg.setCreateTime(new Date());
        //获取关联类型
        Integer type = indexImg.getType();
        if (type == -1) {
            //轮播图未关联商品
            indexImg.setProdId(null);
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
        if (type == 0) {
            //说明:轮播图已关联商品
            //获取关联商品的id
            Long prodId = indexImg.getProdId();
            //远程调用，根据商id查询商品图片和名称
            Result<List<Prod>> result = storeProdFeign.getProdListByIds(Arrays.asList(prodId));
            //判断返回结果是否正确
            if (BusinessEnum.OPERATION_FAIL.getCode().equals(result.getCode())) {
                //操作失败
                throw new BusinessException(result.getMsg());
            }
            //获取数据
            List<Prod> prods = result.getData();
            //判断集合是否有值
            if (CollectionUtil.isNotEmpty(prods) && prods.size() != 0) {
                //获取商品对象
                Prod prod = prods.get(0);
                indexImg.setPic(prod.getPic());
                indexImg.setProdName(prod.getProdName());
            }

        }


        return indexImg;
    }

    @Override
    @CacheEvict(StoreConstants.WX_INDEX_IMG_KEY)
    public Boolean modifyIndexImg(IndexImg indexImg) {
        return indexImgMapper.updateById(indexImg) > 0;
    }

    @Override
    @CacheEvict(StoreConstants.WX_INDEX_IMG_KEY)
    public Boolean removeIndexImgByIds(List<Long> imgIds) {
        return indexImgMapper.deleteBatchIds(imgIds) ==  imgIds.size();
    }

    @Override
    @Cacheable(key = StoreConstants.WX_INDEX_IMG_KEY)
    public List<IndexImg> queryWxIndexImgList() {
        return indexImgMapper.selectList(new LambdaQueryWrapper<IndexImg>()
                .eq(IndexImg::getStatus,1)
                .orderByDesc(IndexImg::getDes)
        );
    }




}
