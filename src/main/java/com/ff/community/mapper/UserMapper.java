package com.ff.community.mapper;

import com.ff.community.model.User;
import org.apache.ibatis.annotations.*;

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
    @Select("select * from user where account_id = #{account_id}")
    User findByAccountId(@Param("account_id")String accountId);
    @Update("update user set name = #{name},token=#{token},gmt_modified=#{gmtModified}," +
            "avert_url=#{avertUrl} where id = #{id}")
    void update(User user);
}
