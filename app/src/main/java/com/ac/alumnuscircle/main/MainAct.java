/**
 * @author 白洋
 * @date 16.08.27
 * @version 2
 * 功能：这是主页面上的Activity，承载了上面的5个子Fragment。
 * 注释1：Activity和Fragment均“不”采用v4包的，使用默认的Fragment。
 */

package com.ac.alumnuscircle.main;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.main.ctc.ContactFgt;
import com.ac.alumnuscircle.main.findcc.FindCircleFgt;
import com.ac.alumnuscircle.main.home.HomeFgt;
import com.ac.alumnuscircle.main.mine.MineFgt;
import com.ac.alumnuscircle.main.msg.MsgFgt;

public class MainAct extends Activity implements View.OnClickListener{

    /** 定义一个变量，来标识是否退出
     *  实现点击两次back键退出
     * 曾博晖
     * 2016年9月13日12:40:33
     * 添加注释
     * */
    private static boolean isExit = false;

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    private Button test;//测试activity界面跳转
    private ContactFgt contactsFragment;
    private HomeFgt homeFragment;
    private FindCircleFgt findCirFragment;
    private MineFgt meFragment;
    private MsgFgt messageFragment;
    private FragmentTransaction switchFragment;//更换fragment

    private ImageView home;
    private ImageView contacts;
    private ImageView findCir;
    private ImageView msg;
    private ImageView me;
    private LinearLayout  homellyt,ctcllyt,fccllyt,msgllyt,minellyt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_mainact);
        init();
    }

    private void init(){
        initView();
        initData();
    }

    /**
     * 对点击back键的事件进行监听
     * 2016年9月12日18:25:42
     * 曾博晖
     * 添加
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 控制界面的退出，实现一个延迟
     * 2016年9月12日18:25:52
     * 曾博晖
     * 添加
     * */
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次后退键退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {

            this.finish();
        }
    }
    private void initView(){
//        test = (Button)findViewById(R.id.test);
//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ActivityName.admin_AdminAct));
//            }
//        });
        //初始化按钮
        home = (ImageView) findViewById(R.id.mainact_home_img);
        contacts = (ImageView)findViewById(R.id.mainact_ctc_img);
        findCir =(ImageView)findViewById(R.id.mainact_fcc_img);
        msg = (ImageView)findViewById(R.id.mainact_msg_img);
        me = (ImageView)findViewById(R.id.mainact_mine_img);

        //初始化布局
        homellyt = (LinearLayout)findViewById(R.id.mainact_home_llyt);
        ctcllyt = (LinearLayout)findViewById(R.id.mainact_ctc_llyt);
        fccllyt = (LinearLayout)findViewById(R.id.mainact_fcc_llyt);
        msgllyt = (LinearLayout)findViewById(R.id.mainact_msg_llyt);
        minellyt = (LinearLayout)findViewById(R.id.mainact_mine_llyt);

        //初始化fragment
        switchFragment = getFragmentManager().beginTransaction();

        homeFragment = new HomeFgt();
        findCirFragment = new FindCircleFgt();
        meFragment = new MineFgt();
        contactsFragment = new ContactFgt();
        messageFragment = new MsgFgt();

        switchFragment.add(R.id.mainact_fgtcontainerflyt,findCirFragment).hide(findCirFragment);
        switchFragment.add(R.id.mainact_fgtcontainerflyt,meFragment).hide(meFragment);
        switchFragment.add(R.id.mainact_fgtcontainerflyt,contactsFragment).hide(contactsFragment);
//        switchFragment.add(R.id.mainact_fgtcontainerflyt,messageFragment).hide(messageFragment);
        switchFragment.add(R.id.mainact_fgtcontainerflyt,messageFragment).hide(messageFragment);
        switchFragment.add(R.id.mainact_fgtcontainerflyt,homeFragment);
        switchFragment.commit();

        //设置监听
        homellyt.setOnClickListener(this);
        fccllyt.setOnClickListener(this);
        ctcllyt.setOnClickListener(this);
        msgllyt.setOnClickListener(this);
        minellyt.setOnClickListener(this);

        homellyt.setSelected(true);
        homellyt.setEnabled(false);
    }

    private void initData(){

    }

    /**
     * 初始化监听的fragment
     */
    private void InitFragment()
    {
        //重新加载fragment选择器
        switchFragment = getFragmentManager().beginTransaction();
        //隐藏所有fragment
        if(homeFragment!=null)
            switchFragment.hide(homeFragment);
        if(findCirFragment!=null)
            switchFragment.hide(findCirFragment);
        if(meFragment!=null)
            switchFragment.hide(meFragment);
        if(messageFragment!=null)
            switchFragment.hide(messageFragment);
        if(contactsFragment!=null)
            switchFragment.hide(contactsFragment);

        //初始化所有图标
        homellyt.setSelected(false);
        ctcllyt.setSelected(false);
        fccllyt.setSelected(false);
        msgllyt.setSelected(false);
        minellyt.setSelected(false);

        homellyt.setEnabled(true);
        ctcllyt.setEnabled(true);
        fccllyt.setEnabled(true);
        msgllyt.setEnabled(true);
        minellyt.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        InitFragment();
        switch (v.getId())
        {
            case R.id.mainact_ctc_llyt:
               if(contactsFragment==null)
               {
                   contactsFragment = new ContactFgt();
                   switchFragment.add(R.id.mainact_fgtcontainerflyt,contactsFragment);
               }
                else
               {
                   switchFragment.show(contactsFragment);
               }
                ctcllyt.setSelected(true);
                ctcllyt.setEnabled(false);
                break;

            case R.id.mainact_fcc_llyt:
                if(findCirFragment==null)
                {
                    findCirFragment = new FindCircleFgt();
                    switchFragment.add(R.id.mainact_fgtcontainerflyt,findCirFragment);

                }
                else{
                    switchFragment.show(findCirFragment);
                }
                fccllyt.setSelected(true);
                fccllyt.setEnabled(false);
                break;
            case R.id.mainact_home_llyt:
                if(homeFragment==null)
                {
                    homeFragment = new HomeFgt();
                    switchFragment.add(R.id.mainact_fgtcontainerflyt,homeFragment);

                }
                else{
                    switchFragment.show(homeFragment);
                }
               homellyt.setSelected(true);
                homellyt.setEnabled(false);
                break;
            case R.id.mainact_mine_llyt:
                if(meFragment==null)
                {
                    meFragment = new MineFgt();
                    switchFragment.add(R.id.mainact_fgtcontainerflyt,meFragment);
                }
                else
                {
                    switchFragment.show(meFragment);
                }
                minellyt.setSelected(true);
                minellyt.setEnabled(false);
                break;
            case R.id.mainact_msg_llyt:
                if(messageFragment==null)
                {
                    messageFragment = new MsgFgt();
                    switchFragment.add(R.id.mainact_fgtcontainerflyt,messageFragment);
                }
                else
                {
                    switchFragment.show(messageFragment);
                }
                msgllyt.setSelected(true);
               msgllyt.setEnabled(false);
                break;
        }
             switchFragment.commit();
    }

    /**
     * 取消重叠
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
}
