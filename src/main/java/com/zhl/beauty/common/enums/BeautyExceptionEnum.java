package com.zhl.beauty.common.enums;

import lombok.Getter;

/**
 * 异常枚举
 *
 * @author huanlin-zhl
 * @date 2020/5/2 8:38
 */
public enum BeautyExceptionEnum {

    /**
     * 参数不全
     */
    NULL_PARAM              (100, "参数不全"),

    /**
     * 衣服编号错误
     */
    CLOTH_ID_ERROR          (101, "衣服编号错误，请确认编号"),

    /**
     * 已被预订
     */
    ALREADY_SCHEDULED       (103, "该衣服在该天已经被预定，请选择其他日期或其他衣服"),

    /**
     * 没有该衣服编号对应的衣服
     */
    NULL_CLOTH               (104, "数据库不存在该衣服，请确定衣服编号或重新添加该衣服"),

    /**
     * 衣服已经存在
     */
    CLOTH_EXIST              (105, "该衣服已经存在，不可重复插入"),

    /**
     * 服务器异常
     */
    SERVER_ERROR            (200, "服务器异常");


    @Getter
    private final int code;

    @Getter
    private final String errMsg;

    BeautyExceptionEnum(int code, String errMsg){
        this.code = code;
        this.errMsg = errMsg;
    }
}
