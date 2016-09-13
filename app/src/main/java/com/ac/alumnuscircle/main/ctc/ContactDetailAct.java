package com.ac.alumnuscircle.main.ctc;
/**
 * @author 曾博晖
 * @date 2016年8月28日
 * @verson 1
 * 功能：实现人脉详情界面
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.Login;
import com.ac.alumnuscircle.auth.MyInfo;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.init.InitLeanCloud;
import com.ac.alumnuscircle.main.ctc.leavemsg.CustomUserProvider;
import com.ac.alumnuscircle.toolbox.json.MapToJson;
import com.ac.alumnuscircle.toolbox.json.ParseComplexJson;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.Map;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ContactDetailAct extends Activity implements View.OnClickListener {
    private SimpleDraweeView headImg_bottom;
    private SimpleDraweeView headImg_Top;

    private TextView userName;
    private TextView userJobTitle;
    private TextView userLocation;
    private TextView userSchool;
    private TextView userDepartment;
    private TextView userClass;
    private TextView userEduStartDate;
    private TextView userCompany;
    private TextView userTelephone;
    private TextView follow_tv;
    private static String job;

    private String uid;
    private String headImgUrl;
    private static String telephone;
    private static String target;
    private static Boolean IsFollowed;
    //下面的都是可以监听事件的
//    private Button btn_leaveMsg;
    private ImageButton btn_back;
   //2016年9月9日18:53:41 将留言按钮注释掉，临时改为使用左下角的原查看动态的按钮
    private LinearLayout dynamicLayout;
    private LinearLayout collectLayout;

    private String httpPostUrl;
    private String httpFollowPostUrl;
    private static Handler mHandler;
    /**
     * 获取到人脉详情的数据
     * */
    private static final int HASGOTDATA=0x26;
    /**
     * 获取到收藏或是取消收藏的回复
     * */
    private static final int HASGOTFOLLOW=0x28;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ctc_contactdetailact);
        initWidget();
        new Thread(postTask).start();
        mHandler=new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==HASGOTDATA){
                    initView();
                }
                if (msg.what==HASGOTFOLLOW){
                    if(IsFollowed)
                        follow_tv.setText("取消收藏");
                    if(!IsFollowed)
                        follow_tv.setText("收藏名片");
                }
            }
        };

    }
    /**
     * 开启post请求的线程
     * */
    Runnable postTask =new Runnable() {
        @Override
        public void run() {
            HttpPost();
        }
    };
    /**
     * 发送Http Post请求，获取到人脉详情
     * 2016年9月10日23:22:23
     * 曾博晖
     * 创建
     * */
    public void HttpPost(){

        RequestBody formBody = new FormBody.Builder()
                .add("_xsrf", HttpGet.loginKey)
                .add("uid",uid)
                .build();

        Request request = new Request.Builder()
                .addHeader("Cookie", Login.UID)
                .url(httpPostUrl)
                .post(formBody)
                .build();
        try{
            Response response =
                    HttpGet.okHttpClient.newCall(request).execute();
            Log.d("Headers 是",response.headers().toString());
            Log.i("the CDA key is",HttpGet.loginKey);
            if(response.isSuccessful()) {
                final String receiveStr = response.body().string();
                Log.i("the CDA DATA ", receiveStr);
                AnalyzeResponse(receiveStr);
                Message message=new Message();
                message.what=HASGOTDATA;
                mHandler.sendMessage(message);
            }
            response.body().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对云端返回的收藏名片的数据进行分析，
     * 曾博晖
     * 2016年9月10日23:33:55
     * 创建
     * */
    private void AnalyzeResponse(String receiveStr) {
        //进行第一步解析
        Map<String, Object> result = new HashMap<>();
        ParseComplexJson.recursiveParseJson(result, receiveStr, null);
        //将解析后的一维数据加到finalComment里面，并且剔除掉无关数据
        for(Map.Entry<String, Object> entry : result.entrySet()){
            Log.i("DETAIL IS ", entry.getKey() + " : " + entry.getValue());
            if(entry.getKey().length()>14){
                if(entry.getKey().split("@")[2].equals("source_uid")){
                    telephone=entry.getValue().toString().substring(1,
                            entry.getValue().toString().length()-1);
                }
                if(entry.getKey().split("@")[2].equals("custom")){
                      if(entry.getKey().split("@")[3].equals("jo")){
                          job=entry.getValue().toString().substring(1,
                                  entry.getValue().toString().length()-1);
                      }
                }
                if(entry.getKey().split("@")[2].equals("has_followed")){
                    String value=entry.getValue().toString().substring(1,
                            entry.getValue().toString().length()-1);
                    if(value.equals("False")){
                        IsFollowed=false;
                    }else {
                        IsFollowed=true;
                    }
                }
            }
        }
    }
    /**
     * 初始化控件列表
     * 2016年8月26日14:43:14
     * 曾博晖
     * 创建
     * */
    private void initWidget(){
        headImg_bottom=(SimpleDraweeView) findViewById(R.id.ctc_contactdetailact_headImgBottom);
        headImg_Top=(SimpleDraweeView) findViewById(R.id.ctc_contactdetailact_headImgTop);
        follow_tv=(TextView)findViewById(R.id.ctc_contactdetailact_follow_tv);

        userName=(TextView)findViewById(R.id.ctc_contactdetailact_Name_tv);
        userJobTitle=(TextView)findViewById(R.id.ctc_contactdetailact_JobTitle_tv);
        userLocation=(TextView)findViewById(R.id.ctc_contactdetailact_Location_tv);
        userSchool=(TextView)findViewById(R.id.ctc_contactdetailact_School_tv);
        userDepartment=(TextView)findViewById(R.id.ctc_contactdetailact_Department_tv);
//        userClass=(TextView)findViewById(R.id.ctc_contactdetailact_Class_tv);
        userEduStartDate=(TextView)findViewById(R.id.ctc_contactdetailact_EduStartDate_tv);
        userCompany=(TextView)findViewById(R.id.ctc_contactdetailact_Company_tv);
        userTelephone=(TextView)findViewById(R.id.ctc_contactdetailact_tele_tv);

        btn_back=(ImageButton)findViewById(R.id.ctc_contactdetailact_btn_back);
//        btn_leaveMsg=(Button)findViewById(R.id.ctc_contactdetailact_leaveMsg_btn);
        collectLayout=(LinearLayout)findViewById(R.id.ctc_contactdetailact_collect_lly);
        dynamicLayout=(LinearLayout)findViewById(R.id.ctc_contactdetailact_leaveMsg_btn);

        httpPostUrl=HttpGet.httpGetUrl+"/user_detail";
        httpFollowPostUrl=HttpGet.httpGetUrl+"/follow";
        IsFollowed=false;
        Bundle bundle=getIntent().getExtras();
        uid=bundle.getString("uid");

        Log.i("<><><><><>>ID<",uid);



    }
    /**
     * 初始化界面，接收上个界面传来的数据
     * 曾博晖 2016年8月26日14:55:08
     * 创建
     * */
    private void initView(){
        Bundle bundle=getIntent().getExtras();
        headImgUrl=bundle.getString("headImgUrl");
        headImg_bottom.setImageURI(Uri.parse(headImgUrl));
        headImg_Top.setImageURI(Uri.parse(headImgUrl));
        userName.setText(bundle.getString("name"));
        userDepartment.setText(bundle.getString("department"));
        userJobTitle.setText(bundle.getString("job"));
//        userClass.setText(bundle.getString("class"));
        userEduStartDate.setText(bundle.getString("grade"));
        userLocation.setText(bundle.getString("location"));
        userCompany.setText(bundle.getString("company"));
        if(job!=null){
            userCompany.setText(job);
        }
        if(IsFollowed){
            follow_tv.setText("取消收藏");

        }else {
            follow_tv.setText("收藏名片");
        }
        btn_back.setOnClickListener(this);
//        btn_leaveMsg.setOnClickListener(this);
        dynamicLayout.setOnClickListener(this);
        userTelephone.setText(telephone);
        collectLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ctc_contactdetailact_btn_back:
                finish();
                break;
            case R.id.ctc_contactdetailact_collect_lly:
                new Thread(followTask).start();
                break;
            case R.id.ctc_contactdetailact_leaveMsg_btn:
                gotoLeaveMsg();
                Toast.makeText(this,"将对"+userName.getText().toString()
                        +"进行留言",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
    Runnable followTask =new Runnable() {
        @Override
        public void run() {
            FollowPost();
        }
    };

    /**
     * 表示点赞或是取消赞时的Http请求
     * 2016年9月11日05:34:29
     * */
    private void FollowPost(){
         if(IsFollowed){
             target="unfollow";
         }
         if(!IsFollowed){
             target="follow";
         }
        RequestBody formBody = new FormBody.Builder()
                .add("_xsrf", HttpGet.loginKey)
                .add("target",target)
                .add("uid",uid)
                .build();
        Request request = new Request.Builder()
                .addHeader("Cookie", Login.UID)
                .url(httpFollowPostUrl)
                .post(formBody)
                .build();
        try{
            Response response =
                    HttpGet.okHttpClient.newCall(request).execute();
            Log.d("Headers 是",response.headers().toString());

            Log.i("the Follow key is",HttpGet.loginKey);
            if(response.isSuccessful()) {
//                if(IsFollowed){
//                    IsFollowed=false;
//                }else {
//                    IsFollowed=true;
//                }
                IsFollowed=!IsFollowed;
                Message message=new Message();
                message.what=HASGOTFOLLOW;
                mHandler.sendMessage(message);
            }
            response.body().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /**
    * 进行留言
    * */
    private void gotoLeaveMsg() {

        LCChatKitUser lcChatKitUser=new LCChatKitUser(userName.getText().toString(),
                userName.getText().toString(),headImgUrl);
        if(!CustomUserProvider.partUsers.contains(lcChatKitUser)) {
            CustomUserProvider.partUsers.add(lcChatKitUser);
        }
        LCChatKit.getInstance().open(MyInfo.myInfo.getName(), new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (null == e) {
                    finish();
                    Intent intent = new Intent(ContactDetailAct.this,
                            LCIMConversationActivity.class);
                    intent.putExtra(LCIMConstants.PEER_ID, userName.getText().toString());
                    intent.putExtra("theName",userName.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(ContactDetailAct.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

