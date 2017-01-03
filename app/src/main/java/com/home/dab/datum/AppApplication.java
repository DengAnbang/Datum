package com.home.dab.datum;

import android.app.Application;

import com.home.dab.datum.tool.SPTool;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by DAB on 2016/12/12 15:14.
 *
 */

public class AppApplication extends Application {
    private static final String APPKEY_UM = "586b1c37f29d98595d00173c";
    private static AppApplication sMyApplication;

    public static AppApplication getMyApplication() {
        return sMyApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.init(this, APPKEY_UM);
        UMShareAPI.get(this);//分享
        sMyApplication = this;
        SPTool.init(getApplicationContext());
    }
}
