package com.jingxi.smartlife.pad.safe.mvp.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView

import com.intercom.sdk.IntercomConstants
import com.intercom.sdk.IntercomObserver
import com.jingxi.smartlife.pad.safe.R
import com.jingxi.smartlife.pad.sdk.JXPadSdk
import com.jingxi.smartlife.pad.sdk.doorAccess.DoorAccessManager
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorDevice
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorEvent
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessConversationUI
import com.wujia.businesslib.base.DataManager
import com.wujia.businesslib.dialog.LoadingDialog
import com.wujia.businesslib.util.LoginUtil
import com.wujia.lib_common.base.BaseActivity
import com.wujia.lib_common.base.Constants
import com.wujia.lib_common.utils.LogUtil

class FullScreenActivity : BaseActivity(), View.OnClickListener, SurfaceHolder.Callback, DoorAccessConversationUI, SeekBar.OnSeekBarChangeListener, IntercomObserver.OnPlaybackListener {

    private var surfaceView: SurfaceView? = null
    private var mDoorAccessManager: DoorAccessManager? = null
    private var mSessionId: String? = null
    private var btnVol: ImageView? = null
    private var btnPlay: ImageView? = null
    private var btnRefresh: ImageView? = null
    private var tvPlaybackCurrentTime: TextView? = null
    private var tvPlaybackCountTime: TextView? = null
    private var seekBar: SeekBar? = null
    private var isPause: Boolean = false
    private var isPlayback: Boolean = false
    private var layoutLive: LinearLayout? = null
    private var layoutPlaybackl: LinearLayout? = null
    private var seek: Int = 0
    private var max: Int = 0
    private val unit = (1000 * 1000).toLong()

    //是否在滑动进度条
    private var isTouchSeek = false
    private var isSeeeionIdValid: Boolean = false
    private var loadingDialog: LoadingDialog? = null
    private var familyID: String? = null


    override fun getLayout(): Int {
        return R.layout.activity_safe_full
    }

    override fun initEventAndData(savedInstanceState: Bundle?) {

        layoutPlaybackl = findViewById(R.id.l1)
        layoutLive = findViewById(R.id.l2)

        surfaceView = findViewById(R.id.sv)
        btnVol = findViewById(R.id.btn2)
        btnRefresh = findViewById(R.id.btn9)

        findViewById<View>(R.id.btn1).setOnClickListener(this)
        btnVol!!.setOnClickListener(this)
        btnRefresh!!.setOnClickListener(this)

        mDoorAccessManager = JXPadSdk.getDoorAccessManager()
        mSessionId = intent.getStringExtra(Constants.INTENT_KEY_1)
        isPlayback = intent.getBooleanExtra(Constants.INTENT_KEY_2, false)

        if (isPlayback) {
            layoutPlaybackl!!.visibility = View.VISIBLE
            seekBar = `$`(R.id.safe_seekbar)
            btnPlay = findViewById(R.id.btn3)

            tvPlaybackCurrentTime = `$`(R.id.safe_progress_time_current_tv)
            tvPlaybackCountTime = `$`(R.id.safe_progress_time_count_tv)

            btnPlay!!.setOnClickListener(this)
            mDoorAccessManager!!.addPlayBackListener(this)

            max = intent.getIntExtra(Constants.INTENT_KEY_3, 0)
            if (max > 0) {
                seekBar!!.setOnSeekBarChangeListener(this)
                seekBar!!.max = max
                seek = intent.getIntExtra(Constants.INTENT_KEY_4, 0)
                isPause = intent.getBooleanExtra(Constants.INTENT_KEY_5, false)
                if (isPause) {//开始
                    btnPlay!!.setImageResource(R.mipmap.btn_safe_full_play)
                } else {//暂停
                    btnPlay!!.setImageResource(R.mipmap.btn_safe_pause)
                }
                seekBar!!.progress = seek
                //                mDoorAccessManager.seekPlayBack(mSessionId, seek * 100 / max);
                //                if (max > seek) {
                //                    mDoorAccessManager.startPlayBack(mSessionId);
                //                }
                tvPlaybackCurrentTime!!.text = format(seek)
                tvPlaybackCountTime!!.text = format(max)
            }
            mDoorAccessManager!!.updatePlayBackWindow(mSessionId, surfaceView)

        } else {
            try {
                familyID = DataManager.getDockKey()
            } catch (e: Exception) {
                LoginUtil.toLoginActivity()
                e.printStackTrace()
                return
            }

            layoutLive!!.visibility = View.VISIBLE
            mDoorAccessManager!!.addConversationUIListener(this)
            setVideo()
            //            surfaceView.postDelayed(new Runnable() {
            //                @Override
            //                public void run() {
            //                    mDoorAccessManager.updateCallWindow(mSessionId, surfaceView);
            //                }
            //            }, 500);
        }
        surfaceView!!.holder.addCallback(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn1) {
            val intent = Intent()
            intent.putExtra(Constants.INTENT_KEY_1, isPause)
            setResult(200, intent)
            finish()
        } else if (v.id == R.id.btn2) {//音量
        } else if (v.id == R.id.btn3) {//播放控制
            if (isPause) {//开始
                btnPlay!!.setImageResource(R.mipmap.btn_safe_pause)
                seekBar!!.setOnSeekBarChangeListener(this)
                mDoorAccessManager!!.startPlayBack(mSessionId)
                mDoorAccessManager!!.seekPlayBack(mSessionId, seek * 100 / max)
                mDoorAccessManager!!.updatePlayBackWindow(mSessionId, surfaceView)
            } else {//暂停
                btnPlay!!.setImageResource(R.mipmap.btn_safe_full_play)
                mDoorAccessManager!!.pausePlayBack(mSessionId)
            }
            isPause = !isPause
        } else if (v.id == R.id.btn9) {//刷新
            //            mDoorAccessManager.updateCallWindow(mSessionId, surfaceView);
            setVideo()
        }
    }

