/**
 * Created by 曾博晖 on 2016/9/10.
 *
 * @author 曾博晖
 * @date 2016年9月10日11:05:00
 * @verson 1
 * 功能：实现用户注册时的上传照片
 */
package com.ac.alumnuscircle.auth.register;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.Login;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.auth.loginJson.MapToJson;
import com.ac.alumnuscircle.supercamera.onetoonecamera.PaPaCrop;
import com.ac.alumnuscircle.toolbox.json.JsonToMap;
import com.ac.alumnuscircle.toolbox.json.ParseComplexJson;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 曾博晖 on 2016/9/10.
 * @author 曾博晖
 * @date 2016年9月10日11:05:00
 * @verson 1
 * 功能：实现用户注册时的上传照片，
 * 并且在这个界面实现对用户信息的注册
 */
public class AuthHeadImg extends Activity {
    public static final int REQUEST_CODE_FOR_CAMEA = 0x1234;
    private static final int SUCCESS_UPLOAD_IMG = 0x10001;

    private static String img_key;
    private SimpleDraweeView headimg;
    private FrameLayout pic_fly;
    private String img_url;
    private Gson gson;
    private String resCode;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS_UPLOAD_IMG:
                    /**
                     * 上传图片成功，从返回的数据中取得key和url。
                     */
                    if (headImgUrl != null) {
                        Log.i("URL IS", headImgUrl);
                        Log.e("KEY IS>>>", img_key);
                        headimg.setImageURI(Uri.parse(headImgUrl));
                    }
                    break;
                default:
                    break;
            }
        }
    };
    ;
    private static String base64ImgStr;
    private String headImgUrl;
    private Button ensure_btn;
    private LinearLayout back_lly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_rgstaddpic);
        initView();

        initData();
    }

    private void initData() {
        pic_fly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.ac.alumnuscircle.supercamera.onetoonecamera.PaPaCamera");
                startActivityForResult(intent, REQUEST_CODE_FOR_CAMEA);
            }
        });
        ensure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(postTask).start();
            }
        });
        back_lly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        ensure_btn = (Button) findViewById(R.id.auth_rgstaddpic_ensure_btn_btn);
        back_lly = (LinearLayout) findViewById(R.id.auth_register_tlb_back_llyt);
        back_lly.setVisibility(View.GONE);
        pic_fly = (FrameLayout) findViewById(R.id.auth_rgstaddpic_pic_fly);
        headimg = (SimpleDraweeView) findViewById(R.id.auth_rgstaddpic_headImg);
        HttpGet.RegisterUrl = HttpGet.httpGetUrl + "/register";
        back_lly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        img_url="";
