/**
 * @author 白洋
 * @Date 2016 8.30
 * 管理管理员的适配器
 */
package com.ac.alumnuscircle.main.findcc.ccdtl.set.admin.admin_sv;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ac.alumnuscircle.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by 15359 on 2016/8/29.
 */
public class AdminAdapter extends BaseAdapter {
private List<Admin> adminList;
    Context context;
    public AdminAdapter(List<Admin>adminList, Context context)
    {
        this.context = context;
        this.adminList = adminList;
    }
    @Override
    public int getCount() {
        return adminList==null?0:adminList.size();
    }

    @Override
    public Admin getItem(int position) {
        return adminList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdminHolder adminHolder = null;
        if(convertView==null) {
            adminHolder = new AdminHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.admin_sv_admin_item,parent,false);
            adminHolder.adminImg = (SimpleDraweeView) convertView.findViewById(R.id.admin_sv_item_user_img);
            adminHolder.adminName = (TextView)convertView.findViewById(R.id.admin_sv_item_user_name);
            convertView.setTag(adminHolder);
        }
        else{
           adminHolder = (AdminHolder) convertView.getTag();
        }
         adminHolder.adminName.setText(adminList.get(position).getAdminName());
        Uri imageUri = Uri.parse(adminList.get(position).getAdminImg());
        adminHolder.adminImg.setImageURI(imageUri);
        return convertView;
    }
}
