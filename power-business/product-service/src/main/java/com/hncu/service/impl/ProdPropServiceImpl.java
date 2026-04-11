package com.hncu.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.constant.ProductConstant;
import com.hncu.domain.ProdProp;
import com.hncu.domain.ProdPropValue;
import com.hncu.mapper.ProdPropMapper;
import com.hncu.mapper.ProdPropValueMapper;
import com.hncu.service.ProdPropService;
import com.hncu.service.ProdPropValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "com.hncu.service.impl.ProdPropServiceImpl")
public class ProdPropServiceImpl extends ServiceImpl<ProdPropMapper, ProdProp> implements ProdPropService {

    @Autowired
    private ProdPropMapper prodPropMapper;

    @Autowired
    private ProdPropValueMapper prodPropValueMapper;

    @Autowired
    private ProdPropValueService prodPropValueService;


    //联表变单表,查询效率up up
    @Override
    public Page<ProdProp> queryProdSpecPage(Long current, Long size, String propName) {
        //创建分页对象
        Page<ProdProp> page = new Page<>(current,size);
        //多条件查询分页查询商品属性
        page = prodPropMapper.selectPage(page,new LambdaQueryWrapper<ProdProp>()
                .like(StringUtils.hasText(propName),ProdProp::getPropName,propName)
        );
        //从分页记录中获取属性记录
        List<ProdProp> prodPropList = page.getRecords();
        //判断是否有值
        if (CollectionUtil.isEmpty(prodPropList) && prodPropList.size() == 0) {
            //如果属性对象集合没有值，锁门属性值也为空
            return page;
        }

        //从属性对象集合中获取属性id集合
        //创建流-->流中元素转换-->流转换为集合
        List<Long> propIdList = prodPropList.stream().map(ProdProp::getPropId).collect(Collectors.toList());
        //属性id集合查询查询属性值对象集合
        List<ProdPropValue> prodPropValueList = prodPropValueMapper.selectList(new LambdaQueryWrapper<ProdPropValue>()
                .in(ProdPropValue::getPropId, propIdList));

        //循环遍历属性对象集合
        prodPropList.forEach(prodProp -> {
            //从属性值对象集合中过滤出与当前属性对象的属性id一致的属性值对象集合
            List<ProdPropValue> propValues = prodPropValueList.stream()
                    .filter(prodPropValue -> prodPropValue.getPropId().equals(prodProp.getPropId()))
                    .collect(Collectors.toList());
            prodProp.setProdPropValues(propValues);
        });
        return page;
    }

    /**
     * 1.新增商品属性对象 -> 属性id
     * 2.批量添加商品属性值对象
     * @param prodProp
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = ProductConstant.PROP_PROD_KEY)
    public Boolean saveProdSpec(ProdProp prodProp) {
        //新增商品属性对象
        prodProp.setShopId(1L);
        prodProp.setRule(2);
        int insert = prodPropMapper.insert(prodProp);

        if (insert > 0) {
            //获取属性id
            Long propId = prodProp.getPropId();
            //添加商品属性对象与属性值记录
            //获取商品属性值集合
            List<ProdPropValue> prodPropValues = prodProp.getProdPropValues();
            //判断是否有值
            if (prodPropValues.size() != 0 && CollectionUtil.isNotEmpty(prodPropValues)) {
                //循环遍历属性值对象集合
                prodPropValues.forEach(prodPropValue -> {
                    prodPropValue.setPropId(propId);
                });
                //批量添加属性值对象集合
                prodPropValueService.saveBatch(prodPropValues);
            }
        }
        return insert > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = ProductConstant.PROP_PROD_KEY)
    public Boolean modifyProdSpec(ProdProp prodProp) {
        //获取新的属性值对象集合
        List<ProdPropValue> prodPropValues = prodProp.getProdPropValues();
        //批量修改属性值集合
        boolean flag = prodPropValueService.updateBatchById(prodPropValues);
        if (flag ) {
            // 修改属性对象
            prodPropMapper.updateById(prodProp);
        }

        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = ProductConstant.PROP_PROD_KEY)
    public Boolean removeProdSpecByPropId(Long propId) {
        //删除属性值
        prodPropValueMapper.delete(new LambdaQueryWrapper<ProdPropValue>()
                .eq(ProdPropValue::getPropId,propId));
        //删除属性对象
        return prodPropMapper.deleteById(propId) > 0;
    }

    @Override
    @Cacheable(key = ProductConstant.PROP_PROD_KEY)
    public List<ProdProp> queryProdPropList() {
        return prodPropMapper.selectList(null);
    }
}