//        base64ImgStr="";
    }

    /**
     * 开启新线程来进行post操作
     *
     * @author 曾博晖
     * @verson 1
     * @date 2016年9月6日09:48:06
     */
    public Runnable postTask = new Runnable() {
        @Override
        public void run() {
            Log.i("THIS BEGIN 1", HttpGet.RegisterUrl);
            PostReq();
        }
    };

    /**
     * @date 2016年9月6日09:49:22
     * @verson 1
     * @author 曾博晖
     */
    public void PostReq() {
        String infoJson = getInfoJson();
        Log.e("THE INFO JSON IS ", infoJson);
        RequestBody formBody = new FormBody.Builder()
                .add("_xsrf", HttpGet.loginKey)
                .add("info_json", infoJson)
                .build();

        Request request = new Request.Builder()
                .addHeader("Cookie", HttpGet.loginHeader)
                .url(HttpGet.RegisterUrl)
                .post(formBody)
                .build();
        try {
            Response response = HttpGet.okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                final String receiveStr = response.body().string();
                Log.i("PostReq", receiveStr);
                validateRes(receiveStr);
                response.body().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分析返回的数据
     * 并且从中判断是否注册成功~
     * 2016年9月13日09:09:20
     * @param receiveStr post请求返回的数据
     * 曾博晖 添加
     */
    private void validateRes(String receiveStr) {
        Map<String, Object> result = new HashMap<>();
        ParseComplexJson.recursiveParseJson(result, receiveStr, null);
        for (Map.Entry<String, Object> entry : result.entrySet()) {
            Log.i("HttpPost", entry.getKey() + " : " + entry.getValue());
            if (entry.getKey().equals("code")) {
                resCode = entry.getValue().toString();
            }
        }
        if (resCode.equals("2700")) {
            Log.i("SUCCESS!", resCode);
            Intent intent = new Intent(AuthHeadImg.this, Login.class);
            AuthHeadImg.this.startActivity(intent);
            finish();
        }
    }

    public String getInfoJson() {
        /**
         * "city": 城市 字符串
         "state": 省份 字符串
         “country”：国家 字符串

         "faculty":院 字符串
         "name": "陈雄辉", 姓名，2~20位数，可以是中文
         "major": 专业，字符串

         "company": "the SEU",公司名字，可以中文英文，空格，2~25
         "admission_year": 2014, 入学年份，4位整形
         "telephone": 15105861442,手机号

         "job": "student",工作，可中文，2~20位的中文或者英文，可以空格
         "gender": 1,性别 0 女，1 男
         "password": "XXXXX" 密码，客户端需要进行md5加密
         * */
        Map<String, String> map = new HashMap<>();
        map.put("telephone", RegisterUser.telephone);
        map.put("password", RegisterUser.password);
        map.put("name", RegisterUser.name);
        map.put("gender", RegisterUser.gender);
        map.put("city", RegisterUser.city);
        if (RegisterUser.state == null) {
            RegisterUser.state = "";
        }
        map.put("state", RegisterUser.state);
        Log.e("STATE IS ", "" + map.get("state"));
        map.put("country", RegisterUser.country);
        map.put("admission_year", RegisterUser.admission_year);
        map.put("job", RegisterUser.job);
        map.put("company", RegisterUser.company);
        map.put("faculty", RegisterUser.faculty);
        map.put("major", RegisterUser.major);
        map.put("icon_url", img_key);
//                "http://b.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=45f10be75edf8db1bc7b74603c13f162/023b5bb5c9ea15ce2f42ea76b6003af33a87b224.jpg");
        String testJson = MapToJson.toJson(map);

        return testJson;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FOR_CAMEA && resultCode == PaPaCrop.PAPACROP_RESULT_CODE && data != null) {
            String absoluteImgPath = data.getStringExtra("absoluteImgPath");
            base64ImgStr = readFileTobase64ImgStr(absoluteImgPath);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int random_num = (int) (1 + Math.random() * (1008611 - 1 + 1));
                    if (base64ImgStr != null && base64ImgStr.length() != 0) {
                        RequestBody formBody = new FormBody.Builder()
                                .add("_xsrf", HttpGet.loginKey)
                                .add("random_num", String.valueOf(random_num))
                                .add("base64ImgStr", base64ImgStr)
                                .build();
                        Request request = new Request.Builder()
                                .addHeader("Cookie", HttpGet.loginHeader)
                                .url(HttpGet.httpGetUrl + "/upload_normal_img")
                                .post(formBody)
                                .build();
                        try {
                            Response response = HttpGet.okHttpClient.newCall(request).execute();
                            if (response.isSuccessful()) {
                                String receiveStr = response.body().string();
                                Map<String, Object> result = JsonToMap.toMap(receiveStr);

                                Log.d("Response", receiveStr);
                                headImgUrl = result.get("img_url").toString()
                                        .substring(1, result.get("img_url").toString().length() - 1);
                                img_key = result.get("img_key").toString()
                                        .substring(1, result.get("img_key").toString().length() - 1);
                                handler.sendEmptyMessage(SUCCESS_UPLOAD_IMG);
                                response.body().close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    public String readFileTobase64ImgStr(String fileName) {
        File file = new File(fileName);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int length = 0;
        try {
            length = fis.available();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[length];
        try {
            fis.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String base64ImgStr = Base64.encodeToString(buffer, Base64.DEFAULT);
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64ImgStr;
    }

}
