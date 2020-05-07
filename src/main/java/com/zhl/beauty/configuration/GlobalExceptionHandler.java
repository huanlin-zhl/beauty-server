package com.zhl.beauty.configuration;

import com.zhl.beauty.common.enums.ResultCodeEnum;
import com.zhl.beauty.common.exception.BeautyException;
import com.zhl.beauty.common.result.BeautyResult;
import com.zhl.beauty.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 全局异常处理器
 *
 * @author huanlin-zhl
 * @date 2020/5/2 8:42
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BeautyException.class)
    @ResponseBody
    public String beautyExceptionHandler(BeautyException e){
        log.warn("后端出现了业务异常， errMsg:{}, exception:{}", e.getErrMsg(), e);
        String jsonString = GsonUtils.toJsonString(new BeautyResult<>(ResultCodeEnum.BUSINESS_ERROR, e.getErrMsg()));
        return jsonString;
    }

    @ExceptionHandler(value = IOException.class)
    @ResponseBody
    public String ioExceptionHandler(IOException e){
        log.warn("io出现了业务异常， errMsg:{}, exception:{}", e.getMessage(), e);
        return GsonUtils.toJsonString(new BeautyResult<>(ResultCodeEnum.BUSINESS_ERROR, e.getMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String exceptionHandler(Exception e){
        log.warn("io出现了业务异常， errMsg:{}, exception:{}", e.getMessage(), e);
        return GsonUtils.toJsonString(new BeautyResult<>(ResultCodeEnum.BUSINESS_ERROR, e.getMessage()));
    }
}
