package com.ecxppsdk.utils;

import android.view.View;

/**
 * Author: VincenT
 * Date: 2017/4/24 10:12
 * Contact:qq 328551489
 * Purpose:View工具类
 */
public class ViewUtils {

    public static void setVisible(View view, boolean isVisible) {
        if (view != null) {
            if (isVisible) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }
    public static void setInvisible(View view, boolean isInvisible) {
        if (view != null) {
            if (isInvisible) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.INVISIBLE);
            }
        }
    }
    public static boolean getVisible(View view) {
        if (view != null) {
            if (view.getVisibility()==View.VISIBLE) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }
}
