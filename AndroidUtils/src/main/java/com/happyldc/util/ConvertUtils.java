package com.happyldc.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;


/**
 * Created by woodman on 2018/3/15.
 * 转换工具类
 */

public final class ConvertUtils {

    public static final int BYTE = 1;
    public static final int KB   = 1024;
    public static final int MB   = 1048576;
    public static final int GB   = 1073741824;

    private static final char hexDigits[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


    private ConvertUtils() {}



    /** bytes转换bits
     * @param bytes 转换的bytes
     * @return 转换后的bits
     */
    public static String bytes2Bits(final byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            for (int j = 7; j >= 0; --j) {
                sb.append(((aByte >> j) & 0x01) == 0 ? '0' : '1');
            }
        }
        return sb.toString();
    }


    /** bits转bytes
     * @param bits  转换的bits
     * @return  转换后的bytes
     */
    public static byte[] bits2Bytes(String bits) {
        int lenMod = bits.length() % 8;
        int byteLen = bits.length() / 8;
        // add "0" until length to 8 times
        if (lenMod != 0) {
            for (int i = lenMod; i < 8; i++) {
                bits = "0" + bits;
            }
            byteLen++;
        }
        byte[] bytes = new byte[byteLen];
        for (int i = 0; i < byteLen; ++i) {
            for (int j = 0; j < 8; ++j) {
                bytes[i] <<= 1;
                bytes[i] |= bits.charAt(i * 8 + j) - '0';
            }
        }
        return bytes;
    }



