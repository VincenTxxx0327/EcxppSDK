package com.ecxppsdk.base;

/**
 * Author: VincenT
 * Date: 2017/3/1 14:38
 * Contact:qq 328551489
 * Purpose:基本View执行类
 */
public interface BaseView<P> {

    void setPresenter(P presenter, boolean isCanSwipeBack);

    void showToast(String msg);

    void showToast(int msg);

    void showToastLong(String msg);

    void showLoadingDialog(String tag, String mainText);

    void hideDialog(String tag);

    void resetView(String msg);
}
