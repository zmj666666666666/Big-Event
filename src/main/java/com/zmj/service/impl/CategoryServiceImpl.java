package com.zmj.service.impl;

import com.zmj.dao.CategoryDao;
import com.zmj.domain.Category;
import com.zmj.service.CategoryService;
import com.zmj.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public void add(Category category) {
        //补充属性值
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Map<String,Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        category.setCreateUser(userId);
        categoryDao.add(category);
    }

    @Override
    public List<Category> getAll() {
        Map<String,Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        List<Category> categoryList=categoryDao.getAll(userId);
        return categoryList;
    }

    @Override
    public Category getById(Integer id) {
        Category category=categoryDao.getById(id);
        return category;
    }

    @Override
    public void deleteById(Integer id) {
        categoryDao.deleteById(id);
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryDao.update(category);
    }
}
