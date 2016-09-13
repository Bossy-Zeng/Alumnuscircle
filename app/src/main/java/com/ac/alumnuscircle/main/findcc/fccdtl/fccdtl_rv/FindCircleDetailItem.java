package com.ac.alumnuscircle.main.findcc.fccdtl.fccdtl_rv;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.main.home.home_rv.HomeHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by 白洋 on 2016/8/29.
 */
public class FindCircleDetailItem extends RecyclerView.Adapter<FindCircleDetailHolder> {
    private List<String> names;//对应模块的标题
    private List<String>imagesUrl;//对应模块的图片
    private OnItemClickListener onItemClickListener;
  public  FindCircleDetailItem(List<String> names,List<String>imagesUrl)
 {
     this.names = names;
     this.imagesUrl = imagesUrl;
 }
    //设置点击事件的接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        if(onItemClickListener!=null)
            this.onItemClickListener = onItemClickListener;
    }
    @Override
    public FindCircleDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fccdtl_rv_findcircledetailitem,parent,false);
        FindCircleDetailHolder findCircleDetailHolder =new FindCircleDetailHolder(itemView,onItemClickListener);
        return findCircleDetailHolder;

    }

    @Override
    public void onBindViewHolder(FindCircleDetailHolder holder, int position) {
        holder.textView.setText(names.get(position));
        Uri imageUri = Uri.parse(imagesUrl.get(position));
        holder.imageView.setImageURI(imageUri);
    }

    @Override
    public int getItemCount() {
        return (names==null)?0:names.size();
    }
   public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    };
}
