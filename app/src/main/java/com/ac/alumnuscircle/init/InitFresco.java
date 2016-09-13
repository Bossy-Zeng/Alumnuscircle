/**
 * @author 吴正凡
 * @date 16.08.28
 * @version 2
 * 功能：封装了Fresco的初始化。
 */

package com.ac.alumnuscircle.init;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

public class InitFresco {
    public InitFresco(Context context){
        Fresco.initialize(context);
    }
}
