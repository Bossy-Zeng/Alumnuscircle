package com.ac.alumnuscircle.main.home.create_cc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.cstt.ServerConfig;
import com.ac.alumnuscircle.net.CookieUtils;
import com.ac.alumnuscircle.supercamera.normalcamera.PaPaCrop;
import com.ac.alumnuscircle.toolbox.json.JsonToMap;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author  白洋
 * 创建封面
 */
public class CreateCoverAct extends Activity {

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build();
    private ImageButton createcc_createcoverback_imgbtn;
    private ImageButton createcc_createcovercamera_btn;
    private SimpleDraweeView createcc_createcoverheadimg;
    private EditText createcc_createcovername_et;
    private TextView nextStep;
    private static final int REQUEST_CODE_FOR_CAMERA = 0x10086;
    private static final int SUCCESS_UPLOAD_IMG = 0x10001;
    private ArrayList<String> localImgPath;
    private ArrayList<String> remoteImgKey;
    private ArrayList<String> remoteImgUrl;
    private int counter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS_UPLOAD_IMG:
                    /**
                     * 上传图片成功，从返回的数据中取得key和url。
                     */
//                    uploadImgItem.addItem(remoteImgUrl.get(counter));
                    createcc_createcoverheadimg.setImageURI(Uri.parse(remoteImgUrl.get(counter-1)));
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createcc_createcover);
        init();
    }

    private void init(){
        initUI();
        initData();
    }

    private void initUI(){
        createcc_createcoverback_imgbtn = (ImageButton)findViewById(R.id.createcc_createcoverback_imgbtn);
        createcc_createcovercamera_btn = (ImageButton)findViewById(R.id.createcc_createcovercamera_btn);
        createcc_createcoverheadimg = (SimpleDraweeView)findViewById(R.id.createcc_createcoverheadimg);
        createcc_createcovername_et = (EditText)findViewById(R.id.createcc_createcovername_et);
        nextStep = (TextView)findViewById(R.id.createcc_createcoverback_nextstep);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textStr = createcc_createcovername_et.getText().toString().trim();
                if(textStr.equals("")) {
                    Toast.makeText(CreateCoverAct.this, "请输入圈子名称", Toast.LENGTH_SHORT).show();
                }
                else if(counter==0){
                    Toast.makeText(CreateCoverAct.this, "请上传图片", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent CreateccMainInfo = new Intent(ActivityName.create_cc_CreateMainInfoAct);
                    CreateccMainInfo.putExtra("CircleName",createcc_createcovername_et.getText().toString());
                    CreateccMainInfo.putExtra("ImageKey",remoteImgKey.get(counter-1));
                    startActivity(CreateccMainInfo);
                    CreateCoverAct.this.finish();
                }

            }
        });
        createcc_createcoverback_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createcc_createcovercamera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityName.normalcamera_PaPaCamera);
                startActivityForResult(intent, REQUEST_CODE_FOR_CAMERA);
            }
        });
    }

    private void initData(){

        counter = 0;
        localImgPath = new ArrayList<String>();
        remoteImgKey = new ArrayList<String>();
        remoteImgUrl = new ArrayList<String>();

    }

    public String readFileToBase64Str(String fileName) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FOR_CAMERA && resultCode == PaPaCrop.PAPACROP_RESULT_CODE) {
            final String absoluteImgPath = data.getStringExtra("absoluteImgPath");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int random_num = (int)(1+Math.random()*(1008611-1+1));
                    String base64ImgStr = readFileToBase64Str(absoluteImgPath);
                    if(base64ImgStr != null && base64ImgStr.length() != 0){
                        RequestBody formBody = new FormBody.Builder()
                                .add("_xsrf", HttpGet.loginKey)
                                .add("random_num",String.valueOf(random_num))
                                .add("base64ImgStr", base64ImgStr)
                                .build();
                        Request request = new Request.Builder()
                                .addHeader("Cookie", HttpGet.loginHeader)
                                .url(HttpGet.httpGetUrl+"/upload_normal_img")
                                .post(formBody)
                                .build();
                        Log.e("图片路径",absoluteImgPath);

                        try{
                            Response response = okHttpClient.newCall(request).execute();
                            if(response.isSuccessful()){
                                String receiveStr = response.body().string();
                                Map<String, Object> result = JsonToMap.toMap(receiveStr);
                                localImgPath.add(absoluteImgPath);
                                remoteImgKey.add(result.get("img_key").toString().substring(1, result.get("img_key").toString().length()-1));
                                remoteImgUrl.add(result.get("img_url").toString().substring(1, result.get("img_url").toString().length()-1));
                                counter++;
                                Log.d("image_url",result.get("img_url").toString().substring(1, result.get("img_url").toString().length()-1));
                                response.body().close();
                                handler.sendEmptyMessage(SUCCESS_UPLOAD_IMG);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }).start();
        }
    }
}
