package com.ecxppsdk.utils;

import android.Manifest;
import android.content.Context;
import android.hardware.Camera;
import android.os.Build;

import com.ecxppsdk.easypermissions.EasyPermissions;


/**
 * Author: VincenT
 * Date: 2017/4/24 14:38
 * Contact:qq 328551489
 * Purpose:权限工具类
 */
public class PermissionUtils {
    /**
     * 判断摄像头是否可用
     * 主要针对6.0 之前的版本，现在主要是依靠try...catch... 报错信息，感觉不太好，
     * 以后有更好的方法的话可适当替换
     *
     * @return
     */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            // setParameters 是针对魅族做的。魅族通过Camera.open() 拿到的Camera
            // 对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null) {
            mCamera.release();
        }
        return canUse;
    }

    public static final int AFTER_M_SUCCESS = 0;
    public static final int AFTER_M_FAILURE = 1;
    public static final int BEFORE_M_SUCCESS = 2;
    public static final int BEFORE_M_FAILURE = 3;

    public static int hasCameraPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return EasyPermissions.hasPermissions(context, Manifest.permission.CAMERA) ? AFTER_M_SUCCESS : AFTER_M_FAILURE;
        } else {
            return isCameraCanUse() ? BEFORE_M_SUCCESS : BEFORE_M_FAILURE;
        }
    }
}
