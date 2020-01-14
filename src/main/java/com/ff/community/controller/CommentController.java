package com.ff.community.controller;

import com.ff.community.Service.CommentService;
import com.ff.community.dto.CommentCreateDTO;
import com.ff.community.dto.CommentDTO;
import com.ff.community.dto.ResultDTO;
import com.ff.community.enums.CommentTypeEnum;
import com.ff.community.exception.CustomizeErrorCode;
import com.ff.community.model.Comment;
import com.ff.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                        HttpServletRequest request ){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorof(CustomizeErrorCode.NOT_LOGIN_IN);
        }
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorof(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setContent(commentCreateDTO.getContent());
        comment.setLikeCount(0L);
        comment.setCommentor(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());

        commentService.insert(comment);
        return ResultDTO.okof();

    }
    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List> comments(@PathVariable(name = "id")Long id){
        List<CommentDTO> commentDTOS = commentService.ListByTargetById(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okof(commentDTOS);
    }

}
