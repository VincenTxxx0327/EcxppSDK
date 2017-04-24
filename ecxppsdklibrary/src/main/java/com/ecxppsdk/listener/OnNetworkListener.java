package com.ecxppsdk.listener;

/**
 * Author: VincenT
 * Date: 2017/4/24 15:18
 * Contact:qq 328551489
 * Purpose:用于网络连接结果回调
 */
public interface OnNetworkListener {

    void onSuccess(String result);

    void onFailure(Throwable e);
}
