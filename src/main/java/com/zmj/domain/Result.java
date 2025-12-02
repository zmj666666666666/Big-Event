package com.zmj.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//统一响应结果
@NoArgsConstructor
@AllArgsConstructor
//因为后端向前端返回数据时会将Result转成json数据，需要setter和getter方法，所以添加@Data注解
@Data
public class Result<T>{
    private Integer code; //业务状态码
    private String message; //提示信息
    private T data; //响应数据

    public static <E> Result<E> success(E data){
        return new Result<>(0,"操作成功",data);
    }

    public static Result success(){
        return new Result<>(0,"操作成功",null);
    }

    public static Result error(String message){
        return new Result(1,message,null);
    }
}
