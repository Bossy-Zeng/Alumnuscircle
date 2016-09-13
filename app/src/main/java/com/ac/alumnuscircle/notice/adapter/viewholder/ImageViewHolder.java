package com.ac.alumnuscircle.notice.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ac.alumnuscircle.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @author 白洋
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {
    public final static int TYPE_URL = 1;
    public final static int TYPE_IMAGE = 2;
//    public final static int TYPE_VIDEO = 3;

    public int viewType;

    public SimpleDraweeView headIv;
    public TextView nameTv;

    public LinearLayout noticellyt;//点击跳转
    /** 动态的内容 */
    public TextView contentTv;
    public TextView timeTv;
    //    public TextView deleteBtn;
//    public ImageView snsBtn;//点赞，评论按钮
//    public ImageView great;
    public ImageView comment;
    public SimpleDraweeView image;
    /** 点赞列表*/
//    public PraiseListView praiseListView;
//
//    public LinearLayout digCommentBody;
    public View digLine;

    /** 评论列表 */
//    public CommentListView commentList;


    public ImageViewHolder(View itemView,int viewType) {
        super(itemView);
        this.viewType = viewType;
        headIv = (SimpleDraweeView) itemView.findViewById(R.id.notice_item_headIv);
        nameTv = (TextView) itemView.findViewById(R.id.notice_item_nameTv);
//        digLine = itemView.findViewById(R.id.notice_lin_dig);
        image = (SimpleDraweeView)itemView.findViewById(R.id.notice_item_img);
        contentTv = (TextView) itemView.findViewById(R.id.notice_item_contentTv);

        timeTv = (TextView) itemView.findViewById(R.id.notice_item_timeTv);

//        praiseListView = (PraiseListView) itemView.findViewById(R.id.notice_praiseListView);
//
//        digCommentBody = (LinearLayout) itemView.findViewById(R.id.notice_digCommentBody);
//        commentList = (CommentListView)itemView.findViewById(R.id.notice_commentList);
        //只显示按钮
//        comment = (ImageView)itemView.findViewById(R.id.noticedtl_comment_img);
//        great = (ImageView)itemView.findViewById(R.id.notice_item_great_img);
        noticellyt = (LinearLayout)itemView.findViewById(R.id.notice_item_view);

    }


}
