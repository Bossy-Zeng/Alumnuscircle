package com.ac.alumnuscircle.main.findcc.fccdtl.fccdtl_rv;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.ac.alumnuscircle.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by 白洋 on 2016/8/29.
 */
public class FindCircleDetailHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected SimpleDraweeView imageView;
    protected TextView textView;
    protected FindCircleDetailItem.OnItemClickListener onItemClickListener;
    public FindCircleDetailHolder(View itemView,FindCircleDetailItem.OnItemClickListener onItemClick) {
        super(itemView);
        imageView = (SimpleDraweeView) itemView.findViewById(R.id.fccdtl_rv_findcircledetailitem_img);
        textView = (TextView)itemView.findViewById(R.id.fccdtl_rv_findcircledetailitem_tv);
        this.onItemClickListener = onItemClick;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(onItemClickListener!=null)
            onItemClickListener.onItemClick(v,getPosition());
    }
}
