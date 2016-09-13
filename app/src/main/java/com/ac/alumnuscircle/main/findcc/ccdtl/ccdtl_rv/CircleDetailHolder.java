
/**
 * @author 白洋
 * @Date 2016/8/28.
 * @version 2
 * 圈子详情的holder
 */
package com.ac.alumnuscircle.main.findcc.ccdtl.ccdtl_rv;

import android.support.v7.widget.RecyclerView;
import android.view.View;


public class CircleDetailHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private CircleDetailItem.OnItemClickListener onItemClickListener;
    public CircleDetailHolder(View itemView,CircleDetailItem.OnItemClickListener onItemClick) {
        super(itemView);
        this.onItemClickListener = onItemClick;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(onItemClickListener!=null)
            onItemClickListener.onItemClick(v,getPosition());
    }
}

