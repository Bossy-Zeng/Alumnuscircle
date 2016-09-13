/**
 * @author 吴正凡
 * @date 16.08.28
 * @version 1
 * 功能：封装了对于本机的一些配置信息，如存储路径等。
 */

package com.ac.alumnuscircle.cstt;

import android.os.Environment;

import java.io.File;

public class Config {

    public static final String ALBUM_PATH = Environment.getExternalStorageDirectory() + File.separator
            + "AlumnusCircle" + File.separator + "Album" + File.separator;

}
