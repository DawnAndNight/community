package com.ff.community.controller;

import com.ff.community.Service.QuestionService;
import com.ff.community.dto.QuestionDTO;
import com.ff.community.mapper.QuestionMapper;
import com.ff.community.mapper.UserMapper;
import com.ff.community.model.Question;
import com.ff.community.model.User;
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
        ,Model model){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie != null && cookies.length!=0) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findBytoken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        List<QuestionDTO> questionList = questionService.list();
        model.addAttribute("questionList",questionList);
        return "index";
    }
}
