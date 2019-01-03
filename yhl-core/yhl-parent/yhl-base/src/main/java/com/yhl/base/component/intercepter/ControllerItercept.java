package com.yhl.base.component.intercepter;


import com.yhl.base.component.dto.ResultDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerItercept {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultDto handlException(final HttpServletRequest request, final Exception e){
       e.printStackTrace();
        return ResultDto.error(e);
    }
}
