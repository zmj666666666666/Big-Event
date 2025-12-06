package com.zmj.dao;

import com.zmj.domain.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryDao {
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time) " +
            "values (#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);

    @Select("select * from category where create_user = #{userId}")
    List<Category> getAll(Integer userId);

    @Select("select * from category where id=#{id}")
    Category getById(Integer id);

    @Delete("delete from category where id=#{id}")
    void deleteById(Integer id);

    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=#{updateTime}" +
            " where id = #{id}")
    void update(Category category);
}
