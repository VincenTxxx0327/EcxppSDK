package com.ecxppsdk.listener;

/**
 * Author: Wh1te
 * Date: 2016/9/21
 */

public interface OnNetworkListener {

    void onSuccess(String result);

    void onFailure(Throwable e);
}
