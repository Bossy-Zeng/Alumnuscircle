/**
 * Created by 曾博晖 on 2016/9/2.
 * @author 曾博晖
 * @date 2016年9月2日12:56:29
 * @verson 1
 * 功能：系统通知碎片
 */
package com.ac.alumnuscircle.main.msg.msgcontent;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
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
import com.ac.alumnuscircle.auth.MyInfo;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.main.msg.msgcontent.notifyfgt_rv.NotifyAdapter;
import com.ac.alumnuscircle.main.msg.msgcontent.notifyfgt_rv.NotifyItem;
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


public class NotifyFgt extends Fragment {
    private RecyclerView notify_rv;
    private List<NotifyItem> data;
    private NotifyAdapter notifyAdapter;
    private SwipeRefreshLayout notifyswrly;
    private View view;
    private String httpPostUrl;
    private Map<String,String>finalNotify;
    private Handler mHandler;
    private static final int HASGOTDATA=0x33;

    Map<Integer,String >getApply=new HashMap<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.msgcontent_notifyfgt,container,false);
        initView();
        initData();
        new Thread(postTask).start();
        mHandler=new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case HASGOTDATA:
                        initRecyclerView();
                        notifyswrly.setRefreshing(false);
                        break;
                    default:
                        break;
                }
            }
        };

        return view;
    }
    /**
     * 初始化控件
     * 2016-09-07 14:12:46
     * 曾博晖
     * 创建
     * */
    private void initView() {
        notify_rv=(RecyclerView)view.findViewById(R.id.msgcontent_notifyfgt_rv);
        data=new ArrayList<>();
        httpPostUrl=HttpGet.httpGetUrl+"/getmessage";
        finalNotify=new HashMap<>();
        notifyswrly=(SwipeRefreshLayout)view.findViewById(R.id.msgcontent_notifyfgt_swrely);
        notifyswrly.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        notifyswrly.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
     * 2016年9月10日21:17:44
     * 曾博晖
     * 创建
     * */
    public void HttpPost(){

//        Map<String, String> sendMap = new HashMap<>();
//        sendMap.put("count","30");
//        sendMap.put("type","received");
//        sendMap.put("page","1");
//        String json = MapToJson.toJson(sendMap);

        RequestBody formBody = new FormBody.Builder()
                .add("_xsrf", HttpGet.loginKey)
//                .add("info_json", json)
                .add("my_circle_list", MyInfo.myInfo.getMy_circle_list())
                .build();
        Log.i("THE LIST IShhhhhhhhhhhj",MyInfo.myInfo.getMy_circle_list());

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
            Log.i("the CTC key is",HttpGet.loginKey);
            if(response.isSuccessful()) {
                data.clear();
                final String receiveStr = response.body().string();
                Log.i("TEST NOTIFY", receiveStr);
                AnalyzeResponse(receiveStr);
                response.body().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分析返回的数据，对服务器返回的通知进行处理
     * 2016年9月10日21:59:54
     * 曾博晖
     * 创建
     * */
    private void AnalyzeResponse(String receiveStr) {
        //进行第一步解析
        Map<String, Object> result = new HashMap<>();
        ParseComplexJson.recursiveParseJson(result, receiveStr, null);
        //将解析后的一维数据加到finalComment里面，并且剔除掉无关数据
        for(Map.Entry<String, Object> entry : result.entrySet()){
            Log.i("NOTIFY", entry.getKey() + " : " + entry.getValue());
            if(entry.getKey().length()>14){
                finalNotify.put(entry.getKey().substring(14, entry.getKey().length()),
                        entry.getValue().toString());
            }
        }
        for(int i=0;i<finalNotify.size();i++){
            NotifyItem notifyItem=new NotifyItem();
            data.add(notifyItem);
        }
        for (Map.Entry<String, String> entry : finalNotify.entrySet()){
            Log.i("NOTIFY DATA IS", entry.getKey() + " : " + entry.getValue());
            for (int i=0;i<finalNotify.size();i++){
                if (entry.getKey().split("@")[0].equals("" + i)){
                    getTrueInfo(i,entry.getKey(),entry.getValue());
                }
            }
        }

        for (int i=0;i<data.size();i++){
            if(data.get(i).getNotify_Name()==null){
                data.remove(i);
                i--;
            }
            if(data.get(i).getNotify_type().equals("4")){
                data.get(i).setNotify_content(getApply.get(i)+"申请加入圈子");
            }
        }
        finalNotify.clear();
        Message message=new Message();
        message.what=HASGOTDATA;
        mHandler.sendMessage(message);
    }

    /**
     * 获取真正的通知信息
     * 2016年9月10日23:57:33
     *  @param  i 传入的数组下标
     * @param key 传入的键值
     * @param value 传入的value值
     * */
    private void getTrueInfo(final int i,String key,String value){

        String apply_name= "";
         if(key.split("@")[1].equals("message")){
             if(key.split("@")[2].equals("circle_name")){
                 data.get(i).setNotify_Name(value.substring(1,value.length()-1));
             }else if(key.split("@")[2].equals("circle_url")){
//                 data.get(i).setNotify_headImgUrl(value.substring(1,value.length()-1));
                    data.get(i).setNotify_headImgUrl(
                            "http://pic.58pic.com/58pic/15/65/94/75558PICQEi_1024.jpg");
             }else if(key.split("@")[2].equals("apply_name")){
                 apply_name=value.substring(1,value.length()-1);
                 getApply.put(i,apply_name);
             }else if(key.split("@")[2].equals("apply_uid")){
                 data.get(i).setNotify_applyid(value.substring(1,value.length()-1));
             }
         }
        if(key.split("@")[1].equals("update_time")){
             data.get(i).setNotify_time(value.substring(1,value.length()-1));
        }
        if(key.split("@")[1].equals("type")){
            data.get(i).setNotify_type(value);
//            data.get(i).setNotify_headImgUrl(
//                    "http://pic.58pic.com/58pic/15/65/94/75558PICQEi_1024.jpg");
            if(value.equals("0")){
                data.get(i).setNotify_content("圈子创建成功！");
            }else if(value.equals("1")){
                data.get(i).setNotify_content("圈子创建失败！");

            }else if(value.equals("2")){
                //所有圈子的成员收到的新的成员加入了
                data.get(i).setNotify_content("新的成员加入！");
                data.get(i).setNotify_headImgUrl(
                        "http://pic77.nipic.com/file/20150909/7746342_203147959140_2.jpg");
            }else if(value.equals("3")){
                //发给申请者用户申请加入圈子的结果
                data.get(i).setNotify_content("申请加入圈子成功！");
                data.get(i).setNotify_headImgUrl(
                        "http://imgsrc.baidu.com/forum/pic/item/4d3f114c510fd9f91b6bb28c252dd42a2934a41b.jpg");
            }else if(value.equals("4")){
                data.get(i).setNotify_headImgUrl(
                        "http://pic2.ooopic.com/11/75/82/72b1OOOPIC1e.jpg");
                //【发给管理员】　某某用户申请加入圈子
                   if(!apply_name.equals("")){
                       data.get(i).setNotify_content(apply_name+"申请加入圈子");
                   }else {
                       data.get(i).setNotify_content("白洋"+"申请加入圈子");
                   }
//                data.get(i).setNotify_content(getApply.get(i)+"申请加入圈子");
            }
        }

    }

    /**
     * 初始化数据
     * 2016年9月7日15:33:01
     * 曾博晖
     * 创建
     * */
    private void initData(){
        NotifyItem notifyItem2=new NotifyItem(
                "http://img1.imgtn.bdimg.com/it/u=293719508,1004767985&fm=21&gp=0.jpg",
                "圈子通知","白洋大神申请加入GIT圈子",
                "GIT圈子","2016年9月7日15:48:22");
        NotifyItem notifyItem3=new NotifyItem(
                "http://img5.imgtn.bdimg.com/it/u=3113483255,3550152016&fm=21&gp=0.jpg",
                "圈子通知","互联网交流圈发布了新公告",
                "互联网交流圈","2016年9月7日15:50:09");
//        data.add(notifyItem1);
        data.add(notifyItem2);
        data.add(notifyItem3);
    }
    /**
     * 为RecyclerView添加数据
     * */
    private void initRecyclerView(){
        notify_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        notify_rv.setAdapter(notifyAdapter = new NotifyAdapter(getActivity(),data));
        notify_rv.addItemDecoration(new DividerLinearItemDecoration(getActivity(),
                DividerLinearItemDecoration.VERTICAL_LIST));
        notifyAdapter.setOnItemClickListener(new NotifyAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(),"你点击了"+
                        data.get(position).getNotify_Name()+"的通知",
                        Toast.LENGTH_SHORT).show();
                HandleNotify(data.get(position).getNotify_type());
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }
    /**
     * 根据传入的通知类型对通知界面的条目点击事件进行相应的处理
     * 2016年10月6日16:14:39
     * 曾博晖 创建
     * @param notify_type 传入的通知类型
     * */
    private void HandleNotify(String notify_type) {
         if(notify_type.equals("0")){
             //进入圈子创建成功的界面
         }else if(notify_type.equals("1")){
             //进入圈子创建失败的界面
         }else if(notify_type.equals("2")){
             //显示某个圈子内有新的成员加入
         }else if(notify_type.equals("3")){
             //告知用户创建某一个圈子是否成功
         }else if(notify_type.equals("4")){
             //进入审核某个用户申请加入圈子的界面
             Intent intent=new Intent(ActivityName.msgcontent_CheckReqAct);
             startActivity(intent);
         }
    }

}
