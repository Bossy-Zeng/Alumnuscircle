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


    private final String APP_ID = "KX3LSDk7hVvMzEpS84FOtNO6-gzGzoHsz";
    private final String APP_KEY = "58fcJOc6EqJPkDovga3trRcx";

    public InitLeanCloud(Context context) {

        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
        AVOSCloud.setDebugLogEnabled(true);
        LCChatKit.getInstance().init(context, APP_ID, APP_KEY);

    }
}
