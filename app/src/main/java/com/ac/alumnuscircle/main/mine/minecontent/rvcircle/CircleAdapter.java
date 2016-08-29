/**
 * @author 吴正凡
 * @date 16.08.26
 * 实现了我的页面中，加入的圈子和管理圈子的RecycleView适配器。
 */

package com.ac.alumnuscircle.main.mine.minecontent.rvcircle;

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

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.MyViewHolder> {
    private List<CircleItem> circleItemList;
    private Context mContext;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener;


    public CircleAdapter(Context context, List<CircleItem> circleItemList){
        this.mContext=context;
        this.circleItemList=circleItemList;
        inflater=LayoutInflater.from(mContext);
    }


    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.minecontent_circle_rv_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.circleName.setText(circleItemList.get(position).getCircleName());
        holder.circleImg.setImageURI(Uri.parse(circleItemList.get(position).getCircleImgUrl()));
        if( mOnItemClickListener!= null){
            holder. itemView.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });

            holder.itemView.setOnLongClickListener( new View.OnLongClickListener() {
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
        return circleItemList.size();
    }

//    /**
//     * 实现添加数据的操作
//     * 曾博晖
//     * 2016年8月10日18:07:37
//     * 添加注释
//     * */
//    public void addData(int position)
//    {
//        contactsItemList.add(position, new ContactsItem("http://v1.qzone.cc/avatar/201412/06/14/03/54829c3a87cd3532.jpg%21200x200.jpg",
//                "盼盼"));
//        notifyItemInserted(position);
//    }

    public void removeData(int position) {
        circleItemList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView circleImg;
        TextView circleName;


        public MyViewHolder(View itemView) {
            super(itemView);
            circleImg=(SimpleDraweeView) itemView.findViewById(R.id.minecontent_circle_rv_item_circleImg);
            circleName=(TextView)itemView.findViewById(R.id.me_item_circleName);
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
