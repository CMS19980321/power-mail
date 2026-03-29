package com.hncu.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.constant.ProductConstant;
import com.hncu.domain.Category;
import com.hncu.ex.handler.BusinessException;
import com.hncu.mapper.CategoryMapper;
import com.hncu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@CacheConfig(cacheNames = "com.hncu.service.impl.CategoryServiceImpl")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    @Cacheable(key = ProductConstant.ALL_CATEGORY_LIST_KEY)
    public List<Category> queryAllCategoryList() {
        return categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .orderByDesc(Category::getSeq));
    }


    @Override
    @Cacheable(key = ProductConstant.FIRST_CATEGORY_LIST_KEY)
    public List<Category> queryFirstCategoryList() {
        return categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .eq(Category::getParentId,0)
                .eq(Category::getStatus,1) //状态为非禁用
                .orderByDesc(Category::getSeq));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(key = ProductConstant.FIRST_CATEGORY_LIST_KEY),
            @CacheEvict(key = ProductConstant.ALL_CATEGORY_LIST_KEY)
    })
    public Boolean saveCategory(Category category) {
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        return categoryMapper.insert(category) > 0;
    }

    /**
     * 修改商品类目
     *   需求:允许修改商品类目级别
     * @param category
     * @return
     */
    @Override
    @Caching(evict = {
            @CacheEvict(key = ProductConstant.FIRST_CATEGORY_LIST_KEY),
            @CacheEvict(key = ProductConstant.ALL_CATEGORY_LIST_KEY)
    })
    public Boolean modifyCategory(Category category) {
        //获取修改后的pid
        Long parentId = category.getParentId();
        //根据标识查询类目详情(修改前)
        Category beforeCategory = categoryMapper.selectById(category.getCategoryId());
        //获取商品修改前的类目，如果parentId为0则为1级类目，不为0则为2级类目
        Long beforeParentId = beforeCategory.getParentId();
        //判断商品类目修改情况
        //1 --> 2 :之前pid为0,修改后pid不为0
        if (beforeParentId == 0 && null != parentId && parentId != 0) {
            //查询当前类目修改前是否包含子类目，如果包含子类目，则不允许修改
            //根据当前类目标识查询子类目
            List<Category> childList = categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                    .eq(Category::getParentId, category.getCategoryId()));
            //判断是否有值
            if (childList.size() !=0 && CollectionUtil.isNotEmpty(childList)) {
                // 当前类目包含子类，不允许修改
                throw new BusinessException("当前类目包含子类,不允许修改");
            }

        }
        //2 --> 1.之前pid不为0, 当前pid为null
        if (0 != beforeParentId && parentId == null) {
            category.setParentId(0L);
        }
        return categoryMapper.updateById(category) > 0;
    }

    /**
     * 删除商品类目
     *   需求:如果一级类目包含子类目，则不可删除
     * @param categoryId
     * @return
     */
    @Caching(evict = {
            @CacheEvict(key = ProductConstant.FIRST_CATEGORY_LIST_KEY),
            @CacheEvict(key = ProductConstant.ALL_CATEGORY_LIST_KEY)
    })
    @Override
    public Boolean removeCategoryById(Long categoryId) {
        //根据类目标识查询子类目集合
        List<Category> chileCategoryList = categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .eq(Category::getParentId, categoryId));
        //判断是否有值
        if (chileCategoryList.size() != 0 && CollectionUtil.isNotEmpty(chileCategoryList)) {
            throw new BusinessException("当前类目包含子类目，不可删除");
        }
        return categoryMapper.deleteById(categoryId) > 0;
    }

    @Override
    public List<Category> queryWxCategoryListByPid(Long pid) {
        return null;
    }
}
