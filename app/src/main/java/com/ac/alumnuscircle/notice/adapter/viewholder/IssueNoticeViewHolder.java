package com.ac.alumnuscircle.notice.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.notice.adapter.IssueNoticeAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @author 白洋
 */
public class IssueNoticeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,View.OnLongClickListener{
   public SimpleDraweeView uploadImg;
   protected IssueNoticeAdapter.OnItemClickListener onItemClickListener;
    public IssueNoticeViewHolder(View itemView, IssueNoticeAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        uploadImg = (SimpleDraweeView)itemView.findViewById(R.id.notice_issue_item_img);
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(onItemClickListener!=null)
            onItemClickListener.onItemClick(v,getPosition());
    }

    @Override
    public boolean onLongClick(View v) {
        if(onItemClickListener!=null)
        {
            onItemClickListener.onLongItemClick(v,getPosition());
            return true;
        }
        return false;
    }
}
