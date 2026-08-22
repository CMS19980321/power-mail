package com.hncu.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hncu.domain.Prod;
import com.hncu.domain.ProdTagReference;
import com.hncu.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class SearchServiceImpl implements SearchService {
    
    @Override
    public Page<Prod> queryWxProdPageByTagId(Long current, Long size, Long tagId) {
        //创建 商品与分组标签关系记录 分页对象
        Page<ProdTagReference> prodTagReferencePage = new Page<>(current, size);
        //远程调用接口:根据分组标签分页查询 商品与分组标签的关系

        return null;
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

        return null;
    }
}
