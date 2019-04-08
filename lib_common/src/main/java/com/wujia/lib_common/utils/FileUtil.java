package com.wujia.lib_common.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-30
 * description ：
 */
public class FileUtil {

    private static final String APK = "apk";


    public static String getDowndloadApkPath(Context context) {
        File file = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + File.separator + APK);
        if (file != null) {
            return file.getAbsolutePath();
        } else {
            return context.getFilesDir().getAbsolutePath() + "/Download/" + APK;
        }
    }

    public static String getDowndloadFilePath(Context context) {
        File file = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (file != null) {
            return file.getAbsolutePath();
        } else {
            return context.getFilesDir().getAbsolutePath() + "/Download";
        }
    }

    public static String getPicDirectory(Context context) {
        File picFile = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (picFile != null) {
            return picFile.getAbsolutePath();
        } else {
            return context.getFilesDir().getAbsolutePath() + "/Pictures";
        }
    }

    public static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static boolean createMkdirs(File mkdirs) {
        return mkdirs.mkdirs();
    }

    public static boolean createFile(File file) {
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String getNameForUrl(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
//                deleteFile(f);
                if (f.exists())
                    f.delete();
            }
//            file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }
}
