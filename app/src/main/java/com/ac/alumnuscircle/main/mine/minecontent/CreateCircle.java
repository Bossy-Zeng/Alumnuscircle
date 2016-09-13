/**
 * @author 吴正凡
 * @date 16.08.29
 * @version 1
 * 功能：参与的圈子的Fragment
 */

package com.ac.alumnuscircle.main.mine.minecontent;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.MyInfo;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.beans.MyCircle;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.main.mine.minecontent.rvcircle.CircleAdapter;
import com.ac.alumnuscircle.main.mine.minecontent.rvcircle.CircleItem;
import com.ac.alumnuscircle.module.divdec.DividerLinearItemDecoration;
import com.ac.alumnuscircle.net.CookieUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateCircle extends Fragment {

    private View view;
    private LayoutInflater layoutInflater;
    private ViewGroup container;
    private  static Gson gson = new Gson();
    private RecyclerView rvJoinCircle;
    private List<CircleItem> data;
    private List<String> imagesUrl;
    private List<String>titles;
    private List<String> id;//圈子id
    private List<Integer> members;
    private CircleAdapter adapter;
    private static int SUCCESS = 0X100;
    private static int FAIL = 0X101;
    private static okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == SUCCESS)
            {

            }
            else if(msg.what == FAIL){

            }
            initRecycleView();
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.minecontent_join_circle, container, false);
        layoutInflater = inflater;
        this.container = container;
        initView(view);
        initData();

        return view;
    }

    private void initRecycleView() {
        rvJoinCircle.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CircleAdapter(getActivity(), data);
        adapter.setOnItemClickListener(new CircleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent circleDetailIntent = new Intent(ActivityName.notice_NoticeAct);
                circleDetailIntent.putExtra("Id",id.get(position));
                circleDetailIntent.putExtra("ImageUrl",imagesUrl.get(position));
                circleDetailIntent.putExtra("Title",titles.get(position));
                circleDetailIntent.putExtra("Memeber",members.get(position).toString());

                startActivity(circleDetailIntent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        rvJoinCircle.setAdapter(adapter);

        rvJoinCircle.addItemDecoration(new DividerLinearItemDecoration(getActivity(),
                DividerLinearItemDecoration.VERTICAL_LIST));
    }

    /**
     * 加载用户数据
     * */
    private void initData() {
//        data = new ArrayList<>();
//        CircleItem item1=new CircleItem(
//                "http://img1.imgtn.bdimg.com/it/u=2385199661,1509060230&fm=21&gp=0.jpg",
//                "艺术圈"
//        );
//        CircleItem item2=new CircleItem(
//                "http://img2.imgtn.bdimg.com/it/u=3413454958,4293050372&fm=11&gp=0.jpg",
//                "软件圈"
//        );
//        CircleItem item3=new CircleItem(
//                "http://img2.imgtn.bdimg.com/it/u=3413454958,4293050372&fm=11&gp=0.jpg",
//                "软件圈"
//        );
//
//        data.add(item1);
//        data.add(item2);
//        data.add(item3);
        data = new ArrayList<>();
        imagesUrl = new ArrayList<>();
        titles = new ArrayList<>();
        id = new ArrayList<>();
        members = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody myCircleBody = new FormBody.Builder().add("_xsrf", HttpGet.loginKey)
                        .add("my_filter_circle", MyInfo.myInfo.getCreate_circle_list())
                        .build();

                Request requestMyCircle  = new Request.Builder().url(HttpGet.httpGetUrl+"/get_my_filter_circle").post(myCircleBody)
                        .addHeader("Cookie", CookieUtils.cookie).build();
                try {
                    Response response = client.newCall(requestMyCircle).execute();
                    if(response.isSuccessful()){
                        String result = response.body().string();
                        handler.sendEmptyMessage(SUCCESS);
                        Log.e("结果",result );
                        MyCircle createCircle = gson.fromJson(result,MyCircle.class);
                        List<MyCircle.DataBean.ResponseBean.ResultsBean> results = createCircle.getData()
                                .getResponse().getResults();
                        for(MyCircle.DataBean.ResponseBean.ResultsBean res:results)
                        {
                            titles.add(res.getName().equals("empty")?"":res.getName());
                            imagesUrl.add(res.getIcon_url());
                            id.add(res.getId());
                            members.add(res.getStats().getFans());
                            data.add(new CircleItem(res.getIcon_url(),res.getName()));
                        }
                        response.body().close();
                        handler.sendEmptyMessage(SUCCESS);
                    }
                    else {
                        handler.sendEmptyMessage(FAIL);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    handler.sendEmptyMessage(FAIL);
                }
            }
        }).start();
    }

    private void initView(View view){
        rvJoinCircle=(RecyclerView)view.findViewById(R.id.rv_join_circle);
    }

}
