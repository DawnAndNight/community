package com.ff.community.controller;

import com.ff.community.Service.CommentService;
import com.ff.community.Service.QuestionService;
import com.ff.community.dto.CommentDTO;
import com.ff.community.dto.QuestionDTO;
import com.ff.community.enums.CommentTypeEnum;
import com.ff.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionContrller {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Long id
            ,Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relateQuestions = questionService.selectRelate(questionDTO);
        List<CommentDTO> questionComment = commentService.ListByTargetById(id, CommentTypeEnum.QUESTION);
        List<CommentDTO> comment = commentService.ListByTargetById(id, CommentTypeEnum.COMMENT);
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",questionComment);
        model.addAttribute("subComment",comment);
        model.addAttribute("relateQuestions",relateQuestions);
        return "question";
    }
}
