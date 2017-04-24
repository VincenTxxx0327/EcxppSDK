package com.ecxppsdk.data;

/**
 * Author: VincenT
 * Date: 2016/11/14
 */

public class Constant {

    public static final boolean DEBUG = true;
    public static final String DB_NAME = "LnledNewDebug.db";
    public static final int REQUEST_SCAN_CODE = 1;
    public static final int RC_CAMERA_PERM = 123;
    public static final int RC_LOCATION_PERM = 124;
    public static final int RC_SETTINGS_SCREEN = 125;
    public static final int RC_WRITE_SD_PERM = 126;
    public static final int SCANNIN_GREQUEST_CODE = 1;
    public static final int TIME_SPACE = 310;
    public static final int TIME_LIMIT = 300;

    //EventBus信息回调key值

    //intent传递extea存储key值
    public static final String EXTRA_CONNECT_SERVER = "extra_connect_server";

    //SharePreference存储key值
    public static final String SPK_HAS_INIT = "has_init";
    public static final String SPK_GATEWAY  = "gateway";
    public static final String SPK_IS_LOCAL = "isLocal";
    public static final String SPK_LOCAL    = "local";
    public static final String SPK_CLOUD    = "cloud";

    //常量数字
    public static final int CONNECT_SERVER = 100;
    public static final int CONNECT_SET = 101;
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
