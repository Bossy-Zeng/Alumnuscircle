package com.ac.alumnuscircle.main.msg.msgcontent.notifyfgt_rv;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ac.alumnuscircle.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.MyViewHolder> {
   private List<NotifyItem> NotifyItemList;
   private Context mContext;
   private LayoutInflater inflater;
   private OnItemClickListener mOnItemClickListener;


   public NotifyAdapter(Context context,List<NotifyItem> NotifyItems){
        this.mContext=context;
        this.NotifyItemList=NotifyItems;
        inflater=LayoutInflater.from(mContext);
        }


@Override
   public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.msg_notifyfgt_rv_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
        }

@Override
   public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name_text.setText(NotifyItemList.get(position).getNotify_Name());
        holder.type_text.setText(NotifyItemList.get(position).getNotify_type());
        holder.head_img.setImageURI(
                Uri.parse(NotifyItemList.get(position).getNotify_headImgUrl()));
        holder.time_text.setText(NotifyItemList.get(position).getNotify_time());
        holder.content_text.setText(NotifyItemList.get(position).getNotify_content());
        if( mOnItemClickListener!= null){
        holder. itemView.setOnClickListener( new View.OnClickListener() {

@Override
   public void onClick(View v) {
        mOnItemClickListener.onClick(position);
        }
        });

        holder. itemView.setOnLongClickListener( new View.OnLongClickListener() {
@Override
  public boolean onLongClick(View v) {
        mOnItemClickListener.onLongClick(position);
        return false;
        }
        });
        }

        }

@Override
  public int getItemCount() {
        return NotifyItemList.size();
        }

/**
 * 实现移除数据的操作
 * 曾博晖
 * 2016年8月10日18:08:17
 * 添加注释
 * */
  public void removeData(int position)
        {
        NotifyItemList.remove(position);
        notifyItemRemoved(position);
        }


  public class MyViewHolder extends RecyclerView.ViewHolder{
    SimpleDraweeView head_img;
    TextView name_text;
    TextView type_text;
    TextView content_text;
    TextView time_text;


    public MyViewHolder(View itemView) {
        super(itemView);
        head_img=(SimpleDraweeView) itemView.findViewById(R.id.msg_notifyfgt_rv_item_headimg);
        name_text=(TextView)itemView.findViewById(R.id.msg_notifyfgt_rv_item_name_tv);
        type_text=(TextView)itemView.findViewById(R.id.msg_notifyfgt_rv_item_type_tv);
        content_text=(TextView)itemView.findViewById(R.id.msg_notifyfgt_rv_item_content_tv);
        time_text=(TextView)itemView.findViewById(R.id.msg_notifyfgt_rv_item_time_tv);
    }
}


/**
 * 定义条目点击接口，实现RecycleView的点击事件
 * 实现点击事件和长按事件
 * 曾博晖 2016年8月10日17:11:20 创建
 * */
    public interface OnItemClickListener{
    void onClick(int position);
    void onLongClick(int position);
}
    /**
     * 设置点击事件的监听器
     * 曾博晖
     * 2016年8月10日17:14:05 创建
     * */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this. mOnItemClickListener=onItemClickListener;
    }
}
