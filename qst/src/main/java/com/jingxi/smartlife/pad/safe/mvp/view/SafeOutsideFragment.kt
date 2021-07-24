package com.jingxi.smartlife.pad.safe.mvp.view

import android.annotation.SuppressLint
import android.content.Context.POWER_SERVICE
import android.content.Intent
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.view.SurfaceView
import android.view.View
import com.jingxi.smartlife.pad.safe.R
import com.sipphone.sdk.SipCoreManager
import com.sipphone.sdk.SipCorePreferences
import com.sipphone.sdk.SipCoreUtils
import com.sipphone.sdk.SipService
import com.wujia.businesslib.base.DataManager
import com.wujia.businesslib.base.MvpFragment
import com.wujia.businesslib.dialog.LoadingDialog
import com.wujia.businesslib.util.LoginUtil
import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.BaseView
import com.wujia.lib_common.utils.DoubleClickUtils
import com.wujia.lib_common.utils.LogUtil
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_safe_outside.*
import org.linphone.core.*
import org.linphone.mediastream.video.AndroidVideoWindowImpl
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：可视安防 外机
 */
class SafeOutsideFragment : MvpFragment<BasePresenter<BaseView>>()
        , View.OnClickListener
{
    private var mWakeLock: PowerManager.WakeLock?=null
    private var mCall: LinphoneCall?=null
    private  var mListener: LinphoneCoreListenerBase?=null
    private var mCaptureView: SurfaceView?=null
    private  var androidVideoWindowImpl: AndroidVideoWindowImpl?=null
    private var mVideoView: SurfaceView?=null
    private var lockDisplayName: String?=null
    private var lockNumber: String?=null
    override val layoutId: Int
        get() =  R.layout.fragment_safe_outside
//    private var inVisibleType = 0//不可见时的状态，0是正常，10，20为全屏，全屏时不做复位操作；
//    private var isSeeeionIdValid: Boolean = false//防止多次刷新创建多个会话，sessionId未超时即有效
//    private var isPalyback: Boolean = false //true为回放，false为直播；
//    private var isPause: Boolean = false//回放的播放状态
//    private var audioHelper: AudioMngHelper? = null
//    private var isMute: Boolean = false
//    private var audioValue: Int = 0

//    private var mSessionId: String? = null
//    private var playBackSessionId: String? = null

    private var familyID: String? = null
    private var loadingDialog: LoadingDialog? = null



    override fun createPresenter(): BasePresenter<BaseView>? {
        return null
    }
    val handler:Handler= Handler(Looper.getMainLooper())
    override fun interruptInject() {
        super.interruptInject()
        mListener = object : LinphoneCoreListenerBase() {
            override fun callState(lc: LinphoneCore?, call: LinphoneCall?, state: LinphoneCall.State?, message: String?) {
                LogUtil.e( "safeOutsideFragment registrationState state = " + state!!.toString() + " message = " + message)
                if (state === LinphoneCall.State.IncomingReceived) {    // 启动CallIncomingActivity
                    if (!VideoCallActivity.started) {
                        VideoCallActivity.started = true
                        startActivity(Intent(context, VideoCallActivity::class.java))
                    }
                }
                if(call?.direction=== CallDirection.Outgoing&&state=== LinphoneCall.State.CallEnd
                        &&isSupportVisible
                        ){

                    var isCalling=false
                    val lc = SipCoreManager.getLc()
                    val calls = SipCoreUtils.getLinphoneCalls(SipCoreManager.getLc())
                    for (call in calls) {
                        if (LinphoneCall.State.OutgoingInit === call.state
                                || LinphoneCall.State.OutgoingProgress === call.state
                                || LinphoneCall.State.OutgoingRinging === call.state
                                || LinphoneCall.State.OutgoingEarlyMedia === call.state
//                                ||LinphoneCall.State.IncomingReceived === call.state
                        ) {
                            mCall = call
                            isCalling=true
                            break
                        }
                    }
                    LogUtil.e("safeOutsideFragment isCalling=${isCalling},VideoCallActivity.started=${VideoCallActivity.started}")
//                    if(!isCalling ){
//                        if(!VideoCallActivity.started){
//                            handler.postDelayed({
//                                if(isSupportVisible&&!VideoCallActivity.started){
//                                    setVideo(true)//fixme 被挂断后重播就crash？这里会一直被回调，是递归调用了？
//                                }
//                            },1000)
//
//                        }
//
//                    }
                }
                if (call === mCall && LinphoneCall.State.Connected === state || LinphoneCall.State.StreamsRunning === state) {
                   LogUtil.e("safeOutsideFragment mCall=="+mCall)
                    val remoteParams = mCall?.getRemoteParams()
                    if (remoteParams != null && remoteParams!!.getVideoEnabled() &&
                            SipCorePreferences.instance().shouldAutomaticallyAcceptVideoRequests()) {
//                        SPhoneHome.instance().startVideoActivity(mCall)

                    } else {
//                        SPhoneHome.instance().startIncallActivity(mCall)
                        LogUtil.e("safeOutsideFragment 语音电话，暂未实现")
                    }
                    // Resume CALL状态
                    if (state === LinphoneCall.State.Resuming) {
                        // 检查是否为视频通话
                        if (SipCorePreferences.instance().isVideoEnabled) {
                            if (call?.getCurrentParamsCopy()!!.videoEnabled) {
                                videoPrepared()    // 显示视频通话界面
                            }
                        }
                    }
                    // 正在传入媒体流，包括视频或音频流
                    if (state === LinphoneCall.State.StreamsRunning) {
                        videoPrepared()    // 显示视频通话界面
                    }
                }
//                else if(state=== LinphoneCall.State.Error){
//                    LogUtil.e("safeOutsideFragment 收到error,15秒后重连")
//                    handler.postDelayed({
//                        if(isSupportVisible&&!VideoCallActivity.started){
//                            LogUtil.e("safeOutsideFragment 15到了，开始重连")
//                            setVideo(false)
//                        }
//                    },1000 * 15)
//                }
            }
        }

        mVideoView =mView?.findViewById(R.id.surfaceView)
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
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        LogUtil.i("SafeOutsideFragment onLazyInitView")
        try {
            familyID = DataManager.dockKey
        } catch (e: Exception) {
            LoginUtil.toLoginActivity()
            LogUtil.t("get familyid failed", e)
            return
        }

//        audioHelper = mContext?.let { AudioMngHelper(it) }
//        audioValue = audioHelper!!.currentVolumePercentage


        safe_swich_live_btn!!.setOnClickListener(this)
//        safe_rec_all_choose_btn.setOnClickListener(this)
//        safe_rec_del_btn.setOnClickListener(this)
        safe_btn_full.setOnClickListener(this)

        safe_btn_sos!!.setOnClickListener(this)
        safe_btn_play!!.setOnClickListener(this)
        safe_btn_pause!!.setOnClickListener(this)
        safe_btn_save!!.setOnClickListener(this)
        safe_btn_mute!!.setOnClickListener(this)
        safe_btn_refresh!!.setOnClickListener(this)
        safe_btn_full!!.setOnClickListener(this)
        safe_btn_sos!!.setOnClickListener(this)
//        safe_play_rec_edit_btn!!.setOnClickListener(this)
//        surfaceView!!.holder.addCallback(this)

        addSubscribe(Flowable.interval(0, 60, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
//                    if (!isPalyback) {
                        setDate(System.currentTimeMillis())
//                    }
                })


        try {
            lockNumber=DataManager.sip!!.sipAddr
            lockDisplayName=DataManager.sip!!.sipDisplayname
        } catch (e: Exception) {
            LoginUtil.toLoginActivity()
            e.printStackTrace()
        }

        setVideo()


    }
    private fun fixZOrder(video: SurfaceView?, preview: SurfaceView?) {
        video?.setZOrderOnTop(false)
        preview?.setZOrderOnTop(true)
        preview?.setZOrderMediaOverlay(true) // Needed to be able to display control layout over
    }
    protected fun setDate(timeInmillis: Long) {
        val dateFormat = SimpleDateFormat("MM.dd HH:mm")
        val time = dateFormat.format(Date(timeInmillis))
        safe_eq_title_tv!!.text = String.format("室外机 Live %s", time)
//        if (!isPalyback) {
//            safe_eq_title_tv!!.text = String.format("室外机 Live %s", time)
//        } else {
//            safe_eq_title_tv!!.text = String.format("回放 %s", time)
//        }
    }

    private fun hangUp() {
        val lc = SipCoreManager.getLc()
        val calls = SipCoreUtils.getLinphoneCalls(SipCoreManager.getLc())
        for (call in calls) {
              if(call.direction== CallDirection.Outgoing) {
                lc.terminateCall(call)
//                mCall = call
//                break
            }
        }



//        val currentCall = lc.currentCall
//        if (currentCall != null ) {
//            lc.terminateCall(currentCall)
//        } else if (lc.isInConference) {
//            lc.terminateConference()
//        }
//        else {
//            lc.terminateAllCalls()
//        }
    }
    private fun setVideo(isHangup: Boolean =true) {
        videoVisible()
        try {
            setDate(System.currentTimeMillis())
            if (null == loadingDialog) {
                loadingDialog = mContext?.let { LoadingDialog(it) }
            }
            loadingDialog!!.setCancelOnTouchOutside(true)
            loadingDialog!!.setTitle("正在连接中...")
            loadingDialog!!.show()

            if(!SipService.isReady()) {
                // 启动SipService
                context?.startService( Intent(android.content.Intent.ACTION_MAIN).setClass(
                        context, SipService::class.java))
                ServiceWaitThread().start()
                return
            }
//            lockNumber="D58-11-1" //todo test
//        lockDisplayName="D58-11-1" //todo test
//            lockDisplayName=null//todo test
            if(isHangup){
                try {
                    hangUp()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            LogUtil.e("safeOutsideFragment setvideo VideoCallActivity.started=${VideoCallActivity.started}")
            var isCalling = false
            val lc = SipCoreManager.getLc()
            val calls = SipCoreUtils.getLinphoneCalls(SipCoreManager.getLc())
            for (call in calls) {
                if (LinphoneCall.State.OutgoingInit === call.state
                        || LinphoneCall.State.OutgoingProgress === call.state
                        || LinphoneCall.State.OutgoingRinging === call.state
                        || LinphoneCall.State.OutgoingEarlyMedia === call.state
//                        ||LinphoneCall.State.IncomingReceived === call.state
                ) {
                    mCall = call
                    isCalling = true
                    break
                }
            }
            if(!isCalling){

                //by shenbingkai
                try {
                    if(!VideoCallActivity.started){
                        SipCoreManager.getInstance().newOutgoingCall(lockNumber,lockDisplayName)
                    }
                } catch (e: LinphoneCoreException) {
                    SipCoreManager.getInstance().terminateCall()
                    LogUtil.e("呼出时异常")
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    @SuppressLint("InvalidWakeLockTag")
    override fun onSupportVisible() {
        super.onSupportVisible()
        var pManager = context!!.getSystemService(POWER_SERVICE) as PowerManager
        mWakeLock = pManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                or PowerManager.ON_AFTER_RELEASE, javaClass.name)
        mWakeLock?.acquire()
        safe_swich_live_btn!!.performClick()
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("SafeOutsideFragment onSupportVisible")
//        if (null != mDoorAccessManager) {
//            val recordBeans = mDoorAccessManager!!.getHistoryListByType(familyID, DoorRecordBean.RECORD_TYPE_DOOR, 0, 50)
//            if (null != recordBeans && recordBeans.size > 0) {
//                recordList!!.clear()
//                recordList!!.addAll(recordBeans)
//            }
//            if (null != recAdapter) {
//                recAdapter!!.notifyDataSetChanged()
//            }
//        }
    }
    fun videoVisible(){
        if(!SipService.isReady()) {
            // 启动SipService
//            context?.startService( Intent(android.content.Intent.ACTION_MAIN).setClass(
//                    context, SipService::class.java))
//            ServiceWaitThread().start()
            return
        }

        val lc = SipCoreManager.getLcIfManagerNotDestroyedOrNull()

        lc?.removeListener(mListener)
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

//        try {
//            videoPrepared()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }
    /**
     * 等待SipService启动的线程，SipService的启动可能需要几秒的时间
     * @author Administrator
     */
    private inner class ServiceWaitThread : Thread() {
        override fun run() {
            while (!SipService.isReady()) {
                try {
                    Thread.sleep(30)
                } catch (e: InterruptedException) {
                    throw RuntimeException("waiting thread sleep() " + "has been interrupted")
                }

            }
            mActivity.runOnUiThread { if(isSupportVisible){

                setVideo()
            }
            }
        }
    }
    private fun videoPrepared() {
        if(!SipService.isReady()) {
            return
        }
        try {
            if (mVideoView != null&&isSupportVisible) {
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
        loadingDialog?.dismiss()
        // 设置一个当前Call对象视频第一帧解码完成监听器，收到回调之后，隐藏加载的UI界面
        // 这里仅提供一个简单的实例，避免网络视频太慢而现实黑屏
        if (SipCoreManager.getLc().currentCall != null) {
            SipCoreManager.getLc().currentCall.setListener(object : LinphoneCall.LinphoneCallListener {
                override fun onNextVideoFrameDecoded(linphoneCall: LinphoneCall) {
//                    loadingDialog?.dismiss()
                    // 第一帧视频解码成功后，可以调用下面的接口，停止本地视频的发送
//                    					SipCoreManager.getInstance().stopLocalVideo(true);
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

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        mWakeLock?.release()
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("SafeOutsideFragment onSupportInvisible")

        handler.removeCallbacksAndMessages(null)
        videoInvisible()
    }
//    var isToFullScreen:Boolean=false
    private fun videoInvisible() {
        if(!SipService.isReady()) {
            // 启动SipService
//            context?.startService( Intent(android.content.Intent.ACTION_MAIN).setClass(
//                    context, SipService::class.java))
            return
        }
        //如果是点击全屏导致fragment不显示，则不重置session有效性
        //        if (inVisibleType != REQUEST_CODE_FULL_LIVE && inVisibleType != REQUEST_CODE_FULL_HISTORY) {
        pausePlay()
        //        }
        //        if (!isEdit && null != safe_play_rec_edit_btn) {
        //            safe_play_rec_edit_btn!!.performClick()
        //        }
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
//        if(isToFullScreen){
//            isToFullScreen=false
//            return
//        }
        try {
            hangUp()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun pausePlay() {
        if (safe_btn_pause!!.visibility == View.VISIBLE) {
            safe_btn_pause!!.performClick()
        }
//        isSeeeionIdValid = false
    }

//    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
//        //        surfaceView.setZOrderOnTop(true);
//        //        surfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
//        if (mDoorAccessManager != null) {
//            if (isPalyback) {
//                mDoorAccessManager!!.updatePlayBackWindow(playBackSessionId, surfaceView)
//            } else {
//                mDoorAccessManager!!.updateCallWindow(mSessionId, surfaceView)
//            }
//
//        }
//    }
//
//    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {
//
//    }
//
//    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
//        if (mDoorAccessManager != null) {
//            if (isPalyback) {
//                mDoorAccessManager!!.updatePlayBackWindow(playBackSessionId, null)
//            } else {
//                mDoorAccessManager!!.updateCallWindow(mSessionId, null)
//            }
//
//        }
//    }

//    override fun startTransPort(sessionId: String) {
//        if (!TextUtils.equals(mSessionId, sessionId)) {
//            return
//        }
//        isSeeeionIdValid = true
//        LogUtil.i("SafeOutsideFragment 开始传输视频 mSessionId $sessionId")
//        if (loadingDialog != null) {
//            loadingDialog!!.dismiss()
//        }
//    }

//    override fun refreshEvent(event: DoorEvent) {
//
//        if (!TextUtils.equals(mSessionId, event.sessionId)) {
//            return
//        }
//        LogUtil.i("SafeOutsideFragment refreshEvent " + event.cmd + "  sessionid = " + event.sessionId)
//        if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandHangup)) {
//            //            mDoorAccessManager.hangupCall(mSessionId);
//
//        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandSessionTimeout)) {
//            if (!isPalyback) {
//                mContext?.let { ToastUtil.showShort(it, "超时") }
//            }
//            isSeeeionIdValid = false
//            //            mDoorAccessManager.hangupCall(mSessionId);
//        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandPickupByOther)) {
//            if (!isPalyback) {
//                ToastUtil.showShort(mContext!!, "其他用户接听")
//            }
//            //            mDoorAccessManager.hangupCall(mSessionId);
//        }
//    }

    override fun onClick(v: View) {

        if (DoubleClickUtils.isDoubleClick) {
            return
        }
//        if (v.id == R.id.safe_play_rec_edit_btn) {//编辑
//            LogUtil.i("编辑")
//            if (isEdit) {   //编辑状态
//                safe_play_rec_edit_btn!!.text = getString(R.string.complete)
//                safe_rec_op_layout!!.visibility = View.VISIBLE
//            } else {
//                safe_play_rec_edit_btn!!.text = getString(R.string.edit)
//                safe_rec_op_layout!!.visibility = View.GONE
//            }
//
//            //            safe_rec_op_layout.animate().translationY(isEdit ? 0 : ScreenUtil.dip2px(80)).setDuration(300).start();
//            recAdapter!!.setEdit(isEdit)
//            recAdapter!!.notifyDataSetChanged()
//
//            isEdit = !isEdit
//        } else
            if (v.id == R.id.safe_btn_sos) {    // SOS
        } else if (v.id == R.id.safe_btn_play) {   // 播放
//            isPause = false
//            safe_btn_pause!!.visibility = View.VISIBLE
//            safe_btn_play!!.visibility = View.GONE
//            val tempSeek = seek
//
//            //            safe_seekbar.setOnSeekBarChangeListener(this);
//            mDoorAccessManager!!.startPlayBack(playBackSessionId)
//            mDoorAccessManager!!.seekPlayBack(playBackSessionId, tempSeek * 100 / max)
//            mDoorAccessManager!!.updatePlayBackWindow(playBackSessionId, surfaceView)

        } else if (v.id == R.id.safe_btn_pause) { //暂停
//            isPause = true
//            safe_btn_pause!!.visibility = View.GONE
//            safe_btn_play!!.visibility = View.VISIBLE
//            mDoorAccessManager!!.pausePlayBack(playBackSessionId)
            //            mDoorAccessManager.removePlayBackListener(this);
            //            safe_seekbar.setOnSeekBarChangeListener(null);
        } else if (v.id == R.id.safe_btn_save) {   //保存
            //sdk自动保存
        } else if (v.id == R.id.safe_btn_refresh) {    //刷新
            setVideo()
        } else if (v.id == R.id.safe_btn_mute) {   //静音 todo????问一下这里的逻辑
//            isMute = !isMute
            //            if(isPalyback){
            //                mDoorAccessManager.enableRemoteToLocalAudio(familyID,playBackSessionId,isMute);//不起作用，
            //            }else {
            //                mDoorAccessManager.enableRemoteToLocalAudio(familyID,mSessionId,isMute);
            //            }
//            if (isMute) {
//                audioHelper!!.setVoice100(0)//todo 音量键对安防sdk的视频播放器不起作用
//                ToastUtil.showShort(mContext!!, "已静音")
//                safe_btn_mute!!.setBackgroundImage(R.mipmap.btn_safe_mutebig_pressed, R.mipmap.btn_safe_mutebig_pressed)
//            } else {
                //                audioHelper.setVoice100(audioValue);
//                audioHelper!!.setVoice100(75)
//                safe_btn_mute!!.setBackgroundImage(R.mipmap.btn_safe_mutebig, R.mipmap.btn_safe_mutebig_pressed)
//            }
        } else if (v.id == R.id.safe_btn_full) {   //全屏
//            if (isPalyback) {//回放
//                val playbackIntent = Intent(mActivity, FullScreenActivity::class.java)
//                playbackIntent.putExtra(Constants.INTENT_KEY_1, playBackSessionId)
//                playbackIntent.putExtra(Constants.INTENT_KEY_2, isPalyback)
//                playbackIntent.putExtra(Constants.INTENT_KEY_3, max)
//                playbackIntent.putExtra(Constants.INTENT_KEY_4, seek)
//                playbackIntent.putExtra(Constants.INTENT_KEY_5, isPause)
//
//                startActivityForResult(playbackIntent, REQUEST_CODE_FULL_HISTORY)
//                inVisibleType = REQUEST_CODE_FULL_HISTORY
//
//            } else {//直播
//                if (!isSeeeionIdValid) {//直播会话无效时不能全屏
//                    return
//                }
//                isToFullScreen=true
//                videoInvisible()
                val liveIntent = Intent(mActivity, FullScreenActivity::class.java)
//                liveIntent.putExtra(Constants.INTENT_KEY_1, mSessionId)
//                liveIntent.putExtra(Constants.INTENT_KEY_2, isPalyback)
                startActivity(liveIntent)
//                inVisibleType = REQUEST_CODE_FULL_LIVE
//            }

        } else if (v.id == R.id.safe_swich_live_btn) { //切回直播
//            startLive()
            setVideo()

        }
//        else if (v.id == R.id.safe_rec_all_choose_btn) { //全选
//            recAdapter!!.chooseAll()
//        }
//        else if (v.id == R.id.safe_rec_del_btn) {    //删除
//
//            if (null == recordList || recordList!!.size < 1)
//                return
//            val map = recAdapter!!.checkMap
//            if (null == map || map.size() < 1)
//                return
//            val temp = ArrayList<DoorRecordBean>()
//            for (i in recordList!!.indices) {
//                if (map.get(i)) {
//                    temp.add(recordList!![i])
//                }
//            }
//            recordList!!.removeAll(temp)
//            recAdapter!!.clearCheck()
//            recAdapter!!.notifyDataSetChanged()
////            mDoorAccessManager!!.deleteListRecord(temp)
//        }
    }

//    override fun onItemClick(adapter: RecyclerView.Adapter<*>?, holder: RecyclerView.ViewHolder, position: Int) {
//        if (isEdit) {
//            recordBean = recordList!![position]
//            if (!File(recordBean!!.videoPath).exists()) {
//                ToastUtil.showShort(mContext!!, "该记录没有录制视频")
//                return
//            }
//
//            isPalyback = true
//            ToastUtil.showShort(mContext!!, "开始回放")
//            startPlayRec(recordList!![position])
//        } else {
//            recAdapter!!.itemClick(position)
//        }
//    }

//    override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
//        LogUtil.i("onProgressChanged seek = $i")
//        seek = i
//    }
//
//    override fun onStartTrackingTouch(seekBar: SeekBar) {
//        isTouchSeek = true
//    }
//
//    override fun onStopTrackingTouch(seekBar: SeekBar) {
//        isTouchSeek = false
//        if (max > 0 && seek > 0) {
//            mDoorAccessManager!!.seekPlayBack(playBackSessionId, seek * 100 / max)
//        }
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == 200) {
//            inVisibleType = 0
//            when (requestCode) {
//                REQUEST_CODE_FULL_LIVE -> if (mDoorAccessManager != null) {
//                    mDoorAccessManager!!.updateCallWindow(mSessionId, surfaceView)
//                }
//                REQUEST_CODE_FULL_HISTORY -> if (mDoorAccessManager != null) {
//                    mDoorAccessManager!!.updatePlayBackWindow(playBackSessionId, surfaceView)
//                    if (data != null) {
//                        val isPause = data.getBooleanExtra(Constants.INTENT_KEY_1, false)
//                        run {
//                            if (isPause) {
//                                safe_btn_pause!!.visibility = View.GONE
//                                safe_btn_play!!.visibility = View.VISIBLE
//                            } else {
//                                safe_btn_pause!!.visibility = View.VISIBLE
//                                safe_btn_play!!.visibility = View.GONE
//                            }
//                        }
//                    }
//
//                }
//            }//                    mDoorAccessManager.addPlayBackListener(this);
//        }
//    }

//    override fun onMediaPlayEvent(session_id: String, event: Int, value: Long) {
//        if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventDuration) {
//            max = (value / unit).toInt()
//            if (max == 0) {
//                LogUtil.e("onMediaPlayEvent value=$value")
//                max = 59
//            }
//            safe_seekbar!!.max = max
//        } else if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventProgress) {
//            seek = (value / unit).toInt()
//            if (isTouchSeek) {
//                return
//            }
//            safe_seekbar!!.setOnSeekBarChangeListener(null)
//            safe_seekbar!!.progress = seek
//            safe_seekbar!!.setOnSeekBarChangeListener(this)
//
//            //            recordBean.chat_time
//            safe_progress_time_current_tv!!.text = format(seek)
//            safe_progress_time_count_tv!!.text = format(max)
//        } else if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventCompleted) {
//            seek = max
//            safe_seekbar!!.setOnSeekBarChangeListener(null)
//            safe_seekbar!!.progress = max
//            safe_seekbar!!.setOnSeekBarChangeListener(this)
//            mDoorAccessManager!!.pausePlayBack(playBackSessionId)
//        }
//        LogUtil.i("onMediaPlayEvent event = $event  value = $value  seek = $seek  max = $max")
//
//    }

//    private fun startLive() {
//        isPalyback = false
//        isPause = false
//
//        mDoorAccessManager!!.updatePlayBackWindow(playBackSessionId, null)
//        mDoorAccessManager!!.updateCallWindow(mSessionId, surfaceView)
//
//        safe_rec_seek_layout.visibility = View.GONE
//        safe_btn_play!!.visibility = View.GONE
//        safe_btn_pause!!.visibility = View.GONE
//        safe_btn_save!!.visibility = View.GONE
//        //        safe_btn_sos.setVisibility(View.VISIBLE);
//        safe_btn_refresh!!.visibility = View.VISIBLE
//
//        mDoorAccessManager!!.pausePlayBack(playBackSessionId)
//    }

//    private fun startPlayRec(record: DoorRecordBean) {
//        setDate(record.startTime)
//        `$`<View>(R.id.safe_rec_seek_layout).visibility = View.VISIBLE
//        safe_btn_pause!!.visibility = View.VISIBLE
//        safe_btn_play!!.visibility = View.GONE
//        //        safe_btn_save.setVisibility(View.VISIBLE);
//        safe_btn_sos!!.visibility = View.GONE
//        safe_btn_refresh!!.visibility = View.GONE
//
//        safe_seekbar!!.setOnSeekBarChangeListener(this)
//
//        playBackSessionId = record.session_id
//        mDoorAccessManager!!.startPlayBack(playBackSessionId)
//
//        mDoorAccessManager!!.updateCallWindow(mSessionId, null)
//        mDoorAccessManager!!.updatePlayBackWindow(playBackSessionId, surfaceView)
//
//    }

//    override fun refreshList() {
//        LogUtil.i("refreshList")
//        setVideo()
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        pausePlay()
        LogUtil.i("SafeOutsideFragment onDestroyView")
        handler.removeCallbacksAndMessages(null)
    }

//    private fun format(time: Int): String {
//
//        val m = time / 60
//        val s = time % 60
//
//        val mstr: String
//        val sstr: String
//        if (m < 10) {
//            mstr = "0$m"
//        } else {
//            mstr = m.toString()
//        }
//        if (s < 10) {
//            sstr = "0$s"
//        } else {
//            sstr = s.toString()
//        }
//
//        return "$mstr:$sstr"
//    }

    companion object {

        private val REQUEST_CODE_FULL_LIVE = 10
        private val REQUEST_CODE_FULL_HISTORY = 20

        fun newInstance(): SafeOutsideFragment {
            val fragment = SafeOutsideFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
