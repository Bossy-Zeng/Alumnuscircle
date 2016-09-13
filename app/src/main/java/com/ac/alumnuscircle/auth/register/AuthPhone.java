/**
 * @author 吴正凡
 * @date 16.09.03
 * @version 1
 * 功能：注册第一个界面，验证手机号。
 */

package com.ac.alumnuscircle.auth.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ac.alumnuscircle.R;

import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.auth.httpreq.RequestData;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthPhone extends Activity {


    private static String httpPostUrl;

    private static String postReq;
    private LinearLayout back_llyt;
    private EditText phone_et;
    private EditText passwd_et;
    private Button register_btn;
    private Gson gson;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_authphoneact);
        init();
    }

    private void init(){
        initView();
        initData();
        HttpGet httpGet=new HttpGet();
        httpGet.doHttpGet();
    }

    private void initView(){
        back_llyt = (LinearLayout)findViewById(R.id.register_authphoneact_back_llyt);
        back_llyt.setVisibility(View.GONE);
        phone_et = (EditText)findViewById(R.id.register_authphoneact_phone_et);
        passwd_et = (EditText)findViewById(R.id.register_authphoneact_passwd_et);
        register_btn = (Button)findViewById(R.id.register_authphoneact_register_btn);


//        HttpGet.httpGetUrl="http://192.168.2.5:8000";
//        httpPostUrl="http://192.168.2.5:8000/checkphone";

        httpPostUrl=HttpGet.httpGetUrl+"/checkphone";
        gson=new Gson();

         mHandler=new Handler() {
             @Override
             public void handleMessage(Message msg) {
                 super.handleMessage(msg);
                 // 接收消息
                 if (msg.what == 3001) {
                     Toast.makeText(AuthPhone.this,"手机号已经被注册！",
                             Toast.LENGTH_SHORT).show();
                     phone_et.setText("");
                 }
                 if (msg.what == 3000) {
                     Toast.makeText(AuthPhone.this,"验证通过！",
                             Toast.LENGTH_SHORT).show();
                     gotoNext();
                 }
                 if (msg.what == 3003) {
                     Toast.makeText(AuthPhone.this,"手机号格式错误",
                             Toast.LENGTH_SHORT).show();
                     phone_et.setText("");
                 }

             }
         };
    }

    private void initData(){
        back_llyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern phonePattern = Pattern.compile("[1][34578][0-9]{9}");
                Matcher phoneMatcher = phonePattern.matcher(
                        phone_et.getText().toString());
                Pattern pwdPattern=Pattern.compile("^[a-zA-Z]\\w{5,64}$\\Z");
                Matcher pwdMatcher=pwdPattern.matcher(
                        passwd_et.getText().toString());
                if(passwd_et.getText().toString().equals("")
                        ||phone_et.getText().toString().equals("")){
                    Toast.makeText(AuthPhone.this,"请先输入手机号和密码！",
                            Toast.LENGTH_SHORT).show();
                    passwd_et.setText("");
                }else if(!phoneMatcher.matches()){
                    Toast.makeText(AuthPhone.this,"您输入的手机号格式错误！",
                            Toast.LENGTH_SHORT).show();
                    passwd_et.setText("");
                }else if(!pwdMatcher.matches()){
                    Toast.makeText(AuthPhone.this,"密码为英文开头的5-64位",
                            Toast.LENGTH_SHORT).show();
                    passwd_et.setText("");
                }
                else{
//                    Intent intent = new Intent(ActivityName.register_AuthName);
//                    startActivity(intent);
                    Thread postThread = new Thread(postTask);
                    postThread.start();
                }
                /**
                 * 点击注册按钮，跳到下一个界面。
                 */
//               gotoNext();
            }
        });
    }

    
    /**
     * 开启新线程来进行post操作
     * @author 曾博晖
     * @verson 1
     * @date 2016年9月6日09:48:06
     * */
    Runnable postTask =new Runnable() {
        @Override
        public void run() {
            Log.i("BEGIN",httpPostUrl);
            PostReq();
        }
    };

    /**
     * 执行Post请求，来进行电话号码的验证测试
     * @date 2016年9月6日09:49:22
     * @verson 1
     * @author 曾博晖
     * */
    public void PostReq(){
        if(HttpGet.loginKey == null || phone_et.getText().toString() == null){
            return;
        }
        RequestBody formBody = new FormBody.Builder()
                .add("_xsrf", HttpGet.loginKey)
                .add("telephone", phone_et.getText().toString())
                .build();

        Request request = new Request.Builder()
                .addHeader("Cookie",HttpGet.loginHeader)
                .url(httpPostUrl)
                .post(formBody)
                .build();
        try{
            Response response = HttpGet.okHttpClient.newCall(request).execute();
            if(response.isSuccessful()){
                final String receiveStr = response.body().string();
                validatePhone(receiveStr);
                Log.i("PostReq", receiveStr);
                response.body().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 对返回的数据进行解析
     * 查询出code数据
     * 来进行号码的进一步验证
     * 2016年9月6日09:57:54
     * 曾博晖
     * 创建
     * */
    private void validatePhone(String res){
        RequestData requestData=gson.fromJson(res,RequestData.class);
        Message message =new Message();
        switch (requestData.getCode()){
            case "3001":
                message.what=3001;
                break;
            case "3000":
                message.what=3000;
                break;
            case "3003":
            case "3002":
                message.what=3003;
                break;
            default:
                break;
        }
        mHandler.sendMessage(message);

    }


    /**
     * 跳转到下一个界面
     * 2016年9月6日09:18:01
     * 曾博晖创建
     * */
    private void gotoNext(){
        Intent intent = new Intent(ActivityName.register_AuthName);
        //将账号密码存入
        RegisterUser.telephone=phone_et.getText().toString();
        RegisterUser.password=passwd_et.getText().toString();
        startActivity(intent);
        finish();
    }

}
