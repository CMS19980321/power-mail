package com.hncu.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.Category;
import com.hncu.mapper.CategoryMapper;
import com.hncu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "com.powernode.service.impl.CategoryServiceImpl")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> queryAllCategoryList() {
        return null;
    }

    @Override
    public List<Category> queryFirstCategoryList() {
        return null;
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
