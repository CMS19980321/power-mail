package com.hncu.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.constant.BusinessEnum;
import com.hncu.domain.Category;
import com.hncu.domain.Prod;
import com.hncu.domain.ProdProp;
import com.hncu.domain.ProdTagReference;
import com.hncu.ex.handler.BusinessException;
import com.hncu.feign.SearchProdFeign;
import com.hncu.model.Result;
import com.hncu.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class SearchServiceImpl implements SearchService {


    @Autowired
    private SearchProdFeign searchProdFeign;
    
    @Override
    public Page<Prod> queryWxProdPageByTagId(Long current, Long size, Long tagId) {
        //创建 商品 分页对象
        Page<Prod> prodPage = new Page<>(current, size);
        //远程调用接口:根据分组标签分页查询 商品与分组标签的关系
        Result<Page<ProdTagReference>> result = searchProdFeign.getProdTagReferencePageTagId(current, size, tagId);
        //判断操作是否成功
        if (result.getCode().equals(BusinessEnum.OPERATION_FAIL.getCode())) {
            throw new RuntimeException("远程接口调用:根据分组标签分页查询 商品与分组标签的关系 失败");
        }
        //获取商品与分页标签的分页对象
        Page<ProdTagReference> prodTagReferencePage = result.getData();
        //从 商品与分组标签的关系 分页对象中获取 商品与分组标签的关系 记录
        List<ProdTagReference> prodTagReferenceList = prodTagReferencePage.getRecords();
        //判断 商品与分组标签的关系 记录是否有值
        if (CollectionUtil.isEmpty(prodTagReferenceList)) {
            // 说明没有数据
            return prodPage;
        }
        //商品与分组标签的关系 中获取商品Id集合
        List<Long> prodIdList = prodTagReferenceList.stream().map(ProdTagReference::getProdId).collect(Collectors.toList());
        //远程调用:根据商品Id集合查询商品对象集合
        Result<List<Prod>> prodResult = searchProdFeign.getProdListByIds(prodIdList);
        //判断是否操作成功
        if (prodResult.getCode().equals(BusinessEnum.OPERATION_FAIL.getCode())) {
            throw new BusinessException("远程调用:根据商品id集合查询商品对象集合失败");
        }
        //获取商品对象集合
        List<Prod> prods = prodResult.getData();
        //组装商品分页对象
        prodPage.setRecords(prods);
        prodPage.setTotal(prodTagReferencePage.getTotal());
        prodPage.setPages(prodTagReferencePage.getPages());
        return prodPage;
    }

    /**
     * 根据商品类目标识查询商品集合
     * 1.当前类目标识只有商品一级类目
     * 2.查询的商品应该包含商品一级类目下的子类目的商品
     * @param categoryId
     * @return
     */
    @Override
    public List<Prod> queryWxProdListByCategoryId(Long categoryId) {
        List<Long> allCategoryIds = new ArrayList<>();
        allCategoryIds.add(categoryId);
        //远程调用:商品一级商品类目id查询子类目集合
        Result<List<Category>> categoryResult = searchProdFeign.getCategoryListByParentId(categoryId);
        //判断是否操作成功
        if (categoryResult.getCode().equals(BusinessEnum.OPERATION_FAIL.getCode())) {
            throw new BusinessException("远程调用失败:根据商品一级类目id查询子类目集合");
        }
        //获取数据
        List<Category> categoryList = categoryResult.getData();
        //判断子类目是否有值
        if (CollectionUtil.isNotEmpty(categoryList)) {
            //从子类目集合中获取类目Id集合
            List<Long> collectIdList = categoryList.stream().map(Category::getCategoryId).collect(Collectors.toList());
            allCategoryIds.addAll(collectIdList);
        }
        //根据产品类目Id集合查询商品对象集合
        return null;
    }
}
