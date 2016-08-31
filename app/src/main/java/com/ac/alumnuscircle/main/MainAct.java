/**
 * @author Zhengfan
 * @date 16.08.27
 * @version 2
 * 功能：这是主页面上的Activity，承载了上面的5个子Fragment。
 * 注释1：Activity和Fragment均“不”采用v4包的，使用默认的Fragment。
 */

package com.ac.alumnuscircle.main;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.main.ctc.ContactFgt;
import com.ac.alumnuscircle.main.findcc.FindCircleFgt;
import com.ac.alumnuscircle.main.home.HomeFgt;
import com.ac.alumnuscircle.main.mine.MineFgt;
import com.ac.alumnuscircle.main.msg.MsgFgt;

public class MainAct extends Activity implements View.OnClickListener{

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

    private void initView(){
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

                break;
        }
             switchFragment.commit();
    }
}
