/**
 * @author Zhengfan
 * @date 16.09.01
 * @version 1
 * 功能：解析高级筛选中年份的数据。
 */

package com.ac.alumnuscircle.auth.register.parsedata;

import java.util.ArrayList;
import java.util.List;

public class ParseYear {

    private int[] years;
    private final int start = 1952;
    private final int end = 2016;

    public ParseYear(){
        init();
    }

    private void init(){
        final int length = (end - start) + 1;
        years = new int[length];
        for(int i = 0; i < length; i++){
            years[i] = start + i;
        }
    }

    public List<String> parseYearLeftData(){
        List<String> result = new ArrayList<String>();
        for(int i = 0; i < years.length; i++){
            result.add(String.valueOf(years[i]));
        }
        return result;
    }

    public List<String> parseYearRightData(String left){
        List<String> result = new ArrayList<String>();
        for(int i = 0; i < years.length; i++){
            if(years[i] >= Integer.valueOf(left)){
                result.add(String.valueOf(years[i]));
            }
        }
        return result;
    }
}
