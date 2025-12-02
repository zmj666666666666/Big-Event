package com.zmj.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String userPic;//当实体类属性名与表字段不一致时，查询不会报错，只会对找不到对应字段的属性设为null
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
