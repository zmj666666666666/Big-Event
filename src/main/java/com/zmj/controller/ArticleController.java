package com.zmj.controller;

import com.zmj.domain.Article;
import com.zmj.domain.Result;
import com.zmj.service.ArticleService;
import com.zmj.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Result<String> list(/*@RequestHeader(name = "Authorization") String token, HttpServletResponse response*/){
        //验证JWT(用拦截器实现，更简洁方便)
//        try {
//            Map<String, Object> claims = JwtUtil.parseToken(token);
//        }catch (Exception e){
//            //http响应状态码为401
//            response.setStatus(401);
//            return Result.error("未登录");
//        }
        return Result.success("文章列表");
    }
}
