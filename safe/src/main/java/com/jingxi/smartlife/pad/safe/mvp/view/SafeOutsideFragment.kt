package com.jingxi.smartlife.pad.safe.mvp.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.SurfaceHolder
import android.view.View
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.intercom.sdk.IntercomConstants
import com.intercom.sdk.IntercomObserver
import com.jingxi.smartlife.pad.safe.R
import com.jingxi.smartlife.pad.safe.mvp.adapter.PlayBackAdapter
import com.jingxi.smartlife.pad.sdk.JXPadSdk
import com.jingxi.smartlife.pad.sdk.doorAccess.DoorAccessManager
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorDevice
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorEvent
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorRecordBean
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessConversationUI
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessListUI
import com.wujia.businesslib.base.DataManager
import com.wujia.businesslib.base.MvpFragment
import com.wujia.businesslib.dialog.LoadingDialog
import com.wujia.businesslib.util.LoginUtil
import com.wujia.lib.widget.util.ToastUtil
import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.BaseView
import com.wujia.lib_common.base.Constants
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter
import com.wujia.lib_common.utils.AudioMngHelper
import com.wujia.lib_common.utils.DoubleClickUtils
import com.wujia.lib_common.utils.LogUtil
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_safe_outside.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：可视安防 外机
 */
class SafeOutsideFragment : MvpFragment<BasePresenter<BaseView>>(), SurfaceHolder.Callback, DoorAccessConversationUI, DoorAccessListUI, View.OnClickListener, MultiItemTypeAdapter.OnRVItemClickListener, SeekBar.OnSeekBarChangeListener, IntercomObserver.OnPlaybackListener {
    private var inVisibleType = 0//不可见时的状态，0是正常，10，20为全屏，全屏时不做复位操作；
    private var isSeeeionIdValid: Boolean = false//防止多次刷新创建多个会话，sessionId未超时即有效
    private var isPalyback: Boolean = false //true为回放，false为直播；
    private var isPause: Boolean = false//回放的播放状态
    private var audioHelper: AudioMngHelper? = null
    private var isMute: Boolean = false
    private var audioValue: Int = 0

    private var mDoorAccessManager: DoorAccessManager? = null
    private var mSessionId: String? = null
    private var playBackSessionId: String? = null


    private var isEdit = true
    private var recAdapter: PlayBackAdapter? = null
    private var seek: Int = 0
    private var max: Int = 0
    /**
     * listener 中返回的value 单位是 微秒
     */
    private val unit = (1000 * 1000).toLong()

    /**
     * 是否在滑动进度条
     */
    private var isTouchSeek = false
    private var familyID: String? = null
    private var recordList: MutableList<DoorRecordBean>? = null
    private var recordBean: DoorRecordBean? = null
    private var loadingDialog: LoadingDialog? = null


    override fun getLayoutId(): Int {
        LogUtil.i("SafeOutsideFragment getLayoutId")
        return R.layout.fragment_safe_outside
    }

    override fun createPresenter(): BasePresenter<BaseView>? {
        return null
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

        audioHelper = AudioMngHelper(mContext)
        audioValue = audioHelper!!.get100CurrentVolume()


        safe_swich_live_btn!!.setOnClickListener(this)
        safe_rec_all_choose_btn.setOnClickListener(this)
        safe_rec_del_btn.setOnClickListener(this)
        safe_btn_full.setOnClickListener(this)

        safe_btn_sos!!.setOnClickListener(this)
        safe_btn_play!!.setOnClickListener(this)
        safe_btn_pause!!.setOnClickListener(this)
        safe_btn_save!!.setOnClickListener(this)
        safe_btn_mute!!.setOnClickListener(this)
        safe_btn_refresh!!.setOnClickListener(this)
        safe_btn_full!!.setOnClickListener(this)
        safe_btn_sos!!.setOnClickListener(this)
        safe_play_rec_edit_btn!!.setOnClickListener(this)
        surfaceView!!.holder.addCallback(this)

        addSubscribe(Flowable.interval(0, 60, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (!isPalyback) {
                        setDate(System.currentTimeMillis())
                    }
                })

        setVideo()

        setHistoryList()

    }

    protected fun setDate(timeInmillis: Long) {
        val dateFormat = SimpleDateFormat("MM.dd HH:mm")
        val time = dateFormat.format(Date(timeInmillis))
        if (!isPalyback) {
            safe_eq_title_tv!!.text = String.format("室外机 Live %s", time)
        } else {
            safe_eq_title_tv!!.text = String.format("回放 %s", time)
        }
    }

    protected fun initDoorAccessManager() {
        if (mDoorAccessManager == null) {
            mDoorAccessManager = JXPadSdk.getDoorAccessManager()
            mDoorAccessManager!!.setListUIListener(this)
            mDoorAccessManager!!.addConversationUIListener(this)
            mDoorAccessManager!!.addPlayBackListener(this)
        }
    }


