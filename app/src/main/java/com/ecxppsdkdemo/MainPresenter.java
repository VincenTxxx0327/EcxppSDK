package com.ecxppsdkdemo;

/**
 * Author: VincenT
 * Date: 2017/4/23 16:47
 * Contact:qq 328551489
 * Purpose:此类用于衔接Model跟View层
 */
public class MainPresenter implements MainContract.Presenter{

    private final MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void openCaptureUi() {
        mView.showCaptureUi();
    }
}
