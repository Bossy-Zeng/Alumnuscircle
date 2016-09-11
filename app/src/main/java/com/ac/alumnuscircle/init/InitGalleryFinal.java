/**
 * @author zhengfanw
 * @date 16.07.26
 * 封装GalleryFinal的初始化过程。
 */

package com.ac.alumnuscircle.init;

import android.content.Context;
import android.graphics.Color;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.supercamera.toolbox.FrescoImageLoader;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

public class InitGalleryFinal {
    public InitGalleryFinal(Context context){
        ThemeConfig themeConfig = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.rgb(0xFF, 0x7C, 0x7A))
                .setTitleBarTextColor(R.color.white)
                .setEditPhotoBgTexture(context.getResources().getDrawable(R.color.toolbarGreen))
                .setCheckNornalColor(Color.rgb(0xFF, 0x7C, 0x7A))
                .setCheckSelectedColor(Color.rgb(0xE0, 0x46, 0x46))
                .setFabNornalColor(Color.rgb(0xFF, 0x7C, 0x7A))
                .setFabPressedColor(Color.rgb(0xE0, 0x46, 0x46))
                .setCropControlColor(Color.rgb(0xFF, 0x7C, 0x7A))
                .setPreviewBg(context.getResources().getDrawable(R.color.toolbarGreen))
                .build();

        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)
                .setEnableEdit(true)
                .setEnableCrop(false)
                .setForceCropEdit(false)
                .setEnableRotate(false)
                .setEnablePreview(true)
                .setCropSquare(false)
                .setCropReplaceSource(false)
                .setRotateReplaceSource(false)
                .build();

        ImageLoader imageLoader = new FrescoImageLoader(context);
        CoreConfig coreConfig = new CoreConfig.Builder(context, imageLoader, themeConfig)
                .setFunctionConfig(functionConfig)
                .build();

        GalleryFinal.init(coreConfig);
    }
}
