package com.jingxi.smartlife.pad.safe.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.intercom.sdk.IntercomConstants;
import com.intercom.sdk.IntercomObserver;
import com.jingxi.smartlife.pad.safe.mvp.adapter.PlayBackAdapter;
import com.jingxi.smartlife.pad.sdk.JXPadSdk;
import com.jingxi.smartlife.pad.sdk.doorAccess.DoorAccessManager;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorDevice;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorEvent;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorRecordBean;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessConversationUI;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessListUI;
import com.wujia.businesslib.Constants;
import com.wujia.businesslib.base.DataManager;
import com.jingxi.smartlife.pad.safe.R;
import com.wujia.businesslib.dialog.LoadingDialog;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.utils.AudioMngHelper;
import com.wujia.lib_common.utils.DoubleClickUtils;
import com.wujia.lib_common.utils.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：可视安防 外机
 */
public class SafeOutsideFragment extends BaseFragment implements
        SurfaceHolder.Callback, DoorAccessConversationUI, DoorAccessListUI, View.OnClickListener, MultiItemTypeAdapter.OnRVItemClickListener, SeekBar.OnSeekBarChangeListener, IntercomObserver.OnPlaybackListener {

    private static final int REQUEST_CODE_FULL_LIVE = 10;
    private static final int REQUEST_CODE_FULL_HISTORY = 20;
    private int inVisibleType = 0;//不可见时的状态，0是正常，10，20为全屏，全屏时不做复位操作；
    private boolean isSeeeionIdValid;//防止多次刷新创建多个会话，sessionId未超时即有效
    private boolean isPalyback; //true为回放，false为直播；
    private boolean isPause;//回放的播放状态
    private AudioMngHelper audioHelper;
    private boolean isMute;
    private int audioValue;

    private SurfaceView surfaceView;
    private SurfaceView svPlayback;
    private DoorAccessManager mDoorAccessManager;
    private String mSessionId, playBackSessionId;

    private RecyclerView rvPlayBack;
    private TextView btnEdit;
    private SeekBar seekBar;
    private TextView btnPlay, btnPause, btnRefrsh, btnSave, btnMute, btnFull, btnSos;
    private TextView tvPlaybackCurrentTime, tvPlaybackCountTime;

    private boolean isEdit = true;
    private PlayBackAdapter recAdapter;
    private int seek;
    private int max;
    /**
     * listener 中返回的value 单位是 微秒
     */
    private final long unit = 1000 * 1000;

    /**
     * 是否在滑动进度条
     */
    private boolean isTouchSeek = false;
    private View layoutBottomOp;
    private String familyID = DataManager.getFamilyId();
    private List<DoorRecordBean> recordList;
    private DoorRecordBean recordBean;
    private LoadingDialog loadingDialog;

    public SafeOutsideFragment() {
    }

    public static SafeOutsideFragment newInstance() {
        SafeOutsideFragment fragment = new SafeOutsideFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        LogUtil.i("SafeOutsideFragment getLayoutId");
        return R.layout.fragment_safe_outside;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        LogUtil.i("SafeOutsideFragment onLazyInitView");

        audioHelper = new AudioMngHelper(mContext);
        audioValue = audioHelper.get100CurrentVolume();

        surfaceView = $(R.id.surface);
        svPlayback = $(R.id.surface_playback);
        rvPlayBack = $(R.id.rv_play_back);
        btnEdit = $(R.id.safe_play_rec_edit_btn);
        seekBar = $(R.id.safe_seekbar);
        layoutBottomOp = $(R.id.safe_rec_op_layout);
        btnSos = $(R.id.safe_btn_sos);
        btnPlay = $(R.id.safe_btn_play);
        btnPause = $(R.id.safe_btn_pause);
        btnSave = $(R.id.safe_btn_save);
        btnMute = $(R.id.safe_btn_mute);
        btnRefrsh = $(R.id.safe_btn_refresh);
        btnFull = $(R.id.safe_btn_full);

        tvPlaybackCurrentTime = $(R.id.safe_progress_time_current_tv);
        tvPlaybackCountTime = $(R.id.safe_progress_time_count_tv);

        $(R.id.safe_swich_live_btn).setOnClickListener(this);
        $(R.id.safe_rec_all_choose_btn).setOnClickListener(this);
        $(R.id.safe_rec_del_btn).setOnClickListener(this);
        $(R.id.safe_btn_full).setOnClickListener(this);

        btnSos.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnMute.setOnClickListener(this);
        btnRefrsh.setOnClickListener(this);
        btnFull.setOnClickListener(this);
        btnSos.setOnClickListener(this);
        btnEdit.setOnClickListener(this);

        mDoorAccessManager = JXPadSdk.getDoorAccessManager();
        mDoorAccessManager.setListUIListener(this);
        mDoorAccessManager.addConversationUIListener(this);
        mDoorAccessManager.addPlayBackListener(this);

        setVideo();

        setHistoryList();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setVideo() {

        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(mContext);
        }
        loadingDialog.setCancelOnTouchOutside(true);
        loadingDialog.setTitle("正在连接中...");
        loadingDialog.show();
        if (isSeeeionIdValid) {
            mDoorAccessManager.updateCallWindow(mSessionId, surfaceView);
            loadingDialog.dismiss();
            return;
        }
        List<DoorDevice> list = mDoorAccessManager.getDevices(familyID);
        for (DoorDevice device : list) {
            if (DoorDevice.TYPE_UNIT == device.getMyDeviceType()) {
                mSessionId = mDoorAccessManager.monitor(familyID, device);
                LogUtil.i("SafeOutsideFragment mSessionId " + mSessionId);

                mDoorAccessManager.updateCallWindow(mSessionId, surfaceView);
                return;
            }
        }
    }

    private void setHistoryList() {

        if (null == recordList) {
            recordList = new ArrayList<>();
        }

        List<DoorRecordBean> recordBeans = mDoorAccessManager.getHistoryListByType(familyID, DoorRecordBean.RECORD_TYPE_DOOR, 0, 50);
        if (null != recordBeans && recordBeans.size() > 0) {
            recordList.clear();
            recordList.addAll(recordBeans);
        }
        recAdapter = new PlayBackAdapter(mContext, recordList);
        rvPlayBack.setAdapter(recAdapter);
        recAdapter.setOnItemClickListener(this);

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("SafeOutsideFragment onSupportVisible");

        if (null != mDoorAccessManager) {
            List<DoorRecordBean> recordBeans = mDoorAccessManager.getHistoryListByType(familyID, DoorRecordBean.RECORD_TYPE_DOOR, 0, 50);
            if (null != recordBeans && recordBeans.size() > 0) {
                recordList.clear();
                recordList.addAll(recordBeans);
            }
            if (null != recAdapter) {
                recAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("SafeOutsideFragment onSupportInvisible");

        //如果是点击全屏导致fragment不显示，则不重置session有效性
        if (inVisibleType != REQUEST_CODE_FULL_LIVE && inVisibleType != REQUEST_CODE_FULL_HISTORY) {

            //不可见时暂停回放
            if (btnPause.getVisibility() == View.VISIBLE) {
                btnPause.performClick();
            }
            isSeeeionIdValid = false;
        }
        if (!isEdit && null != btnEdit) {
            btnEdit.performClick();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
//        surfaceView.setZOrderOnTop(true);
//        surfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        mDoorAccessManager.updateCallWindow(mSessionId, surfaceView);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mDoorAccessManager.updateCallWindow(mSessionId, null);
    }

    @Override
    public void startTransPort(String sessionId) {
        if (!TextUtils.equals(mSessionId, sessionId)) {
            return;
        }
        isSeeeionIdValid = true;
        LogUtil.i("SafeOutsideFragment 开始传输视频 mSessionId " + sessionId);
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void refreshEvent(DoorEvent event) {

        if (!TextUtils.equals(mSessionId, event.sessionId)) {
            return;
        }
        LogUtil.i("SafeOutsideFragment refreshEvent " + event.cmd + "  sessionid = " + event.sessionId);
        if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandHangup)) {
//            mDoorAccessManager.hangupCall(mSessionId);

        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandSessionTimeout)) {
            ToastUtil.showShort(mContext, "超时");
            isSeeeionIdValid = false;
//            mDoorAccessManager.hangupCall(mSessionId);
        } else if (TextUtils.equals(event.getCmd(), IntercomConstants.kIntercomCommandPickupByOther)) {
            ToastUtil.showShort(mContext, "其他用户接听");
//            mDoorAccessManager.hangupCall(mSessionId);
        }
    }

    @Override
    public void onClick(View v) {

        if (DoubleClickUtils.isDoubleClick()) {
            return;
        }
        if (v.getId() == R.id.safe_play_rec_edit_btn) {//编辑
            LogUtil.i("编辑");
            if (isEdit) {   //编辑状态
                btnEdit.setText(getString(R.string.complete));
                layoutBottomOp.setVisibility(View.VISIBLE);
            } else {
                btnEdit.setText(getString(R.string.edit));
                layoutBottomOp.setVisibility(View.GONE);
            }

//            layoutBottomOp.animate().translationY(isEdit ? 0 : ScreenUtil.dip2px(80)).setDuration(300).start();
            recAdapter.setEdit(isEdit);
            recAdapter.notifyDataSetChanged();

            isEdit = !isEdit;
        } else if (v.getId() == R.id.safe_btn_sos) {    // SOS
            startActivity(new Intent(mActivity, VideoCallActivity.class));
        } else if (v.getId() == R.id.safe_btn_play) {   // 播放
            isPause = false;
            btnPause.setVisibility(View.VISIBLE);
            btnPlay.setVisibility(View.GONE);
            int tempSeek = seek;

//            seekBar.setOnSeekBarChangeListener(this);
            mDoorAccessManager.startPlayBack(playBackSessionId);
            mDoorAccessManager.seekPlayBack(playBackSessionId, tempSeek * 100 / max);
            mDoorAccessManager.updatePlayBackWindow(playBackSessionId, svPlayback);

        } else if (v.getId() == R.id.safe_btn_pause) { //暂停
            isPause = true;
            btnPause.setVisibility(View.GONE);
            btnPlay.setVisibility(View.VISIBLE);
            mDoorAccessManager.pausePlayBack(playBackSessionId);
//            mDoorAccessManager.removePlayBackListener(this);
//            seekBar.setOnSeekBarChangeListener(null);
        } else if (v.getId() == R.id.safe_btn_save) {   //保存
            //sdk自动保存
        } else if (v.getId() == R.id.safe_btn_refresh) {    //刷新
//            mDoorAccessManager.updateCallWindow(mSessionId, surfaceView);
            setVideo();
        } else if (v.getId() == R.id.safe_btn_mute) {   //静音
            isMute = !isMute;
            if (isMute) {
                audioHelper.setVoice100(0);
                ToastUtil.showShort(mContext, "已静音");
            } else {
//                audioHelper.setVoice100(audioValue);
                audioHelper.setVoice100(75);
            }
        } else if (v.getId() == R.id.safe_btn_full) {   //全屏
            if (isPalyback) {//回放
//                mDoorAccessManager.removePlayBackListener(this);
                Intent playbackIntent = new Intent(mActivity, FullScreenActivity.class);
                playbackIntent.putExtra(Constants.INTENT_KEY_1, playBackSessionId);
                playbackIntent.putExtra(Constants.INTENT_KEY_2, isPalyback);
                playbackIntent.putExtra(Constants.INTENT_KEY_3, max);
                playbackIntent.putExtra(Constants.INTENT_KEY_4, seek);
                playbackIntent.putExtra(Constants.INTENT_KEY_5, isPause);

                startActivityForResult(playbackIntent, REQUEST_CODE_FULL_HISTORY);
                inVisibleType = REQUEST_CODE_FULL_HISTORY;

            } else {//直播
                if (!isSeeeionIdValid) {//直播会话无效时不能全屏
                    return;
                }
                Intent liveIntent = new Intent(mActivity, FullScreenActivity.class);
                liveIntent.putExtra(Constants.INTENT_KEY_1, mSessionId);
                liveIntent.putExtra(Constants.INTENT_KEY_2, isPalyback);
                startActivityForResult(liveIntent, REQUEST_CODE_FULL_LIVE);
                inVisibleType = REQUEST_CODE_FULL_LIVE;
            }

        } else if (v.getId() == R.id.safe_swich_live_btn) { //切回直播
            isPalyback = false;
            isPause = false;

            surfaceView.setVisibility(View.VISIBLE);
            svPlayback.setVisibility(View.INVISIBLE);
            startLive();
            setVideo();

        } else if (v.getId() == R.id.safe_rec_all_choose_btn) { //全选
            recAdapter.chooseAll();
        } else if (v.getId() == R.id.safe_rec_del_btn) {    //删除

            if (null == recordList || recordList.size() < 1)
                return;
            SparseBooleanArray map = recAdapter.getCheckMap();
            if (null == map || map.size() < 1)
                return;
            List<DoorRecordBean> temp = new ArrayList<>();
            for (int i = 0; i < recordList.size(); i++) {
                if (map.get(i)) {
                    temp.add(recordList.get(i));
                }
            }
            recordList.removeAll(temp);
            recAdapter.clearCheck();
            recAdapter.notifyDataSetChanged();
            mDoorAccessManager.deleteListRecord(temp);
        }
    }

    @Override
    public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (isEdit) {
            recordBean = recordList.get(position);
            if (!new File(recordBean.videoPath).exists()) {
                ToastUtil.showShort(mContext, "该记录没有录制视频");
                return;
            }

            ToastUtil.showShort(mContext, "开始回放");
            startPlayRec(recordList.get(position));
            isPalyback = true;
        } else {
            recAdapter.itemClick(position);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        LogUtil.i("onProgressChanged seek = " + i);
        seek = i;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isTouchSeek = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isTouchSeek = false;
        mDoorAccessManager.seekPlayBack(playBackSessionId, seek * 100 / max);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            inVisibleType = 0;
            switch (requestCode) {
                case REQUEST_CODE_FULL_LIVE:
                    mDoorAccessManager.updateCallWindow(mSessionId, surfaceView);
                    break;
                case REQUEST_CODE_FULL_HISTORY:
                    mDoorAccessManager.updatePlayBackWindow(playBackSessionId, svPlayback);
                    if (data != null) {
                        boolean isPause = data.getBooleanExtra(Constants.INTENT_KEY_1, false);
                        {
                            if (isPause) {
                                btnPause.setVisibility(View.GONE);
                                btnPlay.setVisibility(View.VISIBLE);
                            } else {
                                btnPause.setVisibility(View.VISIBLE);
                                btnPlay.setVisibility(View.GONE);
                            }
                        }
                    }
//                    mDoorAccessManager.addPlayBackListener(this);

                    break;
            }
        }
    }

    @Override
    public void onMediaPlayEvent(String session_id, int event, long value) {
        if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventDuration) {
            max = (int) (value / unit);
            seekBar.setMax(max);
        } else if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventProgress) {
            seek = (int) (value / unit);
            if (isTouchSeek) {
                return;
            }
            seekBar.setOnSeekBarChangeListener(null);
            seekBar.setProgress(seek);
            seekBar.setOnSeekBarChangeListener(this);

//            recordBean.chat_time
            tvPlaybackCurrentTime.setText(String.valueOf(format(seek)));
            tvPlaybackCountTime.setText(String.valueOf(format(max)));
        } else if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventCompleted) {
            seek = max;
            seekBar.setOnSeekBarChangeListener(null);
            seekBar.setProgress(max);
            seekBar.setOnSeekBarChangeListener(this);
            mDoorAccessManager.pausePlayBack(playBackSessionId);
        }
        LogUtil.i("onMediaPlayEvent event = " + event + "  value = " + value + "  seek = " + seek + "  max = " + max);

    }

    private void startLive() {
        $(R.id.safe_rec_seek_layout).setVisibility(View.GONE);
        btnPlay.setVisibility(View.GONE);
        btnPause.setVisibility(View.GONE);
        btnSave.setVisibility(View.GONE);
//        btnSos.setVisibility(View.VISIBLE);
        btnRefrsh.setVisibility(View.VISIBLE);

        mDoorAccessManager.pausePlayBack(playBackSessionId);
//        mDoorAccessManager.removePlayBackListener(this);
//        seekBar.setOnSeekBarChangeListener(null);
    }

    private void startPlayRec(DoorRecordBean record) {
        $(R.id.safe_rec_seek_layout).setVisibility(View.VISIBLE);
        btnPause.setVisibility(View.VISIBLE);
        btnPlay.setVisibility(View.GONE);
//        btnSave.setVisibility(View.VISIBLE);
        btnSos.setVisibility(View.GONE);
        btnRefrsh.setVisibility(View.GONE);

        seekBar.setOnSeekBarChangeListener(this);

        playBackSessionId = record.session_id;
        mDoorAccessManager.startPlayBack(playBackSessionId);

        surfaceView.setVisibility(View.INVISIBLE);
        svPlayback.setVisibility(View.VISIBLE);
        mDoorAccessManager.updatePlayBackWindow(playBackSessionId, svPlayback);

    }

    @Override
    public void refreshList() {
        LogUtil.i("refreshList");
        setVideo();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isSeeeionIdValid = false;
        mDoorAccessManager.removeConversationUIListener(this);
        mDoorAccessManager.removePlayBackListener(this);
        LogUtil.i("SafeOutsideFragment onDestroyView");
    }

    private String format(int time) {

        int m = time / 60;
        int s = time % 60;

        String mstr;
        String sstr;
        if (m < 10) {
            mstr = "0" + m;
        } else {
            mstr = String.valueOf(m);
        }
        if (s < 10) {
            sstr = "0" + s;
        } else {
            sstr = String.valueOf(s);
        }

        return mstr + ":" + sstr;
    }
}
