package com.ecxppsdk.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ecxppsdk.R;
import com.ecxppsdk.data.Constant;
import com.ecxppsdk.easypermissions.AppSettingsDialog;
import com.ecxppsdk.easypermissions.EasyPermissions;
import com.ecxppsdk.utils.ToastUtils;

import org.xutils.common.util.LogUtil;

import java.util.List;


public abstract class BaseFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    protected boolean isDayTheme = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        isDayTheme = isDayTheme();
        super.onCreate(savedInstanceState);
    }

    public abstract boolean isDayTheme();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 获取权限成功
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        LogUtil.i("requestCode:" + requestCode + "-----> success");
    }

    /**
     * 权限被拒绝
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            // 被永久拒绝的话提示自己手动授予
            new AppSettingsDialog.Builder(this, "应用程序所需权限已被禁用，这可能会影响程序的正常使用，是否打开应用程序设置界面授予权限")
                    .setTitle("提示")
                    .setPositiveButton("确定")
                    .setNegativeButton(getString(R.string.dialogText_cancel), null /* click listener */)
                    .setRequestCode(Constant.RC_SETTINGS_SCREEN)
                    .build()
                    .show();
        } else {
            ToastUtils.showToast(getActivity(), "获取权限失败");
        }
    }

}