    /** 将字节数组转换成char数据
     * @param bytes 转换的字节数组
     * @return 转换后的char数据
     */
    public static char[] bytes2Chars(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) (bytes[i] & 0xff);
        }
        return chars;
    }



    /** 将char数组转换成字节数组
     * @param chars 转换的char数据
     * @return 转换后的字节数组
     */
    public static byte[] chars2Bytes(final char[] chars) {
        if (chars == null || chars.length <= 0) return null;
        int len = chars.length;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) (chars[i]);
        }
        return bytes;
    }


    /** 将字节数组转换成String
     * @param bytes 转换的字节数组
     * @return 转换后的String
     */
    public static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }


    /** 将字符串转换成字节数组
     * @param hexString 转换的字符串
     * @return  转换后的字节数组
     */
    public static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Int(hexBytes[i]) << 4 | hex2Int(hexBytes[i + 1]));
        }
        return ret;
    }

    /** 将char转换成int
     * @param hexChar 转换的char
     * @return
     */
    private static int hex2Int(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }





    /** 字节数转合适内存大小
     * @param byteSize 转换的字节
     * @return  内存大小
     */
    @SuppressLint("DefaultLocale")
    public static String byte2FitMemorySize(final long byteSize) {
        if (byteSize < 0) {
            return "shouldn't be less than zero!";
        } else if (byteSize < KB) {
            return String.format("%.3fB", (double) byteSize);
        } else if (byteSize < MB) {
            return String.format("%.3fKB", (double) byteSize / KB);
        } else if (byteSize < GB) {
            return String.format("%.3fMB", (double) byteSize / MB);
        } else {
            return String.format("%.3fGB", (double) byteSize / GB);
        }
    }




    /** 将OutputStream转换成ByteArrayInputStream
     * @param out 转换的OutputStream
     * @return 转换后的ByteArrayInputStream
     */
    public ByteArrayInputStream output2InputStream(final OutputStream out) {
        if (out == null) return null;
        return new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
    }


    /** 将InputStream对象转换成字节数组
     * @param is 转换的InputStream对象
     * @return  转换后的字节数组
     */
    public static byte[] inputStream2Bytes(final InputStream is) {
        if (is == null) return null;
        return input2OutputStream(is).toByteArray();
    }

    /**  将InputStream对象转换成为ByteArrayOutputStream
     * @param is 转换的InputStream
     * @return 转换后的ByteArrayOutputStream
     */
    public static ByteArrayOutputStream input2OutputStream(final InputStream is) {
        if (is == null) return null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[KB];
            int len;
            while ((len = is.read(b, 0, KB)) != -1) {
                os.write(b, 0, len);
            }
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /** 将字节数组转换成InputStream对象
     * @param bytes 转换的字节数组
     * @return 转换后的InputStream对象
     */
    public static InputStream bytes2InputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        return new ByteArrayInputStream(bytes);
    }


    /** 将OutputStream转换成字节数组
     * @param out   转换的OutputStream对象
     * @return 转换后的字节数组
     */
    public static byte[] outputStream2Bytes(final OutputStream out) {
        if (out == null) return null;
        return ((ByteArrayOutputStream) out).toByteArray();
    }


    /** 将字节数组转换成Stream对象
     * @param bytes 转换的字节数组
     * @return 转换后的OutputStream对象
     */
    public static OutputStream bytes2OutputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            os.write(bytes);
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    /** 将InputStream转换成String
     * @param is    转换的InputStream对象
     * @param charsetName  转换编码格式(如：utf-8)
     * @return 转换后的String对象
     */
    public static String inputStream2String(final InputStream is, final String charsetName) {
        if (is == null || isSpace(charsetName)) return null;
        try {
            return new String(inputStream2Bytes(is), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }


    /** 将String对象转换成InputStream
     * @param string 转换的String对象
     * @param charsetName   转换编码格式
     * @return  转换后的InputStream对象
     */
    public static InputStream string2InputStream(final String string, final String charsetName) {
        if (string == null || isSpace(charsetName)) return null;
        try {
            return new ByteArrayInputStream(string.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }


    /** 将OutputStream对象转换成String对象
     * @param out  转换的OutputStream对象
     * @param charsetName  转换编码格式
     * @return  转换后的String对象
     */
    public static String outputStream2String(final OutputStream out, final String charsetName) {
        if (out == null || isSpace(charsetName)) return null;
        try {
            return new String(outputStream2Bytes(out), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }


    /** 将String转换成OutputStream
     * @param string 转换的String对象
     * @param charsetName 转换的编码格式
     * @return 转换后的OutputStream对象
     */
    public static OutputStream string2OutputStream(final String string, final String charsetName) {
        if (string == null || isSpace(charsetName)) return null;
        try {
            return bytes2OutputStream(string.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }


    /** 将bitmap转换成字节数组
     * @param bitmap 转换的bitmap对象
     * @param format 转换后的字节数组
     * @return
     */
    public static byte[] bitmap2Bytes(final Bitmap bitmap, final Bitmap.CompressFormat format) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }


    /** 将字节数组转换成Bitmap
     * @param bytes 转换的字节数组
     * @return  转换后的bitmap
     */
    public static Bitmap bytes2Bitmap(final byte[] bytes) {
        return (bytes == null || bytes.length == 0)
                ? null
                : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 图片转字符串
     * @param bitmap 图片
     * @param quantity 0-100  100表示不压缩
     * @return
     */
    public static String bitmap2StringByBase64(Bitmap bitmap, int quantity) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quantity, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /** 将drawable转换成Bitmap对象
     * @param drawable  转换的drawable
     * @return  转换后的bitmap
     */
    public static Bitmap drawable2Bitmap(final Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    /** 将Bitmap转换Drawable对象
     * @param bitmap    转换的Bitmap
     * @return 转换后的Drawable
     */
    public static Drawable bitmap2Drawable(final Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(Utils.getApp().getResources(), bitmap);
    }


    /** 将Drawable对象转换成字节数组
     * @param drawable  转换的drawable对象
     * @param format    转换的格式
     * @return  转换后的字节数组
     */
    public static byte[] drawable2Bytes(final Drawable drawable,
                                        final Bitmap.CompressFormat format) {
        return drawable == null ? null : bitmap2Bytes(drawable2Bitmap(drawable), format);
    }


    /** 字节数组转换Drawable对象
     * @param bytes 字节数组
     * @return  Drawable对象
     */
    public static Drawable bytes2Drawable(final byte[] bytes) {
        return bytes == null ? null : bitmap2Drawable(bytes2Bitmap(bytes));
    }


    /** 将View转换为Bitmap
     * @param view  转换的View对象
     * @return  Bitmap对象
     */
    public static Bitmap view2Bitmap(final View view) {
        if (view == null) return null;
        Bitmap ret =
                Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }


    /** dp转px
     * @param dpValue   转换的dp值
     * @return  转换后的px值
     */
    public static int dp2px(final float dpValue) {
        final float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /** px转dp
     * @param pxValue 转换的px值
     * @return  转换后的dp值
     */
    public static int px2dp(final float pxValue) {
        final float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /** sp转px
     * @param spValue 转换的sp值
     * @return  转换后的px值
     */
    public static int sp2px(final float spValue) {
        final float fontScale = Utils.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /** px转sp
     * @param pxValue 转换的px值
     * @return  转换后的sp值
     */
    public static int px2sp(final float pxValue) {
        final float fontScale = Utils.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**判断是否为空
     * @param s  判断的对象
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
