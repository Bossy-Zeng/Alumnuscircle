/**
 * @author zhengfanw
 * @date 16.07.20
 * 实现了自定义相机的预览界面和拍照存储。
 */

package com.ac.alumnuscircle.supercamera.normalcamera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;


import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.cstt.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;


public class PaPaCamera extends Activity {

    /**
     * 拍照界面用到的常量信号。
     */
    public static final int PAPACAMERA_SUCCESS_SAVE_TO_SDCARD = 0xF0090;
    public static final int PAPACAMERA_SUCCESS_GET_IMG_FROM_ALBUM = 0xF0091;
    public static final int PAPACAMERA_REQUEST_CODE_FOR_GALLERY = 0xF0092;
    public static final int PAPACAMERA_REQUEST_CODE_FOR_CROP = 0xF0093;

    private Camera camera;

    private FrameLayout camera_tp_preview;
    private PaPaPreview preview;

    private Camera.Parameters param;
    private int cameraID;

    private Intent sendIntent;

    private ImageButton camera_tp_takeBtn;
    private ImageButton camera_tp_beautifyBtn;
    private ImageButton camera_tp_albumBtn;
    private ImageButton camera_tp_backBtn;
    private ImageButton camera_tp_flashBtn;
    private ImageButton camera_tp_changeBtn;

