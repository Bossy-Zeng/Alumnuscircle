package com.ac.alumnuscircle.main.home;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/10.
 */
public class CircleIdInfo {
    /**
     * 圈子Id和名字对应的一个HashMap
     * 第一个表示ID,第二个表示圈子名
     * 2016年9月10日19:23:12
     * 曾博晖 创建
     *
     titles.add("院系圈");
     titles.add("社团圈");
     titles.add("职业圈");
     titles.add("地域圈");
     titles.add("兴趣圈");
     titles.add("创业圈");
     circle_id.add("57cd04e8ea77f7753a8f3c28");

     circle_id.add("57cbd6747019c95ec2d856eb");

     circle_id.add("57cd049d55c400f83aa1384c");

     circle_id.add("57cbd6747019c95ec2d856eb");

     circle_id.add("57bdcad0d0146385e6abb6be");

     circle_id.add("57cd04ba55c400f83aa1384d");
     * */
    public static Map<String ,String>CircleIdMap=new HashMap<>();
    public CircleIdInfo(){
        CircleIdMap.put("57cd04e8ea77f7753a8f3c28","院系圈");
        CircleIdMap.put("57cbd6747019c95ec2d856eb","社团圈");
        CircleIdMap.put("57cd049d55c400f83aa1384c","职业圈");
        CircleIdMap.put("57cbd6747019c95ec2d856eb","地域圈");
        CircleIdMap.put("57bdcad0d0146385e6abb6be","兴趣圈");
        CircleIdMap.put("57cd04ba55c400f83aa1384d","创业圈");
    }
}
