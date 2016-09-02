/**
 * @author白洋
 * @Date 2016/8/28.
 * @version 2
 * 圈子详情的页面
 */
package com.ac.alumnuscircle.main.findcc.ccdtl;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.facebook.drawee.view.SimpleDraweeView;


public class CircleDetailAct extends AppCompatActivity {

    private ImageView float_button;
    private Button edit, chat, share, exit,  setting, back, invite;
    private Toolbar toolbar;
    private LinearLayout redBg;
    private  TextView circleName;
    private SimpleDraweeView circleImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ccdtl_circledetail);
       Init();

    }


    private void Init()
    {
        InitView();
        InitData();
    }

    private void InitData()
    {
        String CircleName = getIntent().getStringExtra("CircleDetailName");
        String CircleImageUrl = getIntent().getStringExtra("CircleDetailImgurl");
        circleName.setText(CircleName);
        Uri imageUri = Uri.parse(CircleImageUrl);
        circleImg.setImageURI(imageUri);
    }

    /**
     * 隐藏toolbar图标
     */
    private void HideToolbar() {
        TextView tv = (TextView) findViewById(R.id.ccdtl_circledetail_tvback);
        tv.setVisibility(View.GONE);

        setting.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
    }

    /**
     * 显示toolbar图标
     */
    private void ShowToolbar() {
        TextView tv = (TextView) findViewById(R.id.ccdtl_circledetail_tvback);
        tv.setVisibility(View.VISIBLE);

        setting.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化界面
     */
    private void InitView() {
        //初始化

        toolbar = (Toolbar) findViewById(R.id.ccdtl_circledetail_tlb);
        setSupportActionBar(toolbar);
        circleName = (TextView )findViewById(R.id.ccdtl_circledetail_ccname_tv);
        circleImg = (SimpleDraweeView)findViewById(R.id.ccdtl_circledetail_img);
        float_button = (ImageView) findViewById(R.id.ccdtl_circledetail_float);
//        invite = (Button) findViewById(R.id.ccdtl_circledetail_invite);
//        search = (Button) findViewById(R.id.detail_search);
        setting = (Button) findViewById(R.id.ccdtl_circledetail_setting);
        edit = (Button) findViewById(R.id.ccdtl_circledetail_edit);
        chat = (Button) findViewById(R.id.ccdtl_circledetail_chat);
        share = (Button) findViewById(R.id.ccdtl_circledetail_share);
        exit = (Button) findViewById(R.id.ccdtl_circledetail_exit);
        back = (Button) findViewById(R.id.ccdtl_circledetail_back);
        redBg = (LinearLayout) findViewById(R.id.ccdtl_circledetail_redBg);

        //监听
        float_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                redBg.setFocusable(true);
                redBg.setVisibility(View.VISIBLE);
                //隐藏浮动按钮
                float_button.setVisibility(View.GONE);
                float_button.setFocusable(false);
                //隐藏toolbar
                HideToolbar();

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redBg.setFocusable(false);
                redBg.setVisibility(View.GONE);
                //显示浮动按钮
                float_button.setVisibility(View.VISIBLE);
                float_button.setFocusable(true);
                //显示toolbar
                ShowToolbar();
            }


        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CircleDetailAct.this, "这是聊天", Toast.LENGTH_SHORT).show();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CircleDetailAct.this, "这是设置", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
