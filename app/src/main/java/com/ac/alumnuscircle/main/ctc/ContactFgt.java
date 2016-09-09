/**
 * @author 曾博晖
 * @date 16.08.22
 * @version 1
 * 功能：实现展示人脉的界面
 */
package com.ac.alumnuscircle.main.ctc;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.Login;
import com.ac.alumnuscircle.auth.MyInfo;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.main.ctc.ctc_rv.ContactAdapter;
import com.ac.alumnuscircle.main.ctc.ctc_rv.ContactFgtItem;
import com.ac.alumnuscircle.main.ctc.hlyflt.HighlyFilterAct;
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

public class ContactFgt extends Fragment implements View.OnClickListener {
    public static final int HighlyFilterAct_REQUEST_CODE = 0x10086;
    private String majorFilter;
    private String minYear;
    private String maxYear;
    private String locationFilter;

    //可监听事件的控件
    private ImageButton search_btn;
    private ImageButton filter_btn;
    private Toolbar toolbar;
    private List<ContactFgtItem>data;

    private RecyclerView ctcFgt_rv;
    private ContactAdapter mAdapter;

    private PopupWindow popupWindow;
    private TextView check1_tv;
    private TextView check2_tv;
    private TextView check3_tv;
    private ImageView check1_img;
    private ImageView check2_img;
    private ImageView check3_img;
    private Button btn_filterOk;
    private Button btn_clearFilter;
    private Button btn_highlyFilter;
    private Button btn_check1;
    private Button btn_check2;
    private Button btn_check3;
    private Boolean IsCheck1Selected;
    private Boolean IsCheck2Selected;
    private Boolean IsCheck3Selected;

