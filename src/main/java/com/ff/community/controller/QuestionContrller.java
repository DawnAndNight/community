package com.ff.community.controller;

import com.ff.community.Service.QuestionService;
import com.ff.community.dto.CommentCreateDTO;
import com.ff.community.dto.CommentDTO;
import com.ff.community.dto.QuestionDTO;
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

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Long id
            ,Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        List<CommentDTO> comments = questionService.ListByQuestionId(id);
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        return "question";
    }
}
