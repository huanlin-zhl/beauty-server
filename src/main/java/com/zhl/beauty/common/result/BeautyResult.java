package com.zhl.beauty.common.result;

import com.zhl.beauty.common.enums.ResultCodeEnum;
import lombok.Getter;

/**
 * 返回结果类
 *
 * @author huanlin-zhl
 * @date 2020/5/2 8:34
 */
@Getter
public class BeautyResult<T> {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    /**
     * 结果码
     */
    private final int code;

    /**
     * 返回消息
     */
    private final String msg;

    /**
     * 消息数据
     */
    private T data;

    /**
     * 默认成功构造器
     */
    public BeautyResult(){
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.msg = DEFAULT_SUCCESS_MESSAGE;
    }

    /**
     * 带数据的默认成功构造器
     * @param data 返回的数据
     */
    public BeautyResult(T data){
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.msg = DEFAULT_SUCCESS_MESSAGE;
        this.data = data;
    }

    /**
     * 失败构造器
     * @param resultCode 调用失败错误码
     * @param msg 错误信息
     */
    public BeautyResult(ResultCodeEnum resultCode, String msg){
        this.code = resultCode.getCode();
        this.msg = msg;
    }
}
