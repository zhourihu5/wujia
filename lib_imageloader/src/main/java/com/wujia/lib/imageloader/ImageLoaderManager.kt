package com.wujia.lib.imageloader

import android.content.Context
import android.net.Uri
import android.widget.ImageView

import com.wujia.lib.imageloader.glideprogress.ProgressLoadListener
import com.wujia.lib.imageloader.listener.ImageSaveListener
import com.wujia.lib.imageloader.listener.SourceReadyListener


/**
 * Created by soulrelay on 2016/10/11 13:42.
 * Class Note:
 * use this class to load image,single instance
 */
class ImageLoaderManager {
    //本应该使用策略模式，用基类声明，但是因为Glide特殊问题
    //持续优化更新
    private var mStrategy: BaseImageLoaderStrategy? = null

    init {
        mStrategy = GlideImageLoaderStrategy()
    }

    /**
     * 统一使用App context
     * 可能带来的问题：http://stackoverflow.com/questions/31964737/glide-image-loading-with-application-context
     *
     * @param url
     * @param placeholder
     * @param imageView
     */
    fun loadImage(url: String?, placeholder: Int, imageView: ImageView) {
         mStrategy!!.loadImage(imageView.context, url, placeholder, imageView)
    }

    /**
     * 加载本地图片
     *
     * @param uri
     * @param placeholder
     * @param imageView
     */
    fun loadImage(uri: Uri, placeholder: Int, imageView: ImageView) {
        mStrategy!!.loadImage(uri, placeholder, imageView)
    }

    fun loadRoundedCornersImages(url: String, placeholder: Int, roundRadius: Int, imageView: ImageView) {
        mStrategy!!.loadRoundedCornersImage(imageView.context, url, placeholder, roundRadius, imageView)
    }

    fun loadRoundedCornersImages(url: String, placeholder: Int, errorHolder: Int, roundRadius: Int, imageView: ImageView) {
        mStrategy!!.loadRoundedCornersImage(imageView.context, url, placeholder, errorHolder, roundRadius, imageView)
    }

    fun loadGifImage(url: String, placeholder: Int, imageView: ImageView) {
        mStrategy!!.loadGifImage(url, placeholder, imageView)
    }

    fun loadCircleImage(url: String, placeholder: Int, imageView: ImageView) {
        mStrategy!!.loadCircleImage(url, placeholder, imageView)
    }

    fun loadCircleImage(url: String, imageView: ImageView) {
        mStrategy!!.loadCircleImage(url, imageView)
    }

    fun loadImage(url: String?, imageView: ImageView?) {
        url?.let { imageView?.let { it1 -> mStrategy!!.loadImage(it, 0, it1) } }
    }

    fun loadImageWithAppCxt(url: String, imageView: ImageView) {
        mStrategy!!.loadImageWithAppCxt(url, imageView)
    }

    fun loadImageWithProgress(url: String, imageView: ImageView, listener: ProgressLoadListener) {
        mStrategy!!.loadImageWithProgress(url, imageView, listener)
    }

    fun loadImageWithPrepareCall(url: String, imageView: ImageView, placeholder: Int, listener: SourceReadyListener) {
        mStrategy!!.loadImageWithPrepareCall(url, imageView, placeholder, listener)
    }

    /**
     * 需要展示图片加载进度的请参考 GalleryAdapter
     * 样例如下所示
     */

    /**
     * 清除图片磁盘缓存
     */
    fun clearImageDiskCache(context: Context) {
        mStrategy!!.clearImageDiskCache(context)
    }

    /**
     * 清除图片内存缓存
     */
    private fun clearImageMemoryCache(context: Context) {
        mStrategy!!.clearImageMemoryCache(context)
    }

    /**
     * 清除图片所有缓存
     */
    fun clearImageAllCache(context: Context) {
        clearImageDiskCache(context.applicationContext)
        clearImageMemoryCache(context.applicationContext)
    }

    fun saveImage(context: Context, url: String, savePath: String, saveFileName: String, listener: ImageSaveListener) {
        mStrategy!!.saveImage(context, url, savePath, saveFileName, listener)
    }

    companion object {

        //图片默认加载类型 以后有可能有多种类型
        const val PIC_DEFAULT_TYPE = 0

        //图片默认加载策略 以后有可能有多种图片加载策略
        const val LOAD_STRATEGY_DEFAULT = 0

        @Volatile
        private var mInstance: ImageLoaderManager? = null

        //单例模式，节省资源
        val instance: ImageLoaderManager
            get() {
                if (mInstance == null) {
                    synchronized(ImageLoaderManager::class.java) {
                        if (mInstance == null) {
                            mInstance = ImageLoaderManager()
                        }
                    }
                }
                return mInstance!!
            }
    }


}
