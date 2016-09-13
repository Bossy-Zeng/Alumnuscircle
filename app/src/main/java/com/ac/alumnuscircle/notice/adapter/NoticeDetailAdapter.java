package com.ac.alumnuscircle.notice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.beans.CircleItem;
import com.ac.alumnuscircle.beans.CommentConfig;
import com.ac.alumnuscircle.beans.CommentItem;
import com.ac.alumnuscircle.beans.FavortItem;
import com.ac.alumnuscircle.notice.activity.ImagePagerActivity;
import com.ac.alumnuscircle.notice.adapter.viewholder.ImageDetailViewHolder;
import com.ac.alumnuscircle.notice.utils.DatasUtil;
import com.ac.alumnuscircle.notice.utils.GlideCircleTransform;
import com.ac.alumnuscircle.notice.utils.UrlUtils;
import com.ac.alumnuscircle.notice.widgets.CommentListView;
import com.ac.alumnuscircle.notice.widgets.MultiImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

//import com.ac.alumnuscircle.notice.adapter.viewholder.URLViewHolder;
//import com.ac.alumnuscircle.notice.adapter.viewholder.VideoViewHolder;

/**
 * 圈子适配器
 * @author 白洋
 */
public class NoticeDetailAdapter extends RecyclerView.Adapter<ImageDetailViewHolder> {

//    public final static int TYPE_HEAD = 0;
    private   static  long mLasttime = 0;
//    private static final int STATE_IDLE = 0;
//    private static final int STATE_ACTIVED = 1;
//    private static final int STATE_DEACTIVED = 2;
//    private int videoState = STATE_IDLE;
    public static final int HEADVIEW_SIZE = 1;

//    int curPlayIndex=-1;
    private List<CircleItem> lists;
    private ShowEditTextBody showEditTextBody;
    private Context context;
    public  interface ShowEditTextBody
    {
        public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);
    }
    public void setShowEditTextBody( ShowEditTextBody showEditTextBody)
    {
        this.showEditTextBody = showEditTextBody;
    }

    public NoticeDetailAdapter(Context context, List<CircleItem> lists, ShowEditTextBody showEditTextBody){
        this.context = context;
        this.lists = lists;
        this.showEditTextBody = showEditTextBody;
    }

    @Override
    public int getItemViewType(int position) {
//        if(position == 0){
//            return TYPE_HEAD;
//        }

        int itemType = 0;//只考虑传图片
        itemType= ImageDetailViewHolder.TYPE_IMAGE;
//        CircleItem item = (CircleItem) datas.get(position);

//        if (CircleItem.TYPE_URL.equals(item.getType())) {
//            itemType = NoticeDetailViewHolder.TYPE_URL;
//        } else if (CircleItem.TYPE_IMG.equals(item.getType())) {
//            itemType = NoticeDetailViewHolder.TYPE_IMAGE;
//        }
//        else if(CircleItem.TYPE_VIDEO.equals(item.getType())){
//            itemType = NoticeDetailViewHolder.TYPE_VIDEO;
//        }
        return itemType;
    }

    @Override
    public ImageDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
//        if(viewType == TYPE_HEAD){
//            View headView = LayoutInflater.from(parent.getContext()).inflate(R.layout.head_circle, parent, false);
//            viewHolder = new HeaderViewHolder(headView);
//        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noticedtl_item, parent, false);

//            if(viewType == NoticeDetailViewHolder.TYPE_URL){
//                viewHolder = new URLViewHolder(view);
//            }else if(viewType == NoticeDetailViewHolder.TYPE_IMAGE){
                viewHolder = new ImageDetailViewHolder(view, ImageDetailViewHolder.TYPE_IMAGE);
