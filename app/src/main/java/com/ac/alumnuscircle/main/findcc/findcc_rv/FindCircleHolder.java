/**
 * @author白洋
 * @Date 2016/8/28.
 * @version 2
 * 发现圈子的holder
 */
package com.ac.alumnuscircle.main.findcc.findcc_rv;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ac.alumnuscircle.R;


public  class FindCircleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected  ImageView imageView;
    protected TextView textView;
    protected FindCircleItem.OnItemClickListener onItemClickListener;
    protected FindCircleHolder(View itemView,FindCircleItem.OnItemClickListener onItemClick) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.findcc_rv_findclrcleitem_img);
        textView = (TextView)itemView.findViewById(R.id.findcc_rv_findclrcleitem_tv);
        this.onItemClickListener = onItemClick;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(onItemClickListener!=null)
            onItemClickListener.onItemClick(v,getPosition());

    }
}