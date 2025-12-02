package com.zmj.controller;

import com.zmj.domain.Result;
import com.zmj.domain.User;
import com.zmj.service.UserService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated//参数校验
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$")String password){
        //参数格式校验(用@Pattern注解指定校验规则，用@Validated注解开启参数校验)
        //查询用户名，看注册用户名是否已存在
        User user=userService.findByUsername(username);
        if(user==null) {
            userService.register(username,password);
            return Result.success();
        }else {
            return Result.error("用户名重复");
        }
    }
}
