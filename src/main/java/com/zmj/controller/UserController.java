package com.zmj.controller;

import com.zmj.domain.Result;
import com.zmj.domain.User;
import com.zmj.service.UserService;
import com.zmj.utils.JwtUtil;
import com.zmj.utils.Md5Util;
import com.zmj.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    //必须在此实体参数前加@Validated注解，有关User属性的校验才会启动,就算在Controller类上已添加过也需要再加(已测试)
    @PutMapping
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        //1.校验参数
        String old_pwd = params.get("old_pwd");
        String new_pwd = params.get("new_pwd");
        String re_pwd = params.get("re_pwd");

        if(!StringUtils.hasLength(old_pwd)||!StringUtils.hasLength(new_pwd)||!StringUtils.hasLength(re_pwd)){
            return Result.error("参数不能为空");
        }

        //校验原密码
        Map<String,Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.findByUsername(username);
        if(!user.getPassword().equals(Md5Util.getMD5String(old_pwd))){
            return Result.error("原密码错误");
        }

        //校验新密码是否符合规范以及新密码和确认密码是否一致
        if(!new_pwd.equals(re_pwd)){
            return Result.error("两次新密码不一致");
        }

        if(new_pwd.length()<5||new_pwd.length()>16){
            return Result.error("密码长度要大于5且小于16");
        }

        //2.更新密码
        userService.updatePwd(new_pwd);
        return Result.success();
    }
}
