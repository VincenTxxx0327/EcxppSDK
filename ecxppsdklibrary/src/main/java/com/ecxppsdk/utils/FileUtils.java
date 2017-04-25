package com.ecxppsdk.utils;

import android.os.Environment;

import java.io.File;

/**
 * Author: Wh1te
 * Date: 2016/9/21
 */

public class FileUtils {

    private FileUtils() {

    }

    private static String downloadPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Lnled";

    public static boolean createFile(String path) {
        boolean result = false;
        if (path != null && path.length() > 0) {
            File file = new File(path);
            if (!file.exists()) {
                result = file.mkdirs();
            }
        }
        return result;
    }

    public static String getDownloadPath() {
        if (new File(downloadPath).exists()) {
            return downloadPath;
        } else {
            boolean b = createFile(downloadPath);
            if (b) {
                return downloadPath;
            } else {
                return "false";
            }
        }
    }
}
