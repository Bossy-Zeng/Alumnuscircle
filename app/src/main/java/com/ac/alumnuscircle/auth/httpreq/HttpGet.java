/**
 * Created by 曾博晖 on 2016/9/6.
 * @author 曾博晖
 * @date 2016年9月6日
 * @verson 1
 * 功能：由于HttpGet请求的相似性较强，
 * 直接将其单独提出
 *
 * 使用说明：每次HttpGet请求之前，
 * TODO
 * HttpGet.httpGetUrl初始化（之后可能就不变了，现在未完全统一）
 * 然后调用
 * HttpGet httpGet=new HttpGet();
          httpGet.doHttpGet();
 * 先获得loginKey值、loginHeader值
 * 之后在Post请求时，
 * 使用HttpGet获取到这两个值加在post请求里面就行了
 * 不需要重复操作了
 * 2016年9月6日13:19:11
 * 曾博晖添加注释
 */
package com.ac.alumnuscircle.auth.httpreq;


import android.util.Log;

import com.ac.alumnuscircle.net.CookieUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpGet {
    public static OkHttpClient okHttpClient=
            new OkHttpClient.Builder()
                    .readTimeout(10,TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();
    /**
     * Http第一次get请求的地址
     * 每次app进行请求前发出请求时用的地址
     * 用这个url进行获取UID值和其他的KEY值
     * 用于其他所有http请求的一些键值
     * 2016年9月13日08:56:38
     * 曾博晖
     * 创建
     * */
    public static final String httpGetUrl=
//             "http://223.3.79.249:8000";
            "http://139.196.207.155:8000";
//           "http://192.168.2.5:8000";
    public static String httpPostUrl;
    public static String RegisterUrl;
    /**
     * 每次进行登陆操作时，从官方获取并解析到的key
     * 作为每次post请求的key
     * 以实现登陆的安全
     */
    public static String loginKey;

    /**
     * Get请求之后获得的header
     * */
    public static String loginHeader;
    public Gson gson=new Gson();

    /**
     * 进行Http请求的测试
     * 2016年9月4日09:29:08
     * 曾博晖
     * 创建
     * */
    public  void doHttpGet() {
        //创建一个Request
        Request request = new Request.Builder()
                .url(httpGetUrl)
                .build();
        Log.i("HTTPGETURL IS>>>>>>>",httpGetUrl);
        //new call
        Call call = okHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                loginHeader=response.headers().get("Set-Cookie").
                        substring(0,response.headers().get("Set-Cookie").length()-8);
                final String res=response.body().string();
                Log.d("head 是",loginHeader);
                Log.d("cookie 是",response.headers().get("Set-Cookie"));
                Log.d("Headers 是",response.headers().toString());
//                CookieUtils.cookie=loginHeader;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Tag ",res);
                        getKey(res);
                    }
                }).start();
                response.body().close();
            }
        });
    }

    /**
     * 从Http获取登录的key
     * */
    private void getKey(String res){
        RequestData reqData= gson.fromJson(res,RequestData.class);
        String xsrf=reqData.getData().get_xsrf();
        loginKey=xsrf;
        Log.d("The KEY is ",loginKey);
        CookieUtils._xsrfKey=loginKey;
    }
}
