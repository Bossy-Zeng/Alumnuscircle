/**
 * @author 吴正凡
 * @date 16.08.28
 * @version 1
 * 功能：封装Gson的使用，把收到的Json字符串变成Map对象。
 */

package com.ac.alumnuscircle.toolbox.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonToMap {

    /**
     * 根据json字符串返回Map对象，外部使用请调用此方法。
     * @param json 源json串
     * @return 目标Map对象，注意值的类型是Object，请自行强制转换类型。
     */
    public static Map<String,Object> toMap(String json){
        return JsonToMap.toMap(JsonToMap.parseJson(json));
    }

    /**
     * 把Json字符串转换成JsonObject。
     * @param json 源Json串
     * @return JsonObject对象
     */
    public static JsonObject parseJson(String json){
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
        return jsonObj;
    }

    /**
     * 将JsonObjec对象转换成Map-List集合
     * @param json 源JsonObjec对象
     * @return Map对象。
     */
    public static Map<String, Object> toMap(JsonObject json){
        Map<String, Object> map = new HashMap<String, Object>();
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        for (Iterator<Map.Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext(); ){
            Map.Entry<String, JsonElement> entry = iter.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value instanceof JsonArray)
                map.put((String) key, toList((JsonArray) value));
            else if(value instanceof JsonObject)
                map.put((String) key, toMap((JsonObject) value));
            else
                map.put((String) key, value);
        }
        return map;
    }

    /**
     * 将JsonArray对象转换成List集合
     * @param json 源JsonArray对象
     * @return  目标List对象
     */
    public static List<Object> toList(JsonArray json){
        List<Object> list = new ArrayList<Object>();
        for (int i=0; i<json.size(); i++){
            Object value = json.get(i);
            if(value instanceof JsonArray){
                list.add(toList((JsonArray) value));
            }
            else if(value instanceof JsonObject){
                list.add(toMap((JsonObject) value));
            }
            else{
                list.add(value);
            }
        }
        return list;
    }
}
