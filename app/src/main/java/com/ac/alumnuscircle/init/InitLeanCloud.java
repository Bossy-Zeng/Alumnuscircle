/**
 * @author 曾博晖
 * @date 2016年8月31日
 * @version 1
 * 功能：封装了LeanCloud的初始化。
 */
package com.ac.alumnuscircle.init;


import android.content.Context;
import android.widget.Toast;

import com.ac.alumnuscircle.main.MainAct;
import com.ac.alumnuscircle.main.ctc.leavemsg.CustomUserProvider;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

import cn.leancloud.chatkit.LCChatKit;

public class InitLeanCloud {
    /**
     * 作为全局唯一的ClientId登陆聊天服务器
     * 换终端测试时在此处改掉
     * 2016年9月2日14:16:49
     * 曾博晖
     * 创建
     * */
    public static String ClientId="曾博昊";


    public InitLeanCloud(Context context) {
        final String APP_ID = "KX3LSDk7hVvMzEpS84FOtNO6-gzGzoHsz";
        final String APP_KEY = "58fcJOc6EqJPkDovga3trRcx";
        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
        AVOSCloud.setDebugLogEnabled(true);
        LCChatKit.getInstance().init(context, APP_ID, APP_KEY);
        LCChatKit.getInstance().open(InitLeanCloud.ClientId, new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (null == e) {
                } else {
                }
            }
        });
    }
}
