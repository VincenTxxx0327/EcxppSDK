package com.ecxppsdk.listener;

/**
 * Author: VincenT
 * Date: 2017/4/24 14:38
 * Contact:qq 328551489
 * Purpose:用于发送成功反馈调用
 */
public interface OnFeedbackListener {

    void onSuccess(int type, String result);

}
