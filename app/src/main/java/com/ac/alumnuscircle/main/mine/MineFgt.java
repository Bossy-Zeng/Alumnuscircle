/**
 * @author 吴正凡
 * @date 16.08.26
 * @version 1
 * 功能：这是主页中，“我的”分页。
 */

package com.ac.alumnuscircle.main.mine;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.MyInfo;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.main.MainAct;
import com.ac.alumnuscircle.main.mine.minecontent.AdminCircle;
import com.ac.alumnuscircle.main.mine.minecontent.CollectCard;
import com.ac.alumnuscircle.main.mine.minecontent.CreateCircle;
import com.ac.alumnuscircle.toolbox.json.JsonToMap;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MineFgt extends Fragment {

    private MainAct mainAct;

    private View view;
    private LayoutInflater layoutInflater;
    private ViewGroup container;

    private SimpleDraweeView userHdimgSdv;
    private RelativeLayout cameraRlyt;
    private TextView userNameTv;
    private TextView userCareerTv;
    private TextView userMajorTv;
    private ImageButton settingImgbtn;
    private ImageButton logoutImgbtn;
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

    private void init() {
        initUIBtn();
        initFragment();
        initCollectCard();
        initData();
    }

    private void initData() {
        if (MyInfo.myInfo.getIcon_url() != null) {
            userHdimgSdv.setImageURI(
                    Uri.parse(MyInfo.myInfo.getIcon_url()));
        } else {
            userHdimgSdv.setImageURI(
                    Uri.parse("http://img0.imgtn.bdimg.com/it/u=3691748163,484693479&fm=206&gp=0.jpg"));
        }
        userNameTv.setText(MyInfo.myInfo.getName());
        userCareerTv.setText(MyInfo.myInfo.getJob());
        userMajorTv.setText(MyInfo.myInfo.getFaculty() +
                MyInfo.myInfo.getAdmission_year());
    }

    private void initUIBtn() {
        logoutImgbtn = (ImageButton) view.findViewById(R.id.mine_minefgt_logout_imgbtn);
        settingImgbtn = (ImageButton) view.findViewById(R.id.mine_minefgt_setting_imgbtn);
        userHdimgSdv = (SimpleDraweeView) view.findViewById(R.id.mine_minefgt_hdimg_sdv);
        cameraRlyt = (RelativeLayout) view.findViewById(R.id.mine_minefgt_camera_rlyt);
        userNameTv = (TextView) view.findViewById(R.id.mine_minefgt_username);
        userCareerTv = (TextView) view.findViewById(R.id.mine_minefgt_usercareer);
        userMajorTv = (TextView) view.findViewById(R.id.mine_minefgt_usermajor);
        collectCardLlyt = (LinearLayout) view.findViewById(R.id.mine_minefgt_collectcard_llyt);
        joinCircleLlyt = (LinearLayout) view.findViewById(R.id.mine_minefgt_joincircle_llyt);
        adminCircleLlyt = (LinearLayout) view.findViewById(R.id.mine_minefgt_admincircle_llyt);
        collectCardTabline = (ImageView) view.findViewById(R.id.mine_minefgt_collectcard_tabline);
        joinCircleTabline = (ImageView) view.findViewById(R.id.mine_minefgt_joincircle_tabline);
        adminCircleTabline = (ImageView) view.findViewById(R.id.mine_minefgt_admincircle_tabline);

        logoutImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 登出
                 */
//                Toast.makeText(getActivity(), "你已经登出~", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RequestBody formBody = new FormBody.Builder()
                                .add("_xsrf", HttpGet.loginKey)
                                .build();
                        Request request = new Request.Builder()
                                .addHeader("Cookie", HttpGet.loginHeader)
                                .url(HttpGet.httpGetUrl + "/logout")
                                .post(formBody)
                                .build();
                        try {
                            Response response = HttpGet.okHttpClient.newCall(request).execute();
                            if (response.isSuccessful()) {
                                response.body().close();
                                System.exit(0);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

        settingImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityName.mine_SettingAct);
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

    private void initFragment() {
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
            joinCircleFgt = new CreateCircle();
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
