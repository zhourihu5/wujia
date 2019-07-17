package com.wujia.lib.imageloader

import android.content.Context
import android.net.Uri
import android.widget.ImageView

import com.wujia.lib.imageloader.glideprogress.ProgressLoadListener
import com.wujia.lib.imageloader.listener.ImageSaveListener
import com.wujia.lib.imageloader.listener.SourceReadyListener


/**
 * Created by soulrelay on 2016/10/11.
 * Class Note:
 * abstract class/interface defined to load image
 * (Strategy Pattern used here)
 */
interface BaseImageLoaderStrategy {
    //无占位图
    fun loadImage(url: String, imageView: ImageView)

    //这里的context指定为ApplicationContext
    fun loadImageWithAppCxt(url: String, imageView: ImageView)

    fun loadImage(url: String, placeholder: Int, imageView: ImageView)

    fun loadImage(url: Uri, placeholder: Int, imageView: ImageView)

    fun loadImage(context: Context, url: String?, placeholder: Int, imageView: ImageView)

    fun loadCircleImage(url: String, placeholder: Int, imageView: ImageView)

    fun loadCircleImage(url: String, imageView: ImageView)

    fun loadBlurImage(url: String, imageView: ImageView)

    fun loadBlurImage(resID: Int, imageView: ImageView)

    fun loadBlurImage(url: String, placeHolder: Int, imageView: ImageView)


    fun loadCircleBorderImage(url: String, placeholder: Int, imageView: ImageView, borderWidth: Float, borderColor: Int)

    fun loadCircleBorderTopImage(url: String, placeholder: Int, imageView: ImageView, borderWidth: Float, borderColor: Int)

    fun loadCircleBorderImage(url: String, placeholder: Int, imageView: ImageView, borderWidth: Float, borderColor: Int, heightPx: Int, widthPx: Int)

    fun loadGifImage(url: String, placeholder: Int, imageView: ImageView)

    fun loadImageWithProgress(url: String, imageView: ImageView, listener: ProgressLoadListener)

    fun loadImageWithPrepareCall(url: String, imageView: ImageView, placeholder: Int, listener: SourceReadyListener)

    fun loadGifWithPrepareCall(url: String, imageView: ImageView, listener: SourceReadyListener)

    //清除硬盘缓存
    fun clearImageDiskCache(context: Context)

    //清除内存缓存
    fun clearImageMemoryCache(context: Context)

    //根据不同的内存状态，来响应不同的内存释放策略
    fun trimMemory(context: Context, level: Int)

    //获取缓存大小
    fun getCacheSize(context: Context): String

    fun saveImage(context: Context, url: String, savePath: String, saveFileName: String, listener: ImageSaveListener)

    fun loadRoundedCornersImage(context: Context, url: String, placeholder: Int, roundRadius: Int, imageView: ImageView)

    fun loadRoundedCornersImage(context: Context, url: String, placeholder: Int, errorHolder: Int, roundRadius: Int, imageView: ImageView)
}
