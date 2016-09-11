/**
 * @author zhengfanw
 * @date 16.08.04
 * 封装了与特定机型有关的屏幕信息，包括屏幕尺寸等，
 * Note：需要传入Activity进行初始化，另外本类不可以进行实例化，
 * 只要且只可以初始化一次，整个生命周期使用，均为静态方法和静态变量。
 */

package com.ac.alumnuscircle.supercamera.toolbox;

import android.app.Activity;
import android.view.WindowManager;

public class ScreenInfoSingleton {

    /**
     *
     * 具体内容请见ConstSignal.
     *
     * 关于屏幕方向与宽高的具体说明：
     * 1、PORTRAIT指肖像，即竖屏；LANDSCAPE指风景，即横屏。
     * 2、LANDSCAPE_LEFT指由竖屏向左（逆时针90度）旋转之后，所呈现的方向。
     * 3、LANDSCAPE_LEFT指由竖屏向右（顺时针90度）旋转之后，所呈现的方向。
     * 4、暂时没有发现可以倒立（逆、顺时针旋转180度）的情况，有则补充。
     * Note：宽高与屏幕方向密切相关，当为PORTRAIT时，手机端大部分情况宽小于高。
     * 当为LANDSCAPE时，手机端大部分情况宽大于高。
     * 无论如何，宽指的是自左向右的长度，高指的是自下向上的长度。
     */

    private static ScreenInfoSingleton instance;

    private int orientation;

    private int screenWidth;
    private int screenHeight;

    private int cropWidth;
    private int cropHeight;

    /**
     * 对外的唯一接口
     *
     * @param activity 用Activity对象来获取屏幕管理器。
     * @return
     */
    public static synchronized ScreenInfoSingleton getInstance(Activity activity) {
        if (instance == null) {
            instance = new ScreenInfoSingleton();
        }
        instance.init(activity);
        return instance;
    }

    /**
     * 将构造方法私有化。
     */
    private ScreenInfoSingleton() {

    }

    /**
     * 每次获取实例，都会用Activity初始化一遍，相当于刷新一次属性。
     *
     * @param activity
     */
    private void init(Activity activity) {
        /**
         * 获取WindowManager对象。
         */
        WindowManager windowManager = activity.getWindowManager();

        /**
         * 必须要先初始化屏幕尺寸之后，才能初始化裁剪尺寸。
         */
        initOrientation(windowManager);
        initScreenSize(windowManager);
        initCropSize();
    }

    /**
     * 初始化方向
     *
     * @param windowManager
     */
    private void initOrientation(WindowManager windowManager) {
        setOrientation(windowManager.getDefaultDisplay().getOrientation());
    }

    /**
     * 初始化屏幕尺寸。
     *
     * @param windowManager
     */
    private void initScreenSize(WindowManager windowManager) {
        setScreenWidth(windowManager.getDefaultDisplay().getWidth());
        setScreenHeight(windowManager.getDefaultDisplay().getHeight());
    }

    /**
     * 初始化裁剪尺寸，默认是屏幕尺寸。
     */
    private void initCropSize() {
        setCropWidth(screenWidth);
        setCropHeight(screenHeight);
    }

    /**
     * 成员的getter和setter方法，getter要公有，setter要私有。
     *注意getCropWidth、getCropHeight，
     * 这两个get方法各自接收一个参数scale，表示放缩比例。
     * 用于初始化裁剪尺寸。
     */
    public int getOrientation() {
        return orientation;
    }

    private void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    private void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    private void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getCropWidth(double scale) {
        return (int)(cropWidth * scale);
    }

    private void setCropWidth(int cropWidth) {
        this.cropWidth = cropWidth;
    }

    public int getCropHeight(double scale) {
        return (int)(cropHeight * scale);
    }

    private void setCropHeight(int cropHeight) {
        this.cropHeight = cropHeight;
    }

}


