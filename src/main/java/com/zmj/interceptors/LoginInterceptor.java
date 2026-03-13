package com.zmj.interceptors;

import com.zmj.domain.Result;
import com.zmj.utils.JwtUtil;
import com.zmj.utils.RedisUtil;
import com.zmj.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌认证
        String token=request.getHeader("Authorization");
        try {
            //从redis中获取相同的token
            String redisToken = (String) redisUtil.get(token);
            if(redisToken==null){
                //证明该token已经失效
               throw new RuntimeException();
            }
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //把业务数据存到TreadLocal中
            ThreadLocalUtil.set(claims);
        }catch (Exception e){
            //http响应状态码为401
            response.setStatus(401);
            return false;
        }
        //JWT认证成功，放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空TreadLocal里的数据
        ThreadLocalUtil.remove();
    }
}
