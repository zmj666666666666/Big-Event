package com.zmj.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;
    private String username;
    @JsonIgnore//让springmvc把当前对象转换成json字符串时，忽略该字段(password)
    private String password;
    private String nickname;
    private String email;
    private String userPic;//当实体类属性名与表字段不一致时，查询不会报错，只会对找不到对应字段的属性设为null
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
