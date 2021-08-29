package com.wujia.lib.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import androidx.core.content.ContextCompat
import com.wujia.lib.uikit.R
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.min
import kotlin.math.sqrt

/**
 * 作用: 圆弧形 SeekBar
 * 作者: GcsSloop
 * 摘要: 该 SeekBar 实现的核心原理非常简单,其绘制内容实际上为一个圆弧,只用了一条 Path,
 * 尽管核心原理简单,但仍有以下细节需要进行注意:
 * 1. 画布旋转导致的坐标系问题.
 * -  为了让开口方向可控,并且实现起来简单,进行了画布旋转,因此实际显示的坐标系下的坐标和绘制坐标系坐标是不同的,尤其需要注意.
 * 2.当前进度确定问题
 * -  进度是由角度确定的, 先根据当前点击位置和中心点连线与水平线的夹角表示当前角度,
 * -  根据该角度和实际圆弧的角度相关信息推算出当前进度百分比.
 * -  根据百分比和最大数值确定当前实际的进度.
 * 3. 拖动与点击
 * -  为了防止误触, 只有手指按下位置在拖动按钮附近时才可以执行拖动.
 * -  但是用户单击时,可以可以直接跳转进度, 当然,只有点击在圆弧进度条的区域内才允许设置新的进度.
 * -  如何判断是否点击在圆弧区域内,使用了 Region 的相关方法,该 Region 区域是从 Paint 的 getFillPath 得到的.
 * 4. 进度回调去重
 * -  用户拖动时,判断是否和上次进度相同,如果相同,则不发送回调.
 * 5. 防止突变
 * -  由于进度条时圆弧形状的,因此进度可能会从 0.0 直接突变到 1.0 或者相反,因此在计算进度与当前进度差异过大时,禁止改变当前进度.
 */
class ArcSeekBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    // 可配置数据
    private var mArcColors: IntArray? = null       // Seek 颜色
    private var mArcWidth: Float = 0.toFloat()        // Seek 宽度
    private var mOpenAngle: Float = 0.toFloat()       // 开口的角度大小 0 - 360
    private var mRotateAngle: Float = 0.toFloat()     // 旋转角度
    private var mBorderWidth: Int = 0       // 描边宽度
    private var mBorderColor: Int = 0       // 描边颜色

    private var mThumbColor: Int = 0        // 拖动按钮颜色
    private var mThumbWidth: Float = 0.toFloat()      // 拖动按钮宽度
    private var mThumbRadius: Float = 0.toFloat()     // 拖动按钮半径
    private var mThumbMode: Int = 0         // 拖动按钮模式

    private var mMaxValue: Int = 0          // 最大数值

    private var mCenterX: Float = 0.toFloat()         // 圆弧 SeekBar 中心点 X
    private var mCenterY: Float = 0.toFloat()         // 圆弧 SeekBar 中心点

    private var mThumbX: Float = 0.toFloat()         // 圆弧 SeekBar 中心点 X
    private var mThumbY: Float = 0.toFloat()         // 圆弧 SeekBar 中心点

    private var mSeekBgPath: Path? = null
    private var mSeekPath: Path? = null
    private var mBorderPath: Path? = null
    private var mArcBgPaint: Paint? = null
    private var mArcPaint: Paint? = null
    private var mThumbPaint: Paint? = null
    private var mBorderPaint: Paint? = null

    private var mTempPos: FloatArray? = null
    private var mTempTan: FloatArray? = null
    private var mSeekPathMeasure: PathMeasure? = null

    private var mProgressPresent = 0f         // 当前进度百分比
    private var mCanDrag = false           // 是否允许拖动
    private val mAllowTouchSkip = false    // 是否允许越过边界
    private var mDetector: GestureDetector? = null
    private var mInvertMatrix: Matrix? = null               // 逆向 Matrix, 用于计算触摸坐标和绘制坐标的转换
    private var mArcRegion: Region? = null                  // ArcPath的实际区域大小,用于判定单击事件
    private var content: RectF? = null//中心区域

    private var mThumbBmp: Bitmap? = null
    private var mThumbRect: Rect? = null

    private var moved = false
    private var lastProgress = -1

    /**
     * 获取当前进度数值
     *
     * @return 当前进度数值
     */
    //--- 对外接口 ---------------------------------------------------------------------------------

    /**
     * 设置进度
     *
     * @param progress 进度值
     */
    var progress: Int
        get() = (mProgressPresent * mMaxValue).toInt()
        set(progress) {
            var progress = progress
            println("setProgress = $progress")
            if (progress < 0) progress = 0
            if (progress > mMaxValue) progress = mMaxValue
            mProgressPresent = progress * 1.0f / mMaxValue
            println("setProgress = $mProgressPresent")
            if (null != mOnProgressChangeListener) {
                mOnProgressChangeListener!!.onProgressChanged(this, progress, false)
            }

            postInvalidate()
        }

    internal var mOnProgressChangeListener: OnProgressChangeListener? = null

    init {
        initAttrs(context, attrs)
        initData()
        initPaint()
    }

    //--- 初始化 -----------------------------------------------------------------------------------

    // 初始化各种属性
    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ArcSeekBar)
        mArcColors = getArcColors(context, ta)
        mArcWidth = ta.getDimensionPixelSize(R.styleable.ArcSeekBar_arc_width, dp2px(DEFAULT_ARC_WIDTH)).toFloat()
        mOpenAngle = ta.getFloat(R.styleable.ArcSeekBar_arc_open_angle, DEFAULT_OPEN_ANGLE)
        mRotateAngle = ta.getFloat(R.styleable.ArcSeekBar_arc_rotate_angle, DEFAULT_ROTATE_ANGLE)
        mMaxValue = ta.getInt(R.styleable.ArcSeekBar_arc_max, DEFAULT_MAX_VALUE)
        var progress = ta.getInt(R.styleable.ArcSeekBar_arc_progress, 0)
        mBorderWidth = ta.getDimensionPixelSize(R.styleable.ArcSeekBar_arc_border_width, dp2px(DEFAULT_BORDER_WIDTH))
        mBorderColor = ta.getColor(R.styleable.ArcSeekBar_arc_border_color, DEFAULT_BORDER_COLOR)

        mThumbColor = ta.getColor(R.styleable.ArcSeekBar_arc_thumb_color, DEFAULT_THUMB_COLOR)
        mThumbRadius = ta.getDimensionPixelSize(R.styleable.ArcSeekBar_arc_thumb_radius, dp2px(DEFAULT_THUMB_RADIUS)).toFloat()
        mThumbWidth = ta.getDimensionPixelSize(R.styleable.ArcSeekBar_arc_thumb_width, dp2px(DEFAULT_THUMB_WIDTH)).toFloat()
        mThumbMode = ta.getInt(R.styleable.ArcSeekBar_arc_thumb_mode, THUMB_MODE_STROKE)
        ta.recycle()
    }

    // 获取 Arc 颜色数组
    private fun getArcColors(context: Context, ta: TypedArray): IntArray {
        val ret: IntArray
        var resId = ta.getResourceId(R.styleable.ArcSeekBar_arc_colors, 0)
        if (0 == resId) {
            resId = R.array.arc_colors_default
        }
        val colorArray = context.resources.obtainTypedArray(resId)
        ret = IntArray(colorArray.length())
        for (i in 0 until colorArray.length()) {
            ret[i] = colorArray.getColor(i, 0)
        }
        return ret
    }

    // 初始化数据
    private fun initData() {
        mSeekPath = Path()

        mSeekBgPath = Path()
        mBorderPath = Path()
        mSeekPathMeasure = PathMeasure()
        mTempPos = FloatArray(2)
        mTempTan = FloatArray(2)

        mDetector = GestureDetector(context, OnClickListener())
        mInvertMatrix = Matrix()
        mArcRegion = Region()
    }

    // 初始化画笔
    private fun initPaint() {
        initArcPaint()
        initThumbPaint()
        initBorderPaint()
    }

    // 初始化圆弧画笔
    private fun initArcPaint() {
        //背景
        mArcPaint = Paint()
        mArcPaint!!.isAntiAlias = true
        mArcPaint!!.strokeWidth = mArcWidth
        mArcPaint!!.style = Paint.Style.STROKE
        mArcPaint!!.strokeCap = Paint.Cap.ROUND

        mArcBgPaint = Paint()
        mArcBgPaint!!.isAntiAlias = true
        mArcBgPaint!!.strokeWidth = mArcWidth
        mArcBgPaint!!.style = Paint.Style.STROKE
        mArcBgPaint!!.strokeCap = Paint.Cap.ROUND
        mArcBgPaint!!.color = ContextCompat.getColor(context, R.color.clr_eaeaea)
    }

    // 初始化拖动按钮画笔
    private fun initThumbPaint() {
        mThumbPaint = Paint()
        mThumbPaint!!.isAntiAlias = true
        mThumbPaint!!.color = mThumbColor
        mThumbPaint!!.strokeWidth = mThumbWidth
        mThumbPaint!!.strokeCap = Paint.Cap.ROUND
        when (mThumbMode) {
            THUMB_MODE_FILL -> {
                mThumbPaint!!.style = Paint.Style.FILL_AND_STROKE
            }
            THUMB_MODE_FILL_STROKE -> {
                mThumbPaint!!.style = Paint.Style.FILL_AND_STROKE
            }
            else -> {
                mThumbPaint!!.style = Paint.Style.STROKE
            }
        }
        mThumbPaint!!.textSize = 56f

        setLayerType(LAYER_TYPE_SOFTWARE, null)//对单独的View在运行时阶段禁用硬件加速
        mThumbPaint!!.setShadowLayer(5f, 0f, 0f, ContextCompat.getColor(context, R.color.c75))


        mThumbBmp = BitmapFactory.decodeResource(resources, R.drawable.bg_white_circle)
        mThumbRect = Rect(0, 0, mThumbBmp!!.width, mThumbBmp!!.height)
    }

    // 初始化拖动按钮画笔
    private fun initBorderPaint() {
        mBorderPaint = Paint()
        mBorderPaint!!.isAntiAlias = true
        mBorderPaint!!.color = mBorderColor
        mBorderPaint!!.strokeWidth = mBorderWidth.toFloat()
        mBorderPaint!!.style = Paint.Style.STROKE
    }

    //--- 初始化结束 -------------------------------------------------------------------------------

    //--- 状态存储 ---------------------------------------------------------------------------------

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable("superState", super.onSaveInstanceState())
        bundle.putFloat(KEY_PROGRESS_PRESENT, mProgressPresent)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var state = state
        if (state is Bundle) {
            val bundle = state as Bundle?
            this.mProgressPresent = bundle!!.getInt(KEY_PROGRESS_PRESENT).toFloat()
            state = bundle.getParcelable("superState")
        }
        super.onRestoreInstanceState(state)
    }

    //--- 状态存储结束 -----------------------------------------------------------------------------

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var ws = MeasureSpec.getSize(widthMeasureSpec)     //取出宽度的确切数值
        var wm = MeasureSpec.getMode(widthMeasureSpec)     //取出宽度的测量模式
        var hs = MeasureSpec.getSize(heightMeasureSpec)    //取出高度的确切数值
        var hm = MeasureSpec.getMode(heightMeasureSpec)    //取出高度的测量模

        if (wm == MeasureSpec.UNSPECIFIED) {
            wm = MeasureSpec.EXACTLY
            ws = dp2px(DEFAULT_EDGE_LENGTH)
        } else if (wm == MeasureSpec.AT_MOST) {
            wm = MeasureSpec.EXACTLY
            ws = min(dp2px(DEFAULT_EDGE_LENGTH), ws)
        }
        if (hm == MeasureSpec.UNSPECIFIED) {
            hm = MeasureSpec.EXACTLY
            hs = dp2px(DEFAULT_EDGE_LENGTH)
        } else if (hm == MeasureSpec.AT_MOST) {
            hm = MeasureSpec.EXACTLY
            hs = min(dp2px(DEFAULT_EDGE_LENGTH), hs)
        }
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(ws, wm), MeasureSpec.makeMeasureSpec(hs, hm))
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 计算在当前大小下,内容应该显示的大小和起始位置
        val safeW = w - paddingLeft - paddingRight
        val safeH = h - paddingTop - paddingBottom
        val edgeLength: Float
        val startX: Float
        val startY: Float
        val fix = mArcWidth / 2 + mBorderWidth  // 修正距离,画笔宽度的修正
        if (safeW < safeH) {
            // 宽度小于高度,以宽度为准
            edgeLength = safeW - fix
            startX = paddingLeft.toFloat()
            startY = (safeH - safeW) / 2.0f + paddingTop
        } else {
            // 宽度大于高度,以高度为准
            edgeLength = safeH - fix
            startX = (safeW - safeH) / 2.0f + paddingLeft
            startY = paddingTop.toFloat()
        }

        // 得到显示区域和中心的
        content = RectF(startX + fix, startY + fix, startX + edgeLength, startY + edgeLength)
        mCenterX = content!!.centerX()
        mCenterY = content!!.centerY()

        // 得到路径
        mSeekBgPath!!.reset()
        mSeekBgPath!!.addArc(content!!, mOpenAngle / 2, CIRCLE_ANGLE - mOpenAngle)
        mSeekPathMeasure!!.setPath(mSeekBgPath, false)
        computeThumbPos(mProgressPresent)

        // 计算渐变数组
        val startPos = mOpenAngle / 2 / CIRCLE_ANGLE
        val stopPos = (CIRCLE_ANGLE - mOpenAngle / 2) / CIRCLE_ANGLE
        val len = mArcColors!!.size - 1
        val distance = (stopPos - startPos) / len
        val pos = FloatArray(mArcColors!!.size)
        for (i in mArcColors!!.indices) {
            pos[i] = startPos + distance * i
        }
        val gradient = SweepGradient(content!!.centerX(), content!!.centerY(), mArcColors!!, pos)
        mArcPaint!!.shader = gradient

        mInvertMatrix!!.reset()
        mInvertMatrix!!.preRotate(-mRotateAngle, mCenterX, mCenterY)

        mArcBgPaint!!.getFillPath(mSeekBgPath, mBorderPath)
        mBorderPath!!.close()
        mArcRegion!!.setPath(mBorderPath!!, Region(0, 0, w, h))

        //背影轨迹
        mSeekPath!!.reset()
        mSeekPath!!.addArc(content!!, mOpenAngle / 2, (CIRCLE_ANGLE - mOpenAngle) * mProgressPresent)

    }

    // 具体绘制
    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.rotate(mRotateAngle, mCenterX, mCenterY)
        canvas.drawPath(mSeekBgPath!!, mArcBgPaint!!)
        canvas.drawPath(mSeekPath!!, mArcPaint!!)
        if (mBorderWidth > 0) {
            canvas.drawPath(mBorderPath!!, mBorderPaint!!)
        }
        canvas.drawCircle(mThumbX, mThumbY, mThumbRadius, mThumbPaint!!)
        //        canvas.drawBitmap(mThumbBmp,mThumbX,mThumbY,mThumbPaint);
        canvas.restore()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        when (event.actionMasked) {
            ACTION_DOWN -> {
                moved = false
                judgeCanDrag(event)
                if (null != mOnProgressChangeListener) {
                    mOnProgressChangeListener!!.onStartTrackingTouch(this)
                }
            }
            ACTION_MOVE ->// move@
            {
                var flag:Boolean=true
                if (!mCanDrag) {
//                    return@move
                    flag=false
                }
                val tempProgressPresent = getCurrentProgress(event.x, event.y)
                if (!mAllowTouchSkip) {
                    // 不允许突变
                    if (abs(tempProgressPresent - mProgressPresent) > 0.5f) {
//                        return@move
                        flag=false
                    }
                }
                if(flag){
                    // 允许突变 或者非突变
                    mProgressPresent = tempProgressPresent
                    computeThumbPos(mProgressPresent)
                    // 事件回调
                    if (null != mOnProgressChangeListener && progress != lastProgress) {
                        mOnProgressChangeListener!!.onProgressChanged(this, progress, true)
                        lastProgress = progress
                    }

                    //背影轨迹
                    mSeekPath!!.reset()
                    mSeekPath!!.addArc(content!!, mOpenAngle / 2, (CIRCLE_ANGLE - mOpenAngle) * mProgressPresent)

                    moved = true
                }

            }
            ACTION_UP, ACTION_CANCEL -> if (null != mOnProgressChangeListener && moved) {
                mOnProgressChangeListener!!.onStopTrackingTouch(this)
            }
        }
        mDetector!!.onTouchEvent(event)
        invalidate()
        return true
    }

    // 判断是否允许拖动
    private fun judgeCanDrag(event: MotionEvent) {
        val pos = floatArrayOf(event.x, event.y)
        mInvertMatrix!!.mapPoints(pos)
        mCanDrag = getDistance(pos[0], pos[1]) <= mThumbRadius * 3
    }

    private inner class OnClickListener : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            // 判断是否点击在了进度区域
            if (!isInArcProgress(e.x, e.y)) return false
            // 点击允许突变
            mProgressPresent = getCurrentProgress(e.x, e.y)
            computeThumbPos(mProgressPresent)
            // 事件回调
            if (null != mOnProgressChangeListener) {
                mOnProgressChangeListener!!.onProgressChanged(this@ArcSeekBar, progress, true)
                mOnProgressChangeListener!!.onStopTrackingTouch(this@ArcSeekBar)
            }
            return true
        }
    }

    // 判断该点是否在进度条上面
    private fun isInArcProgress(px: Float, py: Float): Boolean {
        val pos = floatArrayOf(px, py)
        mInvertMatrix!!.mapPoints(pos)
        return mArcRegion!!.contains(pos[0].toInt(), pos[1].toInt())
    }

    // 获取当前进度理论进度数值
    private fun getCurrentProgress(px: Float, py: Float): Float {
        val diffAngle = getDiffAngle(px, py)
        var progress = diffAngle / (CIRCLE_ANGLE - mOpenAngle)
        if (progress < 0) progress = 0f
        if (progress > 1) progress = 1f
        return progress
    }

    // 获得当前点击位置所成角度与开始角度之间的数值差
    private fun getDiffAngle(px: Float, py: Float): Float {
        val angle = getAngle(px, py)
        var diffAngle: Float
        diffAngle = angle - mRotateAngle
        if (diffAngle < 0) {
            diffAngle = (diffAngle + CIRCLE_ANGLE) % CIRCLE_ANGLE
        }
        diffAngle -= mOpenAngle / 2
        return diffAngle
    }

    // 计算指定位置与内容区域中心点的夹角
    private fun getAngle(px: Float, py: Float): Float {
        var angle = (atan2((py - mCenterY).toDouble(), (px - mCenterX).toDouble()) * 180 / 3.14f).toFloat()
        if (angle < 0) {
            angle += 360f
        }
        return angle
    }

    // 计算指定位置与上次位置的距离
    private fun getDistance(px: Float, py: Float): Float {
        return sqrt(((px - mThumbX) * (px - mThumbX) + (py - mThumbY) * (py - mThumbY)).toDouble()).toFloat()
    }

    private fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
    }

    // 计算拖动快应该显示的位置
    private fun computeThumbPos(present: Float) {
        var present = present
        if (present < 0) present = 0f
        if (present > 1) present = 1f
        val distance = mSeekPathMeasure!!.length * present
        mSeekPathMeasure!!.getPosTan(distance, mTempPos, mTempTan)
        mThumbX = mTempPos!![0]
        mThumbY = mTempPos!![1]
    }

    fun setOnProgressChangeListener(onProgressChangeListener: OnProgressChangeListener) {
        mOnProgressChangeListener = onProgressChangeListener
    }

    interface OnProgressChangeListener {
        /**
         * 进度发生变化
         *
         * @param seekBar  拖动条
         * @param progress 当前进度数值
         * @param isUser   是否是用户操作, true 表示用户拖动, false 表示通过代码设置
         */
        fun onProgressChanged(seekBar: ArcSeekBar, progress: Int, isUser: Boolean)

        /**
         * 用户开始拖动
         *
         * @param seekBar 拖动条
         */
        fun onStartTrackingTouch(seekBar: ArcSeekBar)

        /**
         * 用户结束拖动
         *
         * @param seekBar 拖动条
         */
        fun onStopTrackingTouch(seekBar: ArcSeekBar)
    }

    companion object {
        private const val DEFAULT_EDGE_LENGTH = 260              // 默认宽高

        private const val CIRCLE_ANGLE = 360f                  // 圆周角
        private const val DEFAULT_ARC_WIDTH = 40                // 默认宽度 dp
        private const val DEFAULT_OPEN_ANGLE = 120f            // 开口角度
        private const val DEFAULT_ROTATE_ANGLE = 90f           // 旋转角度
        private const val DEFAULT_BORDER_WIDTH = 0              // 默认描边宽度
        private const val DEFAULT_BORDER_COLOR = -0x1     // 默认描边颜色

        private const val DEFAULT_THUMB_COLOR = -0x1      // 拖动按钮颜色
        private const val DEFAULT_THUMB_WIDTH = 2               // 拖动按钮描边宽度 dp
        private const val DEFAULT_THUMB_RADIUS = 15             // 拖动按钮半径 dp

        private const val THUMB_MODE_STROKE = 0                 // 拖动按钮模式 - 描边
        private const val THUMB_MODE_FILL = 1                   // 拖动按钮模式 - 填充
        private const val THUMB_MODE_FILL_STROKE = 2            // 拖动按钮模式 - 填充+描边

        private const val DEFAULT_MAX_VALUE = 100               // 默认最大数值

        private const val KEY_PROGRESS_PRESENT = "PRESENT"   // 用于存储和获取当前百分比
    }
}
