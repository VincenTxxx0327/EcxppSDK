package com.ecxppsdk.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.math.BigDecimal;

/**
 * Author: VincenT
 * Date: 2017/6/08 16:38
 * Contact:qq 328551489
 * Purpose:下载工具类
 */

public class DownLoadUtils {

    public static void download(String url, final String path, final Context context) {
        final NotificationManager mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle("版本更新")
                .setContentText("正在下载...")
                .setContentInfo("0%")
                .setSmallIcon(context.getApplicationInfo().icon);
        RequestParams params = new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(path);
        x.http().get(params, new Callback.ProgressCallback<File>() {

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                ToastUtils.showToast(x.app(), "开始下载");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                BigDecimal bigDecimal = new BigDecimal((float) current / (float) total);
                float value = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                mBuilder.setProgress(100, (int) (value * 100), false);
                mBuilder.setContentInfo((int) (value * 100) + "%");
                mNotifyManager.notify(1, mBuilder.build());
            }

            @Override
            public void onSuccess(File result) {
                mBuilder.setContentText("正在下载...")
                        .setProgress(0, 0, false);
                mNotifyManager.notify(1, mBuilder.build());
                mNotifyManager.cancel(1);
                ToastUtils.showToast(x.app(), "下载成功");
                DeviceUtils.installApp(context, path);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mBuilder.setContentText("下载失败")
                        .setProgress(0, 0, false);
                mNotifyManager.notify(1, mBuilder.build());
//                mNotifyManager.cancel(1);
                ToastUtils.showToast(x.app(), "下载失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
