package com.ac.alumnuscircle.main.ctc;
/**
 * @author 曾博晖
 * @date 2016年8月28日
 * @verson 1
 * 功能：实现人脉详情界面
 */
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ac.alumnuscircle.R;

import com.ac.alumnuscircle.main.ctc.leavemsg.CustomUserProvider;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.facebook.drawee.view.SimpleDraweeView;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;


public class ContactDetailAct extends Activity implements View.OnClickListener {
    private SimpleDraweeView headImg_bottom;
    private SimpleDraweeView headImg_Top;

    private TextView userName;
    private TextView userJobTitle;
    private TextView userLocation;
    private TextView userSchool;
    private TextView userDepartment;
    private TextView userClass;
    private TextView userEduStartDate;
    private TextView userCompany;
    private TextView userVocation;


    private String headImgUrl;

    //下面的都是可以监听事件的
    private Button btn_leaveMsg;
    private ImageButton btn_back;
    private LinearLayout dynamicLayout;
    private LinearLayout collectLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ctc_contactdetailact);
        initWidget();
        initView();

    }
    /**
     * 初始化控件列表
     * 2016年8月26日14:43:14
     * 曾博晖
     * 创建
     * */
    private void initWidget(){
        headImg_bottom=(SimpleDraweeView) findViewById(R.id.ctc_contactdetailact_headImgBottom);
        headImg_Top=(SimpleDraweeView) findViewById(R.id.ctc_contactdetailact_headImgTop);

        userName=(TextView)findViewById(R.id.ctc_contactdetailact_Name_tv);
        userJobTitle=(TextView)findViewById(R.id.ctc_contactdetailact_JobTitle_tv);
        userLocation=(TextView)findViewById(R.id.ctc_contactdetailact_Location_tv);
        userSchool=(TextView)findViewById(R.id.ctc_contactdetailact_School_tv);
        userDepartment=(TextView)findViewById(R.id.ctc_contactdetailact_Department_tv);
        userClass=(TextView)findViewById(R.id.ctc_contactdetailact_Class_tv);
        userEduStartDate=(TextView)findViewById(R.id.ctc_contactdetailact_EduStartDate_tv);
        userCompany=(TextView)findViewById(R.id.ctc_contactdetailact_Company_tv);
        userVocation=(TextView)findViewById(R.id.ctc_contactdetailact_Vocation_tv);

        btn_back=(ImageButton)findViewById(R.id.ctc_contactdetailact_btn_back);
        btn_leaveMsg=(Button)findViewById(R.id.ctc_contactdetailact_leaveMsg_btn);
        collectLayout=(LinearLayout)findViewById(R.id.ctc_contactdetailact_collect_lly);
        dynamicLayout=(LinearLayout)findViewById(R.id.ctc_contactdetailact_dynamic_lly);
    }
    /**
     * 初始化界面，接收上个界面传来的数据
     * 曾博晖 2016年8月26日14:55:08
     * 创建
     * */
    private void initView(){
        Bundle bundle=getIntent().getExtras();
        headImgUrl=bundle.getString("headImgUrl");
        headImg_bottom.setImageURI(Uri.parse(headImgUrl));
        headImg_Top.setImageURI(Uri.parse(headImgUrl));
        userName.setText(bundle.getString("name"));
        userDepartment.setText(bundle.getString("department"));
        userJobTitle.setText(bundle.getString("job"));
        userClass.setText(bundle.getString("class"));
        userEduStartDate.setText(bundle.getString("grade"));
        userLocation.setText(bundle.getString("location"));

        btn_back.setOnClickListener(this);
        btn_leaveMsg.setOnClickListener(this);
        dynamicLayout.setOnClickListener(this);
        collectLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ctc_contactdetailact_btn_back:
                finish();
                break;
            case R.id.ctc_contactdetailact_dynamic_lly:
                //跳转到动态界面
                Toast.makeText(this,"展开到动态",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ctc_contactdetailact_collect_lly:
                Toast.makeText(this,"收藏"+userName.getText().toString()
                        +"的名片！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ctc_contactdetailact_leaveMsg_btn:
                initAndLeaveMsg();
//                Toast.makeText(this,"将对"+userName.getText().toString()
//                        +"进行留言",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
    /**
     * 初始化并进行留言
     * 曾博晖
     * 2016年8月31日
     * 添加
     * */
    private void initAndLeaveMsg() {
        /**
         * 每次打开留言私聊界面时
         * 立刻把当前被聊天的对象的ID（这里还是写成名字，之后改成ID）
         * 姓名以及头像URL地址新增到
         * partUsers 数组里面
         * 以便可以加载到该对象的头像等信息
         * 2016年9月1日00:05:20
         * 曾博晖
         * 添加
         * @verson 1
         * @date 2016年9月1日
         * @author 曾博晖
         * */
        CustomUserProvider.partUsers.add(new LCChatKitUser(userName.getText().toString(),
                userName.getText().toString(),
                headImgUrl));
        /***
         * clientId现在只做测试，之后换成当前登录用户的ID值
         * 2016年9月1日00:08:11
         * 曾博晖
         * 添加
         */
        String clientId = "白洋";

        LCChatKit.getInstance().open(clientId, new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (null == e) {
                    //finish();
                    Intent intent = new Intent(ContactDetailAct.this,
                            LCIMConversationActivity.class);
                    intent.putExtra(LCIMConstants.PEER_ID, userName.getText().toString());
                    intent.putExtra("theName",userName.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(ContactDetailAct.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

