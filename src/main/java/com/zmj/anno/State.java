package com.zmj.anno;

import com.zmj.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented//元注解
@Target({ElementType.FIELD})//元注解,标识自定义注解可以用在哪些地方
@Retention(RetentionPolicy.RUNTIME)//元注解,标识自定义注解在哪个阶段被保留
@Constraint( validatedBy = {StateValidation.class})//指定提供校验规则的类
public @interface State {
    //提供校验失败后的信息
    String message() default "state参数的值只能为已发布或草稿";
    //指定分组
    Class<?>[] groups() default {};
    //负载 获取到State注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
