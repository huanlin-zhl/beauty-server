package com.zhl.beauty.service.impl;

import com.zhl.beauty.common.enums.BeautyExceptionEnum;
import com.zhl.beauty.common.exception.BeautyException;
import com.zhl.beauty.domain.entity.Cloth;
import com.zhl.beauty.domain.entity.ClothScheduledLog;
import com.zhl.beauty.mapper.ClothMapper;
import com.zhl.beauty.mapper.ClothScheduledLogMapper;
import com.zhl.beauty.service.ClothScheduledLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 日志服务实现类
 *
 * @author huanlin-zhl
 * @date 2020/5/2 10:30
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ClothScheduledLogServiceImpl implements ClothScheduledLogService {

    private final ClothScheduledLogMapper clothScheduledLogMapper;
    private final ClothMapper clothMapper;

    private final String BASE_LOCK_PREFIX = "cloth-schedule-service:";

    @Override
    public ClothScheduledLog insertLog(ClothScheduledLog scheduledLog) {
        //插入记录前置检验
        insertPreCheck(scheduledLog);

        //同步插入
        synchronized ((BASE_LOCK_PREFIX + "insert:" + scheduledLog.getClothId() + "," + scheduledLog.getScheduledTime()).intern()){
            if(!isScheduledLogExist(scheduledLog)){
                //设置初始值
                scheduledLog.setCreateTime(new Date());
                scheduledLog.setUpdateTime(new Date());

                //插入实例
                clothScheduledLogMapper.insert(scheduledLog);
            }
        }

        //返回数据
        return scheduledLog;
    }

    @Override
    public ClothScheduledLog updateLog(ClothScheduledLog scheduledLog) {
        //校验参数
        checkLog(scheduledLog);

        //同步更新
        synchronized ((BASE_LOCK_PREFIX + "update:" + scheduledLog.getClothId() + "," + scheduledLog.getScheduledTime()).intern()){
            //校验是否存在该记录，如果存在则进行更新，不存在则重新插入
            //新建一个查找对象，预定时间设为null，因为修改时传入预定时间为准备修改的时间，不设为null，查不到信息
            ClothScheduledLog scheduleCheckLog = new ClothScheduledLog();
            BeanUtils.copyProperties(scheduledLog, scheduleCheckLog);
            scheduleCheckLog.setScheduledTime(null);
            if(isScheduledLogExist(scheduleCheckLog)){
                //进行更新
                //校验记录中id是否存在，没有id无法进行更新
                if(scheduledLog.getId() == null){
                    log.warn("主键id为空，无法进行更新， scheduledLog:{}", scheduledLog);
                    throw new BeautyException(BeautyExceptionEnum.NULL_PARAM);
                }
                scheduledLog.setUpdateTime(new Date());
                clothScheduledLogMapper.updateByPrimaryKey(scheduledLog);
            }else {
                //进行插入
                insertLog(scheduledLog);
            }
        }

        //返回数据
        return clothScheduledLogMapper.selectOne(scheduledLog);
    }

    @Override
    public ClothScheduledLog findLogByClothIdAndScheduledTime(String clothId, Date scheduledTime) {
        ClothScheduledLog scheduledLog = ClothScheduledLog.builder()
                .clothId(clothId)
                .scheduledTime(scheduledTime)
                .build();

        //校验参数
        checkLog(scheduledLog);

        return clothScheduledLogMapper.selectOne(scheduledLog);
    }

    @Override
    public LinkedList<ClothScheduledLog> findAllScheduledLog(String clothId) {
        //校验是否存在该衣服，不存在直接抛异常
        if(!isClothExist(clothId)){
            log.warn("不存在该衣服，无法查找相关预定记录, clothId:{}", clothId);
            throw new BeautyException(BeautyExceptionEnum.NULL_CLOTH);
        }

        //查询该衣服的所有记录
        List<ClothScheduledLog> scheduledLogList = clothScheduledLogMapper.select(ClothScheduledLog.builder()
                .clothId(clothId)
                .build());

        //转成LinkedList，并按照预定时间进行倒排（为了防止序列化过程中乱序，因此转为LinkedList）
        LinkedList<ClothScheduledLog> logLinkedList = new LinkedList<>(scheduledLogList);
        logLinkedList.sort((log1, log2) -> {
            Date date1 = log1.getScheduledTime();
            Date date2 = log2.getScheduledTime();
            return Long.compare(date2.getTime(), date1.getTime());
        });

        //返回数据
        return logLinkedList;
    }

    /**
     * 插入新记录前置检查
     * @param scheduledLog 准备插入的预定记录
     */
    private void insertPreCheck(ClothScheduledLog scheduledLog){
        //校验参数
        checkLog(scheduledLog);

        //检验是否存在该衣服
        if(!isClothExist(scheduledLog.getClothId())){
            log.warn("不存在该衣服，无法进行预定 clothId:{}, scheduleTime:{}",
                    scheduledLog.getClothId(), scheduledLog.getScheduledTime());
            throw new BeautyException(BeautyExceptionEnum.NULL_CLOTH);
        }

        //校验该记录是否已经存在
        if(isScheduledLogExist(scheduledLog)){
            log.warn("指定衣服在指定日期已被预订 clothId:{}, scheduleTime:{}",
                    scheduledLog.getClothId(), scheduledLog.getScheduledTime());
            throw new BeautyException(BeautyExceptionEnum.ALREADY_SCHEDULED);
        }
    }

    /**
     * 校验日志记录参数是否完整
     * @param clothScheduledLog 日志实例
     */
    private void checkLog(ClothScheduledLog clothScheduledLog){
        if(clothScheduledLog.getClothId() == null || clothScheduledLog.getScheduledTime() == null){
            log.warn("参数为空 ： scheduledLog:{}", clothScheduledLog);
            throw new BeautyException(BeautyExceptionEnum.NULL_PARAM);
        }
    }

    /**
     * 校验数据库中是否存在该记录，存在则抛异常
     * @param scheduledLog 带校验记录
     * @return 该记录是否存在
     */
    private boolean isScheduledLogExist(ClothScheduledLog scheduledLog){
        ClothScheduledLog scheduledLogInDatabase = clothScheduledLogMapper.selectOne(scheduledLog);
        return scheduledLogInDatabase != null;
    }

    /**
     * 校验衣服是否存在
     * @param clothId 衣服Id
     * @return 是否存在该衣服
     */
    private boolean isClothExist(String clothId){
        Cloth clothInDatabase = clothMapper.selectOne(Cloth.builder()
                .clothId(clothId)
                .build());

        return clothInDatabase != null;
    }
}