//            }
//          else  if(viewType == NoticeDetailViewHolder.TYPE_VIDEO){
//                viewHolder = new VideoViewHolder(view);
//            }
//        }

        return (ImageDetailViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageDetailViewHolder viewHolder, final int position) {

//        if(getItemViewType(position)==TYPE_HEAD){
//            //HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
//        }else{

//            final int circlePosition = position - HEADVIEW_SIZE;
        final int circlePosition = position;
            final ImageDetailViewHolder holder = (ImageDetailViewHolder) viewHolder;
            CircleItem circleItem = lists.get(position);
            final String circleId = circleItem.getId();
            String name = circleItem.getUser().getName();
            String headImg = circleItem.getUser().getHeadUrl();
            final String content = circleItem.getContent();
            String createTime = circleItem.getCreateTime();
            final List<FavortItem> favortDatas = circleItem.getFavorters();
            final List<CommentItem> commentsDatas = circleItem.getComments();
            boolean hasFavort = circleItem.hasFavort();
            boolean hasComment = circleItem.hasComment();
            final int commentNum = circleItem.getLikeNumber();

            Glide.with(context).load(headImg).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).transform(new GlideCircleTransform(context)).into(holder.headIv);

            holder.nameTv.setText(name.substring(11,name.length()));
            holder.timeTv.setText(createTime);
//             if(commentNum>0)
//                 holder.commentNum.setText(Integer.toString(commentNum));
            if(!TextUtils.isEmpty(content)){
                holder.contentTv.setText(UrlUtils.formatUrlString(content));
            }
            holder.contentTv.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);

//            if(DatasUtil.curUser.getId().equals(circleItem.getUser().getId())){
//                holder.deleteBtn.setVisibility(View.VISIBLE);
//            }else{
//                holder.deleteBtn.setVisibility(View.GONE);
//            }
//            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //删除
//                    if(presenter!=null){
//                        presenter.deleteCircle(circleId);
//                    }
//                }
//            });
            if(hasFavort || hasComment){
                if(hasFavort){//处理点赞列表

                }else{

                }

                if(hasComment){//处理评论列表
                    holder.commentList.setOnItemClickListener(new CommentListView.OnItemClickListener() {
                        @Override
                        public void onItemClick(int commentPosition) {
                            CommentItem commentItem = commentsDatas.get(commentPosition);

                        }
                    });
                    holder.commentList.setOnItemLongClickListener(new CommentListView.OnItemLongClickListener() {
                        @Override
                        public void onItemLongClick(int commentPosition) {
                            //长按进行复制
//                            CommentItem commentItem = commentsDatas.get(commentPosition);
//                            CommentDialog dialog = new CommentDialog(context, presenter, commentItem, circlePosition);
//                            dialog.show();
                        }
                    });
                    holder.commentList.setDatas(commentsDatas);
                    holder.commentList.setVisibility(View.VISIBLE);

                }else {
                    holder.commentList.setVisibility(View.GONE);
            }
                holder.digCommentBody.setVisibility(View.VISIBLE);
            }else{
                holder.digCommentBody.setVisibility(View.GONE);
            }

            holder.digLine.setVisibility(hasFavort && hasComment ? View.VISIBLE : View.GONE);

//            final SnsPopupWindow snsPopupWindow = holder.snsPopupWindow;

            //判断是否已点赞
            final String curUserFavortId = circleItem.getCurUserFavortId(DatasUtil.curUser.getId());
              boolean greatSelect = false;
            if(!TextUtils.isEmpty(curUserFavortId)){
//                snsPopupWindow.getmActionItems().get(0).mTitle = "取消";
                holder.great.setSelected(true);
                greatSelect = true;
            }else{
//                snsPopupWindow.getmActionItems().get(0).mTitle = "赞";
                holder.great.setSelected(false);

                greatSelect = false;
            }
             final boolean  greatSelected=greatSelect;//点赞按钮点击之前的状态
          //点赞操作
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    CommentConfig config = new CommentConfig();
                    config.circlePosition = position;
                    config.commentType = CommentConfig.Type.PUBLIC;
                    showEditTextBody.updateEditTextBodyVisible(View.VISIBLE,config);

                }

        });

//            snsPopupWindow.update();
//            snsPopupWindow.setmItemClickListener(new PopupItemClickListener(circlePosition, circleItem, curUserFavortId));
//            holder.snsBtn.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View view) {
//                    //弹出popupwindow
////                    snsPopupWindow.showPopupWindow(view);
//                }
//            });

