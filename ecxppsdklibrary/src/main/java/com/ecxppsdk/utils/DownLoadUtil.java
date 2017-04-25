package com.ecxppsdk.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.lnlednewversion.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.math.BigDecimal;

/**
 * Author: Wh1te
 * Date: 2016/9/21
 */

public class DownLoadUtil {

    public static void download(String url, final String path, final Context context) {
        final NotificationManager mNotifyManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle("版本更新")
                .setContentText("正在下载...")
                .setContentInfo("0%")
                .setSmallIcon(R.mipmap.icon_logo_lnled);

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
                Toast.makeText(x.app(), "开始下载", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                BigDecimal b = new BigDecimal((float) current / (float) total);
                float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                mBuilder.setProgress(100, (int) (f1 * 100), false);
                mBuilder.setContentInfo((int) (f1 * 100) + "%");
                mNotifyManager.notify(1, mBuilder.build());
            }

            @Override
            public void onSuccess(File result) {
                mBuilder.setContentText("正在下载...")
                        // Removes the progress bar
                        .setProgress(0, 0, false);
                mNotifyManager.notify(1, mBuilder.build());
                mNotifyManager.cancel(1);
                Toast.makeText(x.app(), "下载成功", Toast.LENGTH_LONG).show();
                InstallUtils.installApp(context, path);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mBuilder.setContentText("下载失败")
                        // Removes the progress bar
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
