/**
 * @author白洋
 * @Date 2016/8/28.
 * @version 2
 * 主页的holder
 */
package com.ac.alumnuscircle.main.home.home_rv;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ac.alumnuscircle.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by 15359 on 2016/8/28.
 */
public class HomeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    protected SimpleDraweeView imageView;
    protected TextView textView;
    protected HomeItem.OnItemClickListener onItemClickListener;
    public HomeHolder(View itemView,HomeItem.OnItemClickListener onItemClick) {
        super(itemView);
        imageView = (SimpleDraweeView) itemView.findViewById(R.id.home_rv_homeitem_img);
        textView = (TextView)itemView.findViewById(R.id.home_rv_homeitem_tv);
        this.onItemClickListener = onItemClick;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(onItemClickListener!=null)
            onItemClickListener.onItemClick(v,getPosition());

    }


}