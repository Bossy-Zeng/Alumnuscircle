/**
 * @author zhengfanw
 * @date 16.07.19
 * 功能：提供相机（PaPaCamera）的预览界面
 */

package com.ac.alumnuscircle.supercamera.onetoonecamera;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PaPaPreview extends SurfaceView implements SurfaceHolder.Callback {

    private Camera camera;
    private SurfaceHolder surfaceHolder;

    public PaPaPreview(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public PaPaPreview(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try{
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        if (surfaceHolder.getSurface() == null){
            return ;
        }
        try{
            camera.stopPreview();
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
