package com.home.dab.datum.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.Map;
import java.util.Objects;


/**
 * Created by DAB on 2016/12/20 14:49.
 */

public class SPTool {
    public static final String FILE_NAME = "share_data";

    private static Context sContext;
    private static Gson sGson;

    public static void init(Context context) {
        sContext = context;
    }

    private static Context getContext() {
        if (sContext == null) {
            throw new RuntimeException("没有初始化SPTool");
        }
        return sContext;
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {
        SharedPreferences sp = getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences sp = getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferences sp = getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        SharedPreferences sp = getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sp = getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 保存对象
     *
     * @param key
     * @param object
     */
    public static void saveObject(String key, Object object) {
        if (sGson == null) {
            sGson = new Gson();
        }

        String objectJson = sGson.toJson(object, object.getClass());
        //防止key是url的时候,太长了
        String md5Value = Utlis.getMD5Value(key);
        SharedPreferences sp = getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        if (md5Value == null) throw new RuntimeException("key为null!!");
        sp.edit().putString(md5Value, objectJson).apply();

    }

    /**
     * 获取对象
     *
     * @param key
     * @param aClass
     */
    public static <T> T getObject(@NonNull String key, Class<T> aClass) {
        if (sGson == null) {
            sGson = new Gson();
        }
        SharedPreferences sp = getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        //防止key是url的时候,太长了
        String md5Key = Utlis.getMD5Value(key);
        String string = sp.getString(md5Key, "");
        if (Objects.equals(string, ""))
            return null;
        return sGson.fromJson(string, aClass);

    }

    /**
     * 删除对象
     *
     * @param key
     */
    public static void removeObject(@NonNull String key) {
        String md5Key = Utlis.getMD5Value(key);
        SharedPreferences sp = getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        sp.edit().remove(md5Key).apply();
    }
}
