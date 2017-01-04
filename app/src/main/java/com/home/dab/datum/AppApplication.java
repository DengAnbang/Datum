package com.home.dab.datum;

import android.app.Application;

import com.home.dab.datum.tool.SPTool;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import static com.home.dab.datum.Constant.APP_KEY_QQ;
import static com.home.dab.datum.Constant.APP_KEY_SINA;
import static com.home.dab.datum.Constant.APP_KEY_UM;
import static com.home.dab.datum.Constant.APP_KEY_WX;
import static com.home.dab.datum.Constant.APP_SECRET_QQ;
import static com.home.dab.datum.Constant.APP_SECRET_SINA;
import static com.home.dab.datum.Constant.APP_SECRET_WX;

/**
 * Created by DAB on 2016/12/12 15:14.
 *
 */

public class AppApplication extends Application {

    private static AppApplication sMyApplication;

    public static AppApplication getMyApplication() {
        return sMyApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.init(this, APP_KEY_UM);
        UMShareAPI.get(this);//分享
        sMyApplication = this;
        SPTool.init(getApplicationContext());
        PlatformConfig.setWeixin(APP_KEY_WX, APP_SECRET_WX);
        PlatformConfig.setQQZone(APP_KEY_QQ, APP_SECRET_QQ);
        PlatformConfig.setSinaWeibo(APP_KEY_SINA, APP_SECRET_SINA);
        Config.REDIRECT_URL = "http://sns.whalecloud.com";
//        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/calback";
//        Config.DEBUG = true;
    }
}
