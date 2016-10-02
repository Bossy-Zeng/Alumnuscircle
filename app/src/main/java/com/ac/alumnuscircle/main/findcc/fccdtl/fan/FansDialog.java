/**
 * @author 吴正凡
 * @date 16.08.30
 * @version 1
 * 功能：实现创建圈子的自定义对话框。
 */

package com.ac.alumnuscircle.main.findcc.fccdtl.fan;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.beans.FindCircleDetail;
import com.ac.alumnuscircle.beans.MyCircle;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.main.home.home_rv.HomeItem;
import com.ac.alumnuscircle.net.CookieUtils;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FansDialog extends Dialog {

    private Context context;
    private int themeResId;
    private boolean cancelable;
    private OnCancelListener cancelListener;
    private static OkHttpClient client = new OkHttpClient();
    private static Gson gson = new Gson();
    private SimpleDraweeView fan_fansdialog_hdimg;
    private SimpleDraweeView fan_fansdialog_hdimg_bg;
    private SimpleDraweeView fan_fansdialog_hdimg_bg_mask;
    private TextView fan_fansdialog_circlename_tv;
    private TextView fan_fansdialog_memberinfo_tv;
    private TextView fan_fansdialog_intro_tv;
    private Button fan_fansdialog_connectus_btn;
    private Button fan_fansdialog_applyjoin_btn;
    private String circleName,circleSign,circleImgUrl,createTime,circleId;
    private FindCircleDetail.DataBean.ResponseBean.ResultsBean.CustomBean customBean;
    private Window window = null;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0X100) {
                Toast.makeText(getContext(),"申请已发送",Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(getContext(),"申请发送失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        }
    };
    public FansDialog(Context context,String circleId,String circleName,String createTime,
                      FindCircleDetail.DataBean.ResponseBean.ResultsBean.CustomBean customBean,
                    String circleImgUrl, String circleSign) {
        super(context);
        this.context = context;
        this.circleName = circleName;
        this.circleSign = circleSign;
        this.circleId = circleId;
        this.createTime=createTime;
        this.customBean = customBean;
        this.circleImgUrl = circleImgUrl;
    }

    public FansDialog(Context context, int themeResId, String circleId,String circleName,
                      String circleImgUrl,String createTime
            , FindCircleDetail.DataBean.ResponseBean.ResultsBean.CustomBean customBean,
                      String circleSign) {
        super(context, themeResId);
        this.context = context;
        this.themeResId = themeResId;
        this.circleName = circleName;
        this.circleSign = circleSign;
       this.customBean = customBean;
      this.createTime = createTime;
        this.circleImgUrl = circleImgUrl;
        this.circleId=circleId;
    }

    protected FansDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        this.cancelable = cancelable;
        this.cancelListener = cancelListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fan_fansdialog);
        init();
    }

    private void init() {
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
      int  x = window.getWindowManager().getDefaultDisplay().getWidth()/100;
       int y = window.getWindowManager().getDefaultDisplay().getHeight()/100;
        wl.x = x; //x小于0左移，大于0右移
        wl.y = y; //y小于0上移，大于0下移
//            wl.alpha = 0.6f; //设置透明度
//            wl.gravity = Gravity.BOTTOM; //设置重力
        window.setAttributes(wl);
    }

    private void initView() {
        fan_fansdialog_hdimg = (SimpleDraweeView)findViewById(R.id.fan_fansdialog_hdimg);
        fan_fansdialog_hdimg_bg = (SimpleDraweeView)findViewById(R.id.fan_fansdialog_hdimg_bg);
        fan_fansdialog_hdimg_bg_mask = (SimpleDraweeView)findViewById(R.id.fan_fansdialog_hdimg_bg_mask);
        fan_fansdialog_circlename_tv = (TextView)findViewById(R.id.fan_fansdialog_circlename_tv);
        fan_fansdialog_memberinfo_tv = (TextView)findViewById(R.id.fan_fansdialog_memberinfo_tv);
        fan_fansdialog_intro_tv = (TextView)findViewById(R.id.fan_fansdialog_intro_tv);
        fan_fansdialog_connectus_btn = (Button)findViewById(R.id.fan_fansdialog_connectus_btn);
        fan_fansdialog_applyjoin_btn = (Button)findViewById(R.id.fan_fansdialog_applyjoin_btn);
    }

    private void initData() {
        fan_fansdialog_hdimg.setImageURI(Uri.parse(circleImgUrl));
        fan_fansdialog_hdimg_bg.setImageURI(Uri.parse(circleImgUrl));
        fan_fansdialog_hdimg_bg_mask.setImageURI(Uri.parse("res:///" + R.mipmap.gradient_mask));
        fan_fansdialog_circlename_tv.setText(circleName);
        fan_fansdialog_memberinfo_tv.setText("创建时间 "+createTime+"  ·  创建人 "+customBean.getCreator_name());

        fan_fansdialog_intro_tv.setText(circleSign);

        fan_fansdialog_connectus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  dismiss();
                Intent conntectUs = new Intent(ActivityName.fan_ConntectUs);
                conntectUs.putExtra("Id",customBean.getVirtual_cid());
                getContext().startActivity(conntectUs);
            }
        });

        fan_fansdialog_applyjoin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RequestBody myCircleBody = new FormBody.Builder()
                                .add("_xsrf", CookieUtils._xsrfKey)
                               .add("circle_name",circleName)
                                .add("circle_id",circleId)
                                .add("circle_url", circleImgUrl)
                                .add("reason","" )
                                .add("creator_id",customBean.getCreator_uid()).build();

                        Request requestMyCircle  = new Request.Builder().url(HttpGet.httpGetUrl+"/apply_circle").post(myCircleBody)
                                .addHeader("Cookie", CookieUtils.cookie).build();
                        try {
                            Response response = client.newCall(requestMyCircle).execute();
                            if(response.isSuccessful()) {
                                handler.sendEmptyMessage(0x100);
                            }
                            else{
                                handler.sendEmptyMessage(0x101);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                dismiss();
            }
        });
    }
}
