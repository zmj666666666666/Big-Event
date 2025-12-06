package com.zmj.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    @NotNull//不能为NULL
    private Integer id;
    private String username;
    @JsonIgnore//让springmvc把当前对象转换成json字符串时，忽略该字段(password)
    private String password;
    @NotEmpty//不能为NULL且不能为空字符串
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;
    @NotEmpty
    @Email//要满足邮箱格式(这些validation有关的注解不会影响跟数据库有关的操作，只会在前端传递有关参数到后端时对这些属性进行相关校验）
    private String email;
    private String userPic;//当实体类属性名与表字段不一致时，查询不会报错，只会对找不到对应字段的属性设为null
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
