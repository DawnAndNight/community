package com.ff.community.dto;

import lombok.Data;
//与页面交互使用的类
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
