package com.ff.community.Service;

import com.ff.community.enums.CommentTypeEnum;
import com.ff.community.exception.CustomizeErrorCode;
import com.ff.community.exception.CustomizeException;
import com.ff.community.mapper.CommentMapper;
import com.ff.community.mapper.QuestionExtMapper;
import com.ff.community.mapper.QuestionMapper;
import com.ff.community.model.Comment;
import com.ff.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public void insert(Comment comment){
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TAGET_PARENT_NOT_FOUND);
        }
        if(comment.getType() == null || ! CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARENT_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbcomment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMET_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else{
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incComment(question);
        }
    }
}
