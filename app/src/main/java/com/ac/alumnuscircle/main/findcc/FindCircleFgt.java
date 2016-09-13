/**
 * @author白洋
 * @Date2016.8.28
 * 功能：发现圈子主界面
 */

package com.ac.alumnuscircle.main.findcc;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.main.findcc.findcc_rv.FindCircleItem;

import com.ac.alumnuscircle.module.divdec.GridViewItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class FindCircleFgt extends Fragment implements FindCircleItem.OnItemClickListener{
    private static List<Integer> images;
    private static List<String>titles;
    private  static List<String>circle_id;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.findcc_findcirclefgt, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.findcc_findcirclefgt_rv);
        //网格布局
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        //分割线
        recyclerView.addItemDecoration(new GridViewItemDecoration());
        InitList();
        FindCircleItem findCircleItem = new FindCircleItem(titles,images);
        recyclerView.setAdapter(findCircleItem);
        findCircleItem.setOnItemClickListener(this);
        return view;
    }

    //初始化列表
    private void InitList()
    {
        images = new ArrayList<>();
        titles = new ArrayList<>();
        circle_id = new ArrayList<>();
        images.add(R.mipmap.findcc_carveout);
        images.add(R.mipmap.findcc_software);
        images.add(R.mipmap.findcc_study);
        images.add(R.mipmap.findcc_law);
        images.add(R.mipmap.findcc_electro);
        images.add(R.mipmap.findcc_elec);

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



    }

    @Override
    public void onItemClick(View v, int position) {
        TextView tv = (TextView)v.findViewById(R.id.findcc_rv_findclrcleitem_tv);
        Intent fccdtlIntent = new Intent(ActivityName.fccdtl_FindCricleDetailAct);
        String circleId=circle_id.get(position);

        fccdtlIntent.putExtra("Title",tv.getText().toString());
        fccdtlIntent.putExtra("Id",circleId);

        startActivity(fccdtlIntent);
    }
}