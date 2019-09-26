package com.jingxi.smartlife.pad.safe.mvp.view

import android.graphics.SurfaceTexture
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import com.intercom.sdk.IntercomConstants
import com.jingxi.smartlife.pad.safe.R
import com.jingxi.smartlife.pad.sdk.JXPadSdk
import com.jingxi.smartlife.pad.sdk.doorAccess.DoorAccessManager
import com.jingxi.smartlife.pad.sdk.doorAccess.base.DoorSessionManager
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorEvent
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessConversationUI
import com.wujia.businesslib.dialog.LoadingDialog
import com.wujia.businesslib.event.EventBaseButtonClick
import com.wujia.businesslib.event.EventBusUtil
import com.wujia.businesslib.event.IMiessageInvoke
import com.wujia.lib_common.base.BaseActivity
import com.wujia.lib_common.utils.LogUtil
import kotlinx.android.synthetic.main.activity_video_call.*

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-21
 * description ：
 */
class VideoCallActivity : BaseActivity(), View.OnClickListener, SurfaceHolder.Callback, DoorAccessConversationUI, TextureView.SurfaceTextureListener {
    override val layout: Int
        get() =  R.layout.activity_video_call
    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        this.surface=null
        manager?.updateCallWindow(sessionId,null)
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        this.surface=Surface(surface)
        updateSurface()
    }
    var surface:Surface?=null

    private var sessionId: String? = null
    private var manager: DoorAccessManager? = null
    private var loadingDialog: LoadingDialog? = null



    private var btnCallFlag: Boolean = false

    private var mSoundPool: SoundPool? = null
    private var sampleId: Int = 0
    private var mCurrentId: Int = 0

    private val eventBaseButtonClick = EventBaseButtonClick(object : IMiessageInvoke<EventBaseButtonClick> {
        override fun eventBus(event: EventBaseButtonClick) {
            if (IntercomConstants.kButtonUnlock == event.keyCmd) {
                onClick(btn_safe_open)
            } else if (IntercomConstants.kButtonPickup == event.keyCmd) {
                if (!btnCallFlag) {
                    onClick(btnCall)
                }
            }
        }
    })
    private var accepted = false
    internal var ringStoped = false

    override fun setContentView() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        super.setContentView()
    }


    override fun initEventAndData(savedInstanceState: Bundle?) {

        manager = JXPadSdk.getDoorAccessManager()
        manager!!.addConversationUIListener(this)

        sessionId = intent.getStringExtra(SESSION_ID)
        if (null == loadingDialog) {
            loadingDialog = mContext?.let { LoadingDialog(it) }
        }
        loadingDialog!!.setCancelOnTouchOutside(true)
        loadingDialog!!.setTitle("正在连接中...")
        loadingDialog!!.show()


        mSoundPool = SoundPool(1, AudioManager.STREAM_VOICE_CALL, 0)
        mSoundPool!!.setOnLoadCompleteListener { soundPool, sampleId, status ->
            if (!ringStoped) {
                startRing()
            }
        }
        sampleId = mSoundPool!!.load(mContext, R.raw.call_ring, 1)

//        surfaceView!!.holder.addCallback(this)
        textureView.surfaceTextureListener=this
        LogUtil.i("initEventAndData")
        EventBusUtil.register(eventBaseButtonClick)
    }

    internal fun startRing() {
        stopRing()
        mCurrentId = mSoundPool!!.play(sampleId, 1f, 1f, 1, -1, 1f)
        LogUtil.i("mCurrentId==$mCurrentId")
    }

    private fun stopRing() {
        LogUtil.i("stopRing")
        if (mCurrentId != 0) {
            mSoundPool!!.stop(mCurrentId)
            //            mCurrentId=0;
        }
        ringStoped = true
    }

    internal fun acceptCall() {
        if (!accepted) {
            accepted = true
            manager!!.acceptCall(sessionId)
        }
    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.btn_close) {
            finish()
        } else if (v.id == R.id.btnCall) {//接听
            if (!btnCallFlag) {
                stopRing()
                acceptCall()
                btnCall!!.setBackgroundResource(R.mipmap.btn_safe_hangup)
            } else {
                finish()
            }
            btnCallFlag = !btnCallFlag
        } else if (v.id == R.id.btn_safe_open) {//开门
            stopRing()
            acceptCall()
            manager!!.openDoor(sessionId)
            finish()
        } else if (v.id == R.id.btn_safe_refresh) {//refresh
            updateSurface()
        } else if (v.id == R.id.btn_safe_hangup) {//挂断
            finish()
        }
    }

    override fun finish() {
        LogUtil.i("finish")
        stopRing()
        manager!!.hangupCall(sessionId)
        super.finish()
    }

    override fun onDestroy() {
        LogUtil.i("onDestroy")
        super.onDestroy()
        manager?.apply{
            removeConversationUIListener(this@VideoCallActivity)
            hangupCall(sessionId)
        }
        EventBusUtil.unregister(eventBaseButtonClick)
        mSoundPool?.apply {
            stopRing()
            setOnLoadCompleteListener(null)
            release()
        }
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        LogUtil.i("surfaceCreated")
        updateSurface()

    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {

    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        LogUtil.i("surfaceDestroyed")
        manager!!.updateCallWindow(sessionId, null)
    }


    fun updateSurface() {
//        manager!!.updateCallWindow(sessionId, surfaceView)
        surface_foreground.visibility=View.VISIBLE
        manager!!.updateCallWindow(sessionId, surface)
        surface_foreground.postDelayed({surface_foreground.visibility=View.GONE},500)
    }

    override fun startTransPort(sessionID: String) {
        //        if (!TextUtils.equals(sessionId, sessionID)) {
        //            return;
        //        }
        LogUtil.i("VideoCallActivity 开始传输视频 sessionId " + sessionId!!)
        //        Toast.makeText(this, "开始传输视频", Toast.LENGTH_SHORT).show();
        surface_foreground!!.visibility = View.GONE
        if (loadingDialog != null) {
            loadingDialog!!.dismiss()
        }
        updateSurface()
    }

    override fun refreshEvent(event: DoorEvent) {

        if (!TextUtils.equals(sessionId, event.sessionId)) {
            return
        }
        LogUtil.i("VideoCallActivity refreshEvent " + event.cmd + "  sessionid = " + event.sessionId)

        if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandHangup)) {
            finish()
        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandSessionTimeout)) {
            Toast.makeText(this, "超时", Toast.LENGTH_SHORT).show()
            finish()
        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandPickupByOther)) {
            Toast.makeText(this, "其他用户接听", Toast.LENGTH_SHORT).show()
            finish()
        }
    }


    companion object{
        const val SESSION_ID="sessionId"
    }
}

private fun DoorAccessManager.updateCallWindow(sessionId: String?, surface: Surface?) {
    val intercom=DoorSessionManager.getCurrentIntercom(sessionId)
    if (intercom == null) {
        return
    }
    intercom.updateSurface(sessionId,  surface)
}
