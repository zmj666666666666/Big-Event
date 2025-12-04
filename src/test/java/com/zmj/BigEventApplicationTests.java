package com.zmj;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.plaf.TableHeaderUI;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class BigEventApplicationTests {

	@Test
	public void testGen() {
		Map<String,Object> claims=new HashMap<>();
		claims.put("id",1);
		claims.put("username","张三");
		//生成jwt
		String token=JWT.create()
				.withClaim("user",claims)//添加载荷
				.withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))//添加过期时间
				.sign(Algorithm.HMAC256("zmj"));//指定算法，配置密钥
		System.out.println(token);
	}

	@Test
	public void testParse(){
		String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
				"eyJleHAiOjE3NjQ3MTQ0NjAsInVzZXIiOnsiaWQiOjEsInVzZXJuYW1lIjoi5byg5LiJIn19." +
				"ERETGSDWEfir-GjKdQjhLwa8nMMYJPWnLQOuax_vIEk";
		//申请JWT验证器,注意算法和密钥要与生成JWT时的配置保持一致
		JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256("zmj")).build();
		DecodedJWT decodedJWT=jwtVerifier.verify(token);//验证token，生成一个解析后的JWT对象
		Map<String, Claim> claims=decodedJWT.getClaims();
		System.out.println(claims.get("user"));
	}

	@Test
	public void testThreadLocalSetAndGet(){
		//提供一个ThreadLocal对象
		ThreadLocal tl=new ThreadLocal();

		//开启两个线程
		new Thread(() -> {
			tl.set("zmj");
			System.out.println(Thread.currentThread().getName()+":"+tl.get());
			System.out.println(Thread.currentThread().getName()+":"+tl.get());
			System.out.println(Thread.currentThread().getName()+":"+tl.get());
		},"蓝色").start();

		new Thread(() -> {
			tl.set("hml");
			System.out.println(Thread.currentThread().getName()+":"+tl.get());
			System.out.println(Thread.currentThread().getName()+":"+tl.get());
			System.out.println(Thread.currentThread().getName()+":"+tl.get());
		},"绿色").start();
	}

}
