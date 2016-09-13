/**
 * @author白洋
 * @Date 2016/8/28.
 * @version 2
 * 发现圈子的适配器
 */

package com.ac.alumnuscircle.main.findcc.findcc_rv;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.ac.alumnuscircle.R;

import java.util.List;

/**
 * Created by 白洋
 * 2016年9月13日15:45:54
 */
public class FindCircleItem extends RecyclerView.Adapter<FindCircleHolder> {
    private List<String>names;//对应模块的标题
    private List<Integer>images;//对应模块的图片
    private OnItemClickListener onItemClickListener;

    //点击事件的接口
    public interface OnItemClickListener
    {
        void onItemClick(View v, int position);//实现的方法
    }

    //设置点击事件的接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        if(onItemClickListener!=null)
            this.onItemClickListener = onItemClickListener;
    }
    public FindCircleItem(List<String> names, List<Integer> images)
    {
        this.images = images;
        this.names = names;
    }
    @Override
    public FindCircleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载每一项的视图
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.findcc_rv_findcircleitem,parent,false);
       FindCircleHolder findCircleHolder = new FindCircleHolder(itemView,onItemClickListener);
        return findCircleHolder;
    }

    @Override
    public void onBindViewHolder(FindCircleHolder holder, int position) {
        holder.textView.setText(names.get(position));
        holder.imageView.setBackgroundResource(images.get(position));
    }



    @Override
    public int getItemCount() {
        return names.size();
    }



}
