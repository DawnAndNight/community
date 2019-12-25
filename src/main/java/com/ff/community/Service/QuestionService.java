package com.ff.community.Service;

import com.ff.community.dto.QuestionDTO;
import com.ff.community.mapper.QuestionMapper;
import com.ff.community.mapper.UserMapper;
import com.ff.community.model.Question;
import com.ff.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    public List<QuestionDTO> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions){
            User user = userMapper.findById(question.getId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            if(user != null){
                questionDTO.setUser(user);
                questionDTOS.add(questionDTO);
            }
        }
        return questionDTOS;
    }
}
