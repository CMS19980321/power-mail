package com.hncu.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.Prod;
import com.hncu.domain.ProdTagReference;
import com.hncu.domain.Sku;
import com.hncu.mapper.ProdMapper;
import com.hncu.mapper.ProdTagReferenceMapper;
import com.hncu.mapper.SkuMapper;
import com.hncu.service.ProdService;
import com.hncu.service.ProdTagReferenceService;
import com.hncu.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdServiceImpl extends ServiceImpl<ProdMapper, Prod> implements ProdService {

    @Autowired
    private ProdMapper prodMapper;



    @Autowired
    private ProdTagReferenceMapper prodTagReferenceMapper;

    @Autowired
    private SkuMapper skuMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveProd(Prod prod) {
        //新增商品
        prod.setProdId(1L);
        prod.setSoldNum(0);
        prod.setCreateTime(new Date());
        prod.setUpdateTime(new Date());
        prod.setPutawayTime(new Date());
        prod.setVersion(0);
        Prod.DeliveryModeVo deliveryModeVo = prod.getDeliveryModeVo();
        prod.setDeliveryMode(JSONObject.toJSONString(deliveryModeVo));
        int insert = prodMapper.insert(prod);
        //prod表插入数据成功
        if (insert > 0) {

        }

        return null;
    }

    @Override
    public Prod queryProdInfoById(Long prodId) {
        return null;
    }

    @Override
    public Boolean modifyProdInfo(Prod prod) {
        return null;
    }

    @Override
    public Boolean removeProdById(Long prodId) {
        return null;
    }

    @Override
    public Prod queryWxProdInfoByProdId(Long prodId) {
        return null;
    }
}
