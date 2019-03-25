package com.wujia.intellect.terminal.safe.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.intercom.sdk.IntercomConstants;
import com.intercom.sdk.IntercomObserver;
import com.jingxi.smartlife.pad.sdk.JXPadSdk;
import com.jingxi.smartlife.pad.sdk.doorAccess.DoorAccessManager;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorEvent;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorRecordBean;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessConversationUI;
import com.wujia.businesslib.Constants;
import com.wujia.intellect.terminal.safe.R;
import com.wujia.intellect.terminal.safe.mvp.adapter.PlayBackAdapter;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.utils.AudioMngHelper;
import com.wujia.lib_common.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：可视安防 外机
 */
public class SafeOutsideFragment extends BaseFragment implements
        SurfaceHolder.Callback, DoorAccessConversationUI, View.OnClickListener, MultiItemTypeAdapter.OnRVItemClickListener, SeekBar.OnSeekBarChangeListener, IntercomObserver.OnPlaybackListener {

    private AudioMngHelper audioHelper;
    private boolean isMute;
    private int audioValue;

    private SurfaceView surfaceView;
    private DoorAccessManager mDoorAccessManager;
    private String sessionId;

    private RecyclerView rvPlayBack;
    private TextView btnEdit;
    private SeekBar seekBar;
    private TextView btnPlay, btnPause, btnRefrsh, btnSave, btnMute, btnFull, btnSos;


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

        sessionId = getArguments().getString(Constants.ARG_PARAM_1);

        surfaceView = $(R.id.surface);
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


        mDoorAccessManager = JXPadSdk.getDoorAccessManager();
        mDoorAccessManager.setConversationUIListener(this);

        btnEdit.setOnClickListener(this);

        List<DoorRecordBean> datas = new ArrayList<>();
        datas.add(null);
        datas.add(null);
        datas.add(null);
        datas.add(null);
        datas.add(null);
        datas.add(null);
        datas.add(null);
        datas.add(null);
        datas.add(null);
        datas.add(null);
        datas.add(null);
        datas.add(null);
        datas.add(null);
        datas.add(null);
        datas.add(null);
        datas.add(null);
        datas.add(null);

        recAdapter = new PlayBackAdapter(mContext, datas);
        rvPlayBack.setAdapter(recAdapter);

        recAdapter.setOnItemClickListener(this);

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("SafeOutsideFragment onSupportVisible");

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("SafeOutsideFragment onSupportInvisible");
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mDoorAccessManager.updateCallWindow(sessionId, surfaceView);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mDoorAccessManager.updateCallWindow(sessionId, null);
    }


    public void updateSurface(View v) {
        mDoorAccessManager.updateCallWindow(sessionId, surfaceView);
    }

//    @Override
//    public void startTransPort(String sessionID) {
//        showToast("开始传输视频");
//    }

    @Override
    public void startTransPort() {

    }

    @Override
    public void refreshEvent(DoorEvent doorEvent) {

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.safe_play_rec_edit_btn) {
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
        } else if (v.getId() == R.id.safe_btn_sos) {
            startActivity(new Intent(mActivity, VideoCallActivity.class));
        } else if (v.getId() == R.id.safe_btn_play) {

        } else if (v.getId() == R.id.safe_btn_pause) {

        } else if (v.getId() == R.id.safe_btn_save) {

        } else if (v.getId() == R.id.safe_btn_refresh) {

        } else if (v.getId() == R.id.safe_btn_mute) {
            isMute = !isMute;
            if (isMute) {
                audioHelper.setVoice100(0);
            } else {
                audioHelper.setVoice100(audioValue);
            }
        } else if (v.getId() == R.id.safe_btn_full) {
            startActivity(new Intent(mActivity, SafeFullScreenActivity.class));

        } else if (v.getId() == R.id.safe_swich_live_btn) {
            startLive();
        } else if (v.getId() == R.id.safe_rec_all_choose_btn) {
            recAdapter.chooseAll();
        } else if (v.getId() == R.id.safe_rec_del_btn) {

        }
    }

    @Override
    public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (isEdit) {
//            ToastUtil.showShort(mContext,"开始回放");
            startPlayRec(position);
        } else {
            recAdapter.itemClick(position);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        seek = i;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isTouchSeek = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isTouchSeek = false;
        mDoorAccessManager.seekPlayBack(sessionId, seek * 100 / max);
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
        } else if (event == IntercomConstants.MediaPlayEvent.MediaPlayEventCompleted) {
            seek = max;
            seekBar.setOnSeekBarChangeListener(null);
            seekBar.setProgress(max);
            seekBar.setOnSeekBarChangeListener(this);
            mDoorAccessManager.pausePlayBack(sessionId);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDoorAccessManager.removePlayBackListener(this);
        LogUtil.i("SafeOutsideFragment onDestroyView");
    }

    private void startLive() {
        $(R.id.safe_rec_seek_layout).setVisibility(View.GONE);
        btnPlay.setVisibility(View.GONE);
        btnPause.setVisibility(View.GONE);
        btnSave.setVisibility(View.GONE);
        btnSos.setVisibility(View.VISIBLE);
        btnRefrsh.setVisibility(View.VISIBLE);
    }

    private void startPlayRec(int pos) {
        $(R.id.safe_rec_seek_layout).setVisibility(View.VISIBLE);
        btnPause.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.VISIBLE);
        btnSos.setVisibility(View.GONE);
        btnRefrsh.setVisibility(View.GONE);
    }

}
