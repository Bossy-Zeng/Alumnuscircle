package com.ac.alumnuscircle.notice.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.notice.adapter.viewholder.IssueNoticeViewHolder;

import java.util.List;

/**
 * Created by 15359 on 2016/9/7.
 */
public class IssueNoticeAdapter extends  RecyclerView.Adapter<IssueNoticeViewHolder> {
    private List<String> uploadImgUrl;
    private OnItemClickListener onItemClickListener;

    //点击事件的接口
    public interface OnItemClickListener
    {
        void onItemClick(View v, int position);//实现的方法
        void onLongItemClick(View v, int position);
    }


    //设置点击事件的接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        if(onItemClickListener!=null)
            this.onItemClickListener = onItemClickListener;
    }
    public void addItem(String imageUrl)
    {

            uploadImgUrl.add(imageUrl);
            notifyItemInserted(uploadImgUrl.size()-1);



    }

    public void removeItem(int position)
    {
        uploadImgUrl.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public IssueNoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_issue_item,parent,false);
        IssueNoticeViewHolder uploadImgHolder = new IssueNoticeViewHolder(itemView,onItemClickListener);
        return uploadImgHolder;
    }

    @Override
    public void onBindViewHolder(IssueNoticeViewHolder holder, int position) {

            holder.uploadImg.setImageURI(Uri.parse(uploadImgUrl.get(position)));
    }

    @Override
    public int getItemCount() {
        return this.uploadImgUrl==null?0:uploadImgUrl.size();
    }

   public   IssueNoticeAdapter(List<String> uploadImgUrl)
    {
        this.uploadImgUrl =uploadImgUrl;

    }
}
