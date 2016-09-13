package com.ac.alumnuscircle.notice.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.notice.widgets.CommentListView;
import com.ac.alumnuscircle.notice.widgets.ExpandTextView;
import com.ac.alumnuscircle.notice.widgets.MultiImageView;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @author 白洋
 */
public class ImageDetailViewHolder extends RecyclerView.ViewHolder {
    /** 图片*/
    public MultiImageView multiImageView;
    public final static int TYPE_URL = 1;
    public final static int TYPE_IMAGE = 2;
//    public final static int TYPE_VIDEO = 3;

    public int viewType;

    public SimpleDraweeView headIv;
    public TextView nameTv;
    public TextView commentNum;
    /** 动态的内容 */
    public ExpandTextView contentTv;
    public TextView timeTv;
    //    public TextView deleteBtn;
//    public ImageView snsBtn;//点赞，评论按钮
    public ImageView great;
    public ImageView comment;
    /** 点赞列表*/
//    public PraiseListView praiseListView;

    public LinearLayout digCommentBody;
    public View digLine;

    /** 评论列表 */
    public CommentListView commentList;


    public ImageDetailViewHolder(View itemView,int viewType){
        super(itemView);
       this.viewType = viewType;
        ViewStub viewStub = (ViewStub) itemView.findViewById(R.id.noticedtl_viewStub);
        great = (ImageView) itemView.findViewById(R.id.noticedtl_great_img);
        comment =(ImageView) itemView.findViewById(R.id.noticedtl_comment_img);
        headIv = (SimpleDraweeView) itemView.findViewById(R.id.noticedtl_headIv);
        nameTv = (TextView) itemView.findViewById(R.id.noticedtl_nameTv);
        digLine = itemView.findViewById(R.id.noticedtl_lin_dig);
        commentNum = (TextView) itemView.findViewById(R.id.noticedtl_great_num);
        contentTv = (ExpandTextView) itemView.findViewById(R.id.noticedtl_contentTv);
//        urlTipTv = (TextView) itemView.findViewById(R.id.urlTipTv);
        timeTv = (TextView) itemView.findViewById(R.id.noticedtl_timeTv);
//        deleteBtn = (TextView) itemView.findViewById(R.id.deleteBtn);
//        snsBtn = (ImageView) itemView.findViewById(R.id.snsBtn);
//        praiseListView = (PraiseListView) itemView.findViewById(R.id.noticedtl_praiseListView);

        digCommentBody = (LinearLayout) itemView.findViewById(R.id.noticedtl_digCommentBody);
        commentList = (CommentListView)itemView.findViewById(R.id.noticedtl_commentList);


        initSubView(viewType,viewStub);


    }


    public void initSubView(int viewType, ViewStub viewStub) {
        if(viewStub == null){
            throw new IllegalArgumentException("viewStub is null...");
        }
        viewStub.setLayoutResource(R.layout.notice_viewstub_imgbody);
        View subView = viewStub.inflate();
        MultiImageView multiImageView = (MultiImageView) subView.findViewById(R.id.multiImagView);
        if(multiImageView != null){
            this.multiImageView = multiImageView;
        }
    }
}
