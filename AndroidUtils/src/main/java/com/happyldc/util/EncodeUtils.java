package com.happyldc.util;

import android.os.Build;
import android.text.Html;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by woodman on 2018/3/14.
 * 编码工具类
 */

public class EncodeUtils {

    private EncodeUtils(){}



    /**URL编码（默认UTF-8）
     * @param encodeString  要编码的字符串
     * @return 编码后的字符串
     */
    public static String urlEncode(final String encodeString) {
        return urlEncode(encodeString, "UTF-8");
    }

    /**URL编码
     * @param encodeString  要编码的字符串
     * @param charsetName  编码名称
     * @return  编码后的字符串
     */
    public static String urlEncode(final String encodeString, final String charsetName) {
        try {
            return URLEncoder.encode(encodeString, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }


    /**URL解码
     * @param decodeString  需要解码的字符串
     * @return  解码后的字符串
     */
    public static String urlDecode(final String decodeString) {
        return urlDecode(decodeString, "UTF-8");
    }

    /**URL解码
     * @param decodeString 需要解码的字符串
     * @param charsetName 解码后的字符串
     * @return
     */
    public static String urlDecode(final String decodeString, final String charsetName) {
        try {
            return URLDecoder.decode(decodeString, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }


    /** base64编码
     * @param encodeString 需要编码的字符串
     * @return 编码后的字节数组
     */
    public static byte[] base64Encode(final String encodeString) {
        return base64Encode(encodeString.getBytes());
    }

    /** base64编码
     * @param encodeBytes  需要编码的字节数组
     * @return 编码后的字节数组
     */
    public static byte[] base64Encode(final byte[] encodeBytes) {
        return Base64.encode(encodeBytes, Base64.NO_WRAP);
    }

    /** base64编码转换字符串
     * @param encodeBytes base64字节数组
     * @return 编码后的字符串
     */
    public static String base64Encode2String(final byte[] encodeBytes) {
        return Base64.encodeToString(encodeBytes, Base64.NO_WRAP);
    }

    /** base64解码
     * @param encodeString  需要解码的字符串
     * @return  解码后的字节数组
     */
    public static byte[] base64Decode(final String encodeString) {
        return Base64.decode(encodeString, Base64.NO_WRAP);
    }

    /** base64解码
     * @param encodeByte 需要解码的字节数组
     * @return 解码后的字节数组
     */
    public static byte[] base64Decode(final byte[] encodeByte) {
        return Base64.decode(encodeByte, Base64.NO_WRAP);
    }

    /** Html编码
     * @param encodeString 需要编码的CharSequence
     * @return  解码后的字符串
     */
    public static String htmlEncode(final CharSequence encodeString) {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0, len = encodeString.length(); i < len; i++) {
            c = encodeString.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;"); //$NON-NLS-1$
                    break;
                case '>':
                    sb.append("&gt;"); //$NON-NLS-1$
                    break;
                case '&':
                    sb.append("&amp;"); //$NON-NLS-1$
                    break;
                case '\'':
                    //http://www.w3.org/TR/xhtml1
                    // The named character reference &apos; (the apostrophe, U+0027) was
                    // introduced in XML 1.0 but does not appear in HTML. Authors should
                    // therefore use &#39; instead of &apos; to work as expected in HTML 4
                    // user agents.
                    sb.append("&#39;"); //$NON-NLS-1$
                    break;
                case '"':
                    sb.append("&quot;"); //$NON-NLS-1$
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /** Html解码
     * @param decodeString 需要解码的字符串
     * @return 解码后的CharSequence
     */
    public static CharSequence htmlDecode(final String decodeString) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(decodeString, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(decodeString);
        }
    }

}
