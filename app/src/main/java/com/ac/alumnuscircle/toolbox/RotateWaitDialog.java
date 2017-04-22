package com.ac.alumnuscircle.toolbox;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.ac.alumnuscircle.R;

/**
 * Created by hasee on 2017/2/3.
 */

public class RotateWaitDialog extends Dialog {

    private Context context;
    private int themeResId;
    private boolean cancelable;
    private OnCancelListener cancelListener;
    private ImageView rotate_wait_img;
    private Window window = null;

    public RotateWaitDialog(Context context) {
        super(context);
        this.context = context;
    }

    public RotateWaitDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.themeResId = themeResId;
    }

    protected RotateWaitDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        this.cancelable = cancelable;
        this.cancelListener = cancelListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotate_wait_dialog);
        init();
    }

    private void init() {
        rotate_wait_img = (ImageView)findViewById(R.id.rotate_wait_img);
        AnimationSet rotateAnimation = (AnimationSet) AnimationUtils.loadAnimation(context, R.anim.rotate_wait);
        rotate_wait_img.setAnimation(rotateAnimation);
        windowDeploy();
        setCanceledOnTouchOutside(false);
    }

    private void windowDeploy(){
        window = getWindow(); //得到对话框
        window.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
        window.setBackgroundDrawableResource(R.color.transparent); //设置对话框背景为透明
        WindowManager.LayoutParams wl = window.getAttributes();
        //根据x，y坐标设置窗口需要显示的位置
        int  x = window.getWindowManager().getDefaultDisplay().getWidth()/100;
        int y = window.getWindowManager().getDefaultDisplay().getHeight()/100;
        wl.x = x; //x小于0左移，大于0右移
        wl.y = y; //y小于0上移，大于0下移
//            wl.alpha = 0.6f; //设置透明度
//            wl.gravity = Gravity.BOTTOM; //设置重力
        window.setAttributes(wl);
    }

    @Override
    public void onBackPressed() {
        /**
         * 暂时不截获Back，需要时删除下列super语句。
         */
        super.onBackPressed();
    }
}
