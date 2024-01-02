package com.tg.base.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(XueChengPlusException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse customException(XueChengPlusException e){

        log.error("系统异常{}",e.getMessage(),e);

        if (e.getMessage().equals("课程计划信息还有子级信息，无法操作")) {
            return new RestErrorResponse(e.getMessage(),"120409");
        }
        return new RestErrorResponse(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse customException(Exception e){

        log.error("系统异常{}",e.getMessage(),e);

        return new RestErrorResponse(CommonError.UNKOWN_ERROR.getErrMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException e){

        BindingResult bindingResult = e.getBindingResult();
        List<String> erros = new ArrayList<>();

        bindingResult.getFieldErrors().stream().forEach(item -> erros.add(item.getDefaultMessage()));

        String errMessage = StringUtils.join(erros, ",");

        log.error("系统异常{}",e.getMessage(),errMessage);

        return new RestErrorResponse(errMessage);
    }

}
