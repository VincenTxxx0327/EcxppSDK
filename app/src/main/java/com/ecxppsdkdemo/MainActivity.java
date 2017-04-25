package com.ecxppsdkdemo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ecxppsdk.base.MVPActivity;
import com.ecxppsdk.data.Constant;
import com.ecxppsdk.easypermissions.EasyPermissions;
import com.ecxppsdk.utils.DeviceUtils;
import com.ecxppsdk.utils.PermissionUtils;


public class MainActivity extends MVPActivity<MainContract.Presenter> implements MainContract.View {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv_main_text);
        textView.setText("当前应用版本号："+DeviceUtils.getVersionName(this)+"\n点击此处跳转至扫描界面");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.openCaptureUi();
            }
        });
    }

    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void showCaptureUi() {
        int hasCameraPermission = PermissionUtils.hasCameraPermission(this);
        switch (hasCameraPermission) {
            case PermissionUtils.AFTER_M_FAILURE:
                // 请求摄像头权限
                EasyPermissions.requestPermissions(this, "请允许应用程序获取摄像头权限以使用扫描功能", Constant.RC_PERM_CAMERA, Manifest.permission.CAMERA);
                break;
            case PermissionUtils.BEFORE_M_FAILURE:
                openSystemSettingUI(this);
                break;
            case PermissionUtils.AFTER_M_SUCCESS:
            case PermissionUtils.BEFORE_M_SUCCESS:
                // 打开扫描界面
                openCaptureActivity(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.RC_SCAN_CODE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        textView.setText(bundle.getString("result"));
                    } else {
                        showToast("扫描失败");
                    }
                }
                break;
        }
    }
}
