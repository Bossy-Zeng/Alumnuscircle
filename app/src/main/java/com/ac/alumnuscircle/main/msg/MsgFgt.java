
/**
 * Created by 曾博晖 on 2016/8/28.
 */
package com.ac.alumnuscircle.main.msg;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.init.InitLeanCloud;
import com.ac.alumnuscircle.main.MainAct;
import com.ac.alumnuscircle.main.msg.msgcontent.CommentFgt;
import com.ac.alumnuscircle.main.msg.msgcontent.NotifyFgt;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.activity.LCIMConversationListFragment;

public class MsgFgt extends Fragment implements View.OnClickListener {

    private View view;

    private LCIMConversationListFragment lcimConversationListFragment;
    private CommentFgt commentFgt;
    private NotifyFgt notifyFgt;
    private android.app.FragmentTransaction switchFragment;//更换fragment

    private LinearLayout msg_lly;
    private LinearLayout notify_lly;
    private LinearLayout comment_lly;

    private ImageView msg_tabline;
    private ImageView notify_tabline;
    private ImageView comment_tabline;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.msg_msgfgt, container, false);
        initView();
        initLeanCloud();
        return view;
    }

    /**
     * 登陆聊天服务器
     * 2016年9月2日13:54:53
     * 曾博晖 添加
     * */
    private void initLeanCloud() {
        LCChatKit.getInstance().open(InitLeanCloud.ClientId, new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (null == e) {

                } else {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void initView() {
        lcimConversationListFragment=new LCIMConversationListFragment();
        commentFgt=new CommentFgt();
        notifyFgt=new NotifyFgt();
        switchFragment = getFragmentManager().beginTransaction();
        //初始化fragment
        switchFragment.add(R.id.msg_msgfgt_content_flyt,lcimConversationListFragment);
        switchFragment.add(R.id.msg_msgfgt_content_flyt,commentFgt).hide(commentFgt);
        switchFragment.add(R.id.msg_msgfgt_content_flyt,notifyFgt).hide(notifyFgt);
        switchFragment.commit();

        msg_lly=(LinearLayout)view.findViewById(R.id.msg_msgfgt_msg_llyt);
        notify_lly=(LinearLayout)view.findViewById(R.id.msg_msgfgt_notify_llyt);
        comment_lly=(LinearLayout)view.findViewById(R.id.msg_msgfgt_comment_llyt);

        msg_tabline=(ImageView)view.findViewById(R.id.msg_msgfgt_msg_tabline);
        notify_tabline=(ImageView)view.findViewById(R.id.msg_msgfgt_notify_tabline);
        comment_tabline=(ImageView)view.findViewById(R.id.msg_msgfgt_comment_tabline);
        //设置监听
        msg_lly.setOnClickListener(this);
        notify_lly.setOnClickListener(this);
        comment_lly.setOnClickListener(this);

        //初始化设置为显示msgFgt界面
        msg_tabline.setSelected(true);
    }



    /**
     * 初始化监听的fragment
     */
    private void InitFgt() {
        //重新加载fragment选择器
        switchFragment = getFragmentManager().beginTransaction();
        //隐藏所有fragment
        if(lcimConversationListFragment!=null)
            switchFragment.hide(lcimConversationListFragment);
        if(commentFgt!=null)
            switchFragment.hide(commentFgt);
        if(notifyFgt!=null)
            switchFragment.hide(notifyFgt);
        msg_tabline.setSelected(false);
        notify_tabline.setSelected(false);
        comment_tabline.setSelected(false);
    }
    @Override
    public void onClick(View v) {
        InitFgt();
        switch(v.getId()){
            case R.id.msg_msgfgt_msg_llyt:
                if(lcimConversationListFragment==null){
                    lcimConversationListFragment=new LCIMConversationListFragment();
                    switchFragment.add(R.id.msg_msgfgt_content_flyt,
                            lcimConversationListFragment);
                }else {
                    switchFragment.show(lcimConversationListFragment);
                }
                msg_tabline.setSelected(true);
                break;
            case R.id.msg_msgfgt_notify_llyt:
                if(notifyFgt==null){
                    notifyFgt=new NotifyFgt();
                    switchFragment.add(R.id.msg_msgfgt_content_flyt,
                            notifyFgt);
                }else {
                    switchFragment.show(notifyFgt);
                }
                notify_tabline.setSelected(true);
                break;
            case R.id.msg_msgfgt_comment_llyt:
                if(commentFgt==null){
                    commentFgt=new CommentFgt();
                    switchFragment.add(R.id.msg_msgfgt_content_flyt,
                            commentFgt);
                }else {
                    switchFragment.show(commentFgt);
                }
                comment_tabline.setSelected(true);
                break;

        }
        switchFragment.commit();
    }
}
