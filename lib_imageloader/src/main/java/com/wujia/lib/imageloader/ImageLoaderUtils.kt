package com.wujia.lib.imageloader

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Environment
import android.text.TextUtils

import java.io.File
import java.math.BigDecimal

object ImageLoaderUtils {

    val isSDCardExsit: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
        }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    @Throws(Exception::class)
    fun getFolderSize(file: File): Long {
        var size: Long = 0
        try {
            val fileList = file.listFiles()
            for (aFileList in fileList) {
                if (aFileList.isDirectory) {
                    size = size + getFolderSize(aFileList)
                } else {
                    size = size + aFileList.length()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return size
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    fun getFormatSize(size: Double): String {

        val kiloByte = size / 1024
        if (kiloByte < 1) {
            return size.toString() + "Byte"
        }

        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            val result1 = BigDecimal(java.lang.Double.toString(kiloByte))
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB"
        }

        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 = BigDecimal(java.lang.Double.toString(megaByte))
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB"
        }

        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB"
        }
        val result4 = BigDecimal(teraBytes)

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
    }

    fun getPicType(pathName: String): String {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(pathName, options)
        var type = options.outMimeType
        if (!TextUtils.isEmpty(type)) {
            try {
                type = type.substring(6)
                if ("gif" == type) {
                    return ".gif"
                }
            } catch (e: IndexOutOfBoundsException) {
                e.printStackTrace()
            }

        }
        return ".jpg"
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context?, dpValue: Float): Int {
        if (context == null) {
            return 0
        }
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


}
