package com.ff.community.controller;

import com.ff.community.Service.QuestionService;
import com.ff.community.dto.PageDTO;
import com.ff.community.mapper.UserMapper;
import com.ff.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,@PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "2")Integer size){

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        if("question".equals(action)){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的问题展示");
        }
        if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","我的回应");
        }
        PageDTO questionList = questionService.list(user.getId(),page,size);
        model.addAttribute("pageDTO",questionList);

        return "profile";
    }
}
