package com.zmj.controller;

import com.zmj.domain.Result;
import com.zmj.domain.User;
import com.zmj.service.UserService;
import com.zmj.utils.JwtUtil;
import com.zmj.utils.Md5Util;
import com.zmj.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated//参数校验
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
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

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$")String password){
        User user=userService.findByUsername(username);
        if(user!=null){
            //user的密码是密文，所以需要将参数中的密码转成密文才能验证
            if(Md5Util.getMD5String(password).equals(user.getPassword())){
                //登录成功
                //生成JWT
                Map<String,Object> claims=new HashMap<>();
                claims.put("id",user.getId());
                claims.put("username",user.getUsername());
                String token=JwtUtil.genToken(claims);
                return Result.success(token);
            }
            return Result.error("密码错误");
        }
        return Result.error("用户名错误");
    }

    //根据用户名查询用户
    @GetMapping
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization")String token*/){
        //利用TreadLocal传递用户名信息
//        Map<String, Object> map = JwtUtil.parseToken(token);
//        String username = (String) map.get("username");
        Map<String,Object> claims = ThreadLocalUtil.get();
        String username= (String) claims.get("username");
        User user=userService.findByUsername(username);
        return Result.success(user);
    }
}
