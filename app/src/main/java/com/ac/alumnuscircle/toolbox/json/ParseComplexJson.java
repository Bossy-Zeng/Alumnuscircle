/**
 * @author 吴正凡
 * @date 16.08.28
 * @version 1
 * 功能：解析复杂Json数据的工具类，把多维嵌套的Json解析成一个一维的Map对象，通过键值对获取。
 * 注释1：每个嵌套的键用下划线叠加起来。
 * 注释2：对外接口为:recursiveParseJson
 * 使用方法：Map<String, Object> jsonDataJar = new HashMap<>();
 * ParseComplexJson.recursiveParseJson(jsonDataJar, sourceJsonDataStr, null);
 */

package com.ac.alumnuscircle.toolbox.json;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ParseComplexJson {

    /**
     * 对外的接口方法，通过这个方法进入递归。
     *
     * @param resultDataJar 收集结果的Map对象
     * @param sourceJsonStr 源Json字符串
     * @param newKey 键的前缀
     */
    public static void recursiveParseJson(Map<String, Object> resultDataJar, String sourceJsonStr, String newKey) {
        if (sourceJsonStr == null || sourceJsonStr.length() == 0) {
            if (newKey == null || newKey.length() == 0) {
                return;
            } else {
                resultDataJar.put(newKey, "");
                return;
            }
        }
        int lengthOfSourceJsonStr = sourceJsonStr.length();
        String start = sourceJsonStr.substring(0, 1);
        String end = sourceJsonStr.substring(lengthOfSourceJsonStr - 1, lengthOfSourceJsonStr);
        if (!((start.equals("{") && end.equals("}")) || (start.equals("[") && end.equals("]")))) {
            if (newKey != null) {
                resultDataJar.put(newKey, sourceJsonStr);
            } else {
                return;
            }
        } else if (start.equals("{") && end.equals("}")) {
            Map<String, Object> indexMap = JsonToMap.toMap(sourceJsonStr);
            Iterator<Map.Entry<String, Object>> iterator = indexMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                if (entry.getValue() instanceof List) {
                    List list = (List) entry.getValue();
                    if (newKey != null) {
                        recursiveParseJsonArray(resultDataJar, list, newKey + "@" + entry.getKey());
                    } else {
                        recursiveParseJsonArray(resultDataJar, list, entry.getKey());
                    }
                } else {
                    if (newKey != null) {
                        recursiveParseJson(resultDataJar, entry.getValue().toString(), newKey + "@" + entry.getKey());
                    } else {
                        recursiveParseJson(resultDataJar, entry.getValue().toString(), entry.getKey());
                    }
                }
            }
        }
    }

    /**
     * 工具方法，解析Json数组。
     *
     * @param resultDataJar  收集结果的Map对象
     * @param sourceJsonList 源Json列表
     * @param newKey 键的前缀
     */
    private static void recursiveParseJsonArray(Map<String, Object> resultDataJar, List sourceJsonList, String newKey) {
        if (sourceJsonList == null || sourceJsonList.size() == 0) {
            if (newKey == null || newKey.length() == 0) {
                return;
            } else {
                resultDataJar.put(newKey, "");
                return;
            }
        }
        for (int i = 0; i < sourceJsonList.size(); i++) {
            if (sourceJsonList.get(i) instanceof List) {
                List subList = (List) sourceJsonList.get(i);
                if (newKey != null) {
                    recursiveParseJsonArray(resultDataJar, subList, newKey + "@" + i);
                } else {
                    return;
                }
            } else {
                if (newKey != null) {
                    recursiveParseJson(resultDataJar, sourceJsonList.get(i).toString(), newKey + "@" + i);
                } else {
                    return;
                }
            }
        }
    }

}