    private static Map<String,String>finalUserInfo;
    private List<UserInfo>userInfoList;
    private static List<ContactFgtItem> contactFgtItemList;
    private static String httpPostUrl;
    /**
     * 人脉列表获取到的response 利用这个数据更新UID
     * */
    private static String ctc_response;
    private Handler mHandler;
    private static final int HASGOTDATA=0x66;
    private static final int GOTFILTER=0x88;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.ctc_contactfgt,container,false);
        initView(view);
        minYear="0";
        maxYear="9999";
        majorFilter="[]";
        locationFilter="[]";
        new Thread(postTask).start();
        mHandler=new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==HASGOTDATA){
                    initcontactFgtItemList();
                    initRecyclerView();
//        updateUID();

                }
                if(msg.what==GOTFILTER){
                    new Thread(postTask).start();
                }

            }
        };
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
     * 向RecycleView里面添加数据
     * 曾博晖
     * 2016年8月23日17:37:12
     * 创建*/
    private void initRecyclerView() {
        ctcFgt_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        ctcFgt_rv.setAdapter(mAdapter = new ContactAdapter(getActivity(),data));
        ctcFgt_rv.addItemDecoration(new DividerLinearItemDecoration(getActivity(),
                DividerLinearItemDecoration.VERTICAL_LIST));
        mAdapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(
                        ActivityName.ctc_ContactDetailAct);
                Bundle bundle=new Bundle();
                bundle.putString("headImgUrl",data.get(position).getHeadImgUrl());
                bundle.putString("name",data.get(position).getUserName());
                bundle.putString("location",data.get(position).getUserLocation());
                bundle.putString("department",data.get(position).getUserFaculty());
                bundle.putString("grade",data.get(position).getUserGrade());
//                bundle.putString("class",contactFgtItemList.get(position).getUserClass());
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
     * 传入各项数据给contactFgtItemList数组
     * @author 曾博晖
     * @date 2016年8月28日
     * */
    private void initcontactFgtItemList() {
        //contactFgtItemList.clear();
        data.clear();
        for(int i=0;i<contactFgtItemList.size();i++){
            data.add(contactFgtItemList.get(i));
        }
//        data=contactFgtItemList;
        Log.i("TAG LENTH IS",""+contactFgtItemList.size());
        ContactFgtItem contactFgtItem=new ContactFgtItem(
                "http://ww1.sinaimg.cn/crop.95.235.1000.1000.1024/d71a5054jw8euqdybnb1ij20xc1e0tht.jpg",
                "刘畅","南京","软件学院","2014",
                "阿里巴巴Master"
        );
        ContactFgtItem contactFgtItem0=new ContactFgtItem(
                "http://b.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=45f10be75edf8db1bc7b74603c13f162/023b5bb5c9ea15ce2f42ea76b6003af33a87b224.jpg",
                "曾博晖","南京","软件学院","2014",
                "帅气码农"
        );
        ContactFgtItem contactFgtItem1=new ContactFgtItem(
                "http://img1.imgtn.bdimg.com/it/u=2385199661,1509060230&fm=21&gp=0.jpg",
                "董莹莹","南京","艺术学院","2012",
                "彩妆师"
        );
        ContactFgtItem contactFgtItem2=new ContactFgtItem(
                "http://v1.qzone.cc/avatar/201508/30/00/39/55e1e026dc781749.jpg%21200x200.jpg",
                "李崇","苏州","信息学院","2012",
                "软件工程师"
        );
        ContactFgtItem contactFgtItem3=new ContactFgtItem(
                "http://img2.imgtn.bdimg.com/it/u=3529368069,13239119&fm=21&gp=0.jpg",
                "苏小陌","杭州","经管学院","2012",
                "高级理财师"
        );
        ContactFgtItem contactFgtItem4=new ContactFgtItem(
                "http://img5.imgtn.bdimg.com/it/u=146486684,2713066059&fm=11&gp=0.jpg",
                "李梦雅","武汉","软件学院","2010",
                "高级架构师"
        );
        ContactFgtItem contactFgtItem5=new ContactFgtItem(
                "http://www.th7.cn/d/file/p/2016/07/26/b18e716fdfa5e890c4c9ebcb5f7e1afe.jpg",
                "崔皓宇","扬州","软件学院","2014",
                "高级码农"
        );
        ContactFgtItem contactFgtItem6=new ContactFgtItem(
                "http://img3.a0bi.com/upload/ttq/20160825/1472114871781.png",
                "白洋","尼古拉斯","软件学院","2014",
                "特级架构师"
        );
        ContactFgtItem contactFgtItem7=new ContactFgtItem(
                "http://v1.qzone.cc/avatar/201501/17/14/52/54ba06b65074b350.jpg%21200x200.jpg",
                "陈小辉","德玛西亚","软件学院","2014",
                "国家级特级架构师"
        );
        ContactFgtItem contactFgtItem8=new ContactFgtItem(
                "http://img4.imgtn.bdimg.com/it/u=3868407632,2636498616&fm=206&gp=0.jpg",
                "吴小宝","美国硅谷","软件学院","2014",
                "互联网时代super全栈工程师"
        );
        ContactFgtItem contactFgtItem9=new ContactFgtItem(
                "http://img5.imgtn.bdimg.com/it/u=2030615142,3525420243&fm=21&gp=0.jpg",
                "欧阳盼盼","南京","经管学院","2014",
                "设计师"
        );
        ContactFgtItem contactFgtItem10=new ContactFgtItem(
                "http://img0.imgtn.bdimg.com/it/u=581732747,2670419869&fm=21&gp=0.jpg",
                "于轩","南京","人文学院","2013",
                "知名作家"
        );
        data.add(contactFgtItem);
        data.add(contactFgtItem0);
        data.add(contactFgtItem1);
        data.add(contactFgtItem2);
        data.add(contactFgtItem3);
        data.add(contactFgtItem4);
        data.add(contactFgtItem5);
        data.add(contactFgtItem6);
        data.add(contactFgtItem7);
        data.add(contactFgtItem8);
        data.add(contactFgtItem9);
        data.add(contactFgtItem10);
    }



    /**
     *
     * @author 曾博晖
     * @param view 传入的view
     * @date 2016年8月28日
     * 功能：初始化各个控件
     * */
    private void initView(View view) {
        search_btn=(ImageButton)view.findViewById(R.id.ctc_contactfgt_tlb_search_btn);
        filter_btn=(ImageButton)view.findViewById(R.id.ctc_contactfgt_tlb_flt_btn) ;
        toolbar=(Toolbar)view.findViewById(R.id.ctc_contactfgt_tlb);
        search_btn.setOnClickListener(this);
        filter_btn.setOnClickListener(this);
        ctcFgt_rv=(RecyclerView)view.findViewById(R.id.ctc_contactfgt_rv);
        IsCheck1Selected=false;
        IsCheck2Selected=false;
        IsCheck3Selected=false;

        data=new ArrayList<>();
        httpPostUrl=HttpGet.httpGetUrl+"/search_user";
        finalUserInfo=new HashMap<>();
        userInfoList=new ArrayList<>();
        contactFgtItemList=new ArrayList<>();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ctc_contactfgt_tlb_search_btn:
                Toast.makeText(getActivity(),"精确查找",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ActivityName.ctc_FuzzySearchAct);
                startActivity(intent);
                break;
            case R.id.ctc_contactfgt_tlb_flt_btn:
//                    Toast.makeText(getActivity(), "筛选", Toast.LENGTH_SHORT).show();
                    showPopWindow();
                break;
            case R.id.ctc_contactfgt_popwindow_checkbox1:
            case R.id.ctc_contactfgt_popwindow_filter_tv1:
                if(check1_img.isSelected()){
                    check1_img.setSelected(false);
                    btn_check1.setBackgroundResource(R.mipmap.ctc_contactfgt_popwindow_checkbox);
                }else {
                    check1_img.setSelected(true);
                    btn_check1.setBackgroundResource(R.mipmap.ctc_contactfgt_popwindow_checkboxok);
                }
                break;
            case R.id.ctc_contactfgt_popwindow_checkbox2:
            case R.id.ctc_contactfgt_popwindow_filter_tv2:
                if(check2_img.isSelected()) {
                    check2_img.setSelected(false);
                    btn_check2.setBackgroundResource(R.mipmap.ctc_contactfgt_popwindow_checkbox);
                }else {
                    check2_img.setSelected(true);

                    btn_check2.setBackgroundResource(R.mipmap.ctc_contactfgt_popwindow_checkboxok);
                }
                break;
            case R.id.ctc_contactfgt_popwindow_checkbox3:
            case R.id.ctc_contactfgt_popwindow_filter_tv3:
                if(check3_img.isSelected()){
                    check3_img.setSelected(false);

                    btn_check3.setBackgroundResource(R.mipmap.ctc_contactfgt_popwindow_checkbox);
                }else {
                    check3_img.setSelected(true);

                    btn_check3.setBackgroundResource(R.mipmap.ctc_contactfgt_popwindow_checkboxok);
                }break;
            case R.id.ctc_contactfgt_popwindow_btn_filterOK:
                initcontactFgtItemList();
                getChooseAndFlt();

                popupWindow.dismiss();
                break;
            case R.id.ctc_contactfgt_popwindow_btn_clearFilter:
                clearFlt();
                break;
            case R.id.ctc_contactfgt_popwindow_btn_highlyFilter:
                //跳转到高级筛选界面
                gotoHighlyFlt();
                popupWindow.dismiss();
            default:
                break;
        }
    }
    /**
     * 清空所有选项
     * 曾博晖
     * 2016年8月30日08:44:12
     * 创建
     * */
    private void clearFlt(){
        btn_check1.setBackgroundResource(
                R.mipmap.ctc_contactfgt_popwindow_checkbox);
        btn_check2.setBackgroundResource(
                R.mipmap.ctc_contactfgt_popwindow_checkbox);
        btn_check3.setBackgroundResource(
                R.mipmap.ctc_contactfgt_popwindow_checkbox);
        check1_img.setSelected(false);
        check2_img.setSelected(false);
        check3_img.setSelected(false);

    }
    /**
     * 获取选项并开始筛选
     * 曾博晖
     * 2016年8月29日
     * 创建
     * */
    private void getChooseAndFlt(){
        if(check1_img.isSelected()){
            getSameDepartment("软件学院");
            IsCheck1Selected=true;
        }else {
            IsCheck1Selected=false;
        }
        if(check2_img.isSelected()){
            getSameGrade(MyInfo.myInfo.getAdmission_year());
            IsCheck2Selected=true;
        }else {
            IsCheck2Selected=false;
        }
        if(check3_img.isSelected()){
            getSameLocation("南京");
            IsCheck3Selected=true;
        }else {
            IsCheck3Selected=false;
        }

        mAdapter.notifyDataSetChanged();
    }

    /**
     * 根据地区进行的搜寻
     * @author 曾博晖
     * @param userLocation 传入的特定地区
     * @date 2016年9月2日18:52:58
     * */
    private void getSameLocation(String userLocation) {
        for(int i=0;i<data.size();i++){
            if(!(data.get(i).getUserLocation().equals(userLocation))){
                data.remove(i);
                i--;
            }
        }
    }
    /**
     * 根据年级进行搜寻
     * @param userGrade 传入的年级
     * */
    private void getSameGrade(String userGrade) {
        for(int i=0;i<data.size();i++){
            if(!(data.get(i).getUserGrade().equals(userGrade))){
                data.remove(i);
                i--;
            }
        }
    }

    /**
     * 获取与用户学院名字
     * 相同的对象
     * @param userDepart 传入的特定院系名
     * */
    private void getSameDepartment(String userDepart) {
        //List<ContactFgtItem> mcontactFgtItemList=new ArrayList<>();
        for(int i=0;i<data.size();i++){
            if(!(data.get(i).getUserFaculty().equals(userDepart))){
                //mcontactFgtItemList.add(contactFgtItemList.get(i));
                data.remove(i);
                i--;
            }
        }

    }

    /**
     * 转到高级筛选界面
     * 2016年8月29日
     * 曾博晖
     * 创建
     * @auuthor 曾博晖
     * @date
     * */
    private void gotoHighlyFlt() {
        Intent intent=new Intent(
                ActivityName.hlyflt_HighlyFilterAct);
        startActivityForResult(intent,HighlyFilterAct_REQUEST_CODE);
    }

    /**
     * 显示PopupWindow
     * 曾博晖
     * 2016年8月24日11:26:16
     * 创建
     * */
    private void showPopWindow() {
            //设置contentView
            View contentView = LayoutInflater.from(getActivity()).
                    inflate(R.layout.ctc_contactfgt_popwindow, null);
            popupWindow = new PopupWindow(contentView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setContentView(contentView);
        /**
         * 使用该方法实现
         * popupWindow
         * 点击空白处消失
         * 提高用户体验
         * 2016年8月30日00:29:22
         * 曾博晖
         * 添加
         * */
        popupWindow.setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        initPopView(contentView);
        //显示PopupWindow
        View rootview = LayoutInflater.from(
                getActivity()).inflate(R.layout.ctc_contactfgt, null);
        popupWindow.showAtLocation(
                rootview, Gravity.TOP, 0,toolbar.getHeight()+20);
    }
    /**
     * 对PopView界面里面的控件进行Init
     * @param contentView 传入的PopupWindow对象
     * @author 曾博晖
     * */
    private void initPopView (View contentView)
    {
        check1_img=(ImageView)contentView.findViewById(
                R.id.ctc_contactfgt_popwindow_fltcheck1_img);
        check2_img=(ImageView)contentView.findViewById(
                R.id.ctc_contactfgt_popwindow_fltcheck2_img);
        check3_img=(ImageView)contentView.findViewById(
                R.id.ctc_contactfgt_popwindow_fltcheck3_img);
        check1_tv=(TextView)contentView.findViewById(
                R.id.ctc_contactfgt_popwindow_filter_tv1);
        check2_tv=(TextView)contentView.findViewById(
                R.id.ctc_contactfgt_popwindow_filter_tv2);
        check3_tv=(TextView)contentView.findViewById(
                R.id.ctc_contactfgt_popwindow_filter_tv3);
        btn_check1=(Button)contentView.findViewById(
                R.id.ctc_contactfgt_popwindow_checkbox1);
        btn_filterOk=(Button)contentView.findViewById(
                R.id.ctc_contactfgt_popwindow_btn_filterOK);
        btn_check2=(Button)contentView.findViewById(
                R.id.ctc_contactfgt_popwindow_checkbox2);
        btn_check3=(Button)contentView.findViewById(
                R.id.ctc_contactfgt_popwindow_checkbox3);
        btn_clearFilter=(Button)contentView.findViewById(
                R.id.ctc_contactfgt_popwindow_btn_clearFilter);
        btn_highlyFilter=(Button)contentView.findViewById(
                R.id.ctc_contactfgt_popwindow_btn_highlyFilter);

        btn_clearFilter.setOnClickListener(this);
        btn_highlyFilter.setOnClickListener(this);
        btn_check3.setOnClickListener(this);
        btn_check2.setOnClickListener(this);
        btn_check1.setOnClickListener(this);
        btn_filterOk.setOnClickListener(this);
        check1_tv.setOnClickListener(this);
        check2_tv.setOnClickListener(this);
        check3_tv.setOnClickListener(this);
        initCheckBox();
    }
    /**
     * 将各个选项前的选框改为未选
     * 并且根据IsCheckBoxSelected的值来对
     * 三个选框的资源图片进行判断加载
     * */
    private void initCheckBox(){
        btn_check1.setBackgroundResource(R.mipmap.ctc_contactfgt_popwindow_checkbox);
        btn_check2.setBackgroundResource(R.mipmap.ctc_contactfgt_popwindow_checkbox);
        btn_check3.setBackgroundResource(R.mipmap.ctc_contactfgt_popwindow_checkbox);
        check1_img.setSelected(IsCheck1Selected);
        check2_img.setSelected(IsCheck2Selected);
        check3_img.setSelected(IsCheck3Selected);
        if(IsCheck1Selected){
            btn_check1.setBackgroundResource(
                    R.mipmap.ctc_contactfgt_popwindow_checkboxok);
        }
        if(IsCheck2Selected){
            btn_check2.setBackgroundResource(
                    R.mipmap.ctc_contactfgt_popwindow_checkboxok);
        }
        if(IsCheck3Selected){
            btn_check3.setBackgroundResource(
                    R.mipmap.ctc_contactfgt_popwindow_checkboxok);
        }
    }

    /**
     * 发送Http Post请求，获取到人脉列表数据
     * 2016年9月7日22:50:07
     * 曾博晖
     * 创建
     * */
    public void HttpPost(){


        Map<String, String> sendMap = new HashMap<>();

        String json = MapToJson.toJson(sendMap);
        RequestBody formBody = new FormBody.Builder()
                .add("_xsrf", HttpGet.loginKey)
                .add("filter_admission_year_min", minYear)
                .add("filter_admission_year_max",maxYear)
                .add("filter_major_list",majorFilter)
                .add("filter_city_list",locationFilter)
                .add("all_match","0")
                .add("query","")
                .build();
        Log.i("Filter is",locationFilter);
        Log.i("Filter is",majorFilter);
        Log.i("Filter is",minYear);
        Log.i("Filter is",maxYear);
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
                contactFgtItemList.clear();
                userInfoList.clear();
                data.clear();
                final String receiveStr = response.body().string();
                Log.i("TEST", receiveStr);
                AnalyzeResponse(receiveStr);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 对从云端传来的数据进行解析
     * 并且输出到ContactItem数据里面
     * 2016年9月8日20:38:35
     * */
    public void AnalyzeResponse(String receiveStr){
        //进行第一步解析
        Map<String, Object> result = new HashMap<>();
        ParseComplexJson.recursiveParseJson(result, receiveStr, null);
        //将解析后的一维数据加到finalUserInfo里面，并且剔除掉无关数据
        for(Map.Entry<String, Object> entry : result.entrySet()){
            Log.i("test", entry.getKey() + " : " + entry.getValue());
            if(entry.getKey().length()>24&&
                    entry.getKey().substring(24,
                            entry.getKey().length()).length()>10 ){
                finalUserInfo.put(
                        entry.getKey().substring(24,
                                entry.getKey().length()),
                        entry.getValue().toString());
            }
//            finalUserInfo.put(entry.getKey(),entry.getValue().toString());
        }
        for (int i = 0; i <finalUserInfo.size(); i++) {
            UserInfo user = new UserInfo();
            userInfoList.add(user);
            ContactFgtItem contact=new ContactFgtItem();
            contactFgtItemList.add(contact);
        }
        Map<String,String>EveryUser=new HashMap<>();
        for (Map.Entry<String ,String>entry:finalUserInfo.entrySet()) {
            Log.i("UserInfoIs", entry.getKey() + " : " + entry.getValue());
            for (int i = 0; i < finalUserInfo.size(); i++) {
                if (entry.getKey().substring(0, 1).equals("" + i)) {
                    EveryUser.put(entry.getKey(), entry.getValue().toString());
                    Log.i("The value is", entry.getKey().substring(10,
                            entry.getKey().length()) +"  "+
                            entry.getValue());

                    getTrueInfo(i, entry.getKey().substring(10,
                            entry.getKey().length()),
                            entry.getValue().toString());
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
                contactFgtItemList.get(i).setUserName(userInfoList.get(i).getName());
                contactFgtItemList.get(i).setHeadImgUrl(userInfoList.get(i).getIcon_url());
                contactFgtItemList.get(i).setUserFaculty(userInfoList.get(i).getFaculty());
                contactFgtItemList.get(i).setUserGrade(userInfoList.get(i).getAdmission_year());
                contactFgtItemList.get(i).setUserJob(userInfoList.get(i).getJob());
                contactFgtItemList.get(i).setUserLocation(userInfoList.get(i).getCity());
            }else {
                Log.i("NULL NAME","STILL");
            }
        }
        /**
         * 剔除掉数据为空的人脉对象
         * */
        for (int i=0;i<contactFgtItemList.size();i++){
            if(contactFgtItemList.get(i).getUserName()==null){
                contactFgtItemList.remove(i);
                i--;
            }
        }
        finalUserInfo.clear();
        Message message=new Message();
        message.what=HASGOTDATA;
        mHandler.sendMessage(message);

    }
    /**
     * 最后一步解析，将数据加到各个里面
     * @param  i 传入的数组下标
     * @param type 传入的类型 如city、job等
     * @param value 传入的value值，与type对应，如南京、学生等
     * @author 曾博晖
     * @date 2016年9月7日22:57:11
     * 曾博晖创建
     */
    private void getTrueInfo(final int i,String type,String value){
        if(type.equals("city")){
            userInfoList.get(i).setCity(value.substring(1,value.length()-1));
        }else if(type.equals("major")){
            userInfoList.get(i).setMajor(value.substring(1,value.length()-1));
        }else if(type.equals("name")){
            userInfoList.get(i).setName(value.substring(1,value.length()-1));
        }else if(type.equals("icon_url")){
            //此处由于煞笔服务器的二逼行为，全部替换为一个默认URL值
            //2016年9月8日20:44:03 曾博晖
            //userInfoList.get(i).setIcon_url(value);
            userInfoList.get(i).setIcon_url(
                    "http://img4.imgtn.bdimg.com/it/u=3868407632,2636498616&fm=206&gp=0.jpg");
        }else if(type.equals("company")){
            userInfoList.get(i).setCompany(value.substring(1,value.length()-1));
        }else if(type.equals("admission_year")){
            userInfoList.get(i).setAdmission_year(value);
        }else if(type.equals("register_time")){
            userInfoList.get(i).setRegister_time(value.substring(1,value.length()-1));
        }else if(type.equals("job")){
            userInfoList.get(i).setJob(value.substring(1,value.length()-1));
        }else if(type.equals("state")){
            userInfoList.get(i).setState(value.substring(1,value.length()-1));
        }else if(type.equals("instroduction")){
            userInfoList.get(i).setInstroduction(value);
        }else if(type.equals("faculty")){
            userInfoList.get(i).setFaculty(value.substring(1,value.length()-1));
        }else if(type.equals("country")){
            userInfoList.get(i).setCountry(value.substring(1,value.length()-1));
        }else if(type.equals("job_list")){
            userInfoList.get(i).setJob_list(value);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("test", "11111111111111111111111111111111111111111");
        if(requestCode == HighlyFilterAct_REQUEST_CODE && resultCode == HighlyFilterAct.HighlyFilterAct_RESULT_CODE && data != null){
            majorFilter = data.getStringExtra("majorFilter");
            Log.i("Filter is",majorFilter);
            minYear = data.getStringExtra("minYear");
            maxYear = data.getStringExtra("maxYear");
            locationFilter = data.getStringExtra("locationFilter");
//            mHandler.sendEmptyMessage(HASGOTDATA);
            mHandler.sendEmptyMessage(GOTFILTER);
        }
    }
}
