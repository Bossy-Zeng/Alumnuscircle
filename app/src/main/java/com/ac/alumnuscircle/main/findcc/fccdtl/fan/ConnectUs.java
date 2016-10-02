package com.ac.alumnuscircle.main.findcc.fccdtl.fan;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.Login;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.notice.adapter.IssueNoticeAdapter;
import com.ac.alumnuscircle.supercamera.normalcamera.PaPaCrop;
import com.ac.alumnuscircle.toolbox.json.JsonToMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author 白洋
 */
public class ConnectUs extends Activity implements IssueNoticeAdapter.OnItemClickListener {

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build();

    private static final int REQUEST_CODE_FOR_CAMERA = 0x10086;
    private static final int SUCCESS_UPLOAD_IMG = 0x10001;
    private static final int ISSUE_SUCCESS = 0X100;
    private static final int ISSUE_FAIL = 0X101;
    private ArrayList<String> localImgPath;
    private ArrayList<String> remoteImgKey;
    private ArrayList<String> remoteImgUrl;
    private int counter;
    private static OkHttpClient uploadHttp = new OkHttpClient();
    private RecyclerView recyclerView;
    private List<String> uploadImgUrl;
    private IssueNoticeAdapter uploadImgItem;//上传图片适配器
    private ImageView uploadImg;
    private EditText issue;

    private TextView finsh;
    private TextView exit;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS_UPLOAD_IMG:
                    /**
                     * 上传图片成功，从返回的数据中取得key和url。
                     */
                    uploadImgItem.addItem(remoteImgUrl.get(counter-1));
//                     uploadImgItem.addItem("");
                    break;
                case ISSUE_SUCCESS:
                    Toast.makeText(ConnectUs.this, "发布成功", Toast.LENGTH_SHORT).show();
                    finish();
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fan_connectuact);
        InitView();
        initData();

    }

    private void initData() {
        counter = 0;
        localImgPath = new ArrayList<String>();
        remoteImgKey = new ArrayList<String>();
        remoteImgUrl = new ArrayList<String>();
    }

    private void InitView() {
        uploadImg = (ImageView) findViewById(R.id.fanconnectus_addImg);
        recyclerView = (RecyclerView) findViewById(R.id.fanconnectusact_rv);
        finsh = (TextView)findViewById(R.id.fanconnectusact_finish);
        exit = (TextView)findViewById(R.id.fanconnectusact_exit);
        issue =(EditText)findViewById(R.id.fanconnectus_input);


        uploadImgUrl = new ArrayList<>();
        uploadImgItem = new IssueNoticeAdapter(uploadImgUrl);
        recyclerView.setLayoutManager(new GridLayoutManager(ConnectUs.this, 3));
        recyclerView.setAdapter(uploadImgItem);
        uploadImgItem.setOnItemClickListener(this);

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (uploadImgUrl.size() < 9) {
                    /**
                     * 调用normal.papacamera
                     * 带任意裁剪比例的普通相机。
                     */
                    Intent intent = new Intent(ActivityName.normalcamera_PaPaCamera);
                    startActivityForResult(intent, REQUEST_CODE_FOR_CAMERA);
//                    uploadImgItem.addItem("http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1305/03/c0/20491540_1367542208810.jpg");

                } else {
                    Toast.makeText(ConnectUs.this, "最多上传9张", Toast.LENGTH_SHORT).show();
                }
            }
        });

        finsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et =(EditText)findViewById(R.id.fanconnectus_input);
                if(et.getText().toString().equals("")) {

                    Toast.makeText(ConnectUs.this, "请发布公告", Toast.LENGTH_SHORT).show();
                }
                else if(remoteImgUrl.size()==0) {
                    Toast.makeText(ConnectUs.this,"请至少上传一张图片",Toast.LENGTH_SHORT).show();
                }
                else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String content = issue.getText().toString();
                            String topicid = getIntent().getStringExtra("Id");
                            String img_str = "";
                            if(remoteImgKey.size()>0)
                            {
                                for(String str:remoteImgKey)
                                    img_str+=(str+";");
                                img_str.substring(0,img_str.length()-1);
                            }

                            RequestBody formBody = new FormBody.Builder()
                                    .add("_xsrf", HttpGet.loginKey)
                                    .add("info_json",String.format("{\"content\":\"%s\",\"virtual_cid\":\"%s\",\"title\":\"沉雄辉bsb\",\"img_str\":\"%s\"}"
                                    ,content,topicid,img_str))
                                    .build();
                            Request request = new Request.Builder()
                                    .addHeader("Cookie", Login.UID)
                                    .url(HttpGet.httpGetUrl+"/myfeed/update")
                                    .post(formBody)
                                    .build();
                            try {
                                Response response = okHttpClient.newCall(request).execute();
                                if(response.isSuccessful()) {
                                    handler.sendEmptyMessage(ISSUE_SUCCESS);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }

            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConnectUs.this, "退出", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    @Override
    public void onItemClick(View v, final int position) {


    }

    @Override
    public void onLongItemClick(View v, final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ConnectUs.this);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                uploadImgItem.removeItem(position);
                remoteImgKey.remove(position);
                remoteImgUrl.remove(position);
                counter--;
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setTitle("确定取消该图片上传？");
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FOR_CAMERA && resultCode == PaPaCrop.PAPACROP_RESULT_CODE) {
            final String absoluteImgPath = data.getStringExtra("absoluteImgPath");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int random_num = (int)(1+Math.random()*(1008621-1+1));
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
                        try{
                            Response response = okHttpClient.newCall(request).execute();
                            if(response.isSuccessful()){
                                String receiveStr = response.body().string();
                                Log.i("test",receiveStr);
                                Map<String, Object> result = JsonToMap.toMap(receiveStr);
                                localImgPath.add(absoluteImgPath);
                                remoteImgKey.add(result.get("img_key").toString().substring(1, result.get("img_key").toString().length()-1));
                                remoteImgUrl.add(result.get("img_url").toString().substring(1, result.get("img_url").toString().length()-1));
                                counter++;
                                handler.sendEmptyMessage(SUCCESS_UPLOAD_IMG);
                                Log.d("Response", response.body().string());
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
}
