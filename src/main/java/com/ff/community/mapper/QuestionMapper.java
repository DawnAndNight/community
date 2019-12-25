package com.ff.community.mapper;

import com.ff.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,descriptions,gmt_create,gmt_modified,creator,tag) " +
            "values (#{title},#{descriptions},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question")
    List<Question> list();
}
