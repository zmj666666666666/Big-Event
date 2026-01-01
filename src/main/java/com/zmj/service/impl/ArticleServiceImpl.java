package com.zmj.service.impl;

import com.zmj.dao.ArticleDao;
import com.zmj.domain.Article;
import com.zmj.service.ArticleService;
import com.zmj.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public void add(Article article) {
        //补充添加用户的id
        Map<String,Object> claims=ThreadLocalUtil.get();
        Integer userid= (Integer) claims.get("id");
        article.setCreateUser(userid);
        articleDao.add(article);
    }
}