//            holder.urlTipTv.setVisibility(View.GONE);
            switch (holder.viewType) {
//                case NoticeDetailViewHolder.TYPE_URL:// 处理链接动态的链接内容和和图片
//                    if(holder instanceof URLViewHolder){
//                        String linkImg = circleItem.getLinkImg();
//                        String linkTitle = circleItem.getLinkTitle();
//                        Glide.with(context).load(linkImg).into(((URLViewHolder)holder).urlImageIv);
//                        ((URLViewHolder)holder).urlContentTv.setText(linkTitle);
//                        ((URLViewHolder)holder).urlBody.setVisibility(View.VISIBLE);
//                        ((URLViewHolder)holder).urlTipTv.setVisibility(View.VISIBLE);
//                    }
//
//                    break;
                case ImageDetailViewHolder.TYPE_IMAGE:// 处理图片
                    if(holder instanceof ImageDetailViewHolder){
                        final List<String> photos = circleItem.getPhotos();
                        if (photos != null && photos.size() > 0) {
                            ((ImageDetailViewHolder)holder).multiImageView.setVisibility(View.VISIBLE);
                            ((ImageDetailViewHolder)holder).multiImageView.setList(photos);
                            ((ImageDetailViewHolder)holder).multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    //imagesize是作为loading时的图片size
                                    ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                                    ImagePagerActivity.startImagePagerActivity(context, photos, position, imageSize);
                                }
                            });
                        } else {
                            ((ImageDetailViewHolder)holder).multiImageView.setVisibility(View.GONE);
                        }
                    }

                    break;
//                case NoticeDetailViewHolder.TYPE_VIDEO:
//                    if(holder instanceof VideoViewHolder){
//                        ((VideoViewHolder)holder).videoView.setVideoUrl(circleItem.getVideoUrl());
//                        ((VideoViewHolder)holder).videoView.setVideoImgUrl(circleItem.getVideoImgUrl());//视频封面图片
//                        ((VideoViewHolder)holder).videoView.setPostion(position);
//                        ((VideoViewHolder)holder).videoView.setOnPlayClickListener(new CircleVideoView.OnPlayClickListener() {
//                            @Override
//                            public void onPlayClick(int pos) {
//                                curPlayIndex = pos;
//                            }
//                        });
//                    }

//                    break;
                default:
                    break;
            }
        }
//    }

    @Override
    public int getItemCount() {
        return lists.size();//有head需要加1
    }

//    public class HeaderViewHolder extends RecyclerView.ViewHolder{
//
//        public HeaderViewHolder(View itemView) {
//            super(itemView);
//        }
//    }

//    private class PopupItemClickListener implements SnsPopupWindow.OnItemClickListener{
//        private String mFavorId;
//        //动态在列表中的位置
//        private int mCirclePosition;
//        private long mLasttime = 0;
//        private CircleItem mCircleItem;
//
//        public PopupItemClickListener(int circlePosition, CircleItem circleItem, String favorId){
//            this.mFavorId = favorId;
//            this.mCirclePosition = circlePosition;
//            this.mCircleItem = circleItem;
//        }

//        @Override
//        public void onItemClick(ActionItem actionitem, int position) {
//            switch (position) {
//                case 0://点赞、取消点赞
//                    if(System.currentTimeMillis()-mLasttime<700)//防止快速点击操作
//                        return;
//                    mLasttime = System.currentTimeMillis();
//                    if(presenter != null){
//                        if ("赞".equals(actionitem.mTitle.toString())) {
//                            presenter.addFavort(mCirclePosition);
//                        } else {//取消点赞
//                            presenter.deleteFavort(mCirclePosition, mFavorId);
//                        }
//                    }
//                    break;
//                case 1://发布评论
//                    if(presenter != null){
//                        CommentConfig config = new CommentConfig();
//                        config.circlePosition = mCirclePosition;
//                        config.commentType = CommentConfig.Type.PUBLIC;
//                        presenter.showEditTextBody(config);
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
}
