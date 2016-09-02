/**
 * Created by 曾博晖 on 2016/9/2.
 * @author 曾博晖
 * @date 2016年9月2日12:56:29
 * @verson 1
 * 功能：系统通知碎片
 */
package com.ac.alumnuscircle.main.msg.msgcontent;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import com.ac.alumnuscircle.R;



public class NotifyFgt extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.msgcontent_notifyfgt,container,false);
        return view;
    }
}
