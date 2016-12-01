package com.ac.alumnuscircle.main.ctc.ctc_rv;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.ac.alumnuscircle.R;
import com.bumptech.glide.load.engine.Engine;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by 曾博晖 on 2016/8/28.
 *
 * @author 曾博晖
 * @version 1
 *          功能：实现加载人脉列表的RecyclerView的Adapter
 * @date 2016年8月28日
 */

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ContactFgtItem> ContactFgtItemList;
    private Context mContext;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener;
    private ProgressBar mProgressBar;
    /**
     * 普通的item类型
     * */
    private static int TYPE_NORMAL = 0;

    /**
     * 上拉加载时出现的item类型
     * */
    private static int TYPE_FOOTER = 1;

    public ContactAdapter(Context context, List<ContactFgtItem> ContactFgtItems) {
        this.mContext = context;
        this.ContactFgtItemList = ContactFgtItems;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            View view = inflater.inflate(R.layout.ctc_contactfgt_rv_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        } else if (viewType == TYPE_FOOTER) {
            View view = inflater.inflate(R.layout.ctc_contactfgt_rv_footerview, parent, false);
            FooterViewHolder holder = new FooterViewHolder(view);
            return holder;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof MyViewHolder) {
            ((MyViewHolder)holder).name_text.setText(
                    ContactFgtItemList.get(position).getUserName());
            ((MyViewHolder)holder).addr_text.setText(
                    ContactFgtItemList.get(position).getUserLocation());
            ((MyViewHolder)holder).grade_text.setText(
                    ContactFgtItemList.get(position).getUserFaculty() +
                    ContactFgtItemList.get(position).getUserGrade() + "级");
//        holder.class_text.setText(ContactFgtItemList.get(position).getUserClass());
            ((MyViewHolder)holder).job_text.setText(
                    ContactFgtItemList.get(position).getUserJob());
            if (ContactFgtItemList.get(position).getHeadImgUrl() != null) {
                ((MyViewHolder)holder).head_img.setImageURI(
                        Uri.parse(ContactFgtItemList.get(position).getHeadImgUrl()));
            }
            if (mOnItemClickListener != null) {
                ((MyViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(position);
                    }
                });

                ((MyViewHolder)holder).itemView.
                        setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mOnItemClickListener.onLongClick(position);
                        return false;
                    }
                });
            }
        }else if (holder instanceof FooterViewHolder){
            mProgressBar=((FooterViewHolder) holder).progressBar;
        }


    }

    public void dismissProBar(){
        if(mProgressBar!=null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return ContactFgtItemList.size()+1;
    }

    /**
     * 实现移除数据的操作
     * 曾博晖
     * 2016年8月10日18:08:17
     * 添加注释
     */
    public void removeData(int position) {
        ContactFgtItemList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView head_img;
        TextView name_text;
        TextView grade_text;
        //        TextView class_text;
        TextView addr_text;
        TextView job_text;

        public MyViewHolder(View itemView) {
            super(itemView);
            head_img = (SimpleDraweeView) itemView.findViewById(R.id.ctc_contactfgt_rv_item_headimg);
            name_text = (TextView) itemView.findViewById(R.id.ctc_contactfgt_rv_item_username_tv);
            grade_text = (TextView) itemView.findViewById(R.id.ctc_contactfgt_rv_item_grade_textView);
//            class_text=(TextView)itemView.findViewById(R.id.ctc_contactfgt_rv_item_class_textView);
            addr_text = (TextView) itemView.findViewById(R.id.ctc_contactfgt_rv_item_addr_textView);
            job_text = (TextView) itemView.findViewById(R.id.ctc_contactfgt_rv_item_job_textView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        private TextView footer;
        private ProgressBar progressBar;
        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar=(ProgressBar)itemView.findViewById(
                    R.id.ctc_contactfgt_rv_footerview_proBar);
//            footer = (TextView) itemView.findViewById(
//                    R.id.ctc_contactfgt_rv_footerview_tv);
        }
    }


    /**
     * 定义条目点击接口，实现RecycleView的点击事件
     * 实现点击事件和长按事件
     * 曾博晖 2016年8月10日17:11:20 创建
     */
    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    /**
     * 设置点击事件的监听器
     * 曾博晖
     * 2016年8月10日17:14:05 创建
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
