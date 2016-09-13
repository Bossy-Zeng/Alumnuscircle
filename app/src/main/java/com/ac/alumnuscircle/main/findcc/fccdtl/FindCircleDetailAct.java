package com.ac.alumnuscircle.main.findcc.fccdtl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.beans.FindCircleDetail;
import com.ac.alumnuscircle.beans.MyCircle;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.main.findcc.fccdtl.fan.FansDialog;
import com.ac.alumnuscircle.main.findcc.fccdtl.fccdtl_rv.FindCircleDetailItem;
import com.ac.alumnuscircle.net.CookieUtils;

import com.ac.alumnuscircle.notice.adapter.NoticeAdapter;
import com.google.gson.Gson;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 白洋 on 2016/8/29.
 */
public class FindCircleDetailAct extends Activity implements FindCircleDetailItem.OnItemClickListener{
    private RecyclerView recyclerView;

    private List<String> imagesUrl;
    private List<String>titles;
    private List<String> des;//圈子描述
    private List<FindCircleDetail.DataBean.ResponseBean.ResultsBean.CustomBean>  creator;//创建者
    private List<String> createTime;
    private List<String> id;
    private TextView titleTv;
    private FindCircleDetailItem findCircleDetailItem;
    private LinearLayout backllyt;
    private static String circle_id;
    private static  OkHttpClient client = new OkHttpClient();
    private static Gson gson = new Gson();
    private static final int RESPONSE_FAIL = 0X100;
    private static final int RESPONSE_SUCCESS = 0X101;
    private static int page = 1;
    private Handler requestHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

                switch (msg.what){
                    case RESPONSE_SUCCESS:
                        findCircleDetailItem = new FindCircleDetailItem(titles,imagesUrl);
                        recyclerView.setAdapter(findCircleDetailItem);
                        findCircleDetailItem.setOnItemClickListener(FindCircleDetailAct.this);
                        break;
                    case RESPONSE_FAIL:
                        Toast.makeText(FindCircleDetailAct.this,"获取数据失败",Toast.LENGTH_SHORT).show();
                        Log.e("Cookie", CookieUtils.cookie);
                        break;
                    default:
                        break;
                }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fccdtl_findcircledtailact);
      Init();

    }
 private void Init()
 {
     InitView();
     InitData();



 }
    private void InitView()
    {
        recyclerView = (RecyclerView) findViewById(R.id.fccdtl_findcircledetailact_rv);
        //网格布局
        recyclerView.setLayoutManager(new GridLayoutManager(FindCircleDetailAct.this,2));
        titleTv = (TextView)findViewById(R.id.fccdtl_findcircledetailact_title);
       backllyt = (LinearLayout)findViewById(R.id.fccdtl_findcircledetailact_back);
        backllyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void InitData()
    {
        //设置标题
        String title = getIntent().getStringExtra("Title");
        titleTv.setText(title);
        circle_id = getIntent().getStringExtra("Id");
        imagesUrl = new ArrayList<>();
        titles = new ArrayList<>();
        des = new ArrayList<>();
        creator = new ArrayList<>();
        createTime = new ArrayList<>();
        id = new ArrayList<>();

        RequestCircle();


    }


    /**
     * 向服务器请求圈子数据
     */
    private void RequestCircle()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody myCircleBody = new FormBody.Builder().add("_xsrf", CookieUtils._xsrfKey)
                        .add("info_json","{\"t_cat_id\":\""+circle_id+"\",\"count\":999,\"page\":"+page+"}")
                        .build();
                Log.e("结果：", "{\"t_cat_id\":\""+circle_id+"\",\"count\":999,\"page\":"+page+"}");
                Request requestMyCircle  = new Request.Builder().url(HttpGet.httpGetUrl+"/gettypetopic")
                        .post(myCircleBody)
                        .addHeader("Cookie", CookieUtils.cookie).build();
                try {
                    Response response = client.newCall(requestMyCircle).execute();
                    if(response.isSuccessful()) {

                        String result = response.body().string();
                        Log.d("result", result);
                        FindCircleDetail fcd = gson.fromJson(result,FindCircleDetail.class);
                        List<FindCircleDetail.DataBean.ResponseBean.ResultsBean> results = fcd.getData()
                                .getResponse().getResults();
                        for(FindCircleDetail.DataBean.ResponseBean.ResultsBean res : results)
                        {
                             imagesUrl.add(res.getIcon_url());
                            titles.add(res.getName());
                            des.add(res.getDescription());
                            creator.add(res.getCustom());
                            createTime.add(res.getCreate_time());
                            id.add(res.getId());
                        }
                        response.body().close();
                         requestHandler.sendEmptyMessage(RESPONSE_SUCCESS);

                    }
                    else{
                      requestHandler.sendEmptyMessage(RESPONSE_FAIL);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    Log.e("Error", "数据类型不匹配"+'\n');
                    e.printStackTrace();
                }

            }
        }).start();
    }
    @Override
    public void onItemClick(View view, int position) {
//        Intent noticeIntent = new Intent(ActivityName.notice_NoticeAct);
//        noticeIntent.putExtra("CircleDetailName",titles.get(position));
//        noticeIntent.putExtra("CircleDetailImgurl",imagesUrl.get(position));
        FansDialog  fansDialog = new FansDialog(FindCircleDetailAct.this,
                R.style.FnasDialog,id.get(position),titles.get(position),imagesUrl.get(position)
                ,createTime.get(position),creator.get(position),des.get(position));
           fansDialog.show();
    }
}
