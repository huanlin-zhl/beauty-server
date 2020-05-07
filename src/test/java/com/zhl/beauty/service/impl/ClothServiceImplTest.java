package com.zhl.beauty.service.impl;

import com.zhl.beauty.domain.entity.Cloth;
import com.zhl.beauty.service.ClothService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author huanlin-zhl
 * @date 2020/5/2 15:11
 */
@SpringBootTest
class ClothServiceImplTest {

    @Autowired
    private ClothService clothService;

    @Test
    void insertCloth() {
        for(int i = 0; i < 10; i++){
            Cloth cloth = Cloth.builder()
                    .clothId(getClothId(i+1))
                    .picture(getPictureLocation(i+1))
                    .build();
            clothService.insertCloth(cloth);
        }

    }

    String getClothId(Integer count){
        return "jq" + count;
    }

    String getPictureLocation(Integer integer){
        return "H:\\ideaproject\\picture\\test" + integer + ".jpg";
    }
}