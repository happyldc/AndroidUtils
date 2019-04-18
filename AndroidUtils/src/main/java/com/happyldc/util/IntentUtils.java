package com.happyldc.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresPermission;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.Manifest.permission.CALL_PHONE;

/**
 * 跳转Activity 工具类
 *
 * @author ldc
 * @Created at 2019/4/11 14:12.
 */

public class IntentUtils {
    private IntentUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    /**
     * 根据包名跳转到系统自带的应用程序信息界面Intent
     *
     * @param packageName 包名
     * @param isNewTask
     * @return
     */
    public static Intent getAppDetailsSettingIntent(String packageName, boolean isNewTask) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        return getIntent(intent, isNewTask);
    }

//    /**
//     * 跳转开发人员选项界面Intent
//     *
//     * @param isNewTask
//     * @return
//     */
//    public static Intent getDevelopmentSettingIntent(boolean isNewTask) {
//        Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
//        return getIntent(intent, isNewTask);
//    }

    /**
     * @param phoneNumber
     * @return
     */
    @RequiresPermission(CALL_PHONE)
    public static Intent getCallPhoneIntent(final String phoneNumber) {
        return getCallPhoneIntent(phoneNumber, false);
    }

    @RequiresPermission(CALL_PHONE)
    public static Intent getCallPhoneIntent(final String phoneNumber, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        return getIntent(intent, isNewTask);
    }

    /**
     * @param phoneNumber
     * @param content
     * @return
     */
    public static Intent getSendSmsIntent(final String phoneNumber, final String content) {
        return getSendSmsIntent(phoneNumber, content, false);
    }

    /**
     * 获取发送短信Intent
     *
     * @param phoneNumber
     * @param content
     * @param isNewTask
     * @return
     */
    public static Intent getSendSmsIntent(final String phoneNumber,
                                          final String content,
                                          final boolean isNewTask) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", content);
        return getIntent(intent, isNewTask);
    }


    /***
     * 分享文本
     * @param content
     * @return
     */
    public static Intent getShareTextIntent(final String content) {
        return getShareTextIntent(content, false);
    }

    /***
     * 分享文本
     * @param content
     * @param isNewTask
     * @return
     */
    public static Intent getShareTextIntent(final String content, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return getIntent(intent, isNewTask);
    }

    /**
     * 分享图片Intent
     * @param content
     * @param imagePath
     * @return
     */
    public static Intent getShareImageIntent(final String content, final String imagePath) {
        return getShareImageIntent(content, imagePath, false);
    }

    /**
     * 分享图片Intent
     * @param content
     * @param imagePath
     * @param isNewTask
     * @return
     */
    public static Intent getShareImageIntent(final String content,
                                             final String imagePath,
                                             final boolean isNewTask) {
        if (imagePath == null || imagePath.length() == 0) return null;
        return getShareImageIntent(content, new File(imagePath), isNewTask);
    }

    /**
     * 分享图片Intent
     * @param content
     * @param image
     * @return
     */
    public static Intent getShareImageIntent(final String content, final File image) {
        return getShareImageIntent(content, image, false);
    }

    /**
     * 分享图片Intent
     * @param content
     * @param image
     * @param isNewTask
     * @return
     */
    public static Intent getShareImageIntent(final String content,
                                             final File image,
                                             final boolean isNewTask) {
        if (image == null || !image.isFile()) return null;
        return getShareImageIntent(content, file2Uri(image), isNewTask);
    }

    /**
     * 图片uri Intent
     * @param content
     * @param uri
     * @return
     */
    public static Intent getShareImageIntent(final String content, final Uri uri) {
        return getShareImageIntent(content, uri, false);
    }

    /**
     * 图片uri Intent
     * @param content
     * @param uri
     * @param isNewTask
     * @return
     */
    public static Intent getShareImageIntent(final String content,
                                             final Uri uri,
                                             final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        return getIntent(intent, isNewTask);
    }

    /**
     * 图片uri Intent
     * @param content
     * @param imagePaths
     * @return
     */
    public static Intent getShareImageIntent(final String content, final LinkedList<String> imagePaths) {
        return getShareImageIntent(content, imagePaths, false);
    }

    /**
     * 图片uri Intent
     * @param content
     * @param imagePaths
     * @param isNewTask
     * @return
     */
    public static Intent getShareImageIntent(final String content,
                                             final LinkedList<String> imagePaths,
                                             final boolean isNewTask) {
        if (imagePaths == null || imagePaths.isEmpty()) return null;
        List<File> files = new ArrayList<>();
        for (String imagePath : imagePaths) {
            files.add(new File(imagePath));
        }
        return getShareImageIntent(content, files, isNewTask);
    }

    /**
     * 图片uri Intent
     * @param content
     * @param images
     * @return
     */
    public static Intent getShareImageIntent(final String content, final List<File> images) {
        return getShareImageIntent(content, images, false);
    }

    /**
     * 图片uri Intent
     * @param content
     * @param images
     * @param isNewTask
     * @return
     */
    public static Intent getShareImageIntent(final String content,
                                             final List<File> images,
                                             final boolean isNewTask) {
        if (images == null || images.isEmpty()) return null;
        ArrayList<Uri> uris = new ArrayList<>();
        for (File image : images) {
            if (!image.isFile()) continue;
            uris.add(file2Uri(image));
        }
        return getShareImageIntent(content, uris, isNewTask);
    }

    /**
     * 图片uri Intent
     * @param content
     * @param uris
     * @return
     */
    public static Intent getShareImageIntent(final String content, final ArrayList<Uri> uris) {
        return getShareImageIntent(content, uris, false);
    }

    /**
     * 图片uri Intent
     * @param content
     * @param uris
     * @param isNewTask
     * @return
     */
    public static Intent getShareImageIntent(String content,
                                             ArrayList<Uri> uris,
                                             boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        intent.setType("image/*");
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取Intent
     *
     * @param intent
     * @param isNewTask true  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
     * @return
     */
    private static Intent getIntent(Intent intent, boolean isNewTask) {
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }

    private static Uri file2Uri(final File file) {
        if (file == null) return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authority = Utils.getApp().getPackageName() + ".provider";
            return FileProvider.getUriForFile(Utils.getApp(), authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }

}
