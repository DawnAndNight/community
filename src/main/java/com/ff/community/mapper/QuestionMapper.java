package com.ff.community.mapper;

import com.ff.community.dto.QuestionDTO;
import com.ff.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,descriptions,gmt_create,gmt_modified,creator,tag) " +
            "values (#{title},#{descriptions},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question Limit #{offset},#{size}")
    List<Question> list(@Param(value="offset") Integer offset, @Param(value = "size")Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{creatorid} Limit #{offset},#{size}")
    List<Question> listByUser(@Param(value="creatorid") Integer creatorid, Integer offset, Integer size);

    @Select("select count(1) from question where creator = #{creatorid}")
    Integer countById(@Param(value="creatorid") Integer creatorid);

    @Select("select * from question where id =  #{id}")
    Question getById(@Param(value="id")String id);
}
