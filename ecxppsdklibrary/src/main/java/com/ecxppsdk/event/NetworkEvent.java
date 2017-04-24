package com.ecxppsdk.event;

/**
 * Author: VincenT
 * Date: 2017/4/23 16:38
 * Contact:qq 328551489
 * Purpose:网络事务反馈
 */
public class NetworkEvent {

    public static final int LOCAL_NETWORK = 1;
    public static final int FEEDBACK = 3;
    public static final int NETWORK_ERROR = 4;
    public static final int RECONNECT = 5;
    public static final int NETWORK_DISCONNECT = 6;
    public static final int WIFIRECONNECT = 7;
    public static final int WIFICONNECT = 9;
    public static final int WIFIDISCONNECT = 10;
    public static final int HEARTBEET = 11;
    public static final int TOOSOON = 12;

    public NetworkEvent(int flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public NetworkEvent(int flag, String msg, byte[] feedBack) {
        this.flag = flag;
        this.msg = msg;
        this.feedback = feedBack;
    }

    //1:设置了网关和IP；2:还未设置; 3:获取反馈数据; 4:连接失败; 5:重新连接WiFi
    public int flag;
    public String msg;
    public byte[] feedback;//网关返回数据
}
