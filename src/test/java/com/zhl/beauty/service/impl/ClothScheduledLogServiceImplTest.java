package com.zhl.beauty.service.impl;

import com.zhl.beauty.domain.entity.ClothScheduledLog;
import com.zhl.beauty.mapper.ClothScheduledLogMapper;
import com.zhl.beauty.service.ClothScheduledLogService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;


/**
 * @author huanlin-zhl
 * @date 2020/5/2 15:32
 */
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ClothScheduledLogServiceImplTest {

    private final ClothScheduledLogService clothScheduledLogService;

    private final ClothScheduledLogMapper clothScheduledLogMapper;

    @Test
    void insertLog() {
        for(int i = 0; i < 10; i++){
            ClothScheduledLog scheduledLog = ClothScheduledLog.builder()
                    .clothId("jq1")
                    .scheduledTime(new Date())
                    .build();
            clothScheduledLogService.insertLog(scheduledLog);
        }


    }

    @Test
    void updateLog() {
        ClothScheduledLog scheduledLog = clothScheduledLogMapper.selectByPrimaryKey(8);
        scheduledLog.setScheduledTime(new Date());
        clothScheduledLogService.updateLog(scheduledLog);
    }

    @Test
    void findLogByClothIdAndScheduledTime() {
    }

    @Test
    void findAllScheduledLog() {
        List<ClothScheduledLog> scheduledLog = clothScheduledLogService.findAllScheduledLog("jq1");
        for(int i = 0; i < scheduledLog.size(); i++){
            System.out.println(scheduledLog.get(i).getScheduledTime());
        }
    }

    @Test
    void selectTest(){
        ClothScheduledLog log = clothScheduledLogMapper.selectOne(ClothScheduledLog.builder()
                .id(3)
                .build());

        ClothScheduledLog log1 = clothScheduledLogMapper.selectOne(log);
        System.out.println(log1);
    }
}