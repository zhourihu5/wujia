package com.wujia.lib_common.utils

import android.content.Context
import android.os.Environment

import java.io.File
import java.io.IOException

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-30
 * description ：
 */
object FileUtil {

    private val APK = "apk"


    fun getDowndloadApkPath(context: Context): String {
        val file = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + File.separator + APK)
        return if (file != null) {
            file.absolutePath
        } else {
            context.filesDir.absolutePath + "/Download/" + APK
        }
    }

    fun getDowndloadFilePath(context: Context): String {
        val file = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        return if (file != null) {
            file.absolutePath
        } else {
            context.filesDir.absolutePath + "/Download"
        }
    }

    fun getPicDirectory(context: Context): String {
        val picFile = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return if (picFile != null) {
            picFile.absolutePath
        } else {
            context.filesDir.absolutePath + "/Pictures"
        }
    }

    fun isExists(path: String): Boolean {
        val file = File(path)
        return file.exists()
    }

    fun createMkdirs(mkdirs: File): Boolean {
        return mkdirs.mkdirs()
    }

    fun createFile(file: File): Boolean {
        if (!file.exists()) {
            try {
                return file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return false
    }

    fun getNameForUrl(url: String): String {
        return url.substring(url.lastIndexOf('/') + 1)
    }

    fun deleteFile(path: String) {
        val file = File(path)
        if (file.isDirectory) {
            val files = file.listFiles()
            for (i in files.indices) {
                val f = files[i]
                //                deleteFile(f);
                if (f.exists())
                    f.delete()
            }
            //            file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete()
        }
    }
}
