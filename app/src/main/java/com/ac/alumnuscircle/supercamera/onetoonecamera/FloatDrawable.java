/**
 * @author 吴正凡
 * @date 16.07.20
 * 实现了裁剪界面中上层浮动的图层效果。
 */

package com.ac.alumnuscircle.supercamera.onetoonecamera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class FloatDrawable extends Drawable {

    private Context mContext;
    private int offset = 50;
    private Paint mLinePaint = new Paint();
    private Paint mLinePaint2 = new Paint();
    {
        mLinePaint.setARGB(200, 50, 50, 50);
        mLinePaint.setStrokeWidth(1F);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(Color.WHITE);

        mLinePaint2.setARGB(200, 50, 50, 50);
        mLinePaint2.setStrokeWidth(7F);
        mLinePaint2.setStyle(Paint.Style.STROKE);
        mLinePaint2.setAntiAlias(true);
        mLinePaint2.setColor(Color.WHITE);
    }

    public FloatDrawable(Context context) {
        super();
        this.mContext = context;

    }

    public int getBorderWidth() {
        /**
         * 根据dip计算像素值，进行适配工作。
         */
        return dipTopx(mContext, offset);
    }

    public int getBorderHeight() {
        /**
         * 根据dip计算像素，适配。
         */
        return dipTopx(mContext, offset);
    }

    /**
     * 在画布上进行绘制。
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {

        int left = getBounds().left;
        int top = getBounds().top;
        int right = getBounds().right;
        int bottom = getBounds().bottom;

        Rect mRect = new Rect(left + dipTopx(mContext, offset) / 2, top
                + dipTopx(mContext, offset) / 2, right
                - dipTopx(mContext, offset) / 2, bottom
                - dipTopx(mContext, offset) / 2);
        /**
         * 绘制一个默认的选框。
         */
        canvas.drawRect(mRect, mLinePaint);

        /**
         * 绘制边缘的四个角和白线。
         */
        canvas.drawLine((left + dipTopx(mContext, offset) / 2 - 3.5f), top
                        + dipTopx(mContext, offset) / 2,
                left + dipTopx(mContext, offset) - 8f,
                top + dipTopx(mContext, offset) / 2, mLinePaint2);
        canvas.drawLine(left + dipTopx(mContext, offset) / 2,
                top + dipTopx(mContext, offset) / 2,
                left + dipTopx(mContext, offset) / 2,
                top + dipTopx(mContext, offset) / 2 + 30, mLinePaint2);
        canvas.drawLine(right - dipTopx(mContext, offset) + 8f,
                top + dipTopx(mContext, offset) / 2,
                right - dipTopx(mContext, offset) / 2,
                top + dipTopx(mContext, offset) / 2, mLinePaint2);
        canvas.drawLine(right - dipTopx(mContext, offset) / 2,
                top + dipTopx(mContext, offset) / 2 - 3.5f,
                right - dipTopx(mContext, offset) / 2,
                top + dipTopx(mContext, offset) / 2 + 30, mLinePaint2);
        canvas.drawLine((left + dipTopx(mContext, offset) / 2 - 3.5f), bottom
                        - dipTopx(mContext, offset) / 2,
                left + dipTopx(mContext, offset) - 8f,
                bottom - dipTopx(mContext, offset) / 2, mLinePaint2);
        canvas.drawLine((left + dipTopx(mContext, offset) / 2), bottom
                        - dipTopx(mContext, offset) / 2,
                (left + dipTopx(mContext, offset) / 2),
                bottom - dipTopx(mContext, offset) / 2 - 30f, mLinePaint2);
        canvas.drawLine((right - dipTopx(mContext, offset) + 8f), bottom
                        - dipTopx(mContext, offset) / 2,
                right - dipTopx(mContext, offset) / 2,
                bottom - dipTopx(mContext, offset) / 2, mLinePaint2);
        canvas.drawLine((right - dipTopx(mContext, offset) / 2), bottom
                        - dipTopx(mContext, offset) / 2 - 30f,
                right - dipTopx(mContext, offset) / 2,
                bottom - dipTopx(mContext, offset) / 2 + 3.5f, mLinePaint2);
    }

    /**
     * 设置边界
     * @param bounds
     */
    @Override
    public void setBounds(Rect bounds) {
        super.setBounds(new Rect(bounds.left - dipTopx(mContext, offset) / 2,
                bounds.top - dipTopx(mContext, offset) / 2, bounds.right
                + dipTopx(mContext, offset) / 2, bounds.bottom
                + dipTopx(mContext, offset) / 2));
    }

    /**
     * 设置不透明度。
     * @param alpha
     */
    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    public int dipTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
