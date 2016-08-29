/**
 * @author Zhengfan
 * @date 16.08.28
 * 封装Gson的使用，把要发送的Map对象转换为Json字符串。
 */

package com.ac.alumnuscircle.toolbox.json;

import com.google.gson.Gson;

import java.util.Map;

public class MapToJson {

    /**
     * 把一个Map对象转换成Json字符串。
     * @param map 源Map对象
     * @param <T> 泛型
     * @return 目标Json字符串
     */
    public static <T> String toJson(Map<String, T> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }
}
