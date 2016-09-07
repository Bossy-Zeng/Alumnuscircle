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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.main.msg.msgcontent.notifyfgt_rv.NotifyAdapter;
import com.ac.alumnuscircle.main.msg.msgcontent.notifyfgt_rv.NotifyItem;
import com.ac.alumnuscircle.module.divdec.DividerLinearItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class NotifyFgt extends Fragment {
    private RecyclerView notify_rv;
    private List<NotifyItem> data;
    private NotifyAdapter notifyAdapter;

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.msgcontent_notifyfgt,container,false);
        initView();
        initData();
        initRecyclerView();
        return view;
    }
    /**
     * 初始化控件
     * 2016-09-07 14:12:46
     * 曾博晖
     * 创建
     * */
    private void initView() {
        notify_rv=(RecyclerView)view.findViewById(R.id.msgcontent_notifyfgt_rv);
        data=new ArrayList<>();
    }
    /**
     * 初始化数据
     * 2016年9月7日15:33:01
     * 曾博晖
     * 创建
     * */
    private void initData(){
        NotifyItem notifyItem1=new NotifyItem(
                //这里用于测试加载本地Res目录下的图片，之后系统图片的URL直接是服务器传来的数据
                "res://com.ac.alumnuscircle/"+R.mipmap.msg_notifyfgt_notify,
                "系统通知", "由于你的帅气，被校友圈选为形象大使",
                "校友圈系统","2016年9月7日15:35:13");
        NotifyItem notifyItem2=new NotifyItem(
                "http://img1.imgtn.bdimg.com/it/u=293719508,1004767985&fm=21&gp=0.jpg",
                "圈子通知","白洋大神申请加入GIT圈子",
                "GIT圈子","2016年9月7日15:48:22");
        NotifyItem notifyItem3=new NotifyItem(
                "http://img5.imgtn.bdimg.com/it/u=3113483255,3550152016&fm=21&gp=0.jpg",
                "圈子通知","互联网交流圈发布了新公告",
                "互联网交流圈","2016年9月7日15:50:09");
        data.add(notifyItem1);
        data.add(notifyItem2);
        data.add(notifyItem3);
    }
    /**
     * 为RecyclerView添加数据
     * */
    private void initRecyclerView(){
        notify_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        notify_rv.setAdapter(notifyAdapter = new NotifyAdapter(getActivity(),data));
        notify_rv.addItemDecoration(new DividerLinearItemDecoration(getActivity(),
                DividerLinearItemDecoration.VERTICAL_LIST));
        notifyAdapter.setOnItemClickListener(new NotifyAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(),"你点击了"+
                        data.get(position).getNotify_Name()+"的通知",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }
}
