package com.happyldc.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ldc
 * @Created at 2019/4/11 14:13.
 */

/***
 * 引用库必须调用init()
 */
public class Utils {
    private static Application sApplication;
    private final static ActivityLifecycleCallbacksImpl ACTIVITY_LIFECYCLE_CALLBACKSIMPL = new ActivityLifecycleCallbacksImpl();

    private Utils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static void init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be empty");
        }
        sApplication = (Application) context.getApplicationContext();
        sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE_CALLBACKSIMPL);
    }

    public static ActivityLifecycleCallbacksImpl getActivityLifecycleCallbacksImpl() {
        return ACTIVITY_LIFECYCLE_CALLBACKSIMPL;
    }

    public static void addOnAppStatusChangedListener(OnAppStatusChangedListener listener) {
        ACTIVITY_LIFECYCLE_CALLBACKSIMPL.addOnAppStatusChangedListener(listener);
    }

    public static void removeOnAppStatusChangedListener(OnAppStatusChangedListener listener) {
        ACTIVITY_LIFECYCLE_CALLBACKSIMPL.removeOnAppStatusChangedListener(listener);
    }

    public static Application getApp() {
        return sApplication;
    }

    public static class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {
        LinkedList<Activity> mActivitys = new LinkedList<Activity>();
        //显示在前台的Activity的数量 >0时APP在前台显示
        private int mForegroundCountForActivity = 0;
        private List<OnAppStatusChangedListener> mOnAppStatusChangedListeners = new LinkedList<OnAppStatusChangedListener>();

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            mActivitys.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            mForegroundCountForActivity++;
            if (mForegroundCountForActivity == 1) {
                postAppStatus(true);
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            mForegroundCountForActivity--;
            if (mForegroundCountForActivity == 0) {
                postAppStatus(false);
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            mActivitys.remove(activity);
        }

        void postAppStatus(boolean isForeground) {
            for (OnAppStatusChangedListener listener : mOnAppStatusChangedListeners) {
                if (listener == null) {
                    return;
                }

                if (isForeground) {
                    listener.onForeground();
                } else {
                    listener.onBackground();
                }

            }
        }

        void addOnAppStatusChangedListener(OnAppStatusChangedListener listener) {
            if (mOnAppStatusChangedListeners.contains(listener)) {
                return;
            }
            mOnAppStatusChangedListeners.add(listener);
        }

        void removeOnAppStatusChangedListener(OnAppStatusChangedListener listener) {
            if (mOnAppStatusChangedListeners.contains(listener)) {
                mOnAppStatusChangedListeners.remove(listener);
            }

        }
    }

    /**
     * 获取已启动的Activity列表
     *
     * @return
     */
    public List<Activity> getActivityList() {
        return getActivityLifecycleCallbacksImpl().mActivitys;
    }

    /***
     * 获取顶部Activity
     * @return
     */
    public Activity getTopActivity() {
        List<Activity> list = getActivityLifecycleCallbacksImpl().mActivitys;
        if (list == null) {
            return null;
        }
        Activity activity = list.get(list.size()-1);
        return activity;
    }

    /***
     * APP状态改变监听
     */
    public interface OnAppStatusChangedListener {
        /**
         * 进入前台
         */
        void onForeground();

        /**
         * 进入后台
         */
        void onBackground();
    }

}
