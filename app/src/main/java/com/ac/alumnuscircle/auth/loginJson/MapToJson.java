/**
 * @author zhengfanw
 * @date 16.07.22
 * 封装Gson的使用，把要发送的Map对象转换为Json字符串。
 */

package com.ac.alumnuscircle.auth.loginJson;

import com.google.gson.Gson;

import java.util.Map;

public class MapToJson {
    public static <T> String toJson(Map<String, T> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }
}
