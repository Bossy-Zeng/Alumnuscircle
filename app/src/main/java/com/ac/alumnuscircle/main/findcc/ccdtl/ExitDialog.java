package com.ac.alumnuscircle.main.findcc.ccdtl;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.cstt.ActivityName;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by 白洋
 * 2016年9月13日15:44:59
 */
public class ExitDialog extends Dialog {

    private Context context;
    private int themeResId;
    private boolean cancelable;
    private OnCancelListener cancelListener;

    private SimpleDraweeView ccdtl_exitdialog_hdimg;
    private SimpleDraweeView ccdtl_exitdialog_hdimg_bg;
    private SimpleDraweeView ccdtl_exitdialog_hdimg_bg_mask;
    private TextView ccdtl_exitdialog_circlename_tv;

    private Button ccdtl_exitdialog_yes_btn;
    private Button ccdtl_exitdialog_no_btn;
    private String circleName,circleImgUrl;

    private Window window = null;
    public ExitDialog(Context context,String circleName,String circleImgUrl) {
        super(context);
        this.context = context;
        this.circleName = circleName;
        this.circleImgUrl = circleImgUrl;
    }

    public ExitDialog(Context context, int themeResId,String circleName, String circleImgUrl) {
        super(context, themeResId);
        this.context = context;
        this.circleName = circleName;
        this.circleImgUrl = circleImgUrl;
        this.themeResId = themeResId;
    }

    protected ExitDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        this.cancelable = cancelable;
        this.cancelListener = cancelListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ccdtl_exitdialog);
        initView();
        initData();

        windowDeploy();
        setCanceledOnTouchOutside(true);
    }

    private void windowDeploy(){
        window = getWindow(); //得到对话框
        window.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
        window.setBackgroundDrawableResource(R.color.transparent); //设置对话框背景为透明
        WindowManager.LayoutParams wl = window.getAttributes();
        //根据x，y坐标设置窗口需要显示的位置
        int  x = window.getWindowManager().getDefaultDisplay().getWidth()/50;
        int y = window.getWindowManager().getDefaultDisplay().getHeight()/100;
        wl.x = x; //x小于0左移，大于0右移
        wl.y = y; //y小于0上移，大于0下移
//            wl.alpha = 0.6f; //设置透明度
//            wl.gravity = Gravity.BOTTOM; //设置重力
        window.setAttributes(wl);
    }

    private void initView() {
        ccdtl_exitdialog_hdimg = (SimpleDraweeView)findViewById(R.id.ccdtl_exitdialog_hdimg);
        ccdtl_exitdialog_hdimg_bg = (SimpleDraweeView)findViewById(R.id.ccdtl_exitdialog_hdimg_bg);
        ccdtl_exitdialog_hdimg_bg_mask = (SimpleDraweeView)findViewById(R.id.ccdtl_exitdialog_hdimg_bg_mask);
        ccdtl_exitdialog_circlename_tv = (TextView)findViewById(R.id.ccdtl_exitdialog_circlename_tv);

        ccdtl_exitdialog_yes_btn = (Button)findViewById(R.id.ccdtl_exitdialog_yes_btn);
        ccdtl_exitdialog_no_btn = (Button)findViewById(R.id.ccdtl_exitdialog_no_btn);
    }

    private void initData() {
        ccdtl_exitdialog_hdimg.setImageURI(Uri.parse(circleImgUrl));
        ccdtl_exitdialog_hdimg_bg.setImageURI(Uri.parse(circleImgUrl));
        ccdtl_exitdialog_hdimg_bg_mask.setImageURI(Uri.parse("res:///" + R.mipmap.gradient_mask));
        ccdtl_exitdialog_circlename_tv.setText("确定退出"+circleName+"?");


        ccdtl_exitdialog_yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"您已退出圈子",Toast.LENGTH_SHORT).show();
                dismiss();
                getContext().startActivity(new Intent(ActivityName.main_MainAct));
            }
        });

        ccdtl_exitdialog_no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
    }
}
