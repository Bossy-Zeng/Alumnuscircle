/**
 * @author zhengfanw
 * @date 16.07.20
 * 把裁剪界面作为单独的一个Activity，拍完照片，存储在SD卡，
 * 跳转到这个裁剪Activity，再从SD卡读取照片，进行裁剪，
 * 裁剪完成，进入下一个上传界面的Activity。
 */

package com.ac.alumnuscircle.supercamera.onetoonecamera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;


import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.cstt.Config;
import com.ac.alumnuscircle.supercamera.toolbox.BitmapAdapter;
import com.ac.alumnuscircle.supercamera.toolbox.BitmapUtil;
import com.ac.alumnuscircle.supercamera.toolbox.CalculateSize;
import com.ac.alumnuscircle.supercamera.toolbox.ScreenInfoSingleton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PaPaCrop extends Activity {

    /**
     * 裁剪界面用到的常量信号。
     */
    public static final int PAPACROP_SUCCESS_CROP_IMG = 0xF00A0;
    public static final int PAPACROP_RESULT_CODE = 0xF00A1;

    private CropImageView cropImageView;
    private Bitmap bitmap;

    private ImageButton camera_cp_cancelBtn;
    private ImageButton camera_cp_uploadBtn;
    private ImageButton camera_cp_backBtn;

    private Intent receiveIntent;
    private Intent resultIntent;

    private String absoluteImgPath;
    private Bitmap imageBitmap;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case  PAPACROP_SUCCESS_CROP_IMG:
                    /**
                     * 执行上传,todo upload()
                     */
                    setResult(PAPACROP_RESULT_CODE, resultIntent);
                    PaPaCrop.this.finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onetoonecamera_cropphoto);
        init();
    }

    /**
     * 总的初始化方法。
     */
    private void init(){
        initData();
        initUIBtn();
        initCropView();
    }

    /**
     * 初始化数据
     */
    private void initData(){
        receiveIntent = getIntent();
        resultIntent = new Intent();
        absoluteImgPath = receiveIntent.getStringExtra("absoluteImgPath");

        /**
         * 获取图片原始尺寸。
         * 再进行适配。
         */
        int[] originalSize = new CalculateSize().getSize(absoluteImgPath);
        int[] adaptedSize = new BitmapAdapter().adaptToScreenSize(this, originalSize[0], originalSize[1]);
        imageBitmap = BitmapUtil.getBitmap(absoluteImgPath, adaptedSize[0], adaptedSize[1]);
        imageBitmap = enlargeBitmap(imageBitmap, adaptedSize[0], adaptedSize[1]);
    }


    /**
     * 初始化UI按钮。
     */
    private void initUIBtn(){
        camera_cp_backBtn = (ImageButton)findViewById(R.id.camera_cp_back_btn);
        camera_cp_cancelBtn = (ImageButton)findViewById(R.id.camera_cp_cancel_btn);
        camera_cp_uploadBtn = (ImageButton)findViewById(R.id.camera_cp_upload_btn);

        camera_cp_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        camera_cp_cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 配置裁剪取消按钮的监听。
                 */
                initCropView();
            }
        });


        camera_cp_uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 成功裁剪，存到本地。
                 */
                bitmap = cropImageView.getCropImage();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] buffer = baos.toByteArray();
                            saveToSDCard(buffer);
                            handler.sendEmptyMessage(PAPACROP_SUCCESS_CROP_IMG);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    /**
     * 初始化裁剪界面。
     */
    private void initCropView(){
        cropImageView = (CropImageView) findViewById(R.id.camera_cp_cropimageview);
        if (imageBitmap != null){

            /**
             * 讲道理的话，
             * 默认裁剪宽度是屏幕宽度的0.6倍
             * 默认裁剪高度是屏幕宽度的0.6倍
             */
            cropImageView.setDrawable(new BitmapDrawable(imageBitmap)
                    , ScreenInfoSingleton.getInstance(this).getCropWidth(0.6)
                    , ScreenInfoSingleton.getInstance(this).getCropWidth(0.6));
        }
    }



    public Bitmap enlargeBitmap(Bitmap bitmap, float targetWidth, float targetHeight) {
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        float scaleX = (float)targetWidth / originalWidth;
        float scaleY = (float)targetHeight / originalHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        Bitmap enlargeBitmap = Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originalHeight, matrix, true);
        return enlargeBitmap;
    }

    /**
     * 静态方法，把原始字节数据转换为JPG照片格式保存到SD卡
     *
     * @param data
     * @throws IOException
     */
    private void saveToSDCard(byte[] data) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = format.format(date) + ".jpg";
        File fileFolder = new File(Config.ALBUM_PATH);
        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }
        File jpgFile = new File(fileFolder, filename);

        /**
         * 把路径信息作为结果传递。
         */
        resultIntent.putExtra("absoluteImgPath", jpgFile.getAbsolutePath());

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(jpgFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
