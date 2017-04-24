package com.ecxppsdk.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecxppsdk.R;
import com.ecxppsdk.data.Constant;
import com.ecxppsdk.easypermissions.AppSettingsDialog;
import com.ecxppsdk.easypermissions.EasyPermissions;
import com.ecxppsdk.utils.ToastUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.util.List;


/**
 * Author: VincenT
 * Date: 2017/4/24 14:38
 * Contact:qq 328551489
 * Purpose:基本Fragment
 */
public class BaseFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    private boolean isInject = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isInject = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isInject) {
            x.view().inject(this, this.getView());
        }
    }

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
            new AppSettingsDialog.Builder(this, getString(R.string.dialogText_needAuthorization))
                    .setTitle(getString(R.string.dialogText_tips))
                    .setPositiveButton(getString(R.string.dialogText_confirm))
                    .setNegativeButton(getString(R.string.dialogText_cancel), null /* click listener */)
                    .setRequestCode(Constant.RC_SETTINGS_SCREEN)
                    .build()
                    .show();
        } else {
            ToastUtils.showToast(getActivity(), getString(R.string.toastText_getAuthorizationFailed));
        }
    }
}
