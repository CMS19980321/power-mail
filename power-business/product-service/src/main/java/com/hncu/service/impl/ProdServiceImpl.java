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
    private ProdTagReferenceService prodTagReferenceService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private ProdTagReferenceMapper prodTagReferenceMapper;

    @Autowired
    private  SkuMapper skuMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveProd(Prod prod) {
        //新增商品
        prod.setShopId(1L);
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
            Long prodId = prod.getProdId();
            //处理商品与分组标签的关系
            //获取商品分组标签
            List<Long> tagIdList = prod.getTagList();
            //判断是否有值
            if (CollectionUtil.isNotEmpty(tagIdList) && tagIdList.size() != 0) {
                //创建商品与分组标签关系集合
                List<ProdTagReference> prodTagReferenceList = new ArrayList<>();
                // 循环遍历分组标签集合
                tagIdList.forEach(tagId -> {
                    //创建商品与分组标签的关系记录
                    ProdTagReference prodTagReference = new ProdTagReference();
                    prodTagReference.setProdId(prodId);
                    prodTagReference.setTagId(tagId);
                    prodTagReference.setCreateTime(new Date());
                    prodTagReference.setShopId(1L);
                    prodTagReference.setStatus(1);
                    prodTagReferenceList.add(prodTagReference);
                });
                //批量添加商品与分组标签的关系记录
                prodTagReferenceService.saveBatch(prodTagReferenceList);

            }

            //处理商品与商品sku的关系
            //获取商品sku集合对象
            List<Sku> skuList = prod.getSkuList();
            //判断是否有值
            if (CollectionUtil.isNotEmpty(skuList) && skuList.size() != 0) {
                //循环遍历sku对象集合
                skuList.forEach(sku -> {
                    sku.setProdId(prodId);
                    sku.setCreateTime(new Date());
                    sku.setUpdateTime(new Date());
                    sku.setVersion(0);
                    sku.setActualStocks(sku.getStocks());
                });
                //批量添加商品sku对象
                skuService.saveBatch(skuList);
            }
        }

        return insert > 0;
    }

    @Override
    public Prod queryProdInfoById(Long prodId) {
        //根据标识查询商品详情
        Prod prod = prodMapper.selectById(prodId);
        //空指针异常处理
        if (ObjectUtil.isNotEmpty(prod)) {
            return prod;
        }
        //根据商品标识查询商品与分组标签的关系
        List<ProdTagReference> prodTagReferenceList = prodTagReferenceMapper.selectList(new LambdaQueryWrapper<ProdTagReference>()
                .eq(ProdTagReference::getProdId, prodId)
        );
        //判断是否有值
        if (CollectionUtil.isNotEmpty(prodTagReferenceList)) {
            //从商品与商品分组标签的集合中获取分组Id的集合
            List<Long> tagIdLIst = prodTagReferenceList.stream()
                    .map(ProdTagReference::getTagId)
                    .collect(Collectors.toList());
            prod.setTagList(tagIdLIst);

        }
        //根据商品id查询商品sku对象集合
        List<Sku> skus = skuMapper.selectList(new LambdaQueryWrapper<Sku>()
                .eq(Sku::getProdId, prodId)
        );
        prod.setSkuList(skus);
        return prod;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean modifyProdInfo(Prod prod) {
        Long prodId = prod.getProdId();
        //删除商品原有的与分组标签的关系
        prodTagReferenceMapper.delete(new LambdaQueryWrapper<ProdTagReference>()
                .eq(ProdTagReference::getProdId,prodId)
        );
        //获取商品分组标签
        List<Long> tagIdList = prod.getTagList();
        //判断是否有值
        if (CollectionUtil.isNotEmpty(tagIdList)) {
            //创建商品与分组标签关系集合
            List<ProdTagReference> prodTagReferenceList = new ArrayList<>();
            // 循环遍历分组标签集合
            tagIdList.forEach(tagId -> {
                //创建商品与分组标签的关系记录
                ProdTagReference prodTagReference = new ProdTagReference();
                prodTagReference.setProdId(prodId);
                prodTagReference.setTagId(tagId);
                prodTagReference.setCreateTime(new Date());
                prodTagReference.setShopId(1L);
                prodTagReference.setStatus(1);
                prodTagReferenceList.add(prodTagReference);
            });
            //批量添加商品与分组标签的关系记录
            prodTagReferenceService.saveBatch(prodTagReferenceList);

        }

        //批量修改商品sku对象集合
        //获取商品sku对象集合
        List<Sku> skuList = prod.getSkuList();
        skuList.forEach(sku -> {
            sku.setUpdateTime(new Date());
            sku.setActualStocks(sku.getStocks());
        });
        //批量修改商品sku对象集合
        skuService.updateBatchById(skuList);

        //修改商品对象
        prod.setUpdateTime(new Date());
        return prodMapper.updateById(prod) > 0;
    }

    @Override
    public Boolean removeProdById(Long prodId) {
        //删除商品与分组标签的关系
        prodTagReferenceMapper.delete(new LambdaQueryWrapper<ProdTagReference>()
                .eq(ProdTagReference::getProdId,prodId)
        );
        //根据商品id删除商品sku对象
        skuMapper.delete(new LambdaQueryWrapper<Sku>()
                .eq(Sku::getProdId,prodId)
        );

        return prodMapper.deleteById(prodId) > 0;
    }

    @Override
    public Prod queryWxProdInfoByProdId(Long prodId) {
        return null;
    }
}
