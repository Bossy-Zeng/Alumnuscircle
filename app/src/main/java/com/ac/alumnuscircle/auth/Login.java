/**
 * @author 曾博晖
 * @date 2016年9月5日10:10:28
 * @version 2
 * 功能：实现用户登录。
 */

package com.ac.alumnuscircle.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.auth.loginJson.PostResData;
import com.ac.alumnuscircle.auth.loginJson.RequestData;
import com.ac.alumnuscircle.auth.loginJson.response;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.main.MainAct;
import com.ac.alumnuscircle.toolbox.json.MapToJson;
import com.ac.alumnuscircle.toolbox.json.ParseComplexJson;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.ac.alumnuscircle.R;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText username_et;
    private EditText userpwd_et;
    private Button login_btn;
    private TextView register_tv;

    public static String UID;//登陆时获取的UID
    private static String httpPostUrl;//post的url地址
    private Handler mHandler;
    private static final int LOGINKEYISNULL=0;
    /**
     * 将最终结果存在finalResult里面
     * 2016年9月5日16:23:44
     * 曾博晖
     * 创建
     * */
    private static Map<String,String> finalResult;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_login);
        initView();
        HttpGet httpGet=new HttpGet();
        httpGet.doHttpGet();
    }
    /**
     * 改写UID的值
     * 每次Http请求时重置UID值
     * */
    public  void setUID(Response res) {
        String id=res.headers().get("Set-Cookie");
        Log.i("the id is",HttpGet.loginHeader);
        UID=id.split(";")[0]+";"+HttpGet.loginHeader;
        Log.i("THE UID IS",UID);
    }

    /**
     * 初始化界面
     * */
    private void initView() {
        username_et=(EditText)findViewById(R.id.auth_login_user_tv);
        userpwd_et=(EditText)findViewById(R.id.auth_login_pwd_tv);


        login_btn=(Button)findViewById(R.id.auth_login_login_btn);
        register_tv=(TextView)findViewById(R.id.auth_login_register_tv);
        login_btn.setOnClickListener(this);
        register_tv.setOnClickListener(this);

//        HttpGet.httpGetUrl="http://139.196.207.155:8000";
//        httpPostUrl="http://139.196.207.155:8000/login";
        HttpGet.httpGetUrl="http://192.168.2.5:8000";
//        HttpGet.httpGetUrl="http://192.168.191.6:8000";
        httpPostUrl=HttpGet.httpGetUrl+"/login";
        mHandler=new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==LOGINKEYISNULL){
                    Toast.makeText(Login.this,"网络错误，请检查网络连接",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.auth_login_login_btn:
                doLogin();
                break;
            case R.id.auth_login_register_tv:
                doRegister();
        }
    }
    /**
     * 进行登陆操作
     * 2016年9月3日13:50:11
     * 曾博晖
     * */
    private void doLogin() {
        String userName=username_et.getText().toString();
        String userPwd=userpwd_et.getText().toString();
        if(userName.equals("")||userPwd.equals("")) {
            Toast.makeText(this, "请输入账号和密码",
                    Toast.LENGTH_SHORT).show();
            userpwd_et.setText("");
            username_et.setText("");
        }
        new Thread(postTask).start();
    }
    /**
     * 跳转到注册界面
     * 进行注册操作
     * 2016年9月3日13:54:29
     * 曾博晖
     * 创建
     * */
    private void doRegister() {
        Toast.makeText(this,"即将跳转到注册界面",
                Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(ActivityName.register_AuthPhone);
        startActivity(intent);
    }

    Runnable postTask =new Runnable() {
        @Override
        public void run() {
            HttpPost();
        }
    };


    public void HttpPost(){
        Map<String, String> sendMap = new HashMap<>();
        sendMap.put("telephone",
                username_et.getText().toString()
        );
        sendMap.put("password",
                userpwd_et.getText().toString()
        );
        String json = MapToJson.toJson(sendMap);
        if(HttpGet.loginKey==null){
            Message message =new Message();
            message.what=LOGINKEYISNULL;
            mHandler.sendMessage(message);
            return;
        }
        RequestBody formBody = new FormBody.Builder()
                .add("_xsrf", HttpGet.loginKey)
                .add("info_json", json)
                .build();
        Request request = new Request.Builder()
                .addHeader("Cookie",HttpGet.loginHeader)
                .url(httpPostUrl)
                .post(formBody)
                .build();
        try{
            Response response =HttpGet.okHttpClient.newCall(request).execute();
            Log.d("Headers 是",response.headers().toString());
            Log.i("the key is",HttpGet.loginKey);
            if(response.isSuccessful()){
                setUID(response);
                final String receiveStr = response.body().string();
                //Log.i("HttpPost", receiveStr);
                getInfo(receiveStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getInfo(String res){
        Log.i("The getInfo Fuck is",res );
        finalResult=new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        ParseComplexJson.recursiveParseJson(result, res, null);

        for(Map.Entry<String, Object> entry : result.entrySet()){
//            Log.i("HttpPost", entry.getKey() + " : " + entry.getValue());
            if(entry.getKey().length()>14) {
                finalResult.put(entry.getKey().split("@")[2],
                        entry.getValue().toString());
            }
        }
        try {
            Log.i("THe final result is",finalResult.get("job"));
            Log.i("THe final result is",finalResult.get("name"));
            for(Map.Entry<String,String>entry:finalResult.entrySet()){
                Log.i("THe Info is", entry.getKey() + " : " + entry.getValue());
                getTrueInfo(entry.getKey(),entry.getValue());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        gotoMainAct();
    }
    /**
     * 将Key值和Value值放入里面
     * 获取当前用户的全部信息
     * 通过多层if&&else语句进行判断
     * 曾博晖
     * 2016年9月9日09:21:49
     * 添加注释
     * */
    public void getTrueInfo(String key,String value){
        if(key.equals("last_update_time")){
            MyInfo.myInfo.setLast_update_time(value.substring(1,value.length()-1));
        }else if(key.equals("major")){
            MyInfo.myInfo.setMajor(value);
        }else if(key.equals("uid")){
            MyInfo.myInfo.setUid(value);
        }else if(key.equals("admission_year")){
            MyInfo.myInfo.setAdmission_year(value);
        }else if(key.equals("instroduction")){
            MyInfo.myInfo.setInstroduction(value);
        }else if(key.equals("create_circle_list")){
            MyInfo.myInfo.setCreate_circle_list(value);
        }else if(key.equals("city")){
            MyInfo.myInfo.setCity(value);
        }else if(key.equals("icon_url")) {
                   if(value.substring(1,value.length()-1).equals("default")) {
                       MyInfo.myInfo.setIcon_url(
                               "http://joymepic.joyme.com/article/uploads/allimg/201508/1440041901750841.jpg");
                   }else {
                       MyInfo.myInfo.setIcon_url(value.substring(1, value.length() - 1));
                   }
                   Log.i("URL IS !@!@!!",MyInfo.myInfo.getIcon_url());
        }else if (key.equals("my_circle_list")){
            MyInfo.myInfo.setMy_circle_list(value);
        }else if(key.equals("state")){
            MyInfo.myInfo.setState(value);
        }else if(key.equals("protect_contact_list")){
            MyInfo.myInfo.setProtect_contact_list(value);
        }else if(key.equals("detail_id")){
            MyInfo.myInfo.setDetail_id(value);
        }else if(key.equals("company_publicity_level")){
            MyInfo.myInfo.setCompany_publicity_level(value);
        }else if(key.equals("public_contact_list")){
            MyInfo.myInfo.setPublic_contact_list(value);
        }else if (key.equals("admin_circle_list")){
            MyInfo.myInfo.setAdmin_circle_list(value);
        }else if(key.equals("tags")){
            MyInfo.myInfo.setTags(value);
        }else if(key.equals("company")){
            MyInfo.myInfo.setCompany(value);
        }else if(key.equals("job")){
            MyInfo.myInfo.setJob(value);
        }else if(key.equals("faculty")){
            MyInfo.myInfo.setFaculty(value.substring(1,value.length()-1));
        }else if(key.equals("name")){
            MyInfo.myInfo.setName(value);
        }else if (key.equals("gender")){
            MyInfo.myInfo.setGender(value);
        }else if(key.equals("job_list_level")){
            MyInfo.myInfo.setJob_list_level(value);
        }else if(key.equals("country")){
            MyInfo.myInfo.setCountry(value);
        }else if(key.equals("adlevel")){
            MyInfo.myInfo.setAdlevel(value);
        }else if(key.equals("job_list")){
            MyInfo.myInfo.setJob_list(value);
        }
    }
    /**
     * 验证成功之后，跳转到主界面
     * 曾博晖
     * 2016年9月5日16:27:27
     * 创建
     * */
    private void gotoMainAct(){
        Intent intent =new Intent(ActivityName.main_MainAct);
        startActivity(intent);
        finish();
    }

}
