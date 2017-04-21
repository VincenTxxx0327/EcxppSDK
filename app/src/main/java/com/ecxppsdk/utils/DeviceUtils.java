package com.ecxppsdk.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.ecxppsdk.R;
import com.ecxppsdk.data.Constant;
import com.ecxppsdk.data.URLs;
import com.ecxppsdk.database.DBService;
import com.ecxppsdk.database.entity.Gateway;

import org.xutils.x;

import static com.ecxppsdk.base.LnledApp.phoneMac;
import static com.ecxppsdk.utils.ConversionUtils.hexString2BytesData;
import static com.ecxppsdk.utils.ConversionUtils.hexString2BytesNew;
import static com.ecxppsdk.utils.ConversionUtils.hexString2BytesOld;


/**
 * Created by zhen on 2016/8/16.
 */
public class DeviceUtils {
    private static int WIDTH;
    private static int HEIGHT;
    private static float DENSITY;

    /**
     * 获取IP地址
     * @param context
     * @return
     */
    public static String getIP(Context context, boolean connectServer) {
        Gateway gateway = DBService.getInstance().loadGatewayById(SharedPreUtils.getLong(context, Constant.SPK_GATEWAY_ID, 1L));
        if(gateway == null){
            SharedPreUtils.putLong(context, Constant.SPK_GATEWAY_ID, 1L);
            gateway = new Gateway();
        }
        String ip;
        if(connectServer) {         //互联网连接，根据勾选判断ip使用局域网还是互联网
            if (SharedPreUtils.getBoolean(context, context.getString(R.string.isLocal), false)) {
                if(TextUtils.isEmpty(gateway.getLocalIP())){
                    ip = URLs.LAN_URL;
                }else {
                    ip = gateway.getLocalIP();
                }
            } else {
                if (TextUtils.isEmpty(gateway.getCloudIP())) {
                    ip = URLs.YUN_URL;
                } else {
                    ip = gateway.getCloudIP();
                }
            }
        }else{                     //局域网连接，ip默认使用局域网，直接去执行登录指令发送
            if(TextUtils.isEmpty(gateway.getLocalIP())){
                ip = URLs.LAN_URL;
            }else {
                ip = gateway.getLocalIP();
            }
        }
        return ip;
    }

    /**
     * 获取网关mac码
     * @return
     */
    public static byte[] getDstMac() {
        long mGatewayId = SharedPreUtils.getLong(x.app(), Constant.SPK_GATEWAY_ID, 1L);
        Gateway mGateway = DBService.getInstance().loadGatewayById(mGatewayId);
        String gateway = "010101010101";
        if(mGateway != null) {
            if(!TextUtils.isEmpty(mGateway.getGatewayId()))
                gateway = mGateway.getGatewayId();
        }
        return hexString2BytesData(gateway);
    }

    /**
     * 获取手机设备Mac地址，转为16进制
     * @return
     */
    public static byte[] getSrcMac() {
        if(SharedPreUtils.getBoolean(x.app(), x.app().getString(R.string.cmdNew), false)) {
            phoneMac = hexString2BytesNew(getSrcMacStr(LnledApp.getInstance()));
        }else{
            phoneMac = hexString2BytesOld(getSrcMacStr(LnledApp.getInstance()));
        }
        return phoneMac;
    }

    /**
     * 获取手机设备Mac地址字符串
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
     * @param context
     * @return
     */
    public static byte[] getPassword(Context context) {
        String pwd = SharedPreUtils.getString(context, context.getString(R.string.GatewayPsw), "00000000");
        return pwd.getBytes();
    }

    public static int getWidth(Context context) {
        if (WIDTH == 0) {
            WIDTH = context.getResources().getDisplayMetrics().widthPixels;
        }
        return WIDTH;
    }

    public static int getHeight(Context context) {
        if (HEIGHT == 0) {
            HEIGHT = context.getResources().getDisplayMetrics().heightPixels;
        }
        return HEIGHT;
    }

    public static float getDensity(Context context) {
        if (DENSITY == 0) {
            DENSITY = context.getResources().getDisplayMetrics().density;
        }
        return DENSITY;
    }

    public static int dip2px(Context context, float dpValue) {
        return (int) (dpValue * getDensity(context) + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        return (int) (pxValue / getDensity(context) + 0.5f);
    }

    public static float sp2px(Context context, float spValue) {
        return spValue * getDensity(context) * 10.0f / 20.0f;
    }

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

    /** 获取屏幕三分之一高度 */
    public static int getOneThirdsWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mWidth = dm.widthPixels;
        return mWidth / 3;
    }

    /** 获取屏幕三分之二宽度 */
    public static int getTwoThirdsWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mWidth = dm.widthPixels;
        return mWidth * 2 / 3;
    }

    /** 获取屏幕五分之四宽度 */
    public static int getFourFifthsWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mWidth = dm.widthPixels;
        return mWidth * 4 / 5;
    }

    /** 获取屏幕五分之四高度 */
    public static int getFourFifthsHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mHeight = dm.heightPixels;
        return mHeight * 4 / 5;
    }

    /** 获取屏幕三分之二高度 */
    public static int getTwoThirdsHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mHeight = dm.heightPixels;
        return mHeight * 2 / 3;
    }

    /** 获取屏幕三分之一高度 */
    public static int getOneThirdsHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mHeight = dm.heightPixels;
        return mHeight / 3;
    }

}
