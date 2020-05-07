package com.zhl.beauty.common.exception;

import com.zhl.beauty.common.enums.BeautyExceptionEnum;
import lombok.Getter;

/**
 * 异常类
 *
 * @author huanlin-zhl
 * @date 2020/5/2 8:35
 */
public class BeautyException extends RuntimeException {
    @Getter
    private final int code;

    @Getter
    private final String errMsg;

    public BeautyException(BeautyExceptionEnum beautyExceptionEnum){
        this.code = beautyExceptionEnum.getCode();
        this.errMsg = beautyExceptionEnum.getErrMsg();
    }
}
