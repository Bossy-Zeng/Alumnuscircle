
/**
 * @author白洋
 * @Date 2016/8/28.
 * @version 2
 * 圈子详情的适配器
 */
package com.ac.alumnuscircle.main.findcc.ccdtl.ccdtl_rv;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ac.alumnuscircle.R;

/**
 * Created by 白洋
 * on 2016/8/28.
 */
public class CircleDetailItem extends RecyclerView.Adapter<CircleDetailHolder> {
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
    @Override
    public CircleDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ccdtl_rv_detailitem,parent,false);
        CircleDetailHolder detailHolder = new CircleDetailHolder(view,onItemClickListener);
        return detailHolder;
    }

    @Override
    public void onBindViewHolder(CircleDetailHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
