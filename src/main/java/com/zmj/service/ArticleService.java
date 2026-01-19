package com.zmj.service;

import com.zmj.domain.Article;
import com.zmj.domain.PageBean;

public interface ArticleService {
    void add(Article article);

    //获取列表(条件分页)
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    Article getById(Integer id);

    void deleteById(Integer id);

    void update(Article article);
}
