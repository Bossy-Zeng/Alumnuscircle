/**
 * @author 博晖
 * @date 2016年8月31日
 * @version 1
 * 功能：封装了LeanCloud的初始化。
 */
package com.ac.alumnuscircle.init;


import android.content.Context;

import com.ac.alumnuscircle.main.ctc.leavemsg.CustomUserProvider;
import com.avos.avoscloud.AVOSCloud;

import cn.leancloud.chatkit.LCChatKit;

public class InitLeanCloud {


    public InitLeanCloud(Context context) {
        final String APP_ID = "KX3LSDk7hVvMzEpS84FOtNO6-gzGzoHsz";
        final String APP_KEY = "58fcJOc6EqJPkDovga3trRcx";
        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
        AVOSCloud.setDebugLogEnabled(true);
        LCChatKit.getInstance().init(context, APP_ID, APP_KEY);
    }
}
