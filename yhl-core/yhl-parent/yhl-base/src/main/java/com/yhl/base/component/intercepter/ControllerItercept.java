package com.yhl.base.component.intercepter;


import com.yhl.base.component.constant.MyConst;
import com.yhl.base.component.dto.ResultDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class ControllerItercept {
     Logger logger = LogManager.getLogger(MyConst.LOGGER_NAME);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultDto handlException(final HttpServletRequest request, final Exception e){
        logger.error(e.getMessage());
        return ResultDto.error(e);
    }
}
