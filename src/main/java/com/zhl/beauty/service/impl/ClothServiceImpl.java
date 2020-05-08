package com.zhl.beauty.service.impl;

import com.zhl.beauty.common.enums.BeautyExceptionEnum;
import com.zhl.beauty.common.exception.BeautyException;
import com.zhl.beauty.domain.entity.Cloth;
import com.zhl.beauty.domain.entity.ClothScheduledLog;
import com.zhl.beauty.mapper.ClothMapper;
import com.zhl.beauty.mapper.ClothScheduledLogMapper;
import com.zhl.beauty.service.ClothService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * 衣服接口实现类
 *
 * @author huanlin-zhl
 * @date 2020/5/2 8:45
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ClothServiceImpl implements ClothService {

    @Value("${PicturePath}")
    private String picturePath;
    private final String BASE_LOCK_PREFIX = "cloth-service:";

    private final ClothMapper clothMapper;
    private final ClothScheduledLogMapper clothScheduledLogMapper;

    @Override
    public Cloth insertCloth(Cloth cloth, MultipartFile clothImage) {
        insertPreCheck(cloth);

        //同步插入
        synchronized ((BASE_LOCK_PREFIX + "insert:" + cloth.getClothId()).intern()){
            if(!isClothExist(cloth.getClothId())){
                try {
                    clothImage.transferTo(new File(Objects.requireNonNull(cloth.getPicture())));
                } catch (IOException e) {
                    throw new BeautyException(BeautyExceptionEnum.IMAGE_TRANSFER_ERROR);
                }
                //设置初始属性
                cloth.setCreateTime(new Date());
                //插入实例
                clothMapper.insert(cloth);
            }
        }

        //返回数据
        return cloth;
    }

    @Override
    public void deleteCloth(String clothId) {
        //查询衣服，若不存在直接返回
        if(!isClothExist(clothId)){
            return;
        }

        //同步删除
        synchronized ((BASE_LOCK_PREFIX + "delete:" + clothId).intern()){
            if(isClothExist(clothId)){
                Cloth clothInDatabase = clothMapper.selectOne(Cloth.builder()
                        .clothId(clothId)
                        .build());

                //删除图片
                File clothImageFile = new File(picturePath + clothInDatabase.getPicture());
                deleteFile(clothImageFile);
                //删除数据库中的记录
                clothMapper.delete(clothInDatabase);
                clothScheduledLogMapper.delete(ClothScheduledLog.builder()
                        .clothId(clothId)
                        .build());
            }
        }
    }

    @Override
    public Cloth getClothByClothId(String clothId) {
        Cloth cloth = clothMapper.selectOne(Cloth.builder()
                .clothId(clothId)
                .build());
        if(cloth == null){
            log.warn("未查找到该衣服，请确定衣服编号 clothId:{}", clothId);
            throw new BeautyException(BeautyExceptionEnum.CLOTH_ID_ERROR);
        }
        return cloth;
    }

    /**
     * 删除文件
     * @param file 文件
     */
    private void deleteFile(File file){
        if(file.exists() && file.isFile()){
            boolean delete = file.delete();
            if(!delete){
                log.warn("文件删除失败，fileName:{}, filePath:{}", file.getName(), file.getAbsolutePath());
            }
        }else {
            log.warn("文件不存在，fileName:{}, filePath:{}", file.getName(), file.getAbsolutePath());
        }
    }

    /**
     * 插入新衣服前置检查
     * @param cloth 待检查衣服实例
     */
    private void insertPreCheck(Cloth cloth){
        //校验参数是否为空
        checkCloth(cloth);

        //校验衣服是否已经存在
        if(isClothExist(cloth.getClothId())){
            log.warn("该衣服已经存在，不可重复插入, cloth：{}", cloth);
            throw new BeautyException(BeautyExceptionEnum.CLOTH_EXIST);
        }
    }

    /**
     * 校验衣服是否存在
     * @param clothId 衣服Id
     * @return 校验结果
     */
    private boolean isClothExist(String clothId){
        Cloth clothInDatabase = clothMapper.selectOne(Cloth.builder()
                .clothId(clothId)
                .build());
        return clothInDatabase != null;
    }

    /**
     * 检验衣服参数
     * @param cloth 衣服实例
     */
    private void checkCloth(Cloth cloth){
        if(cloth.getClothId() == null || cloth.getPicture() == null){
                log.warn("参数为空 ： scheduledLog:{}", cloth);
                throw new BeautyException(BeautyExceptionEnum.NULL_PARAM);

        }
    }
}
