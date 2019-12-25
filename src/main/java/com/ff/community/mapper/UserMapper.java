package com.ff.community.mapper;

import com.ff.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avert_url) values (#{name},#{accountId}," +
            "#{token}," +
            "#{gmtCreate},#{gmtModified},#{avertUrl})")
    void insert(User user);
    @Select("select * from user where token = #{token}")
    User findBytoken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id")Integer id);
}