/**
 * @author Zhengfan
 * @date 16.08.27
 * @version 2
 * 功能：这个类继承于Application，执行一些初始化操作。
 * 注释1：类名前缀AC代表AlumnusCircle（校友圈）的意思。
 * 注释2：这个App用到的第三方开源库有：Gson、Okhttp、Fresco、Volley（详见build.gradle）
 */

package com.ac.alumnuscircle;

import android.app.Application;

import com.ac.alumnuscircle.init.InitFresco;
import com.ac.alumnuscircle.init.InitGalleryFinal;
import com.ac.alumnuscircle.init.InitLeanCloud;
import com.ac.alumnuscircle.main.ctc.leavemsg.CustomUserProvider;
import com.avos.avoscloud.AVOSCloud;

import cn.leancloud.chatkit.LCChatKit;

public class ACApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    /**
     * @author Zhengfan
     * @date 16.08.28
     * @version 1
     * 功能：初始化方法，执行一些需要全局使用的类的初始化。
     * 注释1：配合init包下面的类使用。
     * 注释2：顺序有要求，若需要调换顺序，请与CTO商讨。
     */
    private void init(){
        initFresco();
        initGalleryFinal();
        initLeanCloud();
    }

    private void initFresco(){
        new InitFresco(getApplicationContext());
    }

    /**
     * 初始化GalleryFinal
     */
    private void initGalleryFinal(){
        new InitGalleryFinal(getApplicationContext());
    }


    private void initLeanCloud(){
        new InitLeanCloud(getApplicationContext());
    }
}
