package com.zhl.beauty.controller;

import com.zhl.beauty.common.result.BeautyResult;
import com.zhl.beauty.domain.entity.Cloth;
import com.zhl.beauty.service.ClothService;
import com.zhl.beauty.utils.GsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * 衣服controller
 *
 * @author huanlin-zhl
 * @date 2020/5/2 8:46
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/cloth")
public class ClothController {

    private final ClothService clothService;

    @PostMapping("/add")
    public String uploadCloth(@RequestParam("clothId") String clothId, @RequestParam("clothImage") MultipartFile imageFile) {
        //imageFile.getOriginalFilename() 获取图片文件的原始名称 eg：123.jpg(为防止名称重复，前缀添加毫秒信息)
        //new File(Objects.requireNonNull(imageFile.getOriginalFilename())) 创建一个与图片原始名称一样的文件
        //imageFile.transferTo() 将文件保存至本地，文件目录在application.yum中spring.servlet.multipart.location进行指定
        String pictureOriginalFilename = "" + System.currentTimeMillis() + imageFile.getOriginalFilename();
        //插入衣服新记录
        Cloth cloth = clothService.insertCloth(Cloth.builder()
                .clothId(clothId)
                .picture(pictureOriginalFilename)
                .build(), imageFile);

        return GsonUtils.toJsonString(new BeautyResult<>(cloth));
    }

    @DeleteMapping("/delete/{clothId}")
    public String deleteCloth(@PathVariable String clothId){
        clothService.deleteCloth(clothId);
        return GsonUtils.toJsonString(new BeautyResult<>());
    }

    @GetMapping("/{clothId}")
    public String getClothById(@PathVariable String clothId){
        Cloth cloth = clothService.getClothByClothId(clothId);
        return GsonUtils.toJsonString(new BeautyResult<>(cloth));
    }

}
