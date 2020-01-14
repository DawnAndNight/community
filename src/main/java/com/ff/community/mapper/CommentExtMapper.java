package com.ff.community.mapper;

import com.ff.community.model.Comment;
import com.ff.community.model.CommentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    void incCommentCount(Comment comment);
}