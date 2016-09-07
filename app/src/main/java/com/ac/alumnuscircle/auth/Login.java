/**
 * @author 曾博晖
 * @date 2016年9月5日10:10:28
 * @version 2
 * 功能：实现用户登录。
 */

package com.ac.alumnuscircle.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    private static String httpGetUrl;//get请求的url地址
    private static String httpPostUrl;//post的url地址

    //创建okHttpClient对象
    private static OkHttpClient mOkHttpClient;
    private Gson gson;
    /**
     * 将最终结果存在finalResult里面
     * 2016年9月5日16:23:44
     * 曾博晖
     * 创建
     * */
    private static Map<String,String> finalResult;
    /**
     * 每次进行登陆操作时，从官方获取并解析到的key
     * 作为每次post请求的key
     * 以实现登陆的安全
     */
    private static String loginKey;
    /**
     * Get请求之后获得的header
     * */
    private String loginHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_login);
        initView();
        doHttpGet();
    }
    private void initView() {
        username_et=(EditText)findViewById(R.id.auth_login_user_tv);
        userpwd_et=(EditText)findViewById(R.id.auth_login_pwd_tv);

        username_et.setText("15896153684");
        userpwd_et.setText("cxh1234567");

        login_btn=(Button)findViewById(R.id.auth_login_login_btn);
        register_tv=(TextView)findViewById(R.id.auth_login_register_tv);
        login_btn.setOnClickListener(this);
        register_tv.setOnClickListener(this);

        httpGetUrl="http://139.196.207.155:8000";
        httpPostUrl="http://139.196.207.155:8000/login";
        mOkHttpClient=new
                OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        gson=new Gson();
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
     * 进行Http请求的测试
     * 2016年9月4日09:29:08
     * 曾博晖
     * 创建
     * */
    private void doHttpGet() {
        //创建一个Request
        Request request = new Request.Builder()
                .url(httpGetUrl)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                loginHeader=response.headers().get("Set-Cookie").
                        substring(0,response.headers().get("Set-Cookie").length()-8);
                final String res=response.body().string();
                Log.d("head 是",loginHeader);
                Log.d("cookie 是",response.headers().get("Set-Cookie"));
                Log.d("Headers 是",response.headers().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Tag ",res);
                        getKey(res);
                    }
                });
            }
        });
    }

    /**
     * 从Http
     * */
    private void getKey(String res){
        RequestData reqData= gson.fromJson(res,RequestData.class);
        String xsrf=reqData.getData().get_xsrf();
        loginKey=xsrf;
        Log.d("The Data is ",loginKey);
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
       // Intent intent=new Intent(LoginAct.this,RgstAddPicAct.class);
       // startActivity(intent);
    }
    Runnable postTask =new Runnable() {
        @Override
        public void run() {
            test();
        }
    };



    public void test(){
        OkHttpClient okHttpClient = new  OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        Map<String, String> sendMap = new HashMap<String, String>();
        sendMap.put("telephone",
                username_et.getText().toString()
        );
        sendMap.put("password",
                userpwd_et.getText().toString()
        );
        String json = MapToJson.toJson(sendMap);
        RequestBody formBody = new FormBody.Builder()
                .add("_xsrf", loginKey)
                .add("info_json", json)
                .build();

        Request request = new Request.Builder()
                .addHeader("Cookie",loginHeader)
                .url(httpPostUrl)
                .post(formBody)
                .build();
        try{
            Response response = okHttpClient.newCall(request).execute();
            Log.i("the key is",loginKey);
            if(response.isSuccessful()){
                final String receiveStr = response.body().string();
                //Log.i("test", receiveStr);
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
            Log.i("test", entry.getKey() + " : " + entry.getValue());
            finalResult.put(entry.getKey(),entry.getValue().toString());
        }
        try {
            Log.i("THe final result is",finalResult.get("Data@response@job"));
            Log.i("THe final result is",finalResult.get("Data@response@name"));
            for(Map.Entry<String,String>entry:finalResult.entrySet()){
                Log.i("THe Info is",
//                        entry.getKey() + " : " +
                                 entry.getValue());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        gotoMainAct();
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
    }

}
