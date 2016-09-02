package com.ac.alumnuscircle.main.findcc.fccdtl;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.main.findcc.fccdtl.fccdtl_rv.FindCircleDetailItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15359 on 2016/8/29.
 */
public class FindCircleDetailAct extends Activity implements FindCircleDetailItem.OnItemClickListener{
    private RecyclerView recyclerView;
    private List<String> imagesUrl;
    private List<String>titles;
    private TextView titleTv;
    private FindCircleDetailItem findCircleDetailItem;
    private LinearLayout backllyt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fccdtl_findcircledtailact);
      Init();

    }
 private void Init()
 {
     InitView();
     InitData();
     findCircleDetailItem = new FindCircleDetailItem(titles,imagesUrl);
     recyclerView.setAdapter(findCircleDetailItem);
     findCircleDetailItem.setOnItemClickListener(this);
 }
    private void InitView()
    {
        recyclerView = (RecyclerView)findViewById(R.id.fccdtl_findcircledetailact_rv);
        //网格布局
        recyclerView.setLayoutManager(new GridLayoutManager(FindCircleDetailAct.this,2));
        titleTv = (TextView)findViewById(R.id.fccdtl_findcircledetailact_title);
       backllyt = (LinearLayout)findViewById(R.id.fccdtl_findcircledetailact_back);
        backllyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void InitData()
    {
        //设置标题
        String title = getIntent().getStringExtra("Title");
        titleTv.setText(title);

        imagesUrl = new ArrayList<>();
        titles = new ArrayList<>();

        imagesUrl.add("http://img0.imgtn.bdimg.com/it/u=3766443758,1529519468&fm=21&gp=0.jpg");
        imagesUrl.add("http://img2.imgtn.bdimg.com/it/u=2069918470,3277439936&fm=21&gp=0.jpg");
        imagesUrl.add("http://img2.imgtn.bdimg.com/it/u=4075937517,1358300463&fm=21&gp=0.jpg");

        titles.add("A圈");
        titles.add("B圈");
        titles.add("C圈");
    }
    @Override
    public void onItemClick(View view, int position) {

    }
}
