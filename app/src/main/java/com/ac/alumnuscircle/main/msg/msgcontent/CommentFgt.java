/**
 * Created by 曾博晖 on 2016/9/2.
 * @author 曾博晖
 * @date 2016年9月2日12:58:10
 * @verson 1
 * 功能：实现评论界面
 */
package com.ac.alumnuscircle.main.msg.msgcontent;


import android.app.Fragment;
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
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.Login;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.main.mine.minecontent.rvcircle.CircleAdapter;
import com.ac.alumnuscircle.main.msg.msgcontent.commentfgt_rv.CommentAdapter;
import com.ac.alumnuscircle.main.msg.msgcontent.commentfgt_rv.CommentItem;
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

public class CommentFgt extends Fragment {
    private View view;
    private RecyclerView comment_rv;
    private CommentAdapter commentAdapter;
    private List<CommentItem>data;
    private String httpPostUrl;
    private static Map<String,String>finalComment;
    private Handler mHandler;
    private static final int HASGOTDATA=0x99;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.msgcontent_commentfgt,container,false);
        initView();
        initData();
        new Thread(postTask).start();
        mHandler=new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case HASGOTDATA:
                        initRecyclerView();
                        swipeRefreshLayout.setRefreshing(false);
                        break;
                    default:
                        break;
                }
            }
        };

        return view;
    }

    private void initView() {
        finalComment=new HashMap<>();
        comment_rv=(RecyclerView)view.findViewById(R.id.msgcontent_commentfgt_rv);
        data=new ArrayList<>();
        httpPostUrl=HttpGet.httpGetUrl+"/get_my_comment";
        swipeRefreshLayout=(SwipeRefreshLayout)
                view.findViewById(R.id.msgcontent_commentfgt_swrfly);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(postTask).start();
            }
        });
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
     * 发送Http Post请求，获取到评论列表数据
     * 2016年9月10日21:15:39
     * 曾博晖
     * 创建
     * */
    public void HttpPost(){

        data.clear();
        Map<String, String> sendMap = new HashMap<>();
        sendMap.put("count","30");
        sendMap.put("type","received");
        sendMap.put("page","1");
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
            Log.i("the CTC key is",HttpGet.loginKey);
            if(response.isSuccessful()) {
                data.clear();
                final String receiveStr = response.body().string();
//                Log.i("TEST", receiveStr);
                AnalyzeResponse(receiveStr);
                response.body().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 对从云端传来的数据进行解析
     * 并且分析评论数据
     * 2016年9月8日20:38:35
     * */
    public void AnalyzeResponse(String receiveStr){
        //进行第一步解析
        Map<String, Object> result = new HashMap<>();
        ParseComplexJson.recursiveParseJson(result, receiveStr, null);
        //将解析后的一维数据加到finalComment里面，并且剔除掉无关数据
        for(Map.Entry<String, Object> entry : result.entrySet()){
//            Log.i("test", entry.getKey() + " : " + entry.getValue());
            if(entry.getKey().length()>22){
                finalComment.put(entry.getKey().substring(22,
                        entry.getKey().length()),entry.getValue().toString());
            }
        }
        for(int i=0;i<finalComment.size();i++){
            CommentItem commentItem=new CommentItem();
            data.add(commentItem);
        }
        for (Map.Entry<String, String> entry : finalComment.entrySet()){
//            Log.i("COMMENT IS", entry.getKey() + " : " + entry.getValue());
            for (int i=0;i<finalComment.size();i++){
                if (entry.getKey().substring(0, 1).equals("" + i)){
                    getTrueInfo(i,entry.getKey(),entry.getValue());
                }
            }
        }
        //剔除掉多余的数据
        for (int i=0;i<data.size();i++){
            if(data.get(i).getComment_username()==null){
                data.remove(i);
                i--;
            }
        }
        finalComment.clear();
        Message message=new Message();
        message.what=HASGOTDATA;
        mHandler.sendMessage(message);

    }
    /**
     * 最后一步解析，将数据加到各个里面
     * @param  i 传入的数组下标
     * @param key 传入的键值
     * @param value 传入的value值，与type对应，如南京、学生等
     * @author 曾博晖
     * @date 2016年9月7日22:57:11
     * 曾博晖创建
     */
    public void getTrueInfo(final int i,String key,String value){
       if(key.split("@")[1].equals("creator")){
           if(key.split("@")[2].equals("name")){
               data.get(i).setComment_username(value.substring(12,value.length()-1));
               Log.i("TTTTT NAME IS",i+":"+value.substring(12,value.length()-1));
           }else if(key.split("@")[2].equals("icon_url")){
               data.get(i).setComment_headImgUrl(value.substring(1,value.length()-1));
               Log.i("TTTTT URL IS",i+":"+data.get(i).getComment_headImgUrl());
           }
       }else if(key.split("@")[1].equals("create_time")){
           data.get(i).setComment_time(value.substring(1,value.length()-1));
           Log.i("TTTTT TIME IS",i+":"+data.get(i).getComment_time());
       }else if(key.split("@")[1].equals("content")){
           data.get(i).setComment_content(value.substring(1,value.length()-1));
           Log.i("TTTTT CONTENT IS",i+":"+data.get(i).getComment_content());
       }

    }
    private void initData(){

    }

    private void initRecyclerView(){
        comment_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        comment_rv.setAdapter(commentAdapter = new CommentAdapter(getActivity(),data));

        comment_rv.addItemDecoration(new DividerLinearItemDecoration(getActivity(),
                DividerLinearItemDecoration.VERTICAL_LIST));
        commentAdapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(),"你点击了"+
                data.get(position).getComment_username()+"的评论"
                        ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }
}
