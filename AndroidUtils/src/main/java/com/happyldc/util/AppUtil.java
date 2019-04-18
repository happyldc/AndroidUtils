package com.happyldc.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.List;

/**
 * App帮助类
 *
 * @author ldc
 * @created at 2018/3/28 13:21
 */

public class AppUtil {
private  final static String TAG="AppUtil";

    private AppUtil() {
    }
    /***
     * 注册APP前后台状态监听
     * @param listener
     */
    public static void registerOnAppStatusChangedListener(Utils.OnAppStatusChangedListener listener) {
        Utils.getActivityLifecycleCallbacksImpl().addOnAppStatusChangedListener(listener);
    }

    /***
     * 移除APP前后台状态监听
     * @param listener
     */
    public static void unregisterOnAppStatusChangedListener(Utils.OnAppStatusChangedListener listener) {
        Utils.getActivityLifecycleCallbacksImpl().removeOnAppStatusChangedListener(listener);
    }
    /**
     * 获取当前应用程序的包名
     *
     * @param context 上下文对象
     * @return 返回包名
     */
    public static String getAppPackageName(Context context) {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return "";
    }

    /**
     * 通过包名+类名启动Activity
     *
     * @param context      上下文对象
     * @param packageName  包名
     * @param activityName activity完整类名 如 com.module.activity
     * @return true 调用成功 false 调用失败 可能是 activity 不存在
     */
    public static boolean startActivity(Context context, String packageName, String activityName) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packageName, activityName);
        intent.setComponent(cn);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取应用程序的图标
     *
     * @param packageName App包名
     * @return 应用的图标
     */
    public static Drawable getAppIcon(final String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取App的路径
     *
     * @param packageName 包名
     * @return App路径
     */
    public static String getAppPath(final String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取App版本名称
     *
     * @param packageName 包名
     * @return 版本名称
     */
    public static String getAppVersionName(final String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取App应用名称
     *
     * @param packageName 包名
     * @return 版本名称
     */
    public static String getAppName(final String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            int labelRes = pi.applicationInfo.labelRes;
            return Utils.getApp().getResources().getString(labelRes);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取App版本号
     *
     * @param packageName 包名
     * @return 版本号
     */
    public static int getAppVersionCode(final String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return -1;
        }
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * 获取App的签名
     *
     * @param packageName 包名
     * @return app签名数组
     */
    public static Signature[] getAppSignature(final String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAppLuncherActivityName(Context context) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(getAppPackageName(context), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return null;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = context.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            return className;
        }
        return null;
    }

    /**
     * 获取应用专属缓存目录
     * android 4.4及以上系统不需要申请SD卡读写权限
     * 因此也不用考虑6.0系统动态申请SD卡读写权限问题，切随应用被卸载后自动清空 不会污染用户存储空间
     *
     * @param context      上下文
     * @param childDirName 文件夹类型 可以为空，为空则返回API得到的一级目录
     * @return 缓存文件夹 如果没有SD卡或SD卡有问题则返回内存缓存目录，否则优先返回SD卡缓存目录
     */
    public static File getCacheDirectory(Context context, String childDirName) {
        File appCacheDir = getExternalCacheDirectory(context, childDirName);
        if (appCacheDir == null) {
            appCacheDir = getInternalCacheDirectory(context, childDirName);
        }

        if (appCacheDir == null) {
            Log.e(TAG,"getCacheDirectory fail ,the reason is mobile phone unknown exception !");
        } else {
            if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                Log.e(TAG,"getCacheDirectory fail ,the reason is make directory fail !");
            }
        }
        return appCacheDir;
    }

    /**
     * 获取内存缓存目录
     *
     * @param childDirName 子目录，可以为空，为空直接返回一级目录
     * @return 缓存目录文件夹 或 null（创建目录文件失败）
     * 注：该方法获取的目录是能供当前应用自己使用，外部应用没有读写权限，如 系统相机应用
     */
    public static File getInternalCacheDirectory(Context context, String childDirName) {
        File _appCacheDir = null;
        if (TextUtils.isEmpty(childDirName)) {
            _appCacheDir = context.getCacheDir();// /data/data/app_package_name/cache
        } else {
            _appCacheDir = new File(context.getFilesDir(), childDirName);// /data/data/app_package_name/files/childDirName
        }

        if (!_appCacheDir.exists() && !_appCacheDir.mkdirs()) {
            Log.e(TAG,"getInternalDirectory fail ,the reason is make directory fail !");
        }
        return _appCacheDir;
    }

    /**
     * 获取SD卡缓存目录
     *
     * @param context      上下文
     * @param childDirName 文件夹类型 如果为空则返回 /storage/emulated/0/Android/data/app_package_name/cache
     *                     否则返回对应类型的文件夹如Environment.DIRECTORY_PICTURES 对应的文件夹为 .../data/app_package_name/files/Pictures
     *                     {@link Environment#DIRECTORY_MUSIC},
     *                     {@link Environment#DIRECTORY_PODCASTS},
     *                     {@link Environment#DIRECTORY_RINGTONES},
     *                     {@link Environment#DIRECTORY_ALARMS},
     *                     {@link Environment#DIRECTORY_NOTIFICATIONS},
     *                     {@link Environment#DIRECTORY_PICTURES}, or
     *                     {@link Environment#DIRECTORY_MOVIES}.or 自定义文件夹名称
     * @return 缓存目录文件夹 或 null（无SD卡或SD卡挂载失败）
     */
    public static File getExternalCacheDirectory(Context context, String childDirName) {
        File _cacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (TextUtils.isEmpty(childDirName)) {
                _cacheDir = context.getExternalCacheDir();
            } else {
                _cacheDir = context.getExternalFilesDir(childDirName);
            }
            // 有些手机需要通过自定义目录
            if (_cacheDir == null) {
                _cacheDir = new File(Environment.getExternalStorageDirectory(), "Android/data/" + context.getPackageName() + "/cache/" + childDirName);
            }

            if (_cacheDir == null) {
                Log.e(TAG,"getExternalDirectory fail ,the reason is sdCard unknown exception !");
            } else {
                if (!_cacheDir.exists() && !_cacheDir.mkdirs()) {
                    Log.e(TAG,"getExternalDirectory fail ,the reason is make directory fail !");
                }
            }
        } else {
            Log.e(TAG,"getExternalDirectory fail ,the reason is sdCard nonexistence or sdCard mount fail !");
        }
        return _cacheDir;
    }



    /**
     * 安装应用
     *
     * @param context
     * @param softwarePath 安装包路径
     */
    public static void openSoftwareInstall(Context context, String softwarePath) {
        File file = new File(softwarePath);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Uri data;
            // 判断版本大于等于7.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setAction(Intent.ACTION_INSTALL_PACKAGE);
                //清单文件中配置的authorities
                data = FileProvider.getUriForFile(context, getFileProviderAuthority(context), file);
                // 给目标应用一个临时授权
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//重点！！
            } else {
                data = Uri.fromFile(file);
            }
            intent.setDataAndType(data, "application/vnd.android.package-archive");
            context.startActivity(intent);
        } else {
            Log.e("ruin", file.getName() + "文件不存在!");
        }

    }

    /**
     * 针对7.0以上获取默认的fileprovider authority字符串   ${applicationId}+".fileprovider"
     *
     * @param context
     * @return
     */
    public static String getFileProviderAuthority(Context context) {
        return getAppPackageName(context) + ".fileprovider";
    }

}
