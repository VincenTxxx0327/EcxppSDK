package com.ecxppsdk.base;

/**
 * Author: VincenT
 * Date: 2017/4/24 11:28
 * Contact:qq 328551489
 * Purpose:基本网络回调
 */
public abstract class BaseResult {
    protected String message;
    protected int errcode;

    public BaseResult() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "message='" + message + '\'' +
                ", errcode=" + errcode +
                '}';
    }
}
