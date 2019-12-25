package com.ff.community.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String descriptions;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;

}
