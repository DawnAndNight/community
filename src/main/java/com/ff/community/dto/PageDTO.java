package com.ff.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PageDTO {
    private List<QuestionDTO> questionDTOs;
    private boolean showPrevious;
    private boolean showfirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer currentpage;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();

    public void SetPage(Integer total, Integer page, Integer size) {
        int totalPage ;
        this.currentpage = page;

        //是否多一页
        if(total % size == 0){
            totalPage = total/size;
        }else{
            totalPage = total/size+1;
        }
        this.totalPage = totalPage;
        //保证数据有七个 左右不大于3
        pages.add(page);
        for(int j = 1 ; j <= 3;j++){
            if(page - j > 0){//前插
                pages.add(0,page-j);
            }
            if(page + j <= totalPage){
                pages.add(page+j);
            }
        }

        //显示上一页
        if(page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        //显示下一页
        if(page == totalPage){
            showNext = false;
        }else {
            showNext = true;
        }
        //显示第一页
        showfirstPage = !pages.contains(1);
        //显示最后一页
        showEndPage = !pages.contains(totalPage);

    }
}
