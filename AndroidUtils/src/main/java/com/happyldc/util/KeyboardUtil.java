package com.happyldc.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * Created by woodman on 2018/3/12.
 * 软键盘工具类
 */

public class KeyboardUtil {

    private static int sContentViewInvisibleHeightPre;


    private KeyboardUtil() {}

    /**
     * 显示软键盘我
     *
     * @param activity 当前的activity
     */
    public static void showSoftInput(final Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 根据指定的view显示软键盘
     *
     * @param view 指定的view
     */
    public static void showSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }


    /**
     * 隐藏软键盘
     *
     * @param activity 当前的activity
     */
    public static void hideSoftInput(final Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 隐藏软键盘
     *
     * @param view 指定的view
     */
    public static void hideSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * 切换软键盘
     */
    public static void toggleSoftInput() {
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * 软键盘是否可见
     * @param activity 我当前activity
     * @return
     */
    public static boolean isSoftInputVisible(final Activity activity,
                                             final int minHeightOfSoftInput) {
        return getContentViewInvisibleHeight(activity) >= minHeightOfSoftInput;
    }


    /**
     * 获取内容视图可见的高度
     *
     * @param activity 当前的activity
     * @return 可见的高度
     */
    private static int getContentViewInvisibleHeight(final Activity activity) {
        final View contentView = activity.findViewById(android.R.id.content);
        Rect r = new Rect();
        contentView.getWindowVisibleDisplayFrame(r);
        return contentView.getRootView().getHeight() - r.height();
    }


    /**
     * 修复软键盘发生的泄漏  在Activity销毁的时候调用该方法
     *
     * @param context
     */
    public static void fixSoftInputLeaks(final Context context) {
        if (context == null) return;
        InputMethodManager imm =
                (InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        String[] strArr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        for (int i = 0; i < 3; i++) {
            try {
                Field declaredField = imm.getClass().getDeclaredField(strArr[i]);
                if (declaredField == null) continue;
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                Object obj = declaredField.get(imm);
                if (obj == null || !(obj instanceof View)) continue;
                View view = (View) obj;
                if (view.getContext() == context) {
                    declaredField.set(imm, null);
                } else {
                    return;
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }


    /**
     * 注册软键盘改变监听事件
     *
     * @param activity
     * @param listener
     */
    public static void registerSoftInputChangedListener(final Activity activity,
                                                        final OnSoftInputChangedListener listener) {
        if (activity == null) {
            return;
        }
        if (listener == null) {
            return;
        }
        final View contentView = activity.findViewById(android.R.id.content);
        sContentViewInvisibleHeightPre = getContentViewInvisibleHeight(activity);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (listener != null) {
                    int height = getContentViewInvisibleHeight(activity);
                    if (sContentViewInvisibleHeightPre != height) {
                        listener.onSoftInputChanged(height);
                        sContentViewInvisibleHeightPre = height;
                    }
                }
            }
        });
    }


    /**
     * 软键盘监听
     */
    public interface OnSoftInputChangedListener {
        void onSoftInputChanged(int height);
    }


}
