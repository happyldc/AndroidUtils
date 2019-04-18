package com.happyldc.androidutilssample;

import android.app.Application;
import android.widget.Toast;

import com.happyldc.util.AppUtil;
import com.happyldc.util.Utils;

public class App extends Application implements  Utils.OnAppStatusChangedListener {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        AppUtil.registerOnAppStatusChangedListener(this);
    }
    @Override
    public void onForeground() {
        Toast.makeText(this, "前台", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackground() {
        Toast.makeText(this, "进入后台", Toast.LENGTH_SHORT).show();
    }
}
