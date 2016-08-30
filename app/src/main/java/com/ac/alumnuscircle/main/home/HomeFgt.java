/**
 * @author白洋
 * @Date 2016/8/28.
 * @version 2
 * 主页的界面
 */

package com.ac.alumnuscircle.main.home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.main.home.home_rv.HomeItem;

import java.util.ArrayList;
import java.util.List;


public class HomeFgt extends Fragment implements HomeItem.OnItemClickListener {


    private List<String> imagesUrl;
    private List<String>titles;
    private RecyclerView recyclerView;
    private int lastPosition;//创建圈子的位置
    private View view;
    private  HomeItem homeItem;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_homefgt, container, false);
        Init();
        return view;
    }

    private void Init()
    {

        recyclerView=(RecyclerView)view.findViewById(R.id.home_homefgt_rv);
        //网格布局
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));

        InitList();
        homeItem = new HomeItem(titles,imagesUrl);
        recyclerView.setAdapter(homeItem);
        homeItem.setOnItemClickListener(this);
    }

    //初始化列表
    private void InitList()
    {
        imagesUrl = new ArrayList<>();
        titles = new ArrayList<>();

       imagesUrl.add("http://img0.imgtn.bdimg.com/it/u=3766443758,1529519468&fm=21&gp=0.jpg");
       imagesUrl.add("http://img2.imgtn.bdimg.com/it/u=2069918470,3277439936&fm=21&gp=0.jpg");
        imagesUrl.add("http://img2.imgtn.bdimg.com/it/u=4075937517,1358300463&fm=21&gp=0.jpg");

        titles.add("商业圈");
        titles.add("经管圈");
        titles.add("东南大学圈");


        lastPosition = imagesUrl.size();
    }

    @Override
    public void onItemClick(View v, int position) {
        if(position!=lastPosition) {
            TextView tv =(TextView)v.findViewById(R.id.home_rv_homeitem_tv);
            Toast.makeText(getActivity(), tv.getText().toString() + position, Toast.LENGTH_SHORT).show();
        }
        else
        {
            homeItem.addItem("新的项","http://img5.imgtn.bdimg.com/it/u=1846948884,880298315&fm=206&gp=0.jpg");
            lastPosition++;
        }
    }
}
