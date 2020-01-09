package com.ff.community.advice;

import com.alibaba.fastjson.JSON;
import com.ff.community.dto.ResultDTO;
import com.ff.community.exception.CustomizeErrorCode;
import com.ff.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handleControllerException(HttpServletRequest request, Throwable ex
    , Model model, HttpServletResponse response) {
        String contentType = request.getContentType();
        ResultDTO resultDTO = null;
        if("application/json".equals(contentType)||"application/json;charset=UTF-8".equals(contentType)){
            if (ex instanceof CustomizeException) {
                 resultDTO = ResultDTO.errorof((CustomizeException)ex);
            } else {
                resultDTO = ResultDTO.errorof(CustomizeErrorCode.SYS_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.print(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }else {
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }

            return new ModelAndView("error");
        }
    }
}
