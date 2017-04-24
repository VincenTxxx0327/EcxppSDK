package com.ecxppsdkdemo;

import com.ecxppsdk.base.BasePresenter;
import com.ecxppsdk.base.BaseView;


/**
 * Author: VincenT
 * Date: 2017/3/21 16:19
 * Contact:qq 328551489
 * Purpose:此类用于主界面契约类
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void showCaptureUi();
    }

    interface Presenter extends BasePresenter {

        void openCaptureUi();
    }
}
