package com.ac.alumnuscircle.notice.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.beans.CircleItem;
import com.ac.alumnuscircle.notice.activity.NoticeDetailAct;
import com.ac.alumnuscircle.notice.adapter.viewholder.ImageDetailViewHolder;
import com.ac.alumnuscircle.notice.adapter.viewholder.ImageViewHolder;
import com.ac.alumnuscircle.notice.mvp.presenter.CirclePresenter;
import com.ac.alumnuscircle.notice.utils.DatasUtil;

import java.util.List;

/**
 * @author 白洋
 */
public class NoticeAdapter  extends BaseRecycleViewAdapter {
    //    public final static int TYPE_HEAD = 0;
    private   static  long mLasttime = 0;
    private static final int STATE_IDLE = 0;
    private static final int STATE_ACTIVED = 1;
    private static final int STATE_DEACTIVED = 2;
    //    private int videoState = STATE_IDLE;
    public static final int HEADVIEW_SIZE = 1;

    int curPlayIndex=-1;

    private CirclePresenter presenter;
    private Context context;
    public void setCirclePresenter(CirclePresenter presenter){
        this.presenter = presenter;
    }

    public NoticeAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
//        if(position == 0){
//            return TYPE_HEAD;
//        }

        int itemType = 0;//只考虑传图片
        itemType= ImageViewHolder.TYPE_IMAGE;
//        CircleItem item = (CircleItem) datas.get(position);

//        if (CircleItem.TYPE_URL.equals(item.getType())) {
//            itemType = ImageDetailViewHolder.TYPE_URL;
//        } else if (CircleItem.TYPE_IMG.equals(item.getType())) {
//            itemType = ImageDetailViewHolder.TYPE_IMAGE;
//        }
//        else if(CircleItem.TYPE_VIDEO.equals(item.getType())){
//            itemType = ImageDetailViewHolder.TYPE_VIDEO;
//        }
        return itemType;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);
        viewHolder = new ImageViewHolder(view, ImageViewHolder.TYPE_IMAGE);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final int circlePosition = position;
        final ImageViewHolder holder = (ImageViewHolder) viewHolder;
        final CircleItem circleItem = (CircleItem) datas.get(circlePosition);
        final String circleId = circleItem.getId();
        final String name = circleItem.getUser().getName().substring(11,
                circleItem.getUser().getName().length() );
        final String headImg = circleItem.getUser().getHeadUrl();
        final String content = circleItem.getContent();
        final String createTime = circleItem.getCreateTime();
        final int commentNum = circleItem.getCommentNumber();
        boolean hasFavort = circleItem.hasFavort();
        boolean hasComment = circleItem.hasComment();

//        Glide.with(context).load(headImg).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(context)).into(holder.headIv);
        Uri imageUri = Uri.parse(headImg);
        holder.headIv.setImageURI(imageUri);
        holder.nameTv.setText(name);
        holder.timeTv.setText(createTime);

        if (!TextUtils.isEmpty(content)) {
            holder.contentTv.setText(content);
        }
        holder.contentTv.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);


        //点击进入详情界面
        holder.noticellyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noticeDetail = new Intent(context, NoticeDetailAct.class);
                noticeDetail.putExtra("CircleItem",circleItem);
                noticeDetail.putExtra("CommentNum",commentNum);
                context.startActivity(noticeDetail);
            }
        });
//        if (hasFavort || hasComment) {
//            if(hasFavort){//处理点赞列表
//                holder.praiseListView.setOnItemClickListener(new PraiseListView.OnItemClickListener() {
//                    @Override
//                    public void onClick(int position) {
//                        String userName = favortDatas.get(position).getUser().getName();
//                        String userId = favortDatas.get(position).getUser().getId();
//                        Toast.makeText(MyApplication.getContext(), userName + " &id = " + userId, Toast.LENGTH_SHORT).show();
//                    }
//                });
//                holder.praiseListView.setDatas(favortDatas);
//                holder.praiseListView.setVisibility(View.VISIBLE);
//            }else{
//                holder.praiseListView.setVisibility(View.GONE);
//            }

//            if(hasComment){//处理评论列表
//                holder.commentList.setOnItemClickListener(new CommentListView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(int commentPosition) {
//                        CommentItem commentItem = commentsDatas.get(commentPosition);
//                        if(DatasUtil.curUser.getId().equals(commentItem.getUser().getId())){//复制或者删除自己的评论
//
//                            CommentDialog dialog = new CommentDialog(context, presenter, commentItem, circlePosition);
//                            dialog.show();
//                        }
////                        else{//回复别人的评论
////                            if(presenter != null){
////                                CommentConfig config = new CommentConfig();
////                                config.circlePosition = circlePosition;
////                                config.commentPosition = commentPosition;
////                                config.commentType = CommentConfig.Type.REPLY;
////                                config.replyUser = commentItem.getUser();
////                                presenter.showEditTextBody(config);
////                            }
////                        }
//                    }
//                });
//                holder.commentList.setOnItemLongClickListener(new CommentListView.OnItemLongClickListener() {
//                    @Override
//                    public void onItemLongClick(int commentPosition) {
//                        //长按进行复制
//                        CommentItem commentItem = commentsDatas.get(commentPosition);
//                        CommentDialog dialog = new CommentDialog(context, presenter, commentItem, circlePosition);
//                        dialog.show();
//                    }
//                });
//                holder.commentList.setDatas(commentsDatas);
//                holder.commentList.setVisibility(View.VISIBLE);
//
//            }else {
//                holder.commentList.setVisibility(View.GONE);
//            }
//            holder.digCommentBody.setVisibility(View.VISIBLE);
//        }else{
//            holder.digCommentBody.setVisibility(View.GONE);
//        }
//        holder.digLine.setVisibility(hasFavort && hasComment ? View.VISIBLE : View.GONE);

            //判断是否已点赞
            final String curUserFavortId = circleItem.getCurUserFavortId(DatasUtil.curUser.getId());

//            if (!TextUtils.isEmpty(curUserFavortId)) {
////                snsPopupWindow.getmActionItems().get(0).mTitle = "取消";
//                holder.great.setSelected(true);
//
//            } else {
////                snsPopupWindow.getmActionItems().get(0).mTitle = "赞";
//                holder.great.setSelected(false);
//
//
//            }

//            switch (holder.viewType) {
//                case ImageDetailViewHolder.TYPE_IMAGE:// 处理图片
//                    if (holder instanceof ImageViewHolder) {
                        final List<String> photo = circleItem.getPhotos();//一张封面图
                        if (photo.size()>= 1) {
                            Uri uri = Uri.parse(photo.get(0));
                            Log.e("封面的url", photo.get(0));
                            //自适应高度
//                        int screenWidth = 300;//获取图片宽度
//                        ViewGroup.LayoutParams lp = holder.image.getLayoutParams();
//                        lp.width = screenWidth;
//                        lp.height =ViewGroup.LayoutParams.WRAP_CONTENT;
//                        holder.image.setLayoutParams(lp);
//
//                        holder.image.setMaxWidth(screenWidth);
//                        holder.image.setMaxHeight(screenWidth * 5);
                            holder.image.setImageURI(uri);
                            holder.image.setVisibility(View.VISIBLE);

                        } else {
                            holder.image.setVisibility(View.GONE);
                        }
//                    }
//                    break;
//            }

//        }
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }
}
