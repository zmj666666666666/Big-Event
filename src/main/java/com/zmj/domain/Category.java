package com.zmj.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {
    @NotNull(groups = Update.class)
    private Integer id;
    @NotEmpty
    private String categoryName;
    @NotEmpty
    private String categoryAlias;
    private Integer createUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")//属性转换成json字符时指定格式
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    //如果说某个校验项没有指定分组，则默认属于Default分组(@Validated注解没有指定分组时，也是默认使用Default分组的校验规则)
    //分组之间可以继承，A extends B，那么A中拥有B中所有的校验项
//    public interface Add extends Default{
//
//    }

    public interface Update extends Default {

    }
}
