package com.happyldc.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.Map;
import java.util.Set;


/**
 * Created by woodman on 2018/3/15.
 *  sharedpreferences工具类
 */

@SuppressLint("ApplySharedPref")
public final class SharePreferUtils {


    private SharedPreferences sp;
    private String spName="default";

    public SharePreferUtils(){}
    public static  SharePreferUtils getInstnce(){

        return SingletonHolder.singleton;
    }
    public static class SingletonHolder{
        private static final SharePreferUtils singleton = new  SharePreferUtils();
    }




    /**初始化 设置sp XML文件名
     * @param spName
     */
    public void init(String spName){
        this.spName = spName;

        sp = Utils.getApp().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }


    /** 设置字符串
     * @param key  键名
     * @param value 设置的值
     */
    public void put(@NonNull final String key, @NonNull final String value) {
        put(key, value, false);
    }


    /** 设置字符串
     * @param key 键名
     * @param value 设置的值
     * @param isCommit 是否马上保存
     */
    public void put(@NonNull final String key,
                    @NonNull final String value,
                    final boolean isCommit) {
        if (isCommit) {
            sp.edit().putString(key, value).commit();
        } else {
            sp.edit().putString(key, value).apply();
        }
    }


    /** 获取字符串
     * @param key 字符串对应的键名
     * @return 存储的字符串
     */
    public String getString(@NonNull final String key) {
        return getString(key, "");
    }


    /** 获取字符串
     * @param key 字符串对应的键名
     * @param defaultValue  默认的值
     * @return 存储的字符串
     */
    public String getString(@NonNull final String key, @NonNull final String defaultValue) {
        return sp.getString(key, defaultValue);
    }


    /** 设置int型
     * @param key 键名
     * @param value 存储的值
     */
    public void put(@NonNull final String key, final int value) {
        put(key, value, false);
    }


    /** 设置int型
     * @param key 键名
     * @param value 存储的值
     * @param isCommit 是否马上保存
     */
    public void put(@NonNull final String key, final int value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putInt(key, value).commit();
        } else {
            sp.edit().putInt(key, value).apply();
        }
    }


    /** 获取int型
     * @param key int对应的键名
     * @return 存储的int值
     */
    public int getInt(@NonNull final String key) {
        return getInt(key, -1);
    }


    /** 获取int型
     * @param key int对应的键名
     * @param defaultValue 默认的值
     * @return  存储的int值
     */
    public int getInt(@NonNull final String key, final int defaultValue) {
        return sp.getInt(key, defaultValue);
    }


    /** 设置long型
     * @param key 键名
     * @param value 设置的值
     */
    public void put(@NonNull final String key, final long value) {
        put(key, value, false);
    }


    /** 设置long型
     * @param key 键名
     * @param value 设置的值
     * @param isCommit 是否马上保存
     */
    public void put(@NonNull final String key, final long value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putLong(key, value).commit();
        } else {
            sp.edit().putLong(key, value).apply();
        }
    }


    /** 获取long值
     * @param key long对应的键名
     * @return 存储的long值
     */
    public long getLong(@NonNull final String key) {
        return getLong(key, -1L);
    }


    /** 获取long值
     * @param key long对应的键名
     * @param defaultValue 默认的值
     * @return   存储的long值
     */
    public long getLong(@NonNull final String key, final long defaultValue) {
        return sp.getLong(key, defaultValue);
    }


    /** 设置float值
     * @param key  键名
     * @param value 设置的值
     */
    public void put(@NonNull final String key, final float value) {
        put(key, value, false);
    }


    /** 设置float值
     * @param key 键名
     * @param value 设置的值
     * @param isCommit 是否马上保存
     */
    public void put(@NonNull final String key, final float value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putFloat(key, value).commit();
        } else {
            sp.edit().putFloat(key, value).apply();
        }
    }


    /** 获取float值
     * @param key float对应的键
     * @return 存储的float
     */
    public float getFloat(@NonNull final String key) {
        return getFloat(key, -1f);
    }


    /** 获取float值
     * @param key float对应的键
     * @param defaultValue 默认的值
     * @return 存储的float
     */
    public float getFloat(@NonNull final String key, final float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }


    /** 设置boolean
     * @param key 键名
     * @param value 设置的值
     */
    public void put(@NonNull final String key, final boolean value) {
        put(key, value, false);
    }


    /** 设置boolean
     * @param key 键名
     * @param value 设置的值
     * @param isCommit 是否马上保存
     */
    public void put(@NonNull final String key, final boolean value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putBoolean(key, value).commit();
        } else {
            sp.edit().putBoolean(key, value).apply();
        }
    }


    /** 获取Boolean
     * @param key 对应的键名
     * @return 存储的Boolea值
     */
    public boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, false);
    }


    /** 获取Boolean
     * @param key 对应的键名
     * @param defaultValue 默认值
     * @return 存储的Boolea值
     */
    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }


    /**
     * @param key
     * @param value
     */
    public void put(@NonNull final String key, @NonNull final Set<String> value) {
        put(key, value, false);
    }


    /**
     * @param key
     * @param value
     * @param isCommit
     */
    public void put(@NonNull final String key,
                    @NonNull final Set<String> value,
                    final boolean isCommit) {
        if (isCommit) {
            sp.edit().putStringSet(key, value).commit();
        } else {
            sp.edit().putStringSet(key, value).apply();
        }
    }


    /**
     * @param key
     * @return
     */
    public Set<String> getStringSet(@NonNull final String key) {
        return getStringSet(key, Collections.<String>emptySet());
    }


    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public Set<String> getStringSet(@NonNull final String key,
                                    @NonNull final Set<String> defaultValue) {
        return sp.getStringSet(key, defaultValue);
    }


    /** 获取所有的key
     * @return
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }


    /** 判断key是否存在
     * @param key 判断的key
     * @return true==yes
     */
    public boolean contains(@NonNull final String key) {
        return sp.contains(key);
    }


    /** 移除对应的键
     * @param key 键名
     */
    public void remove(@NonNull final String key) {
        remove(key, false);
    }


    /** 移除对应的键
     * @param key 键名
     * @param isCommit 是否马上移除
     */
    public void remove(@NonNull final String key, final boolean isCommit) {
        if (isCommit) {
            sp.edit().remove(key).commit();
        } else {
            sp.edit().remove(key).apply();
        }
    }


    /**
     * 清除配置文件
     */
    public void clear() {
        clear(false);
    }


    /** 清除配置文件
     * @param isCommit 是否马上清除
     */
    public void clear(final boolean isCommit) {
        if (isCommit) {
            sp.edit().clear().commit();
        } else {
            sp.edit().clear().apply();
        }
    }

    /** 是否为空
     * @param s 判断的字符串对象
     * @return true==yes
     */
    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
