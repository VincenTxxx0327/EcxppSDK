package com.ecxppsdk.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Author: VincenT
 * Date: 2017/3/1 10:12
 * Contact:qq 328551489
 * Purpose:Toast工具类
 */
public class ToastUtils {


    public static void showToast(Context context, String content) {
        Toast mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void showToast(Context context, int resId) {
        Toast mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void showToastLong(Context context, String content) {
        Toast mToast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        mToast.show();
    }


}
