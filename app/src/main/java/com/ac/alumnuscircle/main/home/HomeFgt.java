/**
 * @author 白洋
 * @Date 2016/8/28.
 * @version 2
 * 主页的界面
 */

package com.ac.alumnuscircle.main.home;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.beans.MyCircle;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.main.home.home_rv.HomeItem;
import com.ac.alumnuscircle.net.CookieUtils;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HomeFgt extends Fragment implements HomeItem.OnItemClickListener {

    private static final int RESPONSE_FAIL = 0X100;
    private static final int UNKNOWN_TYPE = 0X111;
    private static final int SUCCESS = 0X101;
    private static OkHttpClient client = new OkHttpClient();
    private List<String> imagesUrl;
    private List<String>titles;
    private List<String> id;//圈子id
    private List<Integer> members;
    private RecyclerView recyclerView;
    private int lastPosition;//创建圈子的位置
    private View view;
    private  HomeItem homeItem;
    private static Gson gson;
    private static boolean lastHide = false;
    private Handler requestHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            if(msg.what==RESPONSE_FAIL)
            {
                Toast.makeText(getActivity(),CookieUtils.cookie,Toast.LENGTH_SHORT).show();
                Log.e("Cookie", CookieUtils.cookie);
            }
            else if(msg.what==UNKNOWN_TYPE){
                Toast.makeText(getActivity(),"您还未创建任何圈子",Toast.LENGTH_SHORT).show();
                recyclerView.setAdapter(homeItem);
                homeItem.setOnItemClickListener(HomeFgt.this);

            }
            else if(msg.what==SUCCESS){
                recyclerView.setAdapter(homeItem);
                homeItem.setOnItemClickListener(HomeFgt.this);
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_homefgt, container, false);
        Init();
        return view;
    }


    private void Init()
    {

        recyclerView=(RecyclerView)view.findViewById(R.id.home_homefgt_rv);
        //网格布局
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        gson = new Gson();
        InitList();

    }

    //初始化列表
    private void InitList()
    {
        imagesUrl = new ArrayList<>();
        titles = new ArrayList<>();
        id = new ArrayList<>();
        members = new ArrayList<>();

//       imagesUrl.add("http://img0.imgtn.bdimg.com/it/u=3766443758,1529519468&fm=21&gp=0.jpg");
//       imagesUrl.add("http://img2.imgtn.bdimg.com/it/u=2069918470,3277439936&fm=21&gp=0.jpg");
//        imagesUrl.add("http://img2.imgtn.bdimg.com/it/u=4075937517,1358300463&fm=21&gp=0.jpg");
//
//        titles.add("商业圈");
//        titles.add("经管圈");
//        titles.add("东南大学圈");
//        homeItem = new HomeItem(titles,imagesUrl);
//        recyclerView.setAdapter(homeItem);
//        homeItem.setOnItemClickListener(HomeFgt.this);


    new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody myCircleBody = new FormBody.Builder().add("_xsrf", HttpGet.loginKey)
                        .build();

                Request requestMyCircle  = new Request.Builder().url(HttpGet.httpGetUrl+"/get_my_circle").post(myCircleBody)
                        .addHeader("Cookie", CookieUtils.cookie).build();
                try {
                    Response response = client.newCall(requestMyCircle).execute();
                    if(response.isSuccessful()) {

                        String result = response.body().string();
                        Log.e("result", result);
                        MyCircle myCircle = gson.fromJson(result,MyCircle.class);
//                        List<MyCircle.DataBean.ResponseBean.ResultsBean>results =myCircle.getData()
//                                .getResponse().getResults();
//                        for(MyCircle.DataBean.ResponseBean.ResultsBean res:results)
//                        {
//                            titles.add(res.getName().equals("empty")?"":res.getName());
//                            imagesUrl.add(res.getIcon_url());
//                            id.add(res.getId());
//                            members.add(res.getStats().getFans());
//                        }

                        List<MyCircle.DataBean.ResponseBean.ResultsBean> results = myCircle.getData()
                                .getResponse().getResults();
                        for(MyCircle.DataBean.ResponseBean.ResultsBean res:results)
                        {
                            titles.add(res.getName().equals("empty")?"":res.getName());
                            imagesUrl.add(res.getIcon_url());
                            id.add(res.getId());
                            members.add(res.getStats().getFans());

                        }
                        lastPosition = imagesUrl.size();
                        homeItem = new HomeItem(titles,imagesUrl);
                        response.body().close();
                        requestHandler.sendEmptyMessage(SUCCESS);
                    }
                    else{
                        requestHandler.sendEmptyMessage(RESPONSE_FAIL);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    lastPosition = imagesUrl.size();
                    homeItem = new HomeItem(titles,imagesUrl);
                    requestHandler.sendEmptyMessage(0x111);
                }

            }
        }).start();


    }



    @Override
    public void onItemClick(View v, int position) {
        if(position!=lastPosition) {


            Intent circleDetailIntent = new Intent(ActivityName.notice_NoticeAct);
            circleDetailIntent.putExtra("Id",id.get(position));
            circleDetailIntent.putExtra("ImageUrl",imagesUrl.get(position));
            circleDetailIntent.putExtra("Title",titles.get(position));
            circleDetailIntent.putExtra("Memeber",members.get(position).toString());

            startActivity(circleDetailIntent);
        }
        else
        {
//            homeItem.addItem("新的项","http://img5.imgtn.bdimg.com/it/u=1846948884,880298315&fm=206&gp=0.jpg");
//            lastPosition++;
            startActivity(new Intent(ActivityName.create_cc_CreateCoverAct));
        }
    }
}
