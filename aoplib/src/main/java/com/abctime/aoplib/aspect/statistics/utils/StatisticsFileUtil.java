package com.abctime.aoplib.aspect.statistics.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * author:Created by xmren on 2018/7/13.
 * email :renxiaomin@100tal.com
 */

public class StatisticsFileUtil {


    public static String fileSavePath;

    public static File getLogFile() {
        return new File(fileSavePath);
    }


    public static void write(String src) {
        File file = getLogFile();
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (file.isFile()) {
            try {
                boolean append = true;
                long length = file.length();
                if (length > 10 * 1024576L) {
                    append = false;
                }
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, append));
                bufferedWriter.write(src);
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setLogSaveFilePath(String savePath) {
        fileSavePath = savePath + "/statisticslogs";
    }
}
