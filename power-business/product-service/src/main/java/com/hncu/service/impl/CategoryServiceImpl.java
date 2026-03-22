package com.hncu.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.constant.ProductConstant;
import com.hncu.domain.Category;
import com.hncu.mapper.CategoryMapper;
import com.hncu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
    public Boolean saveCategory(Category category) {
        return null;
    }

    @Override
    public Boolean modifyCategory(Category category) {
        return null;
    }

    @Override
    public Boolean removeCategoryById(Long categoryId) {
        return null;
    }

    @Override
    public List<Category> queryWxCategoryListByPid(Long pid) {
        return null;
    }
}
