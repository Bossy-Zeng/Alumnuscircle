/**
 * @author Zhengfan
 * @date 16.08.29
 * @version 1
 * 功能：收藏名片的Fragment
 */

package com.ac.alumnuscircle.main.mine.minecontent;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.main.ctc.ctc_rv.ContactAdapter;
import com.ac.alumnuscircle.main.ctc.ctc_rv.ContactFgtItem;
import com.ac.alumnuscircle.module.divdec.DividerLinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CollectCard extends Fragment {

    private View view;
    private LayoutInflater layoutInflater;
    private ViewGroup container;

    private RecyclerView rvCollectCards;
    private List<ContactFgtItem> data;
    private ContactAdapter adapter;

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
        initRecycleView();
        return view;
    }

    private void initRecycleView() {
        rvCollectCards.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCollectCards.setAdapter(adapter = new ContactAdapter(getActivity(),data));
        
        rvCollectCards.addItemDecoration(new DividerLinearItemDecoration(getActivity(),
                DividerLinearItemDecoration.VERTICAL_LIST));
        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
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
        data = new ArrayList<>();
        data.clear();
        ContactFgtItem contactFgtItem=new ContactFgtItem(
                "http://ww1.sinaimg.cn/crop.95.235.1000.1000.1024/d71a5054jw8euqdybnb1ij20xc1e0tht.jpg",
                "刘畅","南京","软件学院","2014级",
                "阿里巴巴Master"
        );
        ContactFgtItem ContactFgtItem0=new ContactFgtItem(
                "http://b.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=45f10be75edf8db1bc7b74603c13f162/023b5bb5c9ea15ce2f42ea76b6003af33a87b224.jpg",
                "曾博晖","南京","软件学院","2014级",
                "帅气码农"
        );
        ContactFgtItem ContactFgtItem1=new ContactFgtItem(
                "http://img1.imgtn.bdimg.com/it/u=2385199661,1509060230&fm=21&gp=0.jpg",
                "董莹莹","南京","艺术学院","2012级",
                "彩妆师"
        );
        ContactFgtItem ContactFgtItem2=new ContactFgtItem(
                "http://v1.qzone.cc/avatar/201508/30/00/39/55e1e026dc781749.jpg%21200x200.jpg",
                "李崇","苏州","信息学院","2012级",
                "软件工程师"
        );
        ContactFgtItem ContactFgtItem3=new ContactFgtItem(
                "http://img2.imgtn.bdimg.com/it/u=3529368069,13239119&fm=21&gp=0.jpg",
                "苏小陌","杭州","经管学院","2012级",
                "高级理财师"
        );
        ContactFgtItem ContactFgtItem4=new ContactFgtItem(
                "http://img5.imgtn.bdimg.com/it/u=146486684,2713066059&fm=11&gp=0.jpg",
                "李梦雅","武汉","软件学院","2010级",
                "高级架构师"
        );
        ContactFgtItem ContactFgtItem5=new ContactFgtItem(
                "http://www.th7.cn/d/file/p/2016/07/26/b18e716fdfa5e890c4c9ebcb5f7e1afe.jpg",
                "崔皓宇","扬州","软件学院","2014级",
                "高级码农"
        );
        ContactFgtItem ContactFgtItem6=new ContactFgtItem(
                "http://img3.a0bi.com/upload/ttq/20160825/1472114871781.png",
                "白洋","尼古拉斯","软件学院","2014级",
                "特级架构师"
        );
        ContactFgtItem ContactFgtItem7=new ContactFgtItem(
                "http://v1.qzone.cc/avatar/201501/17/14/52/54ba06b65074b350.jpg%21200x200.jpg",
                "陈小辉","德玛西亚","软件学院","2014级",
                "国家级特级架构师"
        );
        ContactFgtItem ContactFgtItem8=new ContactFgtItem(
                "http://img4.imgtn.bdimg.com/it/u=3868407632,2636498616&fm=206&gp=0.jpg",
                "吴小宝","美国硅谷","软件学院","2014级",
                "互联网时代super全栈工程师"
        );
        ContactFgtItem ContactFgtItem9=new ContactFgtItem(
                "http://img5.imgtn.bdimg.com/it/u=2030615142,3525420243&fm=21&gp=0.jpg",
                "欧阳盼盼","南京","经管学院","2014级",
                "设计师"
        );
        ContactFgtItem ContactFgtItem10=new ContactFgtItem(
                "http://img0.imgtn.bdimg.com/it/u=581732747,2670419869&fm=21&gp=0.jpg",
                "于轩","南京","人文学院","2013级",
                "知名作家"
        );
        data.add(contactFgtItem);
        data.add(ContactFgtItem0);
        data.add(ContactFgtItem1);
        data.add(ContactFgtItem2);
        data.add(ContactFgtItem3);
        data.add(ContactFgtItem4);
        data.add(ContactFgtItem5);
        data.add(ContactFgtItem6);
        data.add(ContactFgtItem7);
        data.add(ContactFgtItem8);
        data.add(ContactFgtItem9);
        data.add(ContactFgtItem10);
    }

    private void initView(View view){
        rvCollectCards=(RecyclerView)view.findViewById(R.id.rv_collect_cards);
    }
}
