package com.jingxi.smartlife.pad.safe.mvp.view

import android.content.Intent
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import com.jingxi.smartlife.pad.safe.R
import com.sipphone.sdk.SipCoreManager
import com.sipphone.sdk.SipCorePreferences
import com.sipphone.sdk.SipCoreUtils
import com.sipphone.sdk.SipService
import com.wujia.businesslib.base.DataManager
import com.wujia.businesslib.dialog.LoadingDialog
import com.wujia.businesslib.util.LoginUtil
import com.wujia.lib_common.base.BaseActivity
import com.wujia.lib_common.base.Constants
import com.wujia.lib_common.utils.LogUtil
import org.linphone.core.CallDirection
import org.linphone.core.LinphoneCall
import org.linphone.core.LinphoneCore
import org.linphone.core.LinphoneCoreListenerBase
import org.linphone.mediastream.video.AndroidVideoWindowImpl

class FullScreenActivity : BaseActivity(), View.OnClickListener
//        , SurfaceHolder.Callback, DoorAccessConversationUI, SeekBar.OnSeekBarChangeListener, IntercomObserver.OnPlaybackListener
{
    private var lockDisplayName: String?=null
    private  var lockNumber: String?=null
    private  var mCaptureView: SurfaceView?=null
    private var mCall: LinphoneCall?=null
    private  var androidVideoWindowImpl: AndroidVideoWindowImpl?=null
    private  var mListener: LinphoneCoreListenerBase?=null
    private var mVideoView: SurfaceView?=null
    override val layout: Int
        get() = R.layout.activity_safe_full
    private var surfaceView: SurfaceView? = null
//    private var mDoorAccessManager: DoorAccessManager? = null
//    private var mSessionId: String? = null
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

    private var loadingDialog: LoadingDialog? = null
    private var familyID: String? = null

    override fun setContentView() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        super.setContentView()
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

//        mDoorAccessManager = JXPadSdk.getDoorAccessManager()
//        mSessionId = intent.getStringExtra(Constants.INTENT_KEY_1)
        isPlayback = intent.getBooleanExtra(Constants.INTENT_KEY_2, false)

        if (isPlayback) {
            layoutPlaybackl!!.visibility = View.VISIBLE
            seekBar = `$`(R.id.safe_seekbar)
            btnPlay = findViewById(R.id.btn3)

            tvPlaybackCurrentTime = `$`(R.id.safe_progress_time_current_tv)
            tvPlaybackCountTime = `$`(R.id.safe_progress_time_count_tv)

            btnPlay!!.setOnClickListener(this)
//            mDoorAccessManager!!.addPlayBackListener(this)

            max = intent.getIntExtra(Constants.INTENT_KEY_3, 0)
            if (max > 0) {
//                seekBar!!.setOnSeekBarChangeListener(this)
                seekBar!!.max = max
                seek = intent.getIntExtra(Constants.INTENT_KEY_4, 0)
                isPause = intent.getBooleanExtra(Constants.INTENT_KEY_5, false)
                if (isPause) {//??????
                    btnPlay!!.setImageResource(R.mipmap.btn_safe_full_play)
                } else {//??????
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
//            mDoorAccessManager!!.updatePlayBackWindow(mSessionId, surfaceView)

        } else {
            try {
                familyID = DataManager.dockKey
            } catch (e: Exception) {
                LoginUtil.toLoginActivity()
                e.printStackTrace()
                return
            }

            layoutLive!!.visibility = View.VISIBLE
//            mDoorAccessManager!!.addConversationUIListener(this)
//            videoVisible()
//            setVideo()
            //            surfaceView.postDelayed(new Runnable() {
            //                @Override
            //                public void run() {
            //                    mDoorAccessManager.updateCallWindow(mSessionId, surfaceView);
            //                }
            //            }, 500);
        }
//        surfaceView!!.holder.addCallback(this)


        mListener = object : LinphoneCoreListenerBase() {
            override fun callState(lc: LinphoneCore?, call: LinphoneCall?, state: LinphoneCall.State?, message: String?) {
                LogUtil.e( "safeOutsideFragment registrationState state = " + state!!.toString() + " message = " + message)
//                if(call?.direction== CallDirection.Outgoing&&state== LinphoneCall.State.CallEnd&&isOnResume){
//                    setVideo()
//                }
                if (call === mCall && LinphoneCall.State.Connected === state || LinphoneCall.State.StreamsRunning === state) {
                    val remoteParams = mCall?.remoteParams
                    if (remoteParams != null && remoteParams.videoEnabled &&
                            SipCorePreferences.instance().shouldAutomaticallyAcceptVideoRequests()) {
//                        SPhoneHome.instance().startVideoActivity(mCall)

                    } else {
//                        SPhoneHome.instance().startIncallActivity(mCall)
                        LogUtil.e("???????????????????????????")
                    }
                    // Resume CALL??????
                    if (state === LinphoneCall.State.Resuming) {
                        // ???????????????????????????
                        if (SipCorePreferences.instance().isVideoEnabled) {
                            if (call?.currentParamsCopy!!.videoEnabled) {
                                videoPrepared()    // ????????????????????????
                            }
                        }
                    }
                    // ????????????????????????????????????????????????
                    if (state === LinphoneCall.State.StreamsRunning) {
                        videoPrepared()    // ????????????????????????
                    }
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
                try {
                    val lc = SipCoreManager.getLc()
                    lc?.setVideoWindow(null)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
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
        try {
            lockNumber=DataManager.sip!!.sipAddr
            lockDisplayName=DataManager.sip!!.sipDisplayname
        } catch (e: Exception) {
            LoginUtil.toLoginActivity()
            e.printStackTrace()
        }
//        try {
//            videoPrepared()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

    }

    private fun fixZOrder(video: SurfaceView?, preview: SurfaceView?) {
        video?.setZOrderOnTop(false)
        preview?.setZOrderOnTop(true)
        preview?.setZOrderMediaOverlay(true) // Needed to be able to display control layout over
    }
    private fun videoVisible(){
        if(!SipService.isReady()) {
            // ??????SipService
//            context?.startService( Intent(android.content.Intent.ACTION_MAIN).setClass(
//                    context, SipService::class.java))
            return
        }
        val lc = SipCoreManager.getLcIfManagerNotDestroyedOrNull()
        lc?.addListener(mListener)

        // Only one call ringing at a time is allowed
        if (SipCoreManager.getLcIfManagerNotDestroyedOrNull() != null) {
            val calls = SipCoreUtils.getLinphoneCalls(SipCoreManager.getLc())
            for (call in calls) {
                if (LinphoneCall.State.OutgoingInit === call.state || LinphoneCall.State.OutgoingProgress === call.state ||
                        LinphoneCall.State.OutgoingRinging === call.state || LinphoneCall.State.OutgoingEarlyMedia === call.state) {
                    mCall = call
                    break
                }
            }
        }

    }
    var isOnResume:Boolean=false
    override fun onResume() {
        super.onResume()
        isOnResume=true
        setVideo()
    }
    override fun onPause() {
        super.onPause()
        isOnResume=false
        if(!SipService.isReady()) {
            // ??????SipService
//            context?.startService( Intent(android.content.Intent.ACTION_MAIN).setClass(
//                    context, SipService::class.java))
            return
        }
        val lc = SipCoreManager.getLcIfManagerNotDestroyedOrNull()
        lc?.removeListener(mListener)

        if (androidVideoWindowImpl != null) {
            synchronized(androidVideoWindowImpl!!) {
                /*
				 * this call will destroy native opengl renderer which is used by
				 * androidVideoWindowImpl
				 */
                SipCoreManager.getLc().setVideoWindow(null)
            }
        }

        try {
            if (mVideoView != null) {
                (mVideoView as? GLSurfaceView)?.onPause()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            hangUp()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun hangUp() {
        val lc = SipCoreManager.getLc()
        val calls = SipCoreUtils.getLinphoneCalls(SipCoreManager.getLc())
        for (call in calls) {
            if(call.direction== CallDirection.Outgoing) {
                lc.terminateCall(call)
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
    private fun videoPrepared() {
//        if(true){
//            LogUtil.e("videoPrepared")
//            return
//        }
        if(!SipService.isReady()) {
            // ??????SipService
//            context?.startService( Intent(android.content.Intent.ACTION_MAIN).setClass(
//                    context, SipService::class.java))
            return
        }
        try {
            if (mVideoView != null) {
                (mVideoView as? GLSurfaceView)?.onResume()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (androidVideoWindowImpl != null) {
            synchronized(androidVideoWindowImpl!!) {
                SipCoreManager.getLc().setVideoWindow(androidVideoWindowImpl)
            }
        }
        // ??????????????????Call?????????????????????????????????????????????????????????????????????????????????UI??????
        // ??????????????????????????????????????????????????????????????????????????????
        if (SipCoreManager.getLc().currentCall != null) {
            SipCoreManager.getLc().currentCall.setListener(object : LinphoneCall.LinphoneCallListener {
                override fun onNextVideoFrameDecoded(linphoneCall: LinphoneCall) {
                    loadingDialog?.dismiss()
                    // ??????????????????????????????????????????????????????????????????????????????????????????
                    //					SipCoreManager.getInstance().stopLocalVideo(true);
                }

                override fun tmmbrReceived(linphoneCall: LinphoneCall, i: Int, i1: Int) {

                }
            })
        }

        if (isVideoEnabled(SipCoreManager.getLc().currentCall)) {
            // ?????????????????????????????????????????????????????????????????????????????????????????????
            //            displayVideoCall(false)
        } else if (SipCoreManager.getLc().isInConference) {
            displayConference()
        } else {
            //			SipCoreManager.startProximitySensorForActivity(this);
            SipCoreManager.getInstance().enableProximitySensing(true)
            LogUtil.e("SipCoreManager.getInstance().enableProximitySensing(true)")
        }
    }

    private fun displayConference() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        LogUtil.e("displayConference not implemented")
    }

    private fun isVideoEnabled(call: LinphoneCall?): Boolean {
        return call?.currentParamsCopy?.videoEnabled ?: false
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn1) {
            val intent = Intent()
            intent.putExtra(Constants.INTENT_KEY_1, isPause)
//            setResult(200, intent)
            finish()
        } else if (v.id == R.id.btn2) {//??????
        }
        else if (v.id == R.id.btn3) {//????????????
//            if (isPause) {//??????
//                btnPlay!!.setImageResource(R.mipmap.btn_safe_pause)
//                seekBar!!.setOnSeekBarChangeListener(this)
//                mDoorAccessManager!!.startPlayBack(mSessionId)
//                mDoorAccessManager!!.seekPlayBack(mSessionId, seek * 100 / max)
//                mDoorAccessManager!!.updatePlayBackWindow(mSessionId, surfaceView)
//            } else {//??????
//                btnPlay!!.setImageResource(R.mipmap.btn_safe_full_play)
//                mDoorAccessManager!!.pausePlayBack(mSessionId)
//            }
//            isPause = !isPause
        } else if (v.id == R.id.btn9) {//??????
            //            mDoorAccessManager.updateCallWindow(mSessionId, surfaceView);
            setVideo()
        }
    }
    /**
     * ??????SipService??????????????????SipService????????????????????????????????????
     * @author Administrator
     */
    private inner class ServiceWaitThread : Thread() {
        override fun run() {
            while (!SipService.isReady()) {
                try {
                    sleep(30)
                } catch (e: InterruptedException) {
                    throw RuntimeException("waiting thread sleep() " + "has been interrupted")
                }

            }
            runOnUiThread {
                if (isOnResume) {
//                    videoVisible()
                    setVideo()
                }
            }
        }
    }
    private fun setVideo() {
        videoVisible()
        if (null == loadingDialog) {
            loadingDialog = mContext?.let { LoadingDialog(it) }
        }
        loadingDialog!!.setCancelOnTouchOutside(true)
        loadingDialog!!.setTitle("???????????????...")
        loadingDialog!!.show()
        if(!SipService.isReady()) {
            // ??????SipService
            startService( Intent(Intent.ACTION_MAIN).setClass(
                    this, SipService::class.java))
            ServiceWaitThread().start()
            return
        }
//        lockNumber="D58-11-1" //todo test
//        lockDisplayName="D58-11-1" //todo test
//        lockDisplayName=null//todo test

        try {
           hangUp()
        } catch (e: Exception) {
            e.printStackTrace()
        }

//        try {
//            SipCoreManager.getInstance().newOutgoingCall(lockNumber,lockDisplayName)
//        } catch (e: Exception) {
//            SipCoreManager.getInstance().terminateCall()
//            e.printStackTrace()
//        }


//        if (isSeeeionIdValid) {
//            mDoorAccessManager!!.updateCallWindow(mSessionId, surfaceView)
//            loadingDialog!!.dismiss()
//            return
//        }
//        val list = mDoorAccessManager!!.getDevices(familyID)
//        for (device in list) {
//            if (DoorDevice.TYPE_UNIT == device.myDeviceType) {
//                mSessionId = mDoorAccessManager!!.monitor(familyID, device)
//                LogUtil.i("SafeOutsideFragment mSessionId " + mSessionId!!)
//
//                mDoorAccessManager!!.updateCallWindow(mSessionId, surfaceView)
//                return
//            }
//        }
    }

//    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
//        if (isPlayback) {
//            mDoorAccessManager!!.updatePlayBackWindow(mSessionId, surfaceView)
//        } else {
//            mDoorAccessManager!!.updateCallWindow(mSessionId, surfaceView)
//        }
//    }
//
//    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {
//
//    }
//
//    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
//        if (isPlayback) {
//            mDoorAccessManager!!.updatePlayBackWindow(mSessionId, null)
//        } else {
//            mDoorAccessManager!!.updateCallWindow(mSessionId, null)
//        }
//    }

//    override fun startTransPort(sessionID: String) {
//        if (!TextUtils.equals(sessionID, mSessionId)) {
//            return
//        }
//        isSeeeionIdValid = true
//        LogUtil.i("SafeOutsideFragment ?????????????????? mSessionId " + mSessionId!!)
//        if (loadingDialog != null) {
//            loadingDialog!!.dismiss()
//        }
//    }

//    override fun refreshEvent(event: DoorEvent) {
//
//        if (!TextUtils.equals(mSessionId, event.sessionId)) {
//            return
//        }
//        if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandHangup)) {
//            finish()
//        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandSessionTimeout)) {
//            isSeeeionIdValid = false
//            //            finish();
//        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandPickupByOther)) {
//            finish()
//        }
//
//    }


//    override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
//        LogUtil.i("FullScreenActivity onProgressChanged seek = $i")
//        seek = i
//    }
//
//    override fun onStartTrackingTouch(seekBar: SeekBar) {
//        isTouchSeek = true
//    }
//
//    override fun onStopTrackingTouch(seekBar: SeekBar) {
//        isTouchSeek = false
//        mDoorAccessManager!!.seekPlayBack(mSessionId, seek * 100 / max)
//    }

//    override fun onMediaPlayEvent(session_id: String, event: Int, value: Long) {
//        if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventDuration) {
//            max = (value / unit).toInt()
//            seekBar!!.max = max
//        } else if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventProgress) {
//            seek = (value / unit).toInt()
//            if (isTouchSeek) {
//                return
//            }
//            seekBar!!.setOnSeekBarChangeListener(null)
//            seekBar!!.progress = seek
//            seekBar!!.setOnSeekBarChangeListener(this)
//
//            tvPlaybackCurrentTime!!.text = format(seek)
//            tvPlaybackCountTime!!.text = format(max)
//        } else if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventCompleted) {
//            seek = max
//            seekBar!!.setOnSeekBarChangeListener(null)
//            seekBar!!.progress = max
//            seekBar!!.setOnSeekBarChangeListener(this)
//            mDoorAccessManager!!.pausePlayBack(mSessionId)
//        }
//        LogUtil.i("FullScreenActivity onMediaPlayEvent event = $event  value = $value  seek = $seek  max = $max")
//
//    }

    private fun format(time: Int): String {

        val m = time / 60
        val s = time % 60

        val mstr: String
        val sstr: String
        mstr = if (m < 10) {
            "0$m"
        } else {
            m.toString()
        }
        sstr = if (s < 10) {
            "0$s"
        } else {
            s.toString()
        }

        return "$mstr:$sstr"
    }

}
