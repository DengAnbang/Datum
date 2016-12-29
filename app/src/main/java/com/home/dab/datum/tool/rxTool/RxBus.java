package com.home.dab.datum.tool.rxTool;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by DAB on 2016/12/29 09:13.
 */

public class RxBus<T> {
    //所有事件的CODE
    public static final int TAP = 1; //点击事件
    public static final int OTHER = 21; //其它事件

    //枚举
    @IntDef({TAP, OTHER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EventCode {}


    public @RxBus.EventCode int code;
    public T content;

    public static <O> RxBus<O> setContent(O t) {
        RxBus<O> events = new RxBus<>();
        events.content = t;
        return events;
    }

    public <T> T getContent() {
        return (T) content;
    }
}
