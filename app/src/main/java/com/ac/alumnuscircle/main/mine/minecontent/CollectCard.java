/**
 * @author Zhengfan
 * @date 16.08.29
 * @version 1
 * 功能：收藏名片的Fragment
 */

package com.ac.alumnuscircle.main.mine.minecontent;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ac.alumnuscircle.R;
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
    }

    /**
     * 加载用户数据
     * */
    private void initData() {
        data = new ArrayList<ContactFgtItem>();
        ContactFgtItem contect1=new ContactFgtItem(
                "http://img2.imgtn.bdimg.com/it/u=3413454958,4293050372&fm=11&gp=0.jpg",
                "赵小雨","南京","艺术学院","2012级","工业设计1班",
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
                "李大嘴","武汉","软件学院","2010级","卓工班",
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
        data.add(contect1);
        data.add(contect2);
        data.add(contect3);
        data.add(contect4);
        data.add(contect5);
        data.add(contect6);
        data.add(contect7);
        data.add(contect8);
    }

    private void initView(View view){
        rvCollectCards=(RecyclerView)view.findViewById(R.id.rv_collect_cards);
    }
}
