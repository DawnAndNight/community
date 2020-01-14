package com.ff.community.mapper;

import com.ff.community.model.Question;

import java.util.List;


public interface QuestionExtMapper {
    void incView(Question record);
    void incComment(Question record);
    List<Question> selectRelated(Question question);
}