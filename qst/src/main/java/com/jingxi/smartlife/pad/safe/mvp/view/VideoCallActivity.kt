package com.jingxi.smartlife.pad.safe.mvp.view

//import com.jingxi.smartlife.pad.sdk.JXPadSdk
//import com.jingxi.smartlife.pad.sdk.doorAccess.DoorAccessManager
//import com.jingxi.smartlife.pad.sdk.doorAccess.base.DoorSessionManager
//import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorEvent
//import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessConversationUI


import android.media.AudioManager
import android.media.SoundPool
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.Surface
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import com.intercom.sdk.IntercomConstants
import com.jingxi.smartlife.pad.safe.R
import com.sipphone.sdk.SipCoreManager
import com.sipphone.sdk.SipCoreUtils
import com.wujia.businesslib.dialog.LoadingDialog
import com.wujia.businesslib.event.EventBaseButtonClick
import com.wujia.businesslib.event.EventBusUtil
import com.wujia.businesslib.event.IMiessageInvoke
import com.wujia.lib_common.base.BaseActivity
import com.wujia.lib_common.utils.LogUtil
import kotlinx.android.synthetic.main.activity_video_call.*
import org.linphone.core.CallDirection
import org.linphone.core.LinphoneCall
import org.linphone.core.LinphoneCore
import org.linphone.core.LinphoneCoreListenerBase
import org.linphone.mediastream.video.AndroidVideoWindowImpl

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-21
 * description ：
 */
class VideoCallActivity : BaseActivity(), View.OnClickListener//, SurfaceHolder.Callback
{
    private  var mListener: LinphoneCoreListenerBase?=null
    private  var androidVideoWindowImpl: AndroidVideoWindowImpl?=null
    private var mCaptureView: SurfaceView?=null
    private var mVideoView: SurfaceView?=null
    override val layout: Int
        get() =  R.layout.activity_video_call
//    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
//    }
//
//    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
//    }
//
//    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
//        this.surface=null
//        manager?.updateCallWindow(sessionId,null)
//        return true
//    }
//
//    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
//        this.surface=Surface(surface)
//        updateSurface()
//    }
    var surface:Surface?=null

    private var sessionId: String? = null
//    private var manager: DoorAccessManager? = null
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
        started=true
//        manager = JXPadSdk.getDoorAccessManager()
//        manager!!.addConversationUIListener(this)

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
//        textureView.surfaceTextureListener=this
        LogUtil.i("initEventAndData")
        EventBusUtil.register(eventBaseButtonClick)

        mListener = object : LinphoneCoreListenerBase() {
            override fun callState(lc: LinphoneCore?, call: LinphoneCall?, state: LinphoneCall.State?, message: String?) {
                LogUtil.e( "${javaClass.simpleName} registrationState state = " + state!!.toString() + " message = " + message)
                if(call?.direction=== CallDirection.Incoming&&state=== LinphoneCall.State.CallEnd) {
                    finish()
                }
            }
        }

        mVideoView =surfaceView
//		mCaptureView = (SurfaceView) view.findViewById(R.id.videoCaptureSurface);
//		mCaptureView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); // Warning useless because value is ignored and automatically set by new APIs.

        try {
            fixZOrder(mVideoView, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        androidVideoWindowImpl = AndroidVideoWindowImpl(mVideoView, mCaptureView, object : AndroidVideoWindowImpl.VideoWindowListener {
            override fun onVideoRenderingSurfaceReady(vw: AndroidVideoWindowImpl, surface: SurfaceView) {
                SipCoreManager.getLc().setVideoWindow(vw)
                mVideoView = surface
            }

            override fun onVideoRenderingSurfaceDestroyed(vw: AndroidVideoWindowImpl) {
                val lc = SipCoreManager.getLc()
                lc?.setVideoWindow(null)
            }

            override fun onVideoPreviewSurfaceReady(vw: AndroidVideoWindowImpl, surface: SurfaceView) {
                mCaptureView = surface
                SipCoreManager.getLc().setPreviewWindow(mCaptureView)
            }

            override fun onVideoPreviewSurfaceDestroyed(vw: AndroidVideoWindowImpl) {
                // Remove references kept in jni code and restart camera
                SipCoreManager.getLc().setPreviewWindow(null)
            }
        })

    }
    public override fun onResume() {
        super.onResume()
        val lc = SipCoreManager.getLcIfManagerNotDestroyedOrNull()
        lc?.removeListener(mListener)
        lc?.addListener(mListener)
        if (mVideoView != null) {
            (mVideoView as? GLSurfaceView)?.onResume()
        }

        if (androidVideoWindowImpl != null) {
            synchronized(androidVideoWindowImpl!!) {
                SipCoreManager.getLc().setVideoWindow(androidVideoWindowImpl)
            }
        }
// 设置一个当前Call对象视频第一帧解码完成监听器，收到回调之后，隐藏加载的UI界面
        // 这里仅提供一个简单的实例，避免网络视频太慢而现实黑屏
        if (SipCoreManager.getLc().currentCall != null) {
            SipCoreManager.getLc().currentCall.setListener(object : LinphoneCall.LinphoneCallListener {
                override fun onNextVideoFrameDecoded(linphoneCall: LinphoneCall) {
                    surface_foreground!!.visibility = View.GONE
                    loadingDialog?.dismiss()
                    // 第一帧视频解码成功后，可以调用下面的接口，停止本地视频的发送
                    //					SipCoreManager.getInstance().stopLocalVideo(true);
                }

                override fun tmmbrReceived(linphoneCall: LinphoneCall, i: Int, i1: Int) {

                }
            })
        }

        if (isVideoEnabled(SipCoreManager.getLc().currentCall)) {
            // 视频通话时，不显示视频相关，应该是等到接收到视频流时才显示吧???
//            displayVideoCall(false)
        } else if (SipCoreManager.getLc().isInConference) {
            displayConference()
        } else {
            //			SipCoreManager.startProximitySensorForActivity(this);
            SipCoreManager.getInstance().enableProximitySensing(true)
        }
    }

    private fun displayConference() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        LogUtil.e("displayConference not implemented")
    }

    private fun isVideoEnabled(call: LinphoneCall?): Boolean {
        return call?.currentParamsCopy?.videoEnabled ?: false
    }
    public override fun onPause() {
//        val lc = SipCoreManager.getLcIfManagerNotDestroyedOrNull()
//        lc?.removeListener(mListener)
        if (androidVideoWindowImpl != null) {
            synchronized(androidVideoWindowImpl!!) {
                /*
				 * this call will destroy native opengl renderer which is used by
				 * androidVideoWindowImpl
				 */
                SipCoreManager.getLc().setVideoWindow(null)
            }
        }

        if (mVideoView != null) {
            (mVideoView as? GLSurfaceView)?.onPause()
        }

        super.onPause()
    }


    private fun fixZOrder(video: SurfaceView?, preview: SurfaceView?) {
        video?.setZOrderOnTop(false)
        preview?.setZOrderOnTop(true)
        preview?.setZOrderMediaOverlay(true) // Needed to be able to display control layout over
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
//            manager!!.acceptCall(sessionId)
            accept()
        }
    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.btn_close) {
            finish()
        } else if (v.id == R.id.btnCall) {//接听
            if (!btnCallFlag) {
                stopRing()
                acceptCall()
//                accept()
                btnCall!!.setBackgroundResource(R.mipmap.btn_safe_hangup)
            } else {
                finish()
            }
            btnCallFlag = !btnCallFlag
        } else if (v.id == R.id.btn_safe_open) {//开门
            stopRing()
            acceptCall()
//            manager!!.openDoor(sessionId)
            SipCoreManager.getLc().sendDtmf('#')
//            hangUp()
//            finish()
        } else if (v.id == R.id.btn_safe_refresh) {//refresh
            updateSurface()
        } else if (v.id == R.id.btn_safe_hangup) {//挂断
            hangUp()
            finish()
        }
    }
    private fun hangUp() {
        val lc = SipCoreManager.getLc()
        val calls = SipCoreUtils.getLinphoneCalls(SipCoreManager.getLc())
        for (call in calls) {
            if(call.direction== CallDirection.Incoming) {
                lc.terminateCall(call)
//                mCall = call
//                break
            }
        }
//        val lc = SipCoreManager.getLc()
//        val currentCall = lc.currentCall
//
//        if (currentCall != null) {
//            lc.terminateCall(currentCall)
//        } else if (lc.isInConference) {
//            lc.terminateConference()
//        } else {
//            lc.terminateAllCalls()
//        }
    }

    private fun accept() {
        val lCore = SipCoreManager.getLc()

        val calls = SipCoreUtils.getLinphoneCalls(SipCoreManager.getLc())
        for (call in calls) {
            if(call.direction== CallDirection.Incoming) {
                lCore.acceptCall(call)
//                mCall = call
//                break
            }
        }

//        val currentCall = lCore.currentCall
//        if (currentCall != null) {
//            try {
//                lCore.acceptCall(currentCall)
//            } catch (e: LinphoneCoreException) {
//                e.printStackTrace()
//            }
//
//        }
    }

    override fun finish() {
        LogUtil.i("finish")
        stopRing()
//        manager!!.hangupCall(sessionId)
        hangUp()
        super.finish()
        started=false
    }

    override fun onDestroy() {
        started=false
        LogUtil.i("onDestroy")
        super.onDestroy()
//        manager?.apply{
//            removeConversationUIListener(this@VideoCallActivity)
//            hangupCall(sessionId)
//        }
        hangUp()
        EventBusUtil.unregister(eventBaseButtonClick)
        mSoundPool?.apply {
            stopRing()
            setOnLoadCompleteListener(null)
            release()
        }
        // Prevent linphone from crashing if correspondent hang up while you are rotating
        androidVideoWindowImpl?.release()
        androidVideoWindowImpl = null

    }

//    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
//        LogUtil.i("surfaceCreated")
//        updateSurface()
//
//    }
//
//    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {
//
//    }
//
//    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
//        LogUtil.i("surfaceDestroyed")
////        manager!!.updateCallWindow(sessionId, null)
//    }


    fun updateSurface() {
//        manager!!.updateCallWindow(sessionId, surfaceView)
        surface_foreground.visibility=View.VISIBLE
//        manager!!.updateCallWindow(sessionId, surface)
        surface_foreground.postDelayed({surface_foreground.visibility=View.GONE},500)
    }

//    override fun startTransPort(sessionID: String) {
//        //        if (!TextUtils.equals(sessionId, sessionID)) {
//        //            return;
//        //        }
//        LogUtil.i("VideoCallActivity 开始传输视频 sessionId " + sessionId!!)
//        //        Toast.makeText(this, "开始传输视频", Toast.LENGTH_SHORT).show();
//        surface_foreground!!.visibility = View.GONE
//        if (loadingDialog != null) {
//            loadingDialog!!.dismiss()
//        }
//        updateSurface()
//    }

//    override fun refreshEvent(event: DoorEvent) {
//
//        if (!TextUtils.equals(sessionId, event.sessionId)) {
//            return
//        }
//        LogUtil.i("VideoCallActivity refreshEvent " + event.cmd + "  sessionid = " + event.sessionId)
//
//        if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandHangup)) {
//            finish()
//        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandSessionTimeout)) {
//            Toast.makeText(this, "超时", Toast.LENGTH_SHORT).show()
//            finish()
//        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandPickupByOther)) {
//            Toast.makeText(this, "其他用户接听", Toast.LENGTH_SHORT).show()
//            finish()
//        }
//    }


    companion object{
        const val SESSION_ID="sessionId"
        @Volatile
        var started:Boolean=false
    }
}

//private fun DoorAccessManager.updateCallWindow(sessionId: String?, surface: Surface?) {
//    val intercom= DoorSessionManager.getCurrentIntercom(sessionId)
//    if (intercom == null) {
//        return
//    }
//    intercom.updateSurface(sessionId,  surface)
//}
