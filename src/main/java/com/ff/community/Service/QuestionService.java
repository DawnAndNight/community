package com.ff.community.Service;

import com.ff.community.dto.PageDTO;
import com.ff.community.dto.QuestionDTO;
import com.ff.community.mapper.QuestionMapper;
import com.ff.community.mapper.UserMapper;
import com.ff.community.model.Question;
import com.ff.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//请求需要组装user的同时需要question的时候，就需要service
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;
    public PageDTO list(Integer page, Integer size) {
        Integer total = questionMapper.count();
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
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        for (Question question : questions){
            User user = userMapper.findById(question.getCreator());
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

    public PageDTO list(Integer id, Integer page, Integer size) {
        Integer total = questionMapper.countById(id);
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
        List<Question> questions = questionMapper.listByUser(id,offset,size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        for (Question question : questions){
            User user = userMapper.findById(question.getCreator());
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

    public QuestionDTO getById(String id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
}
