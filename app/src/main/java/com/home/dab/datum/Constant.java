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

    //Key
    public static final String APP_KEY_UM = "586b1c37f29d98595d00173c";

    public static final String APP_KEY_WX = "wxdd736775d8c546a2";
    public static final String APP_KEY_QQ = "1105925428";
    public static final String APP_KEY_SINA = "1054010392";
    public static final String APP_ANDROID_KEY_SINA = "39002eff7e33b02cb196c24299af9ce7";
    public static final String APP_SECRET_WX = "cb251fe019716641c7c70e8e40619957";
    public static final String APP_SECRET_QQ = "LgKkRzuPNpun4jBz";
    public static final String APP_SECRET_SINA = "653a9d68aaf37298d7a10559ded5d1bd";

}
