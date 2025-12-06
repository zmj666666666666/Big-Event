package com.zmj.service;

import com.zmj.domain.Category;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    List<Category> getAll();

    Category getById(Integer id);

    void deleteById(Integer id);

    void update(Category category);
}
