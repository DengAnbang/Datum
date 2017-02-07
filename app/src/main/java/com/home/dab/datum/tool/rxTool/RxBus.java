package com.home.dab.datum.tool.rxTool;

import android.support.annotation.NonNull;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by DAB on 2016/12/29 09:13.
 * 订阅完成可以使用CompositeDisposable或者使用四个参数的那个取消订阅
 */

public class RxBus {
    private HashMap<Object, Subject> maps;
    private static RxBus instance;

    private RxBus() {
        maps = new HashMap<>();
    }

    public static RxBus get() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    /**
     *  如果发生异常,就会自动取消订阅,可以使用retryWhen操作符进行重新订阅
     * @param tag
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> Observable<T> register(@NonNull Object tag, @NonNull Class<T> clazz) {
        Subject subject;
        if (maps.containsKey(tag)) {
            subject = maps.get(tag);

        } else {
            subject = PublishSubject.<T>create();
            maps.put(tag, subject);
        }
        return subject.ofType(clazz);
    }

    /**
     * 反注册
     *
     * @param tag
     */
    public void unregister(@NonNull Object tag) {
        if (maps.containsKey(tag)) {
            maps.remove(tag);
        } else {
            throw new RuntimeException("没有注册这个" + tag.toString() + "Subject");
        }
    }

    @SuppressWarnings("unchecked")
    public void post(@NonNull Object tag, @NonNull Object o) {
        if (maps.containsKey(tag)) {
            Subject subject = maps.get(tag);
            subject.onNext(o);
        } else {
            throw new RuntimeException("没有注册这个" + tag.toString() + "Subject");
        }
    }

}
