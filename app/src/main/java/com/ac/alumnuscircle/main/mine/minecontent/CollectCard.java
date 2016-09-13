/**
 * @author 吴正凡
 * @date 16.08.29
 * @version 1
 * 功能：收藏名片的Fragment
 *
 * @author 曾博晖
 * @date 2016年9月11日
 * @verson 2
 * 功能：实现获取收藏名片的网络请求
 *
 */

package com.ac.alumnuscircle.main.mine.minecontent;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.Login;
import com.ac.alumnuscircle.auth.MyInfo;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.main.ctc.UserInfo;
import com.ac.alumnuscircle.main.ctc.ctc_rv.ContactAdapter;
import com.ac.alumnuscircle.main.ctc.ctc_rv.ContactFgtItem;
import com.ac.alumnuscircle.module.divdec.DividerLinearItemDecoration;
import com.ac.alumnuscircle.toolbox.json.MapToJson;
import com.ac.alumnuscircle.toolbox.json.ParseComplexJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CollectCard extends Fragment {

    private View view;
    private LayoutInflater layoutInflater;
    private ViewGroup container;

    private RecyclerView rvCollectCards;
    private List<ContactFgtItem> data;
    private ContactAdapter adapter;

    private static Map<String,String>finalData;
    private static String httpPostUrl;
    private List<UserInfo>userInfoList;
    private static final int HASGOTDATA=0x33;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Handler mHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.minecontent_collect_card, container, false);
        layoutInflater = inflater;
        this.container = container;
        initView(view);
        initData();
        new Thread(postTask).start();
        mHandler=new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==HASGOTDATA){
                    initRecycleView();
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        };
//        initRecycleView();
        return view;
    }
    /**
     * 开启post请求的线程
     * */
    Runnable postTask =new Runnable() {
        @Override
        public void run() {
            HttpPost();
        }
    };
    /**
     * 发送Http Post请求，获取到收藏名片列表数据
     * 2016年9月10日23:22:23
     * 曾博晖
     * 创建
     * */
    public void HttpPost(){

        data.clear();
        Map<String, String> sendMap = new HashMap<>();
        sendMap.put("count","30");
        sendMap.put("page","1");
        sendMap.put("uid", MyInfo.myInfo.getUid());
//        sendMap.put("max_id","");
        String json = MapToJson.toJson(sendMap);

        RequestBody formBody = new FormBody.Builder()
                .add("_xsrf", HttpGet.loginKey)
                .add("info_json", json)
                .build();

//        String UID=uid.split(";")[0];
        Request request = new Request.Builder()
                .addHeader("Cookie", Login.UID)
                .url(httpPostUrl)
                .post(formBody)
                .build();
        try{
            Response response =
                    HttpGet.okHttpClient.newCall(request).execute();
            Log.d("Headers 是",response.headers().toString());
//            ctc_response=response.headers().get("Set-Cookie");
//            Login login=new Login();
//            login.setUID(response);
            Log.i("the CC_CARD key is",HttpGet.loginKey);
            if(response.isSuccessful()) {
                data.clear();
                final String receiveStr = response.body().string();
                Log.i("the CC_CARD DATA ", receiveStr);
                AnalyzeResponse(receiveStr);

            }
            response.body().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对云端返回的收藏名片的数据进行分析，
     * 曾博晖
     * 2016年9月10日23:33:55
     * 创建
     * */
    private void AnalyzeResponse(String receiveStr) {
        //进行第一步解析
        Map<String, Object> result = new HashMap<>();
        ParseComplexJson.recursiveParseJson(result, receiveStr, null);
        //将解析后的一维数据加到finalComment里面，并且剔除掉无关数据
        for(Map.Entry<String, Object> entry : result.entrySet()){
            Log.i("CARD IS ", entry.getKey() + " : " + entry.getValue());
            if(entry.getKey().length()>22){
                finalData.put(entry.getKey().substring(22,
                        entry.getKey().length()),entry.getValue().toString());
            }
        }
        for(int i=0;i<finalData.size();i++){
            UserInfo user = new UserInfo();
            userInfoList.add(user);
            ContactFgtItem contact=new ContactFgtItem();
            data.add(contact);

        }
        for (Map.Entry<String ,String>entry:finalData.entrySet()) {
            Log.i("UserCARD", entry.getKey() + " : " + entry.getValue());
            for (int i = 0; i < finalData.size(); i++) {
                if (entry.getKey().substring(0, 1).equals("" + i)) {
                    getTrueInfo(i, entry.getKey(),
                            entry.getValue());
                }
            }
        }
        /**
         * 剔除掉数据为空的人脉对象
         * */
        for (int i=0;i<userInfoList.size();i++){
            if(userInfoList.get(i).getName()==null){
                userInfoList.remove(i);
                i--;
            }
        }
        /**
         * 将人脉对象的相应数据加到界面数据中
         * 2016年9月7日23:15:41
         * 曾博晖
         * 创建
         * */
        for (int i=0;i<userInfoList.size();i++){
            if(userInfoList.get(i).getName()!=null){
                Log.d("THe  city Info Is", userInfoList.get(i).getName()+" "+
                        userInfoList.get(i).getCity());
                Log.d("THe  job Info Is", userInfoList.get(i).getName()+" "+
                        userInfoList.get(i).getJob());
                Log.d("THe  URL Info Is", userInfoList.get(i).getName()+" "+
                        userInfoList.get(i).getIcon_url());
                data.get(i).setUserName(userInfoList.get(i).getName());
                data.get(i).setHeadImgUrl(userInfoList.get(i).getIcon_url());
                data.get(i).setUserFaculty(userInfoList.get(i).getFaculty());
                data.get(i).setUserGrade(userInfoList.get(i).getAdmission_year());
                data.get(i).setUserJob(userInfoList.get(i).getJob());
                data.get(i).setUserLocation(userInfoList.get(i).getCity());
            }else {
                Log.i("NULL NAME","STILL");
            }
        }
        /**
         * 剔除掉数据为空的人脉对象
         * */
        for (int i=0;i<data.size();i++){
            if(data.get(i).getUserName()==null){
                data.remove(i);
                i--;
            }
            if(data.get(i).getUserName().equals("社区管理员")){
                data.remove(i);
                i--;
            }
        }
        finalData.clear();
        Message message=new Message();
        message.what=HASGOTDATA;
        mHandler.sendMessage(message);
    }

    /**
     * 最后一步解析，将数据加到各个里面
     * @param  i 传入的数组下标
     * @param key 传入的类型 如city、job等
     * @param value 传入的value值，与type对应，如南京、学生等
     * @author 曾博晖
     * @date 2016年9月7日22:57:11
     * 曾博晖创建
     */
    private void getTrueInfo(final int i, String key, String value) {
          if(key.split("@")[1].equals("custom")){
              if(key.length()>9) {
                  if (key.split("@")[2].equals("ct")) {
                      userInfoList.get(i).setCity(value.substring(1, value.length() - 1));
                  } else if (key.split("@")[2].equals("ma")) {
                      userInfoList.get(i).setMajor(value.substring(1, value.length() - 1));
                  } else if (key.split("@")[2].equals("fa")) {
                      userInfoList.get(i).setFaculty(value.substring(1, value.length() - 1));
                  } else if (key.split("@")[2].equals("jo")) {
                      userInfoList.get(i).setJob(value.substring(1, value.length() - 1));
                  } else if (key.split("@")[2].equals("ay")) {
                      userInfoList.get(i).setAdmission_year(value.substring(1, value.length() - 1));
                  }else if(key.split("@")[2].equals("uid")){
                      userInfoList.get(i).setUser_id(value);
                  }
              }
          }
          if(key.split("@")[1].equals("name")){
              if(value.length()>12) {
                  userInfoList.get(i).setName(value.substring(12, value.length() - 1));
              }else {
                  userInfoList.get(i).setName(value.substring(1, value.length() - 1));
              }
          }else if(key.split("@")[1].equals("icon_url")){
              userInfoList.get(i).setIcon_url(value.substring(1,value.length()-1));
          }


    }


    private void initRecycleView() {
        rvCollectCards.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCollectCards.setAdapter(adapter = new ContactAdapter(getActivity(),data));
        Log.i("THE DATA LENTH IS",""+data.size());
        rvCollectCards.addItemDecoration(new DividerLinearItemDecoration(getActivity(),
                DividerLinearItemDecoration.VERTICAL_LIST));
        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(
                        ActivityName.ctc_ContactDetailAct);
                Bundle bundle=new Bundle();
                bundle.putString("uid",userInfoList.get(position).getUser_id());
                bundle.putString("company",userInfoList.get(position).getCompany());
                bundle.putString("headImgUrl",data.get(position).getHeadImgUrl());
                bundle.putString("name",data.get(position).getUserName());
                bundle.putString("location",data.get(position).getUserLocation());
                bundle.putString("department",data.get(position).getUserFaculty());
                bundle.putString("grade",data.get(position).getUserGrade());
//                bundle.putString("class",data.get(position).getUserClass());
                bundle.putString("job",data.get(position).getUserJob());
                intent.putExtras(bundle);
                startActivity(intent);
            }
            @Override
            public void onLongClick(int position) {

            }
        });
    }

    /**
     * 加载用户数据
     * */
    private void initData() {

//        data.clear();

    }

    private void initView(View view){
        rvCollectCards=(RecyclerView)view.findViewById(R.id.rv_collect_cards);
        finalData=new HashMap<>();
        httpPostUrl=HttpGet.httpGetUrl+"/followslist";
        data=new ArrayList<>();
        userInfoList=new ArrayList<>();
        swipeRefreshLayout=(SwipeRefreshLayout)
                view.findViewById(R.id.mine_minefgt_collectcard_swrfly);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(postTask).start();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
}
