/**
 * @author Zhengfan
 * @date 16.08.28
 * @version 1
 * 功能：位图的尺寸不一定与屏幕尺寸适配，可能会出现过大或者过小的情况，
 * 需要进行自动适配，大概效果类似centerInside。
 * 用于裁剪图片过程中，初始化源图片的时候。
 */

package com.ac.alumnuscircle.toolbox.bitmap;


import android.app.Activity;
import android.media.ExifInterface;

import com.ac.alumnuscircle.info.ScreenInfoSingleton;

import java.io.IOException;

public class BitmapAdapter {

    /**
     * 获取图片的旋转角度。
     * @param filepath 文件路径
     * @return 旋转的度数
     */
    public static int adaptToScreenOrientation(String filepath){
        /**
         * 获取Exif信息。
         */
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException e) {
            e.printStackTrace();
            exif = null;
        }

        /**
         * 解析图片度数。
         */
        int degree = 0;
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
                    break;
            }
        }
        return degree;
    }

    /**
     *
     * @param activity 用于传入ScreenInfoSingleton.getInstance(activity)中
     * @param paramWidth 输入宽度
     * @param paramHeight 输入高度
     * @return
     */
    public static int[] adaptToScreenSize(Activity activity, int paramWidth, int paramHeight) {

        /**
         * 必需要强制转换成浮点数。
         */
        double originalWidth = (double)paramWidth;
        double originalHeight = (double)paramHeight;
        int[] returnSize = new int[2];

        int screenWidth = ScreenInfoSingleton.getInstance(activity).getScreenWidth();
        int screenHeight = ScreenInfoSingleton.getInstance(activity).getScreenHeight();

        /**
         * 求得一个适配的比值ratio，
         * originalWidth/screenWidth:原始宽度除以屏幕宽度widthRatio。
         * orginalHeight/screenHeight:原始高度除以屏幕高度heightRatio。
         * 1、当ratio大于1，说明 widthRatio 大于 heightRatio，
         * 说明原始宽度在屏幕中占比较大，应当用widthRatio作为比例适配，此时高度留白。
         * 2、当ratio小于1，说明 widthRatio 小于 heightRatio，
         * 说明原始高度在屏幕中占比较大，应当用heightRatio作为比例适配，此时宽度留白。
         * 3、当ratio等于1，说明宽高占比相同，默认用宽度来适配。
         */
        double widthRatio = originalWidth / screenWidth;
        double heightRatio = originalHeight / screenHeight;
        double ratio = widthRatio / heightRatio;
        if(ratio >= 1){
            returnSize[0] = (int)(originalWidth / widthRatio);
            returnSize[1] = (int)(originalHeight / widthRatio);
        }else{
            returnSize[0] = (int)(originalWidth / heightRatio);
            returnSize[1] = (int)(originalHeight / heightRatio);
        }
        return returnSize;
    }
}
