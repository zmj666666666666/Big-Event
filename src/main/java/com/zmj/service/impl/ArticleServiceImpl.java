package com.zmj.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zmj.dao.ArticleDao;
import com.zmj.domain.Article;
import com.zmj.domain.PageBean;
import com.zmj.service.ArticleService;
import com.zmj.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //创建PageBean对象
        PageBean<Article> artilces=new PageBean<>();
        //开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);
        //调用dao
        Map<String,Object> claims=ThreadLocalUtil.get();
        Integer userId= (Integer) claims.get("id");
        List<Article> artilceList=articleDao.list(userId,categoryId,state);
        //Page中提供了方法，可以获取PageHelper分页查询后得到的总记录条数和当前页数据
        Page<Article> articlePage= (Page<Article>) artilceList;
        //数据填充
        artilces.setItems(articlePage.getResult());
        artilces.setTotal(articlePage.getTotal());
        return artilces;
    }
}