    protected fun destroyDoorAccessManager() {
        mDoorAccessManager?.apply {
            hangupCall(mSessionId)
            updateCallWindow(mSessionId, null)
            setListUIListener(null)
            removeConversationUIListener(this@SafeOutsideFragment)
            removePlayBackListener(this@SafeOutsideFragment)
            LogUtil.i("destroyDoorAccessManager")
        }
        mDoorAccessManager=null

    }

    private fun setVideo() {
        setDate(System.currentTimeMillis())
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

    private fun setHistoryList() {
       recordList=recordList?:ArrayList()
//        if (null == recordList) {
//            recordList = ArrayList()
//        }

        val recordBeans = mDoorAccessManager!!.getHistoryListByType(familyID, DoorRecordBean.RECORD_TYPE_DOOR, 0, 50)
        if (null != recordBeans && recordBeans.size > 0) {
            recordList!!.clear()
            recordList!!.addAll(recordBeans)
        }
        recAdapter = PlayBackAdapter(mContext, recordList!!)
        rv_play_back!!.adapter = recAdapter
        recAdapter!!.setOnItemClickListener(this)

    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        initDoorAccessManager()
        safe_swich_live_btn!!.performClick()
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("SafeOutsideFragment onSupportVisible")
        if (null != mDoorAccessManager) {
            val recordBeans = mDoorAccessManager!!.getHistoryListByType(familyID, DoorRecordBean.RECORD_TYPE_DOOR, 0, 50)
            if (null != recordBeans && recordBeans.size > 0) {
                recordList!!.clear()
                recordList!!.addAll(recordBeans)
            }
            if (null != recAdapter) {
                recAdapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("SafeOutsideFragment onSupportInvisible")


        //如果是点击全屏导致fragment不显示，则不重置session有效性
        //        if (inVisibleType != REQUEST_CODE_FULL_LIVE && inVisibleType != REQUEST_CODE_FULL_HISTORY) {
        pausePlay()
        destroyDoorAccessManager()
        //        }
        if (!isEdit && null != safe_play_rec_edit_btn) {
            safe_play_rec_edit_btn!!.performClick()
        }
    }

    protected fun pausePlay() {
        if (safe_btn_pause!!.visibility == View.VISIBLE) {
            safe_btn_pause!!.performClick()
        }
        isSeeeionIdValid = false
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        //        surfaceView.setZOrderOnTop(true);
        //        surfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        if (mDoorAccessManager != null) {
            if (isPalyback) {
                mDoorAccessManager!!.updatePlayBackWindow(playBackSessionId, surfaceView)
            } else {
                mDoorAccessManager!!.updateCallWindow(mSessionId, surfaceView)
            }

        }
    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {

    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        if (mDoorAccessManager != null) {
            if (isPalyback) {
                mDoorAccessManager!!.updatePlayBackWindow(playBackSessionId, null)
            } else {
                mDoorAccessManager!!.updateCallWindow(mSessionId, null)
            }

        }
    }

    override fun startTransPort(sessionId: String) {
        if (!TextUtils.equals(mSessionId, sessionId)) {
            return
        }
        isSeeeionIdValid = true
        LogUtil.i("SafeOutsideFragment 开始传输视频 mSessionId $sessionId")
        if (loadingDialog != null) {
            loadingDialog!!.dismiss()
        }
    }

    override fun refreshEvent(event: DoorEvent) {

        if (!TextUtils.equals(mSessionId, event.sessionId)) {
            return
        }
        LogUtil.i("SafeOutsideFragment refreshEvent " + event.cmd + "  sessionid = " + event.sessionId)
        if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandHangup)) {
            //            mDoorAccessManager.hangupCall(mSessionId);

        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandSessionTimeout)) {
            if (!isPalyback) {
                ToastUtil.showShort(mContext, "超时")
            }
            isSeeeionIdValid = false
            //            mDoorAccessManager.hangupCall(mSessionId);
        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandPickupByOther)) {
            if (!isPalyback) {
                ToastUtil.showShort(mContext, "其他用户接听")
            }
            //            mDoorAccessManager.hangupCall(mSessionId);
        }
    }

    override fun onClick(v: View) {

        if (DoubleClickUtils.isDoubleClick()) {
            return
        }
        if (v.id == R.id.safe_play_rec_edit_btn) {//编辑
            LogUtil.i("编辑")
            if (isEdit) {   //编辑状态
                safe_play_rec_edit_btn!!.text = getString(R.string.complete)
                safe_rec_op_layout!!.visibility = View.VISIBLE
            } else {
                safe_play_rec_edit_btn!!.text = getString(R.string.edit)
                safe_rec_op_layout!!.visibility = View.GONE
            }

            //            safe_rec_op_layout.animate().translationY(isEdit ? 0 : ScreenUtil.dip2px(80)).setDuration(300).start();
            recAdapter!!.setEdit(isEdit)
            recAdapter!!.notifyDataSetChanged()

            isEdit = !isEdit
        } else if (v.id == R.id.safe_btn_sos) {    // SOS
        } else if (v.id == R.id.safe_btn_play) {   // 播放
            isPause = false
            safe_btn_pause!!.visibility = View.VISIBLE
            safe_btn_play!!.visibility = View.GONE
            val tempSeek = seek

            //            safe_seekbar.setOnSeekBarChangeListener(this);
            mDoorAccessManager!!.startPlayBack(playBackSessionId)
            mDoorAccessManager!!.seekPlayBack(playBackSessionId, tempSeek * 100 / max)
            mDoorAccessManager!!.updatePlayBackWindow(playBackSessionId, surfaceView)

        } else if (v.id == R.id.safe_btn_pause) { //暂停
            isPause = true
            safe_btn_pause!!.visibility = View.GONE
            safe_btn_play!!.visibility = View.VISIBLE
            mDoorAccessManager!!.pausePlayBack(playBackSessionId)
            //            mDoorAccessManager.removePlayBackListener(this);
            //            safe_seekbar.setOnSeekBarChangeListener(null);
        } else if (v.id == R.id.safe_btn_save) {   //保存
            //sdk自动保存
        } else if (v.id == R.id.safe_btn_refresh) {    //刷新
            setVideo()
        } else if (v.id == R.id.safe_btn_mute) {   //静音 todo????问一下这里的逻辑
            isMute = !isMute
            //            if(isPalyback){
            //                mDoorAccessManager.enableRemoteToLocalAudio(familyID,playBackSessionId,isMute);//不起作用，
            //            }else {
            //                mDoorAccessManager.enableRemoteToLocalAudio(familyID,mSessionId,isMute);
            //            }
            if (isMute) {
                audioHelper!!.setVoice100(0)//todo 音量键对安防sdk的视频播放器不起作用
                ToastUtil.showShort(mContext, "已静音")
                safe_btn_mute!!.setBackgroundImage(R.mipmap.btn_safe_mutebig_pressed, R.mipmap.btn_safe_mutebig_pressed)
            } else {
                //                audioHelper.setVoice100(audioValue);
                audioHelper!!.setVoice100(75)
                safe_btn_mute!!.setBackgroundImage(R.mipmap.btn_safe_mutebig, R.mipmap.btn_safe_mutebig_pressed)
            }
        } else if (v.id == R.id.safe_btn_full) {   //全屏
            if (isPalyback) {//回放
                val playbackIntent = Intent(mActivity, FullScreenActivity::class.java)
                playbackIntent.putExtra(Constants.INTENT_KEY_1, playBackSessionId)
                playbackIntent.putExtra(Constants.INTENT_KEY_2, isPalyback)
                playbackIntent.putExtra(Constants.INTENT_KEY_3, max)
                playbackIntent.putExtra(Constants.INTENT_KEY_4, seek)
                playbackIntent.putExtra(Constants.INTENT_KEY_5, isPause)

                startActivityForResult(playbackIntent, REQUEST_CODE_FULL_HISTORY)
                inVisibleType = REQUEST_CODE_FULL_HISTORY

            } else {//直播
                if (!isSeeeionIdValid) {//直播会话无效时不能全屏
                    return
                }
                val liveIntent = Intent(mActivity, FullScreenActivity::class.java)
                liveIntent.putExtra(Constants.INTENT_KEY_1, mSessionId)
                liveIntent.putExtra(Constants.INTENT_KEY_2, isPalyback)
                startActivityForResult(liveIntent, REQUEST_CODE_FULL_LIVE)
                inVisibleType = REQUEST_CODE_FULL_LIVE
            }

        } else if (v.id == R.id.safe_swich_live_btn) { //切回直播
            startLive()
            setVideo()

        } else if (v.id == R.id.safe_rec_all_choose_btn) { //全选
            recAdapter!!.chooseAll()
        } else if (v.id == R.id.safe_rec_del_btn) {    //删除

            if (null == recordList || recordList!!.size < 1)
                return
            val map = recAdapter!!.checkMap
            if (null == map || map.size() < 1)
                return
            val temp = ArrayList<DoorRecordBean>()
            for (i in recordList!!.indices) {
                if (map.get(i)) {
                    temp.add(recordList!![i])
                }
            }
            recordList!!.removeAll(temp)
            recAdapter!!.clearCheck()
            recAdapter!!.notifyDataSetChanged()
            mDoorAccessManager!!.deleteListRecord(temp)
        }
    }

    override fun onItemClick(adapter: RecyclerView.Adapter<*>?, holder: RecyclerView.ViewHolder, position: Int) {
        if (isEdit) {
            recordBean = recordList!![position]
            if (!File(recordBean!!.videoPath).exists()) {
                ToastUtil.showShort(mContext, "该记录没有录制视频")
                return
            }

            isPalyback = true
            ToastUtil.showShort(mContext, "开始回放")
            startPlayRec(recordList!![position])
        } else {
            recAdapter!!.itemClick(position)
        }
    }

    override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
        LogUtil.i("onProgressChanged seek = $i")
        seek = i
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        isTouchSeek = true
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        isTouchSeek = false
        if (max > 0 && seek > 0) {
            mDoorAccessManager!!.seekPlayBack(playBackSessionId, seek * 100 / max)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 200) {
            inVisibleType = 0
            when (requestCode) {
                REQUEST_CODE_FULL_LIVE -> if (mDoorAccessManager != null) {
                    mDoorAccessManager!!.updateCallWindow(mSessionId, surfaceView)
                }
                REQUEST_CODE_FULL_HISTORY -> if (mDoorAccessManager != null) {
                    mDoorAccessManager!!.updatePlayBackWindow(playBackSessionId, surfaceView)
                    if (data != null) {
                        val isPause = data.getBooleanExtra(Constants.INTENT_KEY_1, false)
                        run {
                            if (isPause) {
                                safe_btn_pause!!.visibility = View.GONE
                                safe_btn_play!!.visibility = View.VISIBLE
                            } else {
                                safe_btn_pause!!.visibility = View.VISIBLE
                                safe_btn_play!!.visibility = View.GONE
                            }
                        }
                    }

                }
            }//                    mDoorAccessManager.addPlayBackListener(this);
        }
    }

    override fun onMediaPlayEvent(session_id: String, event: Int, value: Long) {
        if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventDuration) {
            max = (value / unit).toInt()
            if (max == 0) {
                LogUtil.e("onMediaPlayEvent value=$value")
                max = 59
            }
            safe_seekbar!!.max = max
        } else if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventProgress) {
            seek = (value / unit).toInt()
            if (isTouchSeek) {
                return
            }
            safe_seekbar!!.setOnSeekBarChangeListener(null)
            safe_seekbar!!.progress = seek
            safe_seekbar!!.setOnSeekBarChangeListener(this)

            //            recordBean.chat_time
            safe_progress_time_current_tv!!.text = format(seek)
            safe_progress_time_count_tv!!.text = format(max)
        } else if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventCompleted) {
            seek = max
            safe_seekbar!!.setOnSeekBarChangeListener(null)
            safe_seekbar!!.progress = max
            safe_seekbar!!.setOnSeekBarChangeListener(this)
            mDoorAccessManager!!.pausePlayBack(playBackSessionId)
        }
        LogUtil.i("onMediaPlayEvent event = $event  value = $value  seek = $seek  max = $max")

    }

    private fun startLive() {
        isPalyback = false
        isPause = false

        mDoorAccessManager!!.updatePlayBackWindow(playBackSessionId, null)
        mDoorAccessManager!!.updateCallWindow(mSessionId, surfaceView)

        safe_rec_seek_layout.visibility = View.GONE
        safe_btn_play!!.visibility = View.GONE
        safe_btn_pause!!.visibility = View.GONE
        safe_btn_save!!.visibility = View.GONE
        //        safe_btn_sos.setVisibility(View.VISIBLE);
        safe_btn_refresh!!.visibility = View.VISIBLE

        mDoorAccessManager!!.pausePlayBack(playBackSessionId)
    }

    private fun startPlayRec(record: DoorRecordBean) {
        setDate(record.startTime)
        `$`<View>(R.id.safe_rec_seek_layout).visibility = View.VISIBLE
        safe_btn_pause!!.visibility = View.VISIBLE
        safe_btn_play!!.visibility = View.GONE
        //        safe_btn_save.setVisibility(View.VISIBLE);
        safe_btn_sos!!.visibility = View.GONE
        safe_btn_refresh!!.visibility = View.GONE

        safe_seekbar!!.setOnSeekBarChangeListener(this)

        playBackSessionId = record.session_id
        mDoorAccessManager!!.startPlayBack(playBackSessionId)

        mDoorAccessManager!!.updateCallWindow(mSessionId, null)
        mDoorAccessManager!!.updatePlayBackWindow(playBackSessionId, surfaceView)

    }

    override fun refreshList() {
        LogUtil.i("refreshList")
        setVideo()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        pausePlay()
        destroyDoorAccessManager()
        LogUtil.i("SafeOutsideFragment onDestroyView")
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
