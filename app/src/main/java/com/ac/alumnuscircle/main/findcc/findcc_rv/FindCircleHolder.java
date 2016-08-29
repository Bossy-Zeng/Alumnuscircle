package com.ac.alumnuscircle.main.findcc.findcc_rv;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ac.alumnuscircle.R;

/**
 * Created by 15359 on 2016/8/28.
 */
public  class FindCircleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected  ImageView imageView;
    protected TextView textView;
    protected FindCircleItem.OnItemClickListener onItemClickListener;
    protected FindCircleHolder(View itemView,FindCircleItem.OnItemClickListener onItemClick) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.circleImg);
        textView = (TextView)itemView.findViewById(R.id.circleTv);
        this.onItemClickListener = onItemClick;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(onItemClickListener!=null)
            onItemClickListener.onItemClick(v,getPosition());

    }
}