package com.zhl.beauty.service;

import com.zhl.beauty.domain.entity.Cloth;

import java.util.LinkedList;

/**
 * 衣服服务接口
 *
 * @author huanlin-zhl
 * @date 2020/5/2 8:44
 */
public interface ClothService {

    /**
     * 插入一条衣服数据
     * @param cloth 衣服实例
     * @return 插入的数据
     */
    Cloth insertCloth(Cloth cloth);

    /**
     * 删除衣服
     * @param clothId 衣服的唯一id
     */
    void deleteCloth(String clothId);

    /**
     * 根据衣服Id查询衣服
     * @param clothId 衣服Id
     * @return 查到的衣服实例
     */
    Cloth getClothByClothId(String clothId);
}
