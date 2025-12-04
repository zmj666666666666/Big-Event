package com.zmj.dao;

import com.zmj.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {
    @Select("select * from user where username=#{username}")
    User findByUsername(String username);

    @Insert("insert into user(username,password,create_time,update_time) " +
            "values (#{username},#{password},now(),now())")
    //密码已加密
    void insert(String username, String password);

    @Update("update user set nickname=#{nickname},email=#{email},update_time= now() where id=#{id}")
    void update(User user);
}
