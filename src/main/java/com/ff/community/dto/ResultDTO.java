package com.ff.community.dto;

import com.ff.community.exception.CustomizeErrorCode;
import com.ff.community.exception.CustomizeException;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

@Data
public class ResultDTO {
    private Integer code;
    private String message;

    public static ResultDTO errorof(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }
    public static ResultDTO errorof(CustomizeErrorCode errorCode){
        return errorof(errorCode.getCode(),errorCode.getMessage());
    }
    public static ResultDTO okof(){
        return errorof(200,"评论成功");
    }

    public static ResultDTO errorof(CustomizeException e) {
        return errorof(e.getCode(),e.getMessage());
    }
}