    private int screenWidth;
    private int screenHeight;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PAPACAMERA_SUCCESS_SAVE_TO_SDCARD:
                    PaPaCamera.this.startActivityForResult(sendIntent, PAPACAMERA_REQUEST_CODE_FOR_CROP);
                    break;
                case PAPACAMERA_SUCCESS_GET_IMG_FROM_ALBUM:
                    PaPaCamera.this.startActivityForResult(sendIntent, PAPACAMERA_REQUEST_CODE_FOR_CROP);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normalcamera_takephoto);
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraResume();
        camera_tp_takeBtn.setEnabled(true);
    }

    private void cameraPause() {
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private void cameraResume() {
        if (camera == null) {
            if (checkCameraHardware(this)) {
                camera = getCameraInstance(cameraID);
                camera.setDisplayOrientation(getPreviewDegree());
                resetParam();
            }
        }
        camera_tp_preview.removeView(preview);
        preview = new PaPaPreview(this, camera);
        preview.getHolder().setKeepScreenOn(true);
        camera_tp_preview = (FrameLayout) findViewById(R.id.camera_tp_preview);
        camera_tp_preview.addView(preview);
        camera.startPreview();
    }

    /**
     * 总的初始化方法。
     */
    private void init() {
        initData();
        initCamera();
        initUIBtn();
    }

    /**
     * 初始化数据。
     */
    private void initData() {
        sendIntent = new Intent("com.ac.alumnuscircle.supercamera.normalcamera.PaPaCrop");
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
    }

    /**
     * 初始化相机。
     */
    private void initCamera() {
        cameraID = getDefaultCameraID();
        if (checkCameraHardware(this)) {
            camera = getCameraInstance(cameraID);
            camera.setDisplayOrientation(getPreviewDegree());

            param = null;
            resetParam();

            camera.startPreview();
            preview = new PaPaPreview(this, camera);
            preview.getHolder().setKeepScreenOn(true);
            camera_tp_preview = (FrameLayout) findViewById(R.id.camera_tp_preview);
            camera_tp_preview.addView(preview);
            camera.startPreview();
        }
    }

    private void resetParam() {
        if (camera != null) {
            if (param == null) {
                param = camera.getParameters();
                /**
                 * 迭代求得可支持的预览分辨率。
                 * 如果不存在，则取支持的第一个分辨率。
                 */
                List<Camera.Size> supportedPreviewSizes = param.getSupportedPreviewSizes();
                Camera.Size previewSize = supportedPreviewSizes.get(0);
                for (Camera.Size s : supportedPreviewSizes) {
                    if (((float) s.width / (float) s.height) == ((float) screenHeight / (float) screenWidth)) {
                        previewSize = s;
                        break;
                    }
                }
                /**
                 * 迭代求得最大可支持的照片分辨率。
                 * 默认取支持的第一个分辨率。
                 */
                List<Camera.Size> supportedPictureSizes = param.getSupportedPictureSizes();
                Camera.Size pictureSize = supportedPictureSizes.get(0);
                for (Camera.Size s : supportedPictureSizes) {
                    if (s.width > pictureSize.width && s.height > pictureSize.height) {
                        pictureSize = s;
                    }
                }
                /**
                 * 设置相机参数。
                 */

                param.setPreviewSize(previewSize.width, previewSize.height);
                param.setPictureSize(pictureSize.width, pictureSize.height);
                param.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                param.set("rotation", getPreviewDegree());
                param.setJpegQuality(100);
                param.setPictureFormat(PixelFormat.JPEG);
                camera.setParameters(param);
            }
        }
    }

    /**
     * 初始化UI按钮。
     */
    protected void initUIBtn() {
        camera_tp_takeBtn = (ImageButton) findViewById(R.id.camera_tp_take_btn);
        camera_tp_beautifyBtn = (ImageButton) findViewById(R.id.camera_tp_beautify_btn);
        camera_tp_albumBtn = (ImageButton) findViewById(R.id.camera_tp_album_btn);
        camera_tp_backBtn = (ImageButton) findViewById(R.id.camera_tp_back_btn);
        camera_tp_flashBtn = (ImageButton) findViewById(R.id.camera_tp_flash_btn);
        camera_tp_changeBtn = (ImageButton) findViewById(R.id.camera_tp_change_btn);

        /**
         * 返回按钮的监听动作。
         */
        camera_tp_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaPaCamera.this.finish();
            }
        });

        /**
         * 初始化闪光灯按钮的选中属性。
         */
        if (param == null) {
            param = camera.getParameters();
        }
        List<String> flashModes = param.getSupportedFlashModes();
        if (flashModes == null) {
            camera_tp_flashBtn.setSelected(false);
        }
        String flashMode = param.getFlashMode();
        if (Camera.Parameters.FLASH_MODE_AUTO.equals(flashMode)) {
            camera_tp_flashBtn.setSelected(true);
        }
        if (Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
            camera_tp_flashBtn.setSelected(false);
        }
        /**
         * 切换闪光灯按钮的监听动作。
         */
        camera_tp_flashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 切换闪光灯，并设置相应的按钮选中属性。
                 */
                if (param == null) {
                    param = camera.getParameters();
                }
                List<String> flashModes = param.getSupportedFlashModes();
                if (flashModes == null) {
                    camera_tp_flashBtn.setSelected(false);
                    return;
                }
                String flashMode = param.getFlashMode();
                if (!Camera.Parameters.FLASH_MODE_AUTO.equals(flashMode)) {
                    if (flashModes.contains(Camera.Parameters.FLASH_MODE_AUTO)) {
                        camera_tp_flashBtn.setSelected(true);
                        param.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                        camera.setParameters(param);
                    }
                }
                if (!Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
                    if (flashModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                        camera_tp_flashBtn.setSelected(false);
                        param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(param);
                    }
                }
            }
        });

        /**
         * 初始化切换前后摄像头的按钮选中状态。
         */
        int camera_back_id = 0;
        int camera_front_id = 0;
        int currentID = cameraID;
        int numberOfCamera = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < numberOfCamera; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                camera_back_id = i;
            }
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                camera_front_id = i;
            }
        }
        if (currentID == camera_back_id) {
            camera_tp_changeBtn.setSelected(false);
        }
        if (currentID == camera_front_id) {
            camera_tp_changeBtn.setSelected(true);
        }
        /**
         * 切换前后摄像头按钮的监听动作。
         */
        camera_tp_changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 切换前后镜头，注意没有前镜头的情况，记得设置按钮选中属性。
                 */
                int camera_back_id = 0;
                int camera_front_id = 0;
                int currentID = cameraID;
                int numberOfCamera = Camera.getNumberOfCameras();
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                for (int i = 0; i < numberOfCamera; i++) {
                    Camera.getCameraInfo(i, cameraInfo);
                    if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                        camera_back_id = i;
                    }
                    if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                        camera_front_id = i;
                    }
                }
                if (currentID == camera_back_id) {
                    /**
                     * 释放当前摄像机，开启前置摄像机，设置选中。
                     */
                    cameraPause();
                    cameraID = camera_front_id;
                    cameraResume();
                    camera_tp_changeBtn.setSelected(true);
                }

                if (currentID == camera_front_id) {
                    /**
                     * 释放当前摄像机，开启后置摄像机，取消选中。
                     */
                    cameraPause();
                    cameraID = camera_back_id;
                    cameraResume();
                    camera_tp_changeBtn.setSelected(false);
                }
            }
        });

        /**
         * 相册按钮的监听动作。
         */
        camera_tp_albumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 切换到相册界面，选中图片后返回。
                 */
                GalleryFinal.openGallerySingle(PAPACAMERA_REQUEST_CODE_FOR_GALLERY, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        if (reqeustCode == PAPACAMERA_REQUEST_CODE_FOR_GALLERY) {
                            sendIntent.putExtra("absoluteImgPath", resultList.get(0).getPhotoPath());
                            handler.sendEmptyMessage(PAPACAMERA_SUCCESS_GET_IMG_FROM_ALBUM);
                        }
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {

                    }
                });
            }
        });

        /**
         * 拍照按钮的监听动作。
         */
        camera_tp_takeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 拍完照片之后，保存图片到SD卡，保存成功后，发出信息，进行跳转，跳转到照片裁剪和调整的页面。
                 * 详情请见第三个参数picture。
                 */
                /**
                 * 第一个参数：设置快门声，可以添加震动等。
                 * 第二个参数：设置照片反馈，可以以字节数组方式得到照片原始数据。
                 * 第三个参数：保存在SD卡的照片。
                 */
                camera.takePicture(new Camera.ShutterCallback() {
                    @Override
                    public void onShutter() {

                    }
                }, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {

                    }
                }, picture);
                camera_tp_takeBtn.setEnabled(false);
            }
        });

        /**
         * 美化图片滤镜按钮的监听动作。
         */
        camera_tp_beautifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 1、打开滤镜界面，选择一个滤镜，并且呈现到现有的相机中。
                 * 2、关闭此Activity，并且跳转到滤镜界面，选择滤镜后，再开启新的相机Activity。
                 */
            }
        });
    }

    /**
     * 检查硬件是否支持照相机
     *
     * @param context 上下文
     * @return 真则表示有，假则表示没有。
     */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取默认的相机ID，也就是获取到背后的摄像头ID。
     *
     * @return 返回背部摄像头ID。
     */
    private int getDefaultCameraID() {
        int defaultID = 0;
        int numberOfCamera = Camera.getNumberOfCameras();

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < numberOfCamera; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                defaultID = i;
            }
        }
        return defaultID;
    }

    /**
     * 获取相机实例，因为相机只有一个，所以用单例模式。
     *
     * @param cameraID
     * @return 相机实例
     */
    private Camera getCameraInstance(int cameraID) {
        Camera c = null;
        try {
            c = Camera.open(cameraID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    /**
     * 相机的回调方法。
     * 把获取到的照片存储到SD卡中。
     */
    private Camera.PictureCallback picture = new Camera.PictureCallback() {

        /**
         * 得到照片之后，保存到SD卡。
         * @param data
         * @param camera
         */
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    saveToSDCard(data);
                    handler.sendEmptyMessage(PAPACAMERA_SUCCESS_SAVE_TO_SDCARD);
                }
            }).start();
            camera.stopPreview();
            camera.startPreview();
        }
    };

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
         * 把路径信息传到裁剪界面。
         */
        sendIntent.putExtra("absoluteImgPath", jpgFile.getAbsolutePath());

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

    /**
     * 根据图片，动态识别，转换为竖直的方向。
     *
     * @return 旋转度数
     */
    private int getPreviewDegree() {
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degree = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degree = 90;
                break;
            case Surface.ROTATION_90:
                degree = 0;
                break;
            case Surface.ROTATION_180:
                degree = 270;
                break;
            case Surface.ROTATION_270:
                degree = 180;
                break;
        }
        return degree;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PAPACAMERA_REQUEST_CODE_FOR_CROP){
            setResult(resultCode, data);
            PaPaCamera.this.finish();
        }
    }

    //    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        if (v.getId() == R.id.camera_tp_preview) {
