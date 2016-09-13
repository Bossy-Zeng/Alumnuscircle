package com.ac.alumnuscircle.main.home.create_cc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.net.CookieUtils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 白洋 on 2016/9/5.
 */
public class CreateMainInfo extends Activity {
    private TextView previousTv;
    private TextView finshTv;
    private TextView circleName;
    private EditText circleReason;
    private EditText circleIntro;
    private TextView select;
    private static HashMap<String,String> circleType = new HashMap();
    private static OkHttpClient client = new OkHttpClient();
//    private TextView selectRes;
    private Spinner spinner;
//    private String selectRes="";
    private Handler handler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if(msg.what==0X100){
//            Toast.makeText(CreateMainInfo.this, "已提交申请，正在审核中", Toast.LENGTH_SHORT).show();

        }
        else{
//            Toast.makeText(CreateMainInfo.this, "申请失败", Toast.LENGTH_SHORT).show();
        }
       finish();
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createcc_createmaininfo);
        Init();
    }

    private void Init()
    {
        InitView();

    }
    private void InitView()
    {
        select = (TextView)findViewById(R.id.createcc_createinfo_selected);
        previousTv = (TextView)findViewById(R.id.createcc_createinfo_prev);
        finshTv = (TextView)findViewById(R.id.createcc_createinfo_finish);
        circleName = (TextView) findViewById(R.id.createcc_createinfo_circletv);
        circleReason = (EditText)findViewById(R.id.createcc_createinfo_reason);
        circleIntro = (EditText)findViewById(R.id.createcc_createinfo_introduce);
//        selectRes = (TextView)findViewById(R.id.createcc_createinfo_selectresult);
        spinner = (Spinner)findViewById(R.id.createcc_createinfo_selectcc);

       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String[] circles = getResources().getStringArray(R.array.circle);
               select.setText(circles[position]);
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

        //设置字体
        Typeface simsun = Typeface.createFromAsset(getAssets(),"fonts/simsun.ttf");

        previousTv.setTypeface(simsun);
        finshTv.setTypeface(simsun);
        circleName.setTypeface(simsun);
        circleReason.setTypeface(simsun);
        circleIntro.setTypeface(simsun);
        TextView tv0=(TextView)findViewById(R.id.createcc_createinfo_simsun0);
        tv0.setTypeface(simsun);
        TextView tv1=(TextView)findViewById(R.id.createcc_createinfo_simsun1);
        tv1.setTypeface(simsun);
        TextView tv2=(TextView)findViewById(R.id.createcc_createinfo_simsun2);
        tv2.setTypeface(simsun);
        TextView tv3=(TextView)findViewById(R.id.createcc_createinfo_simsun3);
        tv3.setTypeface(simsun);

        TextPaint bold = previousTv.getPaint();
        bold.setFakeBoldText(true);

        bold = finshTv.getPaint();
        bold.setFakeBoldText(true);

        bold = circleName.getPaint();
        bold.setFakeBoldText(true);

        bold = tv1.getPaint();
        bold.setFakeBoldText(true);

        bold = tv2.getPaint();
        bold.setFakeBoldText(true);

        bold = tv3.getPaint();
        bold.setFakeBoldText(true);

        InitData();

        previousTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent preIntent = new Intent(ActivityName.create_cc_CreateCoverAct);
                startActivity(preIntent);
                CreateMainInfo.this.finish();
            }
        });
        finshTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((!circleIntro.getText().toString().equals(""))&&
                        (!circleReason.getText().toString().equals(""))) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String circleTypeName = select.getText().toString();
                            Log.d("圈子名", getIntent().getStringExtra("CircleName"));
                            Log.d("url", getIntent().getStringExtra("ImageKey"));
                            RequestBody myCircleBody = new FormBody.Builder().add("_xsrf", CookieUtils._xsrfKey)
                                    .add("circle_name",getIntent().getStringExtra("CircleName"))
                                    .add("circle_icon_url",getIntent().getStringExtra("ImageKey"))
//                                    .add("creator_uid",CookieUtils.uid)
                                    .add("circle_type_id",circleType.get(circleTypeName))
                                    .add("circle_type_name",circleTypeName)
                                    .add("reason_message",circleReason.getText().toString())
                                    .add( "description",circleIntro.getText().toString())
                                    .add("creator_name","陈小熊")
                                    .build();

                            Request requestMyCircle  = new Request.Builder().url(HttpGet.httpGetUrl+"/createTopic").post(myCircleBody)
                                    .addHeader("Cookie", CookieUtils.cookie).build();

                            try {
                                Response response = client.newCall(requestMyCircle).execute();
                                if(response.isSuccessful()) {
                                    handler.sendEmptyMessage(0X100);
                                }
                                else{
                                    handler.sendEmptyMessage(0X101);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }
                else{
                    Toast.makeText(CreateMainInfo.this, "请完善您的信息", Toast.LENGTH_SHORT).show();
                }


            }
        });
//        select.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             int visible = pickerView.getVisibility();
//                if(visible == View.GONE)
//             {
//                 pickerView.setVisibility(View.VISIBLE);
//             }
//                else
//                {
//                    pickerView.setVisibility(View.GONE);
//                }
//            }
//        });

    }

    private void InitData()
    {
        String circleNameStr = getIntent().getStringExtra("CircleName");
        circleName.setText(circleNameStr);
         List<String> cirlces = new ArrayList<>();
        cirlces.add("创业圈");
        cirlces.add("软件圈");
        cirlces.add("实习圈");
        cirlces.add("法律圈");
        cirlces.add("经济圈");
        cirlces.add("电气圈");

        circleType.put("院系圈","57cd04e8ea77f7753a8f3c28");
        circleType.put("社团圈","57cbd6747019c95ec2d856eb");
        circleType.put("职业圈","57cd049d55c400f83aa1384c");
        circleType.put("地域圈","57cbd6747019c95ec2d856eb");
        circleType.put("兴趣圈","57bdcad0d0146385e6abb6be");
        circleType.put("创业圈","57cd04ba55c400f83aa1384d");


    }
}
