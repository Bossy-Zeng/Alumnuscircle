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
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.main.ctc.ctc_rv.ContactAdapter;
import com.ac.alumnuscircle.main.ctc.ctc_rv.ContactFgtItem;
import com.ac.alumnuscircle.module.divdec.DividerLinearItemDecoration;


import java.util.ArrayList;
import java.util.List;

public class ContactFgt extends Fragment implements View.OnClickListener {
    //可监听事件的控件
    private ImageButton search_btn;
    private ImageButton filter_btn;
    private Toolbar toolbar;

    private List<ContactFgtItem> data;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.ctc_contactfgt,container,false);
        initView(view);
        initData();
        initRecyclerView();
        return view;
    }

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
                bundle.putString("class",data.get(position).getUserClass());
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
     * 传入各项数据给data数组
     * @author 曾博晖
     * @date 2016年8月28日
     * */
    private void initData() {
        data.clear();
        ContactFgtItem contactFgtItem=new ContactFgtItem(
                "http://ww1.sinaimg.cn/crop.95.235.1000.1000.1024/d71a5054jw8euqdybnb1ij20xc1e0tht.jpg",
                "刘畅","南京","软件学院","2014级","软件工程2班",
                "阿里巴巴Master"
        );
        ContactFgtItem contect0=new ContactFgtItem(
                "http://b.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=45f10be75edf8db1bc7b74603c13f162/023b5bb5c9ea15ce2f42ea76b6003af33a87b224.jpg",
                "曾博晖","南京","软件学院","2014级","软件工程2班",
                "帅气码农"
        );
        ContactFgtItem contect1=new ContactFgtItem(
                "http://img1.imgtn.bdimg.com/it/u=2385199661,1509060230&fm=21&gp=0.jpg",
                "董莹莹","南京","艺术学院","2012级","工业设计1班",
                "彩妆师"
        );
        ContactFgtItem contect2=new ContactFgtItem(
                "http://v1.qzone.cc/avatar/201508/30/00/39/55e1e026dc781749.jpg%21200x200.jpg",
                "李崇","苏州","信息学院","2012级","电子电路4班",
                "软件工程师"
        );
        ContactFgtItem contect3=new ContactFgtItem(
                "http://img2.imgtn.bdimg.com/it/u=3529368069,13239119&fm=21&gp=0.jpg",
                "苏小陌","杭州","经管学院","2012级","投资路4班",
                "高级理财师"
        );
        ContactFgtItem contect4=new ContactFgtItem(
                "http://img5.imgtn.bdimg.com/it/u=146486684,2713066059&fm=11&gp=0.jpg",
                "李梦雅","武汉","软件学院","2010级","卓工班",
                "高级架构师"
        );
        ContactFgtItem contect5=new ContactFgtItem(
                "http://www.th7.cn/d/file/p/2016/07/26/b18e716fdfa5e890c4c9ebcb5f7e1afe.jpg",
                "崔皓宇","扬州","软件学院","2014级","卓工班",
                "高级码农"
        );
        ContactFgtItem contect6=new ContactFgtItem(
                "http://img3.a0bi.com/upload/ttq/20160825/1472114871781.png",
                "白洋","尼古拉斯","软件学院","2014级","特级卓工班",
                "特级架构师"
        );
        ContactFgtItem contect7=new ContactFgtItem(
                "http://v1.qzone.cc/avatar/201501/17/14/52/54ba06b65074b350.jpg%21200x200.jpg",
                "陈小辉","德玛西亚","软件学院","2014级","特级卓工班",
                "国家级特级架构师"
        );
        ContactFgtItem contect8=new ContactFgtItem(
                "http://img4.imgtn.bdimg.com/it/u=3868407632,2636498616&fm=206&gp=0.jpg",
                "吴小宝","美国硅谷","软件学院","2014级","软件学院2班",
                "互联网时代super全栈工程师"
        );
        ContactFgtItem contect9=new ContactFgtItem(
                "http://img5.imgtn.bdimg.com/it/u=2030615142,3525420243&fm=21&gp=0.jpg",
                "欧阳盼盼","南京","经管学院","2014级","财贸管理2班",
                "设计师"
        );
        ContactFgtItem contect10=new ContactFgtItem(
                "http://img0.imgtn.bdimg.com/it/u=581732747,2670419869&fm=21&gp=0.jpg",
                "于轩","南京","人文学院","2013级","古汉语学院2班",
                "知名作家"
        );
        data.add(contactFgtItem);
        data.add(contect0);
        data.add(contect1);
        data.add(contect2);
        data.add(contect3);
        data.add(contect4);
        data.add(contect5);
        data.add(contect6);
        data.add(contect7);
        data.add(contect8);
        data.add(contect9);
        data.add(contect10);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ctc_contactfgt_tlb_search_btn:
                Toast.makeText(getActivity(),"精确查找",Toast.LENGTH_SHORT).show();
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
                initData();
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
            getSameGrade("2014级");
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
        //List<ContactFgtItem> mdata=new ArrayList<>();
        for(int i=0;i<data.size();i++){
            if(!(data.get(i).getUserFaculty().equals(userDepart))){
                //mdata.add(data.get(i));
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
        startActivity(intent);
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
}
