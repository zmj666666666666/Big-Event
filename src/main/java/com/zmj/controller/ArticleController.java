package com.zmj.controller;

import com.zmj.domain.Result;
import com.zmj.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
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
