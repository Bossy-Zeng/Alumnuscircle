/**
 * @author 吴正凡
 * @date 16.08.26
 * @version 1
 * 功能：这是主页中，“我的”分页。
 */

package com.ac.alumnuscircle.main.mine;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.main.mine.minecontent.AdminCircle;
import com.ac.alumnuscircle.main.mine.minecontent.CollectCard;
import com.ac.alumnuscircle.main.mine.minecontent.JoinCircle;
import com.facebook.drawee.view.SimpleDraweeView;


public class MineFgt extends Fragment {

    private View view;
    private LayoutInflater layoutInflater;
    private ViewGroup container;

    private SimpleDraweeView userHdimgSdv;
    private RelativeLayout cameraRlyt;
    private TextView userNameTv;
    private TextView userCareerTv;
    private TextView userMajorTv;
    private ImageButton setImgbtn;
    private LinearLayout collectCardLlyt;
    private LinearLayout joinCircleLlyt;
    private LinearLayout adminCircleLlyt;
    private ImageView collectCardTabline;
    private ImageView joinCircleTabline;
    private ImageView adminCircleTabline;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment currentFgt;
    private Fragment collectCardFgt;
    private Fragment joinCircleFgt;
    private Fragment adminCircleFgt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_minefgt, container, false);
        layoutInflater = inflater;
        this.container = container;
        init();
        return view;
    }

    private void init(){
        initUIBtn();
        initFragment();
        initCollectCard();
        initData();
    }

    private void initData(){
        userHdimgSdv.setImageURI(Uri.parse("http://img0.imgtn.bdimg.com/it/u=3691748163,484693479&fm=206&gp=0.jpg"));
        userNameTv.setText("马天宇");
        userCareerTv.setText("首席架构师");
        userMajorTv.setText("软件学院2014级 · 2班");
    }

    private void initUIBtn(){
        setImgbtn = (ImageButton)view.findViewById(R.id.mine_minefgt_set_imgbtn);
        userHdimgSdv = (SimpleDraweeView)view.findViewById(R.id.mine_minefgt_hdimg_sdv);
        cameraRlyt = (RelativeLayout)view.findViewById(R.id.mine_minefgt_camera_rlyt);
        userNameTv = (TextView)view.findViewById(R.id.mine_minefgt_username);
        userCareerTv = (TextView)view.findViewById(R.id.mine_minefgt_usercareer);
        userMajorTv = (TextView)view.findViewById(R.id.mine_minefgt_usermajor);
        collectCardLlyt = (LinearLayout)view.findViewById(R.id.mine_minefgt_collectcard_llyt);
        joinCircleLlyt = (LinearLayout)view.findViewById(R.id.mine_minefgt_joincircle_llyt);
        adminCircleLlyt = (LinearLayout)view.findViewById(R.id.mine_minefgt_admincircle_llyt);
        collectCardTabline = (ImageView)view.findViewById(R.id.mine_minefgt_collectcard_tabline);
        joinCircleTabline = (ImageView)view.findViewById(R.id.mine_minefgt_joincircle_tabline);
        adminCircleTabline = (ImageView)view.findViewById(R.id.mine_minefgt_admincircle_tabline);

        setImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(ActivityName.set_ResetNameHdimg);
                startActivity(intent);
            }
        });

        cameraRlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        collectCardLlyt.setOnClickListener(new TabBtnClickListener());
        joinCircleLlyt.setOnClickListener(new TabBtnClickListener());
        adminCircleLlyt.setOnClickListener(new TabBtnClickListener());

    }

    private void initFragment(){
        fragmentManager = this.getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    /**
     * 第一次进入主页页面，默认是显示CollectCards这个页面，
     * 也就是第一个页面，所以需要在进入本Fragment就初始一次。
     */
    private void initCollectCard() {
        if (collectCardFgt == null) {
            collectCardFgt = new CollectCard();
        }
        if (!(collectCardFgt.isAdded())) {
            if (fragmentTransaction == null) {
                fragmentManager = this.getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
            }
            fragmentTransaction.add(R.id.mine_minefgt_content_flyt, collectCardFgt).commit();
        }
        currentFgt = collectCardFgt;
        collectCardLlyt.setSelected(true);
        collectCardLlyt.setEnabled(false);
        collectCardTabline.setSelected(true);
        joinCircleLlyt.setSelected(false);
        joinCircleLlyt.setEnabled(true);
        joinCircleTabline.setSelected(false);
        adminCircleLlyt.setSelected(false);
        adminCircleLlyt.setEnabled(true);
        adminCircleTabline.setSelected(false);
    }

    /**
     * 点击导航栏中“收藏的名片”按钮之后的动作。
     */
    public void clickCollectCardLlyt() {
        if (collectCardFgt == null) {
            collectCardFgt = new CollectCard();
        }
        switchFragment(collectCardFgt);
        collectCardLlyt.setSelected(true);
        collectCardLlyt.setEnabled(false);
        collectCardTabline.setSelected(true);
        joinCircleLlyt.setSelected(false);
        joinCircleLlyt.setEnabled(true);
        joinCircleTabline.setSelected(false);
        adminCircleLlyt.setSelected(false);
        adminCircleLlyt.setEnabled(true);
        adminCircleTabline.setSelected(false);
    }

    /**
     * 点击导航栏中“参与的圈子”按钮之后的动作。
     */
    public void clickJoinCircleLlyt() {
        if (joinCircleFgt == null) {
            joinCircleFgt = new JoinCircle();
        }
        switchFragment(joinCircleFgt);
        collectCardLlyt.setSelected(false);
        collectCardLlyt.setEnabled(true);
        collectCardTabline.setSelected(false);
        joinCircleLlyt.setSelected(true);
        joinCircleLlyt.setEnabled(false);
        joinCircleTabline.setSelected(true);
        adminCircleLlyt.setSelected(false);
        adminCircleLlyt.setEnabled(true);
        adminCircleTabline.setSelected(false);
    }

    /**
     * 点击导航栏中“管理的圈子”按钮之后的动作。
     */
    public void clickAdminCircleLlyt() {
        if (adminCircleFgt == null) {
            adminCircleFgt = new AdminCircle();
        }
        switchFragment(adminCircleFgt);
        collectCardLlyt.setSelected(false);
        collectCardLlyt.setEnabled(true);
        collectCardTabline.setSelected(false);
        joinCircleLlyt.setSelected(false);
        joinCircleLlyt.setEnabled(true);
        joinCircleTabline.setSelected(false);
        adminCircleLlyt.setSelected(true);
        adminCircleLlyt.setEnabled(false);
        adminCircleTabline.setSelected(true);
    }


    /**
     * 实现Fragment的切换。
     *
     * @param f 要切换去的fragment。
     */
    private void switchFragment(Fragment f) {
        fragmentManager = this.getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if (f == currentFgt) {
            return;
        }
        if (f.isAdded()) {
            fragmentTransaction.hide(currentFgt).show(f).commit();
        } else {
            fragmentTransaction.hide(currentFgt).add(R.id.mine_minefgt_content_flyt, f).commit();
        }
        currentFgt = f;
    }

    /**
     * 导航栏按钮的监听类。
     */
    class TabBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mine_minefgt_collectcard_llyt:
                    clickCollectCardLlyt();
                    break;
                case R.id.mine_minefgt_joincircle_llyt:
                    clickJoinCircleLlyt();
                    break;
                case R.id.mine_minefgt_admincircle_llyt:
                    clickAdminCircleLlyt();
                    break;
                default:
                    break;
            }
        }
    }

}
