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
    public static final int RC_SCAN_CODE        = 110;
    public static final int RC_SETTINGS_SCREEN  = 101;
    public static final int RC_PERM_CAMERA      = 120;
    public static final int RC_PERM_LOCATION    = 121;
    public static final int RC_PERM_WRITE_SD    = 122;
    public static final int TIME_SPACE310       = 310;
    public static final int TIME_LIMIT300       = 300;

    //intent传递extea存储key值
    public static final String EXTRA_CONNECT_SERVER = "extra_connect_server";

    //SharePreference存储key值
    public static final String SPK_GATEWAY = "gateway";
    public static final String SPK_IS_LOCAL = "isLocal";
    public static final String SPK_LOCAL = "local";
    public static final String SPK_CLOUD = "cloud";
    public static final String GATEWAY_PSW = "gatewayPsw";

    //常量数字
    public static final int TYPE_OPEN_LIGHT     = 200;
    public static final int TYPE_CLOSE_LIGHT    = 201;
    public static final int TYPE_CHANGE_LIGHT   = 202;
    public static final int TYPE_CLOSE_LUMEN    = 203;
    public static final int TYPE_CHANGE_LUMEN   = 204;
    public static final int TYPE_CLOSE_ALARM    = 205;
    public static final int TYPE_CHANGE_ALARM   = 206;
    public static final int TYPE_RESET_ALARM    = 207;
    public static final int TYPE_BIND_LIGHT     = 208;
    public static final int TYPE_UNBIND_LIGHT   = 209;
}
