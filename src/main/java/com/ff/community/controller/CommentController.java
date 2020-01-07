package com.ff.community.controller;

import com.ff.community.Service.CommentService;
import com.ff.community.dto.CommentDTO;
import com.ff.community.dto.ResultDTO;
import com.ff.community.exception.CustomizeErrorCode;
import com.ff.community.mapper.CommentMapper;
import com.ff.community.model.Comment;
import com.ff.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                        HttpServletRequest request ){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorof(CustomizeErrorCode.NOT_LOGIN_IN);
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setContent(commentDTO.getContent());
        comment.setLikeCount(0L);
        comment.setCommentor(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());

        commentService.insert(comment);
        return ResultDTO.okof();

    }
}
