package com.zhl.beauty.controller;

import com.zhl.beauty.common.result.BeautyResult;
import com.zhl.beauty.domain.entity.ClothScheduledLog;
import com.zhl.beauty.service.ClothScheduledLogService;
import com.zhl.beauty.utils.GsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

/**
 * 衣服预定记录controller
 *
 * @author huanlin-zhl
 * @date 2020/5/2 10:29
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/schedule")
@Slf4j
public class ClothScheduledController {

    private final ClothScheduledLogService clothScheduledLogService;

    @GetMapping(value = "/{clothId}")
    public String findAllScheduledByClothId(@PathVariable String clothId){
        LinkedList<ClothScheduledLog> scheduledLogList = clothScheduledLogService.findAllScheduledLog(clothId);
        return GsonUtils.toJsonString(new BeautyResult<>(scheduledLogList));
    }

    @PostMapping("/insert")
    public String insert(@RequestParam("clothScheduledLog") String clothScheduledLogStr){
        ClothScheduledLog clothScheduledLog = GsonUtils.toEntity(clothScheduledLogStr, ClothScheduledLog.class);
        //参数中clothId以及scheduledTime不能为空
        ClothScheduledLog scheduledLog = clothScheduledLogService.insertLog(clothScheduledLog);
        return GsonUtils.toJsonString(new BeautyResult<>(scheduledLog));
    }

    @PostMapping("/update")
    public String updateById(@RequestParam("clothScheduledLog") String clothScheduledLogStr){
        ClothScheduledLog clothScheduledLog = GsonUtils.toEntity(clothScheduledLogStr, ClothScheduledLog.class);
        //更新时要求参数中的id不能为空
        ClothScheduledLog scheduledLog = clothScheduledLogService.updateLog(clothScheduledLog);
        return GsonUtils.toJsonString(new BeautyResult<>(scheduledLog));
    }
}
