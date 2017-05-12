package com.ecxppsdk.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.ecxppsdk.data.Constant;
import com.ecxppsdk.data.URLs;

import java.util.Locale;

import static com.ecxppsdk.utils.ConversionUtils.hexString2Bytes;
import static com.ecxppsdk.utils.ConversionUtils.hexString2BytesNew;


/**
 * Author: VincenT
 * Date: 2017/4/24 16:38
 * Contact:qq 328551489
 * Purpose:设备参数获取工具
 */
public class DeviceUtils {
    private static int WIDTH;
    private static int HEIGHT;
    private static float DENSITY;

    /**
     * 获取IP地址
     *
     * @param context
     * @return
     */
    public static String getIP(Context context) {
        String ip;
        if (SharedPreUtils.getBoolean(context, Constant.SPK_IS_LOCAL, false)) {
            ip = SharedPreUtils.getString(context, Constant.SPK_LOCAL, URLs.LAN_URL);
        } else {
            ip = SharedPreUtils.getString(context, Constant.SPK_CLOUD, URLs.YUN_URL);
        }
        return ip;
    }

    /**
     * 获取网关mac码
     *
     * @param context
     * @return
     */
    public static byte[] getDstMac(Context context) {
        String gateway = SharedPreUtils.getString(context, Constant.SPK_GATEWAY, "123456123456");
        return hexString2Bytes(gateway);
    }

    /**
     * 获取手机设备Mac地址，转为16进制
     *
     * @param context
     * @return
     */
    public static byte[] getSrcMac(Context context) {
        return hexString2BytesNew(getSrcMacStr(context));
    }

    /**
     * 获取手机设备Mac地址字符串
     *
     * @param context
     * @return
     */
    private static String getSrcMacStr(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String macAddress = info.getMacAddress();
        return macAddress;
    }

    /**
     * 获取被连接网络Mac地址字符串
     *
     * @param context
     * @return
     */
    public static String getDstMacStr(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String dstMacStr = info.getBSSID();
        return dstMacStr;
    }

    /**
     * 获取网关密码
     *
     * @param context
     * @return
     */
    public static byte[] getPassword(Context context) {
        String pwd = SharedPreUtils.getString(context, Constant.GATEWAY_PSW, "00000000");
        return pwd.getBytes();
    }

    /**
     * @param context
     * @return
     */
    public static int getWidth(Context context) {
        if (WIDTH == 0) {
            WIDTH = context.getResources().getDisplayMetrics().widthPixels;
        }
        return WIDTH;
    }

    /**
     * @param context
     * @return
     */
    public static int getHeight(Context context) {
        if (HEIGHT == 0) {
            HEIGHT = context.getResources().getDisplayMetrics().heightPixels;
        }
        return HEIGHT;
    }

    /**
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        if (DENSITY == 0) {
            DENSITY = context.getResources().getDisplayMetrics().density;
        }
        return DENSITY;
    }

    /**
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        return (int) (dpValue * getDensity(context) + 0.5f);
    }

    /**
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        return (int) (pxValue / getDensity(context) + 0.5f);
    }

    /**
     * @param context
     * @param spValue
     * @return
     */
    public static float sp2px(Context context, float spValue) {
        return spValue * getDensity(context) * 10.0f / 20.0f;
    }

    /**
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() !=
                PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;

    }

    /**
     * @param drawable
     * @param w
     * @param h
     * @return
     */
    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        // drawable转换成bitmap
        Bitmap oldbmp = drawableToBitmap(drawable);
        // 创建操作图片用的Matrix对象
        Matrix matrix = new Matrix();
        // 计算缩放比例
        float sx = ((float) w / width);
        float sy = ((float) h / height);
        // 设置缩放比例
        matrix.postScale(sx, sy);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(newbmp);
    }

    /**
     * @param drawable
     * @param scale_x
     * @param scale_y
     * @return
     */
    public static Drawable zoomDrawableByScale(Drawable drawable, float scale_x, float scale_y) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        matrix.postScale(scale_x, scale_y);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(newbmp);
    }

    /**
     * 获取当前应用的版本名："1.0"
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前应用的版本号：1
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = null;
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }



    /**
     * 获取应用当前语言
     *
     * @param context
     * @return
     */
    public static Locale getLanguage(Context context) {
        try {
            Resources resources = context.getResources();
            Configuration config = resources.getConfiguration();
            if (Build.VERSION.SDK_INT < 24) {
                return config.locale;
            } else {
                return config.getLocales().get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Locale.SIMPLIFIED_CHINESE;
        }
    }

    /**
     * 切换语言
     *
     * @param context
     * @param locale
     * @return
     */
    public static void switchLanguage(Context context, Locale locale) {
        try {
            Resources resources = context.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration config = resources.getConfiguration();
            if (Build.VERSION.SDK_INT < 17) {
                config.locale = locale;
                resources.updateConfiguration(config, dm);
            } else {
                config.setLocale(locale);
                context.createConfigurationContext(config);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据String获取地区
     * @param strLocale
     * @return
     */
    public static Locale getLocale(String strLocale){
        if(strLocale.equals(Enums.LanguageType.ZH_CN.getLocale().toString())) {
            return Enums.LanguageType.ZH_CN.getLocale();
        }else if(strLocale.equals(Enums.LanguageType.ZH_TW.getLocale().toString())){
            return Enums.LanguageType.ZH_TW.getLocale();
        }else if(strLocale.equals(Enums.LanguageType.EN_US.getLocale().toString())){
            return Enums.LanguageType.EN_US.getLocale();
        }else if(strLocale.equals(Enums.LanguageType.JA_JP.getLocale().toString())){
            return Enums.LanguageType.JA_JP.getLocale();
        }else if(strLocale.equals(Enums.LanguageType.ES_ES.getLocale().toString())){
            return Enums.LanguageType.ES_ES.getLocale();
        }else {
            return Enums.LanguageType.ZH_CN.getLocale();
        }
    }

    /**
     * 重启界面
     *
     * @param context
     * @param cls
     * @return
     */
    public static void rebootActivity(Context context, Class<?> cls) {
        try {
            Intent intent = new Intent(context, cls);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取屏幕三分之一高度
     *
     * @param context
     * @return
     */
    public static int getOneThirdsWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mWidth = dm.widthPixels;
        return mWidth / 3;
    }

    /**
     * 获取屏幕三分之二宽度
     *
     * @param context
     * @return
     */
    public static int getTwoThirdsWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mWidth = dm.widthPixels;
        return mWidth * 2 / 3;
    }

    /**
     * 获取屏幕五分之四宽度
     *
     * @param context
     * @return
     */
    public static int getFourFifthsWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mWidth = dm.widthPixels;
        return mWidth * 4 / 5;
    }

    /**
     * 获取屏幕五分之四高度
     *
     * @param context
     * @return
     */
    public static int getFourFifthsHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mHeight = dm.heightPixels;
        return mHeight * 4 / 5;
    }

    /**
     * 获取屏幕三分之二高度
     *
     * @param context
     * @return
     */
    public static int getTwoThirdsHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mHeight = dm.heightPixels;
        return mHeight * 2 / 3;
    }

    /**
     * 获取屏幕三分之一高度
     *
     * @param context
     * @return
     */
    public static int getOneThirdsHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mHeight = dm.heightPixels;
        return mHeight / 3;
    }

}
