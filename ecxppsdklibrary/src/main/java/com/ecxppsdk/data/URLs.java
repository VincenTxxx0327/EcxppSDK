package com.ecxppsdk.data;

/**
 * Author: VincenT
 * Date: 2017/4/24 14:38
 * Contact:qq 328551489
 * Purpose:常用链接及接口
 */
public class URLs {

    //云端链接
    public static String YUN_URL = "lnled.cloudgw.ecxpp.net";
    //局域网链接
    public static String LAN_URL = "192.168.100.1";
    //账号登录服务器
    public static String BASE_URL = "http://common.app.ecxpp.net";
    // 检查更新
    public static String UPDATE = BASE_URL + "/sw/update";
    //获取注册验证码
    public static String GET_VERIFY_CODE = BASE_URL + "/reg/sms";
    //注册
    public static String REGISTER = BASE_URL + "/reg";
    //登录
    public static String LOGIN = BASE_URL + "/login";
    //获取修改密码验证码
    public static String MODIFY_PWD_SMS_CODE = BASE_URL + "/reset/sms";
    //获取修改密码修改码
    public static String MODIFY_PWD_MODIFY_CODE = BASE_URL + "/reset/vali";
    //修改密码
    public static String MODIFY_PWD = BASE_URL + "/reset/pwd";



    public static final String REQUEST_PARAM_PHONE = "phone";
    public static final String REQUEST_PARAM_PWD = "pwd";
    public static final String REQUEST_PARAM_CODE = "code";
    public static final String REQUEST_PARAM_RESET_CODE = "reset_code";
    public static final String REQUEST_PARAM_VERSION = "currentVersion";

    public static final String RESPONSE_ERROR_CODE = "errcode";
    public static final String RESPONSE_DATA = "data";
    public static final String RESPONSE_MESSAGE = "message";
    public static final String RESPONSE_LASTEST = "lastest";
    public static final String RESPONSE_DOWNLOAD_URL = "downloadUrl";

}
