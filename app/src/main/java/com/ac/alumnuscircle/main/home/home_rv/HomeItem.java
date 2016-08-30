/**
 * @author白洋
 * @Date 2016/8/28.
 * @version 2
 * 主页的适配器
 */
package com.ac.alumnuscircle.main.home.home_rv;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ac.alumnuscircle.R;

import java.util.List;

public class HomeItem extends RecyclerView.Adapter<HomeHolder> {
    private List<String> names;//对应模块的标题
    private List<String>imagesUrl;//对应模块的图片
    private OnItemClickListener onItemClickListener;

    public HomeItem(List<String> names, List<String> imagesUrl)
    {
        this.names = names;
        this.imagesUrl = imagesUrl;
      
        names.add("创建圈子");
       imagesUrl.add("从本地获取");
    }

    //点击事件的接口
    public interface OnItemClickListener
    {
        void onItemClick(View v, int position);//实现的方法
    }


    //设置点击事件的接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        if(onItemClickListener!=null)
            this.onItemClickListener = onItemClickListener;
    }
    //增加圈子
    public void addItem(String title, String image)
    {
        //插入的位置
        int insertPosition = imagesUrl.size()-1;
        names.add(insertPosition,title);
        imagesUrl.add(insertPosition,image);
        notifyItemInserted(insertPosition);
    }
//删除圈子
    public void removeItem(int position)
    {
        names.remove(position);
        imagesUrl.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public HomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载每一项的视图
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_rv_homeitem,parent,false);
        HomeHolder homeHolder =new HomeHolder(itemView,onItemClickListener);
        return homeHolder;
    }

    @Override
    public void onBindViewHolder(HomeHolder holder, int position) {
        holder.textView.setText(names.get(position));
        if(!imagesUrl.get(position).equals("从本地获取")){
            Uri imageUri = Uri.parse(imagesUrl.get(position));
            holder.imageView.setImageURI(imageUri);
        }
        else{
            holder.imageView.setImageResource(R.mipmap.add);
        }
    }


    @Override
    public int getItemCount() {
        return (imagesUrl == null)?0:imagesUrl.size();
    }


}
