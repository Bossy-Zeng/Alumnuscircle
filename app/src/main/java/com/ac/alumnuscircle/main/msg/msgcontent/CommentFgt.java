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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ac.alumnuscircle.R;

public class CommentFgt extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.msgcontent_commentfgt,container,false);
        return view;
    }
}
