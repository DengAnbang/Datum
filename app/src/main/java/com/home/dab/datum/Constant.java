package com.home.dab.datum;

import android.os.Environment;

import java.io.File;

/**
 * Created by DAB on 2016/12/7 09:23.
 */

public class Constant {
    public static final String baseUrl = "http://hengdawb-app.oss-cn-hangzhou.aliyuncs.com/";
    public static final String baseEncryptUrl = "http://www.wifiyun.com/";
    public static final String DOWNLOAD_URL = "http://hengdawb-app.oss-cn-hangzhou.aliyuncs.com/app-debug.apk";
    public static final String fileName = "app-debug.apk";
//    public static final String fileStoreDir = Environment.getRootDirectory().getPath() + File.separator + "app";
    public static final String fileStoreDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "datum";
}
