/**
 * @author 白洋
 * @Date 2016 8.30
 * 管理管理员的界面
 */
package com.ac.alumnuscircle.main.findcc.ccdtl.set.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.main.findcc.ccdtl.set.admin.admin_sv.Admin;
import com.ac.alumnuscircle.main.findcc.ccdtl.set.admin.admin_sv.AdminAdapter;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;


public class AdminAct extends AppCompatActivity {

    private SwipeMenuListView adminSv;
    private List<Admin> admins ;
    private AdminAdapter adminAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_adminact);
        adminSv = (SwipeMenuListView)findViewById(R.id.admin_adminact_sv);

        InitData();
        adminAdapter = new AdminAdapter(admins,AdminAct.this);
        //创建滑动选项
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                //删除选项
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(R.color.admin_adminact_red);
                deleteItem.setIcon(R.mipmap.admin_delete_icon);
                //按钮宽度和布局中保持一致
                deleteItem.setWidth(dp2px(90));

                menu.addMenuItem(deleteItem);
            }
        };
        adminSv.setMenuCreator(creator);
        adminSv.setAdapter(adminAdapter);
        adminSv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index)
                {

                    case 0://第一项即删除项
                        Toast.makeText(AdminAct.this,"删除"+position,Toast.LENGTH_SHORT).show();
                        admins.remove(position);
                        adminAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
    }

    public void InitData()
    {
        admins = new ArrayList<>();
        for(int i =0;i<5;i++)
        {
            Admin admin = new Admin();
            admin.setAdminName("用户"+i);
            admin.setAdminImg("http://img5.imgtn.bdimg.com/it/u=3430974702,3202911219&fm=21&gp=0.jpg");
            admins.add(admin);
        }
    }

    // 将dp转化成px
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
