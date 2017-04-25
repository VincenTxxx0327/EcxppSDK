package com.ecxppsdk.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Author: Wh1te
 * Date: 2016/9/21
 */

public class InstallUtils {
    public static void installApp(Context context, String filePath) {
        File _file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(_file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
