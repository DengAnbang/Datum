package com.home.dab.datum;

import android.app.Application;

import com.home.dab.datum.tool.SPTool;

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
        sMyApplication = this;
        SPTool.init(getApplicationContext());
    }
}
