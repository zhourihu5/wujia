package com.wujia.lib.imageloader

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Looper
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.wujia.lib.imageloader.glideprogress.ProgressLoadListener
import com.wujia.lib.imageloader.glideprogress.ProgressModelLoader
import com.wujia.lib.imageloader.glideprogress.ProgressUIListener
import com.wujia.lib.imageloader.listener.ImageSaveListener
import com.wujia.lib.imageloader.listener.SourceReadyListener
import com.wujia.lib.imageloader.transformation.GlideCircleTransform
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.io.*


/**
 * Created by soulrelay on 2016/10/11 13:48.
 * Class Note:
 * using [Glide] to load image
 */
class GlideImageLoaderStrategy : BaseImageLoaderStrategy {

    override fun loadImage(url: String, placeholder: Int, imageView: ImageView) {
        loadNormal(imageView.context, url, placeholder, imageView)
    }

    override fun loadImage(context: Context, url: String?, placeholder: Int, imageView: ImageView) {
        loadNormal(context, url, placeholder, imageView)
    }

    override fun loadImage(uri: Uri, placeholder: Int, imageView: ImageView) {
        Glide.with(imageView.context).load(uri)
                .placeholder(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView)
    }

    /**
     * 无holder的gif加载
     *
     * @param url
     * @param imageView
     */
    override fun loadImage(url: String, imageView: ImageView) {
        Glide.with(imageView.context).load(url)
                .placeholder(imageView.drawable)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView)
    }

    override fun loadCircleImage(url: String, placeholder: Int, imageView: ImageView) {
        Glide.with(imageView.context).load(url).placeholder(placeholder).dontAnimate()
                .transform(GlideCircleTransform(imageView.context))
                .diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView)
    }

    override fun loadCircleImage(url: String, imageView: ImageView) {
        Glide.with(imageView.context).load(url).dontAnimate()
                .transform(GlideCircleTransform(imageView.context))
                .diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView)
    }

    override fun loadBlurImage(url: String, imageView: ImageView) {
        Glide.with(imageView.context).load(url).dontAnimate().crossFade(500)
                .bitmapTransform(BlurTransformation(imageView.context, 25))
                .diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView)
    }

    override fun loadBlurImage(resID: Int, imageView: ImageView) {
        Glide.with(imageView.context).load(resID).dontAnimate().crossFade(500)
                .bitmapTransform(BlurTransformation(imageView.context, 25))
                .diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView)
    }

    override fun loadBlurImage(url: String, placeHolder: Int, imageView: ImageView) {
        Glide.with(imageView.context).load(url).dontAnimate().crossFade(500)
                .bitmapTransform(BlurTransformation(imageView.context, 25))
                .diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView)
    }

    override fun loadCircleBorderImage(url: String, placeholder: Int, imageView: ImageView, borderWidth: Float, borderColor: Int) {
        Glide.with(imageView.context).load(url).placeholder(placeholder).dontAnimate()
                .transform(GlideCircleTransform(imageView.context, borderWidth, borderColor))
                .diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView)
    }

    override fun loadCircleBorderTopImage(url: String, placeholder: Int, imageView: ImageView, borderWidth: Float, borderColor: Int) {
        Glide.with(imageView.context).load(url).placeholder(placeholder).dontAnimate()
                .transform(GlideCircleTransform(imageView.context, borderWidth, borderColor, true))
                .diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView)
    }

    override fun loadCircleBorderImage(url: String, placeholder: Int, imageView: ImageView, borderWidth: Float, borderColor: Int, heightPx: Int, widthPx: Int) {
        Glide.with(imageView.context).load(url).placeholder(placeholder).dontAnimate()
                .transform(GlideCircleTransform(imageView.context, borderWidth, borderColor, heightPx, widthPx))
                .diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView)
    }


    override fun loadImageWithAppCxt(url: String, imageView: ImageView) {
        Glide.with(imageView.context.applicationContext).load(url)
                .placeholder(imageView.drawable)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView)
    }

    override fun loadGifImage(url: String, placeholder: Int, imageView: ImageView) {
        loadGif(imageView.context, url, placeholder, imageView)
    }

    override fun loadImageWithProgress(url: String, imageView: ImageView, listener: ProgressLoadListener) {
        Glide.with(imageView.context).using(ProgressModelLoader(object : ProgressUIListener {
            override fun update(bytesRead: Int, contentLength: Int) {
                imageView.post { listener.update(bytesRead, contentLength) }
            }
        })).load(url).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESULT).listener(object : RequestListener<String, GlideDrawable> {
            override fun onException(e: Exception, model: String, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
                listener.onException()
                return false
            }

            override fun onResourceReady(resource: GlideDrawable, model: String, target: Target<GlideDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                listener.onResourceReady()
                return false
            }
        }).into(imageView)
    }


    override fun loadGifWithPrepareCall(url: String, imageView: ImageView, listener: SourceReadyListener) {
        Glide.with(imageView.context).load(url).asGif()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESULT).listener(object : RequestListener<String, GifDrawable> {
                    override fun onException(e: Exception, model: String, target: Target<GifDrawable>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: GifDrawable, model: String, target: Target<GifDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        listener.onResourceReady(resource.intrinsicWidth, resource.intrinsicHeight)
                        return false
                    }
                }).into(imageView)
    }

    override fun loadImageWithPrepareCall(url: String, imageView: ImageView, placeholder: Int, listener: SourceReadyListener) {
        Glide.with(imageView.context).load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(placeholder)
                .listener(object : RequestListener<String, GlideDrawable> {
                    override fun onException(e: Exception, model: String, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: GlideDrawable, model: String, target: Target<GlideDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        listener.onResourceReady(resource.intrinsicWidth, resource.intrinsicHeight)
                        return false
                    }
                }).into(imageView)
    }

    override fun clearImageDiskCache(context: Context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Thread(Runnable { Glide.get(context.applicationContext).clearDiskCache() }).start()
            } else {
                Glide.get(context.applicationContext).clearDiskCache()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun clearImageMemoryCache(context: Context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context.applicationContext).clearMemory()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun trimMemory(context: Context, level: Int) {
        Glide.get(context).trimMemory(level)
    }

    override fun getCacheSize(context: Context): String {
        try {
            return ImageLoaderUtils.getFormatSize(ImageLoaderUtils.getFolderSize(Glide.getPhotoCacheDir(context.applicationContext)).toDouble())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    override fun saveImage(context: Context, url: String, savePath: String, saveFileName: String, listener: ImageSaveListener) {
        if (!ImageLoaderUtils.isSDCardExsit || TextUtils.isEmpty(url)) {
            listener.onSaveFail()
            return
        }
        var fromStream: InputStream? = null
        var toStream: OutputStream? = null
        try {
            val cacheFile = Glide.with(context).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get()
            if (cacheFile == null || !cacheFile.exists()) {
                listener.onSaveFail()
                return
            }
            val dir = File(savePath)
            if (!dir.exists()) {
                dir.mkdir()
            }
            val file = File(dir, saveFileName + ImageLoaderUtils.getPicType(cacheFile.absolutePath))

            fromStream = FileInputStream(cacheFile)
            toStream = FileOutputStream(file)
            val length = ByteArray(1024)
            var count: Int
            while (true) {
                count = fromStream.read(length)
                if(count>0) toStream.write(length, 0, count)else break
            }
            //用广播通知相册进行更新相册
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val uri = Uri.fromFile(file)
            intent.data = uri
            context.sendBroadcast(intent)
            listener.onSaveSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            listener.onSaveFail()
        } finally {
            try {
                fromStream?.close()
                toStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
                fromStream = null
                toStream = null
            }

        }

    }

    override fun loadRoundedCornersImage(context: Context, url: String, placeholder: Int, roundRadius: Int, imageView: ImageView) {
        Glide.with(imageView.context).load(url).placeholder(placeholder).dontAnimate()
                .bitmapTransform(RoundedCornersTransformation(context, DensityUtil.dp2px(imageView.context, roundRadius.toFloat()), 0))
                .diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView)
    }

    override fun loadRoundedCornersImage(context: Context, url: String, placeholder: Int, errorHolder: Int, roundRadius: Int, imageView: ImageView) {
        Glide.with(imageView.context).load(url).placeholder(placeholder).error(errorHolder).dontAnimate()
                .bitmapTransform(RoundedCornersTransformation(context, DensityUtil.dp2px(imageView.context, roundRadius.toFloat()), 0))
                .diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView)
    }


    /**
     * load image with Glide
     */
    private fun loadNormal(ctx: Context, url: String?, placeholder: Int, imageView: ImageView) {
        /**
         * 为其添加缓存策略,其中缓存策略可以为:Source及None,None及为不缓存,Source缓存原型.如果为ALL和Result就不行.然后几个issue的连接:
         * https://github.com/bumptech/glide/issues/513
         * https://github.com/bumptech/glide/issues/281
         * https://github.com/bumptech/glide/issues/600
         * modified by xuqiang
         */

        //去掉动画 解决与CircleImageView冲突的问题 这个只是其中的一个解决方案
        //使用SOURCE 图片load结束再显示而不是先显示缩略图再显示最终的图片（导致图片大小不一致变化）
        Glide.with(ctx).load(url)
                .placeholder(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.RESULT).listener(object : RequestListener<String, GlideDrawable> {
                    override fun onException(e: Exception, model: String, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: GlideDrawable, model: String, target: Target<GlideDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        return false
                    }
                }).into(imageView)
    }

    /**
     * load image with Glide
     */
    private fun loadGif(ctx: Context, url: String, placeholder: Int, imageView: ImageView) {
        val startTime = System.currentTimeMillis()
        Glide.with(ctx).load(url).asGif()
                .placeholder(placeholder).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESULT).listener(object : RequestListener<String, GifDrawable> {
                    override fun onException(e: Exception, model: String, target: Target<GifDrawable>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: GifDrawable, model: String, target: Target<GifDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })
                .into(imageView)
    }

}
