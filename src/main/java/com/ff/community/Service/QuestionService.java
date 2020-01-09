package com.ff.community.Service;

import com.ff.community.dto.CommentDTO;
import com.ff.community.dto.PageDTO;
import com.ff.community.dto.QuestionDTO;
import com.ff.community.enums.CommentTypeEnum;
import com.ff.community.exception.CustomizeErrorCode;
import com.ff.community.exception.CustomizeException;
import com.ff.community.mapper.CommentMapper;
import com.ff.community.mapper.QuestionExtMapper;
import com.ff.community.mapper.QuestionMapper;
import com.ff.community.mapper.UserMapper;
import com.ff.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//请求需要组装user的同时需要question的时候，就需要service
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;
    public PageDTO list(Integer page, Integer size) {
        Integer total = (int)questionMapper.countByExample(new QuestionExample());
        Integer totalPage;
        if(total % size == 0){
            totalPage = total/size;
        }else{
            totalPage = total/size+1;
        }
        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }
        //是否多一页

        Integer offset = size*(page-1);
        QuestionExample questionExample = new QuestionExample();
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        for (Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            if(user != null){
                questionDTO.setUser(user);
                questionDTOS.add(questionDTO);
            }
            pageDTO.setQuestionDTOs(questionDTOS);
        }


        pageDTO.SetPage(total,page,size);
        return pageDTO;
    }

    public PageDTO list(Long id, Integer page, Integer size) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andIdEqualTo(id);
        Integer total =(int) questionMapper.countByExample(questionExample);
        Integer totalPage;
        if(total % size == 0){
            totalPage = total/size;
        }else{
            totalPage = total/size+1;
        }
        if(totalPage <1)
            totalPage = 1;
        if (page < 1){
            page = 1;
        }
        if (page > totalPage && totalPage > 1){
            page = totalPage;
        }
        //是否多一页

        Integer offset = size*(page-1);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        for (Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            if(user != null){
                questionDTO.setUser(user);
                questionDTOS.add(questionDTO);
            }
            pageDTO.setQuestionDTOs(questionDTOS);
        }
        pageDTO.SetPage(total,page,size);
        return pageDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        if(user == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void updateOrCreate(Question question) {
        if(question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            QuestionExample questionExample = new QuestionExample();
            int insertInt = questionMapper.insert(question);
            if (insertInt != 1){
                throw new CustomizeException(CustomizeErrorCode.SYS_ERROR);
            }
        }else {
            Question updateQuestion = new Question();
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescriptions(question.getDescriptions());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setCommentCount(0);
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int updateInt = questionMapper.updateByExampleSelective(updateQuestion,questionExample);
            if (updateInt != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }

    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<CommentDTO> ListByQuestionId(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id)
        .andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        commentExample.setOrderByClause("gmt_create");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments.size() == 0){
            return new ArrayList<>();
        }
        Set<Long> commentator = comments.stream().map(
                comment -> comment.getCommentor()
        ).collect(Collectors.toSet());

        List<Long> userId = new ArrayList<>();
        userId.addAll(commentator);

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userId);

        List<User> users = userMapper.selectByExample(userExample);

        Map<Long,User> userMap = users.stream().collect(Collectors.toMap(user->user.getId(),user->user));

        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(commentDTO.getCommentor()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
