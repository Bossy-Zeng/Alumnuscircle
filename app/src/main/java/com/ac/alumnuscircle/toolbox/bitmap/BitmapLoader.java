/**
 * @author Zhengfan
 * @date 16.08.28
 * @version 1
 * 功能：加载大位图的工具类，避免内存溢出和内存溢出的急救处理。
 */

package com.ac.alumnuscircle.toolbox.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class BitmapLoader {

    private static final Size ZERO_SIZE = new Size(0, 0);
    private static final BitmapFactory.Options OPTIONS_GET_SIZE = new BitmapFactory.Options();
    private static final BitmapFactory.Options OPTIONS_DECODE = new BitmapFactory.Options();
    private static final byte[] LOCKED = new byte[0];

    /**
     * 此list保证了最先回收最后使用的bitmap。
     */
    private static final LinkedList CACHE_ENTRIES = new LinkedList();

    /**
     * 线程请求创建图片的队列。
     */
    private static final Queue TASK_QUEUE = new LinkedList();

    /**
     * 保存队列中正在处理的图片的key,有效防止重复添加到请求创建队列。
     */
    private static final Set TASK_QUEUE_INDEX = new HashSet();

    /**
     * 保存图片缓存。
     */
    private static final Map IMG_CACHE_INDEX = new HashMap();

    /**
     * 缓冲区大小。
     */
    private static int CACHE_SIZE = 20;

    static {
        OPTIONS_GET_SIZE.inJustDecodeBounds = true;

        /**
         * 初始化创建图片的线程，并等待处理。
         */
        new Thread() {
            {
                setDaemon(true);
            }
            public void run() {
                while (true) {
                    synchronized (TASK_QUEUE) {
                        if (TASK_QUEUE.isEmpty()) {
                            try {
                                TASK_QUEUE.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    QueueEntry entry = (QueueEntry) TASK_QUEUE.poll();
                    String key = createKey(entry.path, entry.width, entry.height);
                    TASK_QUEUE_INDEX.remove(key);
                    createBitmap(entry.path, entry.width, entry.height);
                }
            }
        }.start();
    }


    /**
     * 外部接口，获取磁盘的位图。
     * @param path 路径
     * @param width 宽度
     * @param height 高度
     * @return 返回位图对象。
     */
    public static Bitmap getBitmap(String path, int width, int height) {
        if (path == null) {
            return null;
        }
        Bitmap bitmap = null;
        try {
            if (CACHE_ENTRIES.size() >= CACHE_SIZE) {
                destoryLast();
            }
            bitmap = useBitmap(path, width, height);
            if (bitmap != null && !bitmap.isRecycled()) {
                return bitmap;
            }
            bitmap = createBitmap(path, width, height);
            String key = createKey(path, width, height);
            synchronized (LOCKED) {
                IMG_CACHE_INDEX.put(key, bitmap);
                CACHE_ENTRIES.addFirst(key);
            }
        } catch (OutOfMemoryError err) {
            destoryLast();
            err.printStackTrace();
            return createBitmap(path, width, height);
        }
        return bitmap;
    }

    /**
     * 也是外部接口类，获取位图尺寸，可以和getBitmap配合使用，获取原大小位图。
     * @param path 位图路径
     * @return Size对象。
     */
    public static Size getBitmapSize(String path) {
        File file = new File(path);
        if (file.exists()) {
            InputStream in = null;
            try {
                in = new FileInputStream(file);
                BitmapFactory.decodeStream(in, null, OPTIONS_GET_SIZE);
                return new Size(OPTIONS_GET_SIZE.outWidth,
                        OPTIONS_GET_SIZE.outHeight);
            } catch (FileNotFoundException e) {
                return ZERO_SIZE;
            } finally {
                closeInputStream(in);
            }
        }
        return ZERO_SIZE;
    }


    /**
     * 私有的工具方法，将位图加入队列头部。
     * @param path 路径
     * @param width 宽度
     * @param height 高度
     * @return 返回位图对象。
     */
    private static Bitmap useBitmap(String path, int width, int height) {
        Bitmap bitmap = null;
        String key = createKey(path, width, height);
        synchronized (LOCKED) {
            bitmap = (Bitmap) IMG_CACHE_INDEX.get(key);
            if (bitmap != null) {
                if (CACHE_ENTRIES.remove(key)) {
                    CACHE_ENTRIES.addFirst(key);
                }
            }
        }
        return bitmap;
    }

    /**
     * 回收最后一张图片。
     */
    private static void destoryLast() {
        synchronized (LOCKED) {
            String key = (String) CACHE_ENTRIES.removeLast();
            if (key.length() > 0) {
                Bitmap bitmap = (Bitmap) IMG_CACHE_INDEX.remove(key);
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                    bitmap = null;
                }
            }
        }
    }

    /**
     * 创建键。
     * @param path 路径
     * @param width 宽度
     * @param height 高度
     * @return 返回键的字符串。
     */
    private static String createKey(String path, int width, int height) {
        if (null == path || path.length() == 0) {
            return "";
        }
        return path + "_" + width + "_" + height;
    }

    /**
     * 创建位图对象。
     * @param path 路径
     * @param width 宽度
     * @param height 高度
     * @return 位图对象。
     */
    private static Bitmap createBitmap(String path, int width, int height) {
        File file = new File(path);
        if (file.exists()) {
            InputStream in = null;
            try {
                in = new FileInputStream(file);
                Size size = getBitmapSize(path);
                if (size.equals(ZERO_SIZE)) {
                    return null;
                }
                int scale = 1;
                int a = size.getWidth() / width;
                int b = size.getHeight() / height;
                scale = Math.max(a, b);
                synchronized (OPTIONS_DECODE) {
                    OPTIONS_DECODE.inSampleSize = scale;
                    Bitmap bitmap = BitmapFactory.decodeStream(in, null,
                            OPTIONS_DECODE);
                    return bitmap;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                closeInputStream(in);
            }
        }
        return null;
    }

    /**
     * 关闭输入流。
     * @param in 输入流对象
     */
    private static void closeInputStream(InputStream in) {
        if (null != in) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 图片大小。
     */
    static class Size {
        private int width, height;

        Size(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    /**
     * 队列缓存参数对象。
     */
    static class QueueEntry {
        public String path;
        public int width;
        public int height;
    }
}
