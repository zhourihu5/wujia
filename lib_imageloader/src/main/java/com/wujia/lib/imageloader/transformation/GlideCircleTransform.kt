package com.wujia.lib.imageloader.transformation

import android.content.Context
import android.content.res.Resources
import android.graphics.*

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.wujia.lib.imageloader.ImageLoaderUtils

class GlideCircleTransform : BitmapTransformation {

    private var isTop = false
    private var mBorderPaint: Paint? = null
    private var mBorderWidth: Float = 0.toFloat()

    private var height = 0
    private var width = 0
    private var borderColor = 0

    constructor(context: Context) : super(context) {}

    constructor(context: Context, borderWidth: Float, borderColor: Int) : super(context) {
        mBorderWidth = Resources.getSystem().displayMetrics.density * borderWidth
        mBorderWidth = ImageLoaderUtils.dip2px(context, borderWidth).toFloat()
        mBorderPaint = Paint()
        mBorderPaint!!.isDither = true
        mBorderPaint!!.isAntiAlias = true
        mBorderPaint!!.color = borderColor
        mBorderPaint!!.style = Paint.Style.STROKE
        mBorderPaint!!.strokeWidth = mBorderWidth
    }

    constructor(context: Context, borderWidth: Float, borderColor: Int, heightPX: Int, widthPx: Int) : super(context) {
        mBorderWidth = Resources.getSystem().displayMetrics.density * borderWidth
        mBorderWidth = ImageLoaderUtils.dip2px(context, borderWidth).toFloat()
        mBorderPaint = Paint()
        mBorderPaint!!.isDither = true
        mBorderPaint!!.isAntiAlias = true
        mBorderPaint!!.color = borderColor
        mBorderPaint!!.style = Paint.Style.STROKE
        mBorderPaint!!.strokeWidth = mBorderWidth
        width = widthPx
        height = heightPX
        this.borderColor = borderColor
    }

    constructor(context: Context, borderWidth: Float, borderColor: Int, isTop: Boolean) : super(context) {
        mBorderWidth = Resources.getSystem().displayMetrics.density * borderWidth
        mBorderWidth = ImageLoaderUtils.dip2px(context, borderWidth).toFloat()
        mBorderPaint = Paint()
        mBorderPaint!!.isDither = true
        mBorderPaint!!.isAntiAlias = true
        mBorderPaint!!.color = borderColor
        mBorderPaint!!.style = Paint.Style.STROKE
        mBorderPaint!!.strokeWidth = mBorderWidth
        this.isTop = isTop
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        return if (width > 0) {
            circleCrop(pool, toTransform, outWidth, outHeight)
        } else {
            circleCrop(pool, toTransform)
        }
    }

    private fun circleCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
        if (source == null) return null

        val size = (Math.min(source.width, source.height) - mBorderWidth / 2).toInt()
        val x = (source.width - size) / 2
        val y: Int
        if (isTop) {
            y = 0
        } else {
            y = (source.height - size) / 2
        }
        // TODO this could be acquired from the pool too
        val squared = Bitmap.createBitmap(source, x, y, size, size)
        var result: Bitmap? = pool.get(size, size, Bitmap.Config.ARGB_8888)
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        }
        val canvas = Canvas(result!!)
        val paint = Paint()
        paint.shader = BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)
        if (mBorderPaint != null) {
            val borderRadius = r - mBorderWidth / 2
            canvas.drawCircle(r, r, borderRadius, mBorderPaint!!)
        }
        return result
    }

    private fun circleCrop(pool: BitmapPool, source: Bitmap?, outWidth: Int, outHeight: Int): Bitmap? {
        if (source == null) return null
        var result = pool.get(width, width, Bitmap.Config.ARGB_8888)
        var output = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint()
        paint.color = borderColor
        paint.isAntiAlias = true
        val rect = Rect(0, 0, width, width)
        val rectF = RectF(rect)
        canvas.drawRoundRect(rectF, width.toFloat(), width.toFloat(), paint)
        output = makeRoundCorner(output, width, borderColor)
        result = makeRoundCorner(source, (width - 2 * mBorderWidth).toInt(), 0)

        result = getAvatarInRoundBg(output, result)

        return result
    }

    //把头像保存成圆形图片
    fun makeRoundCorner(bitmap: Bitmap, px: Int, borderColor: Int): Bitmap {
        var bitmap = bitmap
        val output = Bitmap.createBitmap(px, px, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        var color = -0x1
        if (borderColor != 0) {
            color = borderColor
        }
        val paint = Paint()
        val rect = Rect(0, 0, px, px)
        val rectF = RectF(rect)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, px.toFloat(), px.toFloat(), paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        //        if(bitmap.getWidth()/2-67>0 && bitmap.getHeight()/2-67>0) {
        bitmap = Bitmap.createScaledBitmap(bitmap, px, px, true)
        //        }

        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    //加上圆形背景环
    private fun getAvatarInRoundBg(bg: Bitmap, avatar: Bitmap): Bitmap {
        var bg = bg
        var avatar = avatar
        // 生成画布图像
        val resultBitmap = Bitmap.createBitmap(width,
                width, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(resultBitmap)// 使用空白图片生成canvas

        // 将bmp1绘制在画布上
        bg = Bitmap.createScaledBitmap(bg, width, width, true)
        var srcRect = Rect(0, 0, width, width)// 截取bmp1中的矩形区域
        var dstRect = Rect(0, 0, width, width)// bmp1在目标画布中的位置
        canvas.drawBitmap(bg, srcRect, dstRect, null)

        avatar = Bitmap.createScaledBitmap(avatar, (width - 2 * mBorderWidth).toInt(), (width - 2 * mBorderWidth).toInt(), true)
        // 将bmp2绘制在画布上
        srcRect = Rect(0, 0, (width - 2 * mBorderWidth).toInt(), (width - 2 * mBorderWidth).toInt())// 截取bmp1中的矩形区域
        dstRect = Rect(mBorderWidth.toInt(), mBorderWidth.toInt(), (width - mBorderWidth).toInt(), (width - mBorderWidth).toInt())// bmp2在目标画布中的位置
        canvas.drawBitmap(avatar, srcRect, dstRect, null)
        // 将bmp1,bmp2合并显示
        return resultBitmap
    }

    override fun getId(): String {
        return javaClass.name
    }
}
