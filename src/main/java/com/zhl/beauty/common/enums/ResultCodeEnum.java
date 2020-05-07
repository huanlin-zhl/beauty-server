package com.zhl.beauty.common.enums;

import lombok.Getter;

/**
 * 返回结果码枚举类
 *
 * @author huanlin-zhl
 * @date 2020/5/2 8:20
 */
public enum ResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS(200),

    /**
     * 业务异常
     */
    BUSINESS_ERROR(300);

    @Getter
    private final int code;

    ResultCodeEnum(int code){
        this.code = code;
    }
}
