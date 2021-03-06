/**
 * @author 吴正凡
 * @date 16.08.01
 * 解析出位图的大小，返回一个长度为2的整型数组。
 */

package com.ac.alumnuscircle.supercamera.toolbox;

import android.graphics.BitmapFactory;

import java.io.File;

public class CalculateSize {

    /**
     * 不用静态方法的原因是：
     * 这个方法用的比较少，如果设为静态，浪费堆区，需要时再创建，用完就回收。
     */

    /**
     * 获取位图的大小。
     * @param bitmapPathStr 位图的“绝对路径”
     * @return 如果计算出来了：返回长度为2的整型数组，第一个为宽度，第二个为长度。
     * 如果计算不出来，文件不存在等，返回null。
     */
    public int[] getSize(String bitmapPathStr){
        /**
         * 判断文件是否存在。
         */
        File bitmapFile = new File(bitmapPathStr);
        if(!bitmapFile.exists()){
            return null;
        }
        bitmapFile = null;

        /**
         * 如果存在，开始解析。
         */
        int[] size = new int[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        /**
         * 并不解析出图片，只需要options就可以了。
         */
        BitmapFactory.decodeFile(bitmapPathStr, options);
        if(options.outWidth == 0 || options.outHeight == 0){
            return null;
        }
        size[0] = options.outWidth;
        size[1] = options.outHeight;
        /**
         * 迭代缩小。
         */
        while((size[0] > 800) || (size[1] > 800)){
            size[0] /= 2;
            size[1] /= 2;
        }
        return size;
    }
}
