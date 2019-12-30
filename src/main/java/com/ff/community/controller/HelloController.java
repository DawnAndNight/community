package com.ff.community.controller;

import com.ff.community.Service.QuestionService;
import com.ff.community.dto.PageDTO;
import com.ff.community.dto.QuestionDTO;
import com.ff.community.mapper.QuestionMapper;
import com.ff.community.mapper.UserMapper;
import com.ff.community.model.Question;
import com.ff.community.model.User;
import org.h2.store.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class HelloController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request
        ,Model model,@RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "2")Integer size){

        PageDTO questionList = questionService.list(page,size);
        model.addAttribute("pageDTO",questionList);
        return "index";
    }
}
