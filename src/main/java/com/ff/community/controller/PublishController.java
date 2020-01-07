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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String publish(@PathVariable(name = "id") Long id
                          ,Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescriptions());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
         @RequestParam("title") String title,
         @RequestParam("description") String description,
         @RequestParam("tag") String tag,
        @RequestParam(name = "id",required = false) Long id,
        HttpServletRequest request,
        Model model) {

//处于未登录状态

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title == null || (title.trim()).equals("")){
            model.addAttribute("error", "标题不为空");
            return "publish";
        }
        if(description == null || (description.trim()).equals("")){
            model.addAttribute("error", "内容不为空");
            return "publish";
        }
        if(tag == null || (tag.trim()).equals("")){
            model.addAttribute("error", "标签不为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescriptions(description);
        question.setTag(tag);
        question.setId(id);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(user.getId());
        questionService.updateOrCreate(question);
        return "redirect:/";
    }
}
