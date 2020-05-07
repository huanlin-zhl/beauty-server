package com.zhl.beauty.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author huanlin-zhl
 * @date 2020/5/3 13:41
 */
public class GsonUtils {

    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    public static String toJsonString(Object object){
        return gson.toJson(object);
    }

    public static <T>T toEntity(String jsonStr, Class<T> type){
        return gson.fromJson(jsonStr, type);
    }
}
