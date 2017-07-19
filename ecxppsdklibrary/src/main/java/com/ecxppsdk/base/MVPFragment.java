package com.ecxppsdk.base;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.ecxppsdk.R;
import com.ecxppsdk.data.Constant;
import com.ecxppsdk.easypermissions.AppSettingsDialog;
import com.ecxppsdk.easypermissions.EasyPermissions;
import com.ecxppsdk.utils.ToastUtils;
import com.ecxppsdk.widget.DialogLoading;
import com.ecxppsdk.zxing.activity.CaptureActivity;

import org.xutils.common.util.LogUtil;

import java.util.List;

/**
 * Author: VincenT
 * Date: 2016/11/15 16:54
 * Purpose:View层碎片化处理类
 */
public abstract class MVPFragment<P extends BasePresenter> extends BaseFragment implements BaseView<P>, EasyPermissions.PermissionCallbacks {

    protected P mPresenter;
    protected boolean mIsCanSwipeBack = false;
    protected DialogLoading mDialogLoading;
    protected static FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mPresenter = createPresenter();
        mFragmentManager = getFragmentManager();
        mDialogLoading = new DialogLoading();
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    public void setPresenter(P presenter, boolean isCanSwipeBack) {
        mPresenter = presenter;
        mIsCanSwipeBack = isCanSwipeBack;
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(getActivity(), msg);
    }

    @Override
    public void showToast(int msg) {
        ToastUtils.showToast(getActivity(), msg);
    }

    @Override
    public void showToastLong(String msg) {
        ToastUtils.showToastLong(getActivity(), msg);
    }

    @Override
    public void showLoadingDialog(String tag, String mainText) {
        if(!mDialogLoading.isAdded() && !mDialogLoading.isVisible() && !mDialogLoading.isRemoving()) {
            mDialogLoading.setText(mainText);
            mDialogLoading.show(mFragmentManager, tag);
        }
    }

    @Override
    public void hideDialog(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return;
        }
        DialogFragment dialogFragment = (DialogFragment) mFragmentManager.findFragmentByTag(tag);
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }

    @Override
    public void resetView(String msg) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
                .setNegativeButton(getString(R.string.dialogText_cancel), null)
                .setRequestCode(Constant.RC_SETTINGS_SCREEN)
                .build()
                .show();
        } else {
            showToast(getString(R.string.toastText_getAuthorizationFailed));
        }
    }

    public void openScanActivity(Activity activity) {
        Intent scan = new Intent(activity, CaptureActivity.class);
        scan.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(scan, Constant.RC_SCAN_CODE);
    }

    protected void showAppSettingDialog(Activity activity) {
        new AppSettingsDialog.Builder(activity, getString(R.string.dialogText_needAuthorization))
            .setTitle(getString(R.string.dialogText_tips))
            .setPositiveButton(getString(R.string.dialogText_setting))
            .setNegativeButton(activity.getString(R.string.dialogText_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showToast(getString(R.string.toastText_getAuthorizationFailed));
                }
            })
            .setRequestCode(Constant.RC_SETTINGS_SCREEN)
            .build()
            .show();
    }
}
