package com.zhl.beauty.service;

import com.zhl.beauty.domain.entity.ClothScheduledLog;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 记录服务接口类
 *
 * @author huanlin-zhl
 * @date 2020/5/2 10:30
 */
public interface ClothScheduledLogService {

    /**
     * 插入一条日志记录
     * @param clothScheduledLog 记录实例
     * @return 插入的记录
     */
    ClothScheduledLog insertLog(ClothScheduledLog clothScheduledLog);

    /**
     * 更新记录
     * @param clothScheduledLog 记录实例
     * @return 更改后记录
     */
    ClothScheduledLog updateLog(ClothScheduledLog clothScheduledLog);

    /**
     * 通过衣服Id和预定时间查询一条记录
     * @param clothId 衣服Id
     * @param scheduledTime 预定时间
     * @return 查找到的记录
     */
    ClothScheduledLog findLogByClothIdAndScheduledTime(String clothId, Date scheduledTime);

    /**
     * 通过衣服Id，查找所有预定时间，并按时间倒序
     * @param clothId 衣服id
     * @return 查找到的数据
     */
    LinkedList<ClothScheduledLog> findAllScheduledLog(String clothId);
}
