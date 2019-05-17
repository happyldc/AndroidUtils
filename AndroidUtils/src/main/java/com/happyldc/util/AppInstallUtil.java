package com.happyldc.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;
/****
 QQ com.tencent.mobileqq
 微信 com.tencent.mm
 QQ音乐 com.tencent.qqmusic
 微信读书 com.tencent.weread
 QQ阅读 com.qq.reader
 唱吧 com.changba
 网易云音乐 com.netease.cloudmusic
 钉钉 com.alibaba.android.rimet
 抖音 com.ss.android.ugc.aweme
 美团外卖 com.sankuai.meituan.takeoutnew
 饿了么 me.ele
 摩拜单车 com.mobike.mobikeapp
 OFO so.ofo.labofo
 今日头条 com.ss.android.article.news
 新浪微博 com.sina.weibo
 网易新闻 com.netease.newsreader.activity
 快手 com.smile.gifmaker
 知乎 com.zhihu.android
 虎牙直播 com.duowan.kiwi
 映客直播 com.meelive.ingkee
 秒拍 com.yixia.videoeditor
 美图秀秀 com.mt.mtxx.mtxx
 美颜相机 com.meitu.meiyancamera
 携程 ctrip.android.view
 陌陌 com.immomo.momo
 优酷 com.youku.phone
 爱奇艺 com.qiyi.video
 滴滴出行 com.sdu.didi.psnger
 支付宝 com.eg.android.AlipayGphone
 淘宝 com.taobao.taobao
 京东 com.jingdong.app.mall
 大众点评 com.dianping.v1
 搜狗输入法 com.sohu.inputmethod.sogou
 百度地图 com.baidu.BaiduMap
 高德地图 com.autonavi.minimap
 简书 com.jianshu.haruki
 喜马拉雅 com.ximalaya.ting.android

 */
public class AppInstallUtil {
    private static List<PackageInfo> cachedList;
    private static long lastDetectTime = 0;

    /**
     * 判断是否安装了微信
     */
    public static boolean isWeixinAvailable(Context context) {
        return isPackageAvailable(context, "com.tencent.mm");
    }

    /**
     * 判断qq是否可用
     */
    public static boolean isQQClientAvailable(Context context) {
        return isPackageAvailable(context, "com.tencent.mobileqq");
    }

    /**
     * 判断是否安装了微博
     */
    public static boolean isWeiboAvailable(Context context) {
        return isPackageAvailable(context, "com.sina.weibo");
    }

    /**
     * 判断是否安装了钉钉
     */
    public static boolean isDingDingAvailable(Context context) {
        return isPackageAvailable(context, "com.alibaba.android.rimet");
    }
    public static boolean isTaobaoAvailable(Context context) {
        return isPackageAvailable(context, "com.taobao.taobao");
    }


    public static boolean isPackageAvailable(Context context, String pkgName) {
        List<PackageInfo> pinfo = getCachedList(context);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName.toLowerCase();
                if (pn.equals(pkgName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<PackageInfo> getCachedList(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        if (cachedList == null || cachedList.size() == 0 || System.currentTimeMillis() - lastDetectTime > 60000 * 5) {
            cachedList = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
            lastDetectTime = System.currentTimeMillis();
        }
        return cachedList;
    }
}
