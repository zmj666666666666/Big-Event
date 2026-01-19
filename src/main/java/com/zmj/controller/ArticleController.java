package com.zmj.controller;

import com.zmj.domain.Article;
import com.zmj.domain.PageBean;
import com.zmj.domain.Result;
import com.zmj.service.ArticleService;
import com.zmj.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ){
        PageBean<Article> articles=articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(articles);
    }

    @GetMapping("/{id}")
    public Result<Article> getById(@PathVariable Integer id){
        Article article=articleService.getById(id);
        return Result.success(article);
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id){
        articleService.deleteById(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody @Validated Article article){
        articleService.update(article);
        return Result.success();
    }

}
