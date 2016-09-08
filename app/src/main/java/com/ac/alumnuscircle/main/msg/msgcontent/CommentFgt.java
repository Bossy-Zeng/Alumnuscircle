/**
 * Created by 曾博晖 on 2016/9/2.
 * @author 曾博晖
 * @date 2016年9月2日12:58:10
 * @verson 1
 * 功能：实现评论界面
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
import com.ac.alumnuscircle.main.mine.minecontent.rvcircle.CircleAdapter;
import com.ac.alumnuscircle.main.msg.msgcontent.commentfgt_rv.CommentAdapter;
import com.ac.alumnuscircle.main.msg.msgcontent.commentfgt_rv.CommentItem;
import com.ac.alumnuscircle.module.divdec.DividerLinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CommentFgt extends Fragment {
    private View view;
    private RecyclerView comment_rv;
    private CommentAdapter commentAdapter;
    private List<CommentItem>data;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.msgcontent_commentfgt,container,false);
        initView();
        initData();
        initRecyclerView();
        return view;
    }

    private void initView() {
        comment_rv=(RecyclerView)view.findViewById(R.id.msgcontent_commentfgt_rv);
        data=new ArrayList<>();
    }

    private void initData(){
        CommentItem comment1=new CommentItem("白洋","公告",
                "学姐的分享很精彩，希望有机会一起合作，联系方式","软件圈",
                "2016年9月8日09:55:06",
                "http://img3.a0bi.com/upload/ttq/20160825/1472114871781.png");
        CommentItem comment2=new CommentItem("曾博晖","公告",
                "感觉您的想法还可以再改进","互联网圈",
                "2016年9月8日09:56:32",
                "http://img5.imgtn.bdimg.com/it/u=3113483255,3550152016&fm=21&gp=0.jpg");
        data.add(comment1);
        data.add(comment2);
    }

    private void initRecyclerView(){
        comment_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        comment_rv.setAdapter(commentAdapter = new CommentAdapter(getActivity(),data));
        comment_rv.addItemDecoration(new DividerLinearItemDecoration(getActivity(),
                DividerLinearItemDecoration.VERTICAL_LIST));
        commentAdapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(),"你点击了"+
                data.get(position).getComment_circlename()+"的评论"
                        ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }
}
