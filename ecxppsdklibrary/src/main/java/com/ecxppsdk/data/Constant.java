package com.ecxppsdk.data;

/**
 * Author: VincenT
 * Date: 2017/4/24 14:38
 * Contact:qq 328551489
 * Purpose:基本常量值
 */
public class Constant {

    public static final boolean DEBUG = true;
    public static final String DB_NAME = "Apps.db";
    public static final int RC_SCAN_CODE = 110;
    public static final int RC_CAMERA_PERM = 120;
    public static final int RC_LOCATION_PERM = 121;
    public static final int RC_WRITE_SD_PERM = 122;
    public static final int RC_SETTINGS_SCREEN = 130;
    public static final int TIME_SPACE = 310;
    public static final int TIME_LIMIT = 300;

    //EventBus信息回调key值

    //intent传递extea存储key值
    public static final String EXTRA_CONNECT_SERVER = "extra_connect_server";

    //SharePreference存储key值
    public static final String SPK_HAS_INIT = "has_init";
    public static final String SPK_GATEWAY = "gateway";
    public static final String SPK_IS_LOCAL = "isLocal";
    public static final String SPK_LOCAL = "local";
    public static final String SPK_CLOUD = "cloud";

    //常量数字
    public static final int TYPE_OPEN_LIGHT = 0;
    public static final int TYPE_CLOSE_LIGHT = 1;
    public static final int TYPE_CHANGE_LIGHT = 2;
    public static final int TYPE_CLOSE_LUMEN = 3;
    public static final int TYPE_CHANGE_LUMEN = 4;
    public static final int TYPE_CLOSE_ALARM = 5;
    public static final int TYPE_CHANGE_ALARM = 6;
    public static final int TYPE_RESET_ALARM = 7;
    public static final int TYPE_BIND_LIGHT = 8;
    public static final int TYPE_UNBIND_LIGHT = 9;
}
