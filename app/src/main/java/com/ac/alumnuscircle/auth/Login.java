/**
 * @author 曾博晖
 * @date 2016年9月5日10:10:28
 * @version 2
 * 功能：实现用户登录。
 * 并且在用户登录成功时将用户的数据
 * 加载到MyInfo下的myInfo里面
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
import com.ac.alumnuscircle.main.ctc.leavemsg.CustomUserProvider;
import com.ac.alumnuscircle.net.CookieUtils;
import com.ac.alumnuscircle.toolbox.json.MapToJson;
import com.ac.alumnuscircle.toolbox.json.ParseComplexJson;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.leancloud.chatkit.LCChatKitUser;
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
    /**
     * 第一次进入app时，发出的http请求未获得返回值
     * 也就是说有可能是网络错误·（url地址有错·或是没有进行网络连接）
     * 这时发现loginKey为空，
     * 为避免空指针报错，发post请求之前会对之进行检测
     * 若是为空，则向主线程发出信号
     * 告知用户
     */
    private static final int LOGIN_KEY_IS_NULL = 0x20;
    /**
     * 当服务器返回告知移动端输入的账号或是密码错误时，
     * 或是服务器没有返回数据时，
     * 发出的密码错误的信号
     * 告知主线程
     * 来对之进行处理
     */
    private static final int WRONG_PASS_WORD = 0x16;
    /**
     * 将最终结果存在Get_Result里面
     * 2016年9月5日16:23:44
     * 曾博晖
     * 创建
     */
    private static Map<String, String> Get_Result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_login);
        initView();
        HttpGet httpGet = new HttpGet();
        httpGet.doHttpGet();
    }

    /**
     * 改写UID的值
     * 每次Http请求时重置UID值
     */
    public void setUID(Response res) {
        String id = res.headers().get("Set-Cookie");
        Log.i("the id is", HttpGet.loginHeader);
        UID = id.split(";")[0] + ";" + HttpGet.loginHeader;
        Log.i("THE UID IS", UID);
        CookieUtils.cookie = UID;
    }

    /**
     * 初始化界面
     */
    private void initView() {
        username_et = (EditText) findViewById(R.id.auth_login_user_tv);
        userpwd_et = (EditText) findViewById(R.id.auth_login_pwd_tv);


        login_btn = (Button) findViewById(R.id.auth_login_login_btn);
        register_tv = (TextView) findViewById(R.id.auth_login_register_tv);
        login_btn.setOnClickListener(this);
        register_tv.setOnClickListener(this);


        httpPostUrl = HttpGet.httpGetUrl + "/login";
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == LOGIN_KEY_IS_NULL) {
                    Toast.makeText(Login.this, "网络错误，请检查网络连接",
                            Toast.LENGTH_SHORT).show();
                }
                if (msg.what == WRONG_PASS_WORD) {
                    Toast.makeText(Login.this, "密码错误，请重新输入",
                            Toast.LENGTH_SHORT).show();
                    userpwd_et.setText("");
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
     */
    private void doLogin() {
        String userName = username_et.getText().toString();
        String userPwd = userpwd_et.getText().toString();
        if (userName.equals("") || userPwd.equals("")) {
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
     */
    private void doRegister() {
        Toast.makeText(this, "即将跳转到注册界面",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ActivityName.register_AuthPhone);
        startActivity(intent);
        finish();
    }

    Runnable postTask = new Runnable() {
        @Override
        public void run() {
            HttpPost();
        }
    };


    public void HttpPost() {
        Map<String, String> sendMap = new HashMap<>();
        sendMap.put("telephone",
                username_et.getText().toString()
        );
        sendMap.put("password",
                userpwd_et.getText().toString()
        );
        String json = MapToJson.toJson(sendMap);
        if (HttpGet.loginKey == null) {
            Message message = new Message();
            message.what = LOGIN_KEY_IS_NULL;
            mHandler.sendMessage(message);
            return;
        }
        RequestBody formBody = new FormBody.Builder()
                .add("_xsrf", HttpGet.loginKey)
                .add("info_json", json)
                .build();
        Request request = new Request.Builder()
                .addHeader("Cookie", HttpGet.loginHeader)
                .url(httpPostUrl)
                .post(formBody)
                .build();
        try {
            Response response = HttpGet.okHttpClient.newCall(request).execute();
            Log.d("Headers 是", response.headers().toString());
            Log.i("the key is", HttpGet.loginKey);

            if (response.isSuccessful()) {
                try {
                    setUID(response);
                } catch (NullPointerException e) {
                    mHandler.sendEmptyMessage(WRONG_PASS_WORD);
                    return;
                }
                final String receiveStr = response.body().string();

                getInfo(receiveStr);
                response.body().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getInfo(String res) {
        Log.i("The getInfo Fuck is", res);
        Get_Result = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        ParseComplexJson.recursiveParseJson(result, res, null);

        for (Map.Entry<String, Object> entry : result.entrySet()) {
//            Log.i("HttpPost", entry.getKey() + " : " + entry.getValue());
            if (entry.getKey().length() > 14) {
                Get_Result.put(entry.getKey().split("@")[2],
                        entry.getValue().toString());
            }
        }
        try {
            Log.i("THe final result is", Get_Result.get("job"));
            Log.i("THe final result is", Get_Result.get("name"));
            for (Map.Entry<String, String> entry : Get_Result.entrySet()) {
                Log.i("THe Info is", entry.getKey() + " : " + entry.getValue());
                getTrueInfo(entry.getKey(), entry.getValue());
            }
            LCChatKitUser myUser = new LCChatKitUser(MyInfo.myInfo.getName(),
                    MyInfo.myInfo.getName(), MyInfo.myInfo.getIcon_url());
            CustomUserProvider.partUsers.add(myUser);
        } catch (Exception e) {
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
     */
    public void getTrueInfo(String key, String value) {
        if (key.equals("last_update_time")) {
            MyInfo.myInfo.setLast_update_time(value.substring(1, value.length() - 1));
        } else if (key.equals("major")) {
            MyInfo.myInfo.setMajor(value.substring(1, value.length() - 1));
        } else if (key.equals("uid")) {
            MyInfo.myInfo.setUid(value);
        } else if (key.equals("admission_year")) {
            MyInfo.myInfo.setAdmission_year(value);
        } else if (key.equals("instroduction")) {
            MyInfo.myInfo.setInstroduction(value.substring(1, value.length() - 1));
        } else if (key.equals("create_circle_list")) {
            MyInfo.myInfo.setCreate_circle_list(value);
        } else if (key.equals("city")) {
            MyInfo.myInfo.setCity(value.substring(1, value.length() - 1));
        } else if (key.equals("icon_url")) {
            if (value.substring(1, value.length() - 1).equals("default")) {
                MyInfo.myInfo.setIcon_url(
                        "http://joymepic.joyme.com/article/uploads/allimg/201508/1440041901750841.jpg");
            } else {
                MyInfo.myInfo.setIcon_url(value.substring(1, value.length() - 1));
            }
            Log.i("URL IS !@!@!!", MyInfo.myInfo.getIcon_url());
        } else if (key.equals("my_circle_list")) {
            MyInfo.myInfo.setMy_circle_list(value);
        } else if (key.equals("state")) {
            MyInfo.myInfo.setState(value.substring(1, value.length() - 1));
        } else if (key.equals("protect_contact_list")) {
            MyInfo.myInfo.setProtect_contact_list(value);
        } else if (key.equals("detail_id")) {
            MyInfo.myInfo.setDetail_id(value);
        } else if (key.equals("company_publicity_level")) {
            MyInfo.myInfo.setCompany_publicity_level(value);
        } else if (key.equals("public_contact_list")) {
            MyInfo.myInfo.setPublic_contact_list(value);
        } else if (key.equals("admin_circle_list")) {
            MyInfo.myInfo.setAdmin_circle_list(value);
        } else if (key.equals("tags")) {
            MyInfo.myInfo.setTags(value.substring(1, value.length() - 1));
        } else if (key.equals("company")) {
            MyInfo.myInfo.setCompany(value.substring(1, value.length() - 1));
        } else if (key.equals("job")) {
            MyInfo.myInfo.setJob(value.substring(1, value.length() - 1));
        } else if (key.equals("faculty")) {
            MyInfo.myInfo.setFaculty(value.substring(1, value.length() - 1));
        } else if (key.equals("name")) {
            MyInfo.myInfo.setName(value.substring(1, value.length() - 1));
        } else if (key.equals("gender")) {
            MyInfo.myInfo.setGender(value);
        } else if (key.equals("job_list_level")) {
            MyInfo.myInfo.setJob_list_level(value);
        } else if (key.equals("country")) {
            MyInfo.myInfo.setCountry(value.substring(1, value.length() - 1));
        } else if (key.equals("adlevel")) {
            MyInfo.myInfo.setAdlevel(value);
        } else if (key.equals("job_list")) {
            MyInfo.myInfo.setJob_list(value);
        }
    }

    /**
     * 验证成功之后，跳转到主界面
     * 曾博晖
     * 2016年9月5日16:27:27
     * 创建
     */
    private void gotoMainAct() {
        Intent intent = new Intent(ActivityName.main_MainAct);
        startActivity(intent);
        finish();
    }

}