//            switch (event.getAction() & MotionEvent.ACTION_MASK) {
//                case MotionEvent.ACTION_DOWN:
//                    fingers = 1;
//                    break;
//                case MotionEvent.ACTION_UP:
//                    fingers = 0;
//                    break;
//                case MotionEvent.ACTION_POINTER_UP:
//                    fingers -= 1;
//                    break;
//                case MotionEvent.ACTION_POINTER_DOWN:
//                    oldDist = spacing(event);
//                    fingers += 1;
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    if (fingers >= 2) {
//                        newDist = spacing(event);
//                        if (newDist > oldDist + 1) {
//                            setCameraZoom(newDist / oldDist);
//                            oldDist = newDist;
//                        }
//                        if (newDist < oldDist - 1) {
//                            setCameraZoom(newDist / oldDist);
//                            oldDist = newDist;
//                        }
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//        return true;
//    }
//
//    private void setCameraZoom(double zoom) {
//        if (camera != null) {
//            param = camera.getParameters();
//            if (param.isZoomSupported()) {
//                param.setZoom((int)(param.getZoom() * zoom));
//                camera.setParameters(param);
//            }
//        }
//    }
//
//    private double spacing(MotionEvent event) {
//        double x = event.getX(0) - event.getX(1);
//        double y = event.getY(0) - event.getY(1);
//        return Math.sqrt(x * x + y * y);
//    }

}
