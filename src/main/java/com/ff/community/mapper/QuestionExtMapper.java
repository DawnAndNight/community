package com.ff.community.mapper;

import com.ff.community.model.Question;


public interface QuestionExtMapper {
    void incView(Question record);
    void incComment(Question record);
}