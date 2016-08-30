/**
 * @author Zhengfan
 * @date 16.08.29
 * @version 1
 * 功能：参与的圈子的Fragment
 */

package com.ac.alumnuscircle.main.mine.minecontent;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.main.mine.minecontent.rvcircle.CircleAdapter;
import com.ac.alumnuscircle.main.mine.minecontent.rvcircle.CircleItem;
import com.ac.alumnuscircle.module.divdec.DividerLinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class JoinCircle extends Fragment {

    private View view;
    private LayoutInflater layoutInflater;
    private ViewGroup container;

    private RecyclerView rvJoinCircle;
    private List<CircleItem> data;
    private CircleAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.minecontent_join_circle, container, false);
        layoutInflater = inflater;
        this.container = container;
        initView(view);
        initData();
        initRecycleView();
        return view;
    }

    private void initRecycleView() {
        rvJoinCircle.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvJoinCircle.setAdapter(adapter = new CircleAdapter(getActivity(), data));

        rvJoinCircle.addItemDecoration(new DividerLinearItemDecoration(getActivity(),
                DividerLinearItemDecoration.VERTICAL_LIST));
    }

    /**
     * 加载用户数据
     * */
    private void initData() {
        data = new ArrayList<>();
        CircleItem item1=new CircleItem(
                "http://img1.imgtn.bdimg.com/it/u=2385199661,1509060230&fm=21&gp=0.jpg",
                "艺术圈"
        );
        CircleItem item2=new CircleItem(
                "http://img2.imgtn.bdimg.com/it/u=3413454958,4293050372&fm=11&gp=0.jpg",
                "软件圈"
        );
        CircleItem item3=new CircleItem(
                "http://img2.imgtn.bdimg.com/it/u=3413454958,4293050372&fm=11&gp=0.jpg",
                "软件圈"
        );

        data.add(item1);
        data.add(item2);
        data.add(item3);
    }

    private void initView(View view){
        rvJoinCircle=(RecyclerView)view.findViewById(R.id.rv_join_circle);
    }

}
