/**
 * @author 吴正凡
 * @date 16.08.26
 * @version 1
 * 功能：这是主页中，“我的”分页的设置界面。
 */
package com.ac.alumnuscircle.main.mine;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.MyInfo;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.supercamera.onetoonecamera.PaPaCrop;
import com.ac.alumnuscircle.toolbox.json.JsonToMap;
import com.ac.alumnuscircle.toolbox.json.MapToJson;
import com.facebook.drawee.view.SimpleDraweeView;

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

public class SettingAct extends Activity {

    private LinearLayout mine_settingact_chghdimg_llyt;
    private LinearLayout mine_settingact_back_llyt;
    private SimpleDraweeView mine_settingact_hdimg;
    private TextView mine_settingact_username_tv;
    private EditText mine_settingact_career_et;
    private EditText mine_settingact_location_et;
    private EditText mine_settingact_company_et;
    private EditText mine_settingact_phone_et;
    private EditText mine_settingact_introduction_et;
    private Button mine_settingact_save_btn;

    private String user_hdimg_url;
    private String user_name;
    private String user_career;
    private String user_location;
    private String user_company;
    private String user_phone;
    private String user_introduction;

    private String img_key;
    private String img_url;

    private String absoluteImgPath;
    private String base64ImgStr;

    public static final int REQUEST_CODE_FOR_CAMERA = 0x123;
    public static final int SUCCESS_GET_IMG_PATH = 0x124;
    public static final int SUCCESS_UPLOAD_IMG = 0x125;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS_GET_IMG_PATH:
                    /**
                     * 开始执行图片上传。
                     */
                    break;
                case SUCCESS_UPLOAD_IMG:
                    /**
                     * 成功上传图片,刷新img_key,img_url
                     */
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_settingact);
        init();
    }

    private void init() {
        initView();
        initData();
    }

    private void initView() {
        mine_settingact_chghdimg_llyt = (LinearLayout) findViewById(R.id.mine_settingact_chghdimg_llyt);
        mine_settingact_back_llyt = (LinearLayout) findViewById(R.id.mine_settingact_back_llyt);
        mine_settingact_hdimg = (SimpleDraweeView) findViewById(R.id.mine_settingact_hdimg);
        mine_settingact_username_tv = (TextView) findViewById(R.id.mine_settingact_username_tv);
        mine_settingact_career_et = (EditText) findViewById(R.id.mine_settingact_career_et);
        mine_settingact_location_et = (EditText) findViewById(R.id.mine_settingact_location_et);
        mine_settingact_company_et = (EditText) findViewById(R.id.mine_settingact_company_et);
        mine_settingact_phone_et = (EditText) findViewById(R.id.mine_settingact_phone_et);
        mine_settingact_introduction_et = (EditText) findViewById(R.id.mine_settingact_introduction_et);
        mine_settingact_save_btn = (Button) findViewById(R.id.mine_settingact_save_btn);

        mine_settingact_chghdimg_llyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityName.onetoonecamera_PaPaCamera);
                startActivityForResult(intent, REQUEST_CODE_FOR_CAMERA);
            }
        });

        mine_settingact_back_llyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 普通退出按钮。
                 */
                finish();
            }
        });

        mine_settingact_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 点击保存按钮，发送http请求，更新用户信息。
                 * 注意，当成功更新后，取得回掉，要记得刷新用户数据。
                 */
                user_career = mine_settingact_career_et.getText().toString();
                user_location = mine_settingact_location_et.getText().toString();
                user_company = mine_settingact_company_et.getText().toString();
                user_phone = mine_settingact_phone_et.getText().toString();
                user_introduction = mine_settingact_introduction_et.getText().toString();
//                Toast.makeText(getApplicationContext(), 。", Toast.LENGTH_LONG).show();

                /**
                 * 把上述数据和img_key一起传给服务器。
                 */
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (user_location == null || user_location.length() == 0) {
                            return;
                        }
                        String location[] = user_location.split(",");
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("icon_url", img_key);
                        map.put("job", user_career);
                        map.put("city", location[2]);
                        map.put("state", location[1]);
                        map.put("country", location[0]);
                        map.put("company", user_company);
                        map.put("public_contact_list", "{\"telephone\":\"" + user_phone + "\"}");
                        map.put("introduction", user_introduction);

                        String info_json = MapToJson.toJson(map);

                        RequestBody formBody = new FormBody.Builder()
                                .add("_xsrf", HttpGet.loginKey)
                                .add("info_json", info_json)
                                .build();
                        Request request = new Request.Builder()
                                .addHeader("Cookie", HttpGet.loginHeader)
                                .url(HttpGet.httpGetUrl + "/updateinfo")
                                .post(formBody)
                                .build();
                        try {
                            Response response = HttpGet.okHttpClient.newCall(request).execute();
                            if (response.isSuccessful()) {
                                response.body().close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private void initData() {
        /**
         * 取得默认数据。
         */
        if (MyInfo.myInfo.getIcon_url() != null) {
            user_hdimg_url = MyInfo.myInfo.getIcon_url();
        } else {
            user_hdimg_url = "http://img0.imgtn.bdimg.com/it/u=3691748163,484693479&fm=206&gp=0.jpg";
        }
        //user_hdimg_url = "http://images2.4hw.com.cn/20150616/0a5dffce58dc6efd69473d0248fdea5c.jpg";
        user_name = MyInfo.myInfo.getName();
        user_career = MyInfo.myInfo.getJob();
        user_location = MyInfo.myInfo.getCountry() + ", " + MyInfo.myInfo.getState()
                + ", " + MyInfo.myInfo.getCity();
        user_company = MyInfo.myInfo.getCompany();
        user_phone = "1008611";
        user_introduction = MyInfo.myInfo.getInstroduction();


        /**
         * 默认数据填充。
         */
        mine_settingact_hdimg.setImageURI(Uri.parse(user_hdimg_url));
        mine_settingact_username_tv.setText(user_name);
        mine_settingact_career_et.setText(user_career);
        mine_settingact_location_et.setText(user_location);
        mine_settingact_company_et.setText(user_company);
        mine_settingact_phone_et.setText(user_phone);
        mine_settingact_introduction_et.setText(user_introduction);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FOR_CAMERA && resultCode == PaPaCrop.PAPACROP_RESULT_CODE && data != null) {
            absoluteImgPath = data.getStringExtra(absoluteImgPath);
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
                                .url("http://192.168.2.2:8002/upload_normal_img")
                                .post(formBody)
                                .build();
                        try {
                            Response response = HttpGet.okHttpClient.newCall(request).execute();
                            if (response.isSuccessful()) {
                                String receiveStr = response.body().string();
                                Map<String, Object> result = JsonToMap.toMap(receiveStr);

                                Log.d("Response", receiveStr);
                                user_hdimg_url = result.get("img_url").toString().substring(1, result.get("img_url").toString().length() - 1);
                                img_url = user_hdimg_url;
                                img_key = result.get("img_key").toString().substring(1, result.get("img_url").toString().length() - 1);
                                handler.sendEmptyMessage(SUCCESS_UPLOAD_IMG);
                                response.body().close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            handler.sendEmptyMessage(SUCCESS_GET_IMG_PATH);
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