    private fun setVideo() {

        if (null == loadingDialog) {
            loadingDialog = LoadingDialog(mContext)
        }
        loadingDialog!!.setCancelOnTouchOutside(true)
        loadingDialog!!.setTitle("正在连接中...")
        loadingDialog!!.show()
        if (isSeeeionIdValid) {
            mDoorAccessManager!!.updateCallWindow(mSessionId, surfaceView)
            loadingDialog!!.dismiss()
            return
        }
        val list = mDoorAccessManager!!.getDevices(familyID)
        for (device in list) {
            if (DoorDevice.TYPE_UNIT == device.myDeviceType) {
                mSessionId = mDoorAccessManager!!.monitor(familyID, device)
                LogUtil.i("SafeOutsideFragment mSessionId " + mSessionId!!)

                mDoorAccessManager!!.updateCallWindow(mSessionId, surfaceView)
                return
            }
        }
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        if (isPlayback) {
            mDoorAccessManager!!.updatePlayBackWindow(mSessionId, surfaceView)
        } else {
            mDoorAccessManager!!.updateCallWindow(mSessionId, surfaceView)
        }
    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {

    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        if (isPlayback) {
            mDoorAccessManager!!.updatePlayBackWindow(mSessionId, null)
        } else {
            mDoorAccessManager!!.updateCallWindow(mSessionId, null)
        }
    }

    override fun startTransPort(sessionID: String) {
        if (!TextUtils.equals(sessionID, mSessionId)) {
            return
        }
        isSeeeionIdValid = true
        LogUtil.i("SafeOutsideFragment 开始传输视频 mSessionId " + mSessionId!!)
        if (loadingDialog != null) {
            loadingDialog!!.dismiss()
        }
    }

    override fun refreshEvent(event: DoorEvent) {

        if (!TextUtils.equals(mSessionId, event.sessionId)) {
            return
        }
        if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandHangup)) {
            finish()
        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandSessionTimeout)) {
            isSeeeionIdValid = false
            //            finish();
        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandPickupByOther)) {
            finish()
        }

    }


    override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
        LogUtil.i("FullScreenActivity onProgressChanged seek = $i")
        seek = i
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        isTouchSeek = true
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        isTouchSeek = false
        mDoorAccessManager!!.seekPlayBack(mSessionId, seek * 100 / max)
    }

    override fun onMediaPlayEvent(session_id: String, event: Int, value: Long) {
        if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventDuration) {
            max = (value / unit).toInt()
            seekBar!!.max = max
        } else if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventProgress) {
            seek = (value / unit).toInt()
            if (isTouchSeek) {
                return
            }
            seekBar!!.setOnSeekBarChangeListener(null)
            seekBar!!.progress = seek
            seekBar!!.setOnSeekBarChangeListener(this)

            tvPlaybackCurrentTime!!.text = format(seek)
            tvPlaybackCountTime!!.text = format(max)
        } else if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventCompleted) {
            seek = max
            seekBar!!.setOnSeekBarChangeListener(null)
            seekBar!!.progress = max
            seekBar!!.setOnSeekBarChangeListener(this)
            mDoorAccessManager!!.pausePlayBack(mSessionId)
        }
        LogUtil.i("FullScreenActivity onMediaPlayEvent event = $event  value = $value  seek = $seek  max = $max")

    }

    private fun format(time: Int): String {

        val m = time / 60
        val s = time % 60

        val mstr: String
        val sstr: String
        if (m < 10) {
            mstr = "0$m"
        } else {
            mstr = m.toString()
        }
        if (s < 10) {
            sstr = "0$s"
        } else {
            sstr = s.toString()
        }

        return "$mstr:$sstr"
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlayback) {
            mDoorAccessManager!!.updatePlayBackWindow(mSessionId, null)
            mDoorAccessManager!!.pausePlayBack(mSessionId)
        } else {
            mDoorAccessManager!!.updateCallWindow(mSessionId, null)
            mDoorAccessManager!!.hangupCall(mSessionId)
        }
        surfaceView!!.holder.removeCallback(this)
        DoorAccessManager.getInstance().removeConversationUIListener(this)
        DoorAccessManager.getInstance().removePlayBackListener(this)

    }
}
