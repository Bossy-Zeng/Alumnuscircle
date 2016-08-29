/**
 * @author Zhengfan
 * @date 16.08.29
 * @version 1
 * 功能：修改圈子头像和名称的界面。
 */

package com.ac.alumnuscircle.main.findcc.ccdtl.set;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ac.alumnuscircle.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class ResetNameHdimg extends Activity {

    private ImageButton set_resetnamehdimg_back_imgbtn;
    private ImageButton set_resetnamehdimg_camera_btn;
    private SimpleDraweeView set_resetnamehdimg_headimg;
    private EditText set_resetnamehdimg_name_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_resetnamehdimgact);
        init();
    }

    private void init(){
        initUI();
        initData();
    }

    private void initUI(){
        set_resetnamehdimg_back_imgbtn = (ImageButton)findViewById(R.id.set_resetnamehdimg_back_imgbtn);
        set_resetnamehdimg_camera_btn = (ImageButton)findViewById(R.id.set_resetnamehdimg_camera_btn);
        set_resetnamehdimg_headimg = (SimpleDraweeView)findViewById(R.id.set_resetnamehdimg_headimg);
        set_resetnamehdimg_name_et = (EditText)findViewById(R.id.set_resetnamehdimg_name_et);

        set_resetnamehdimg_back_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        set_resetnamehdimg_camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initData(){
        set_resetnamehdimg_name_et.setText("软件圈");
        set_resetnamehdimg_headimg.setImageURI(Uri.parse("http://f.hiphotos.baidu.com/zhidao/wh%3D600%2C800/sign=39af5230eaf81a4c2667e4cfe71a4c61/3812b31bb051f819b384e30edbb44aed2f73e7d5.jpg"));
    }


}
