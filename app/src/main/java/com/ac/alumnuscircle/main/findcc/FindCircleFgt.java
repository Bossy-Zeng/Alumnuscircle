/**
 * @author白洋
 * @Date2016.8.28
 * 功能：发现圈子主界面
 */

package com.ac.alumnuscircle.main.findcc;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.main.findcc.findcc_rv.FindCircleItem;
import com.ac.alumnuscircle.module.divdec.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class FindCircleFgt extends Fragment implements FindCircleItem.OnItemClickListener{
    private List<Integer> images;
    private List<String>titles;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.findcc_findcirclefgt, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.findcc_findcirclefgt_rv);
        //网格布局
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        //分割线
        recyclerView.addItemDecoration(new DividerGridItemDecoration(view.getContext()));
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

        images.add(R.mipmap.addr);
        images.add(R.mipmap.back);
        images.add(R.mipmap.dynamic);
        images.add(R.mipmap.filter);
        images.add(R.mipmap.mainact_findcircle);

        titles.add("一个");
        titles.add("两个");
        titles.add("三个");
        titles.add("四个");
        titles.add("五个");
    }

    @Override
    public void onItemClick(View v, int position) {
        TextView tv = (TextView)v.findViewById(R.id.circleTv);
        Toast.makeText(getActivity(),tv.getText().toString(),Toast.LENGTH_SHORT).show();
    }
}