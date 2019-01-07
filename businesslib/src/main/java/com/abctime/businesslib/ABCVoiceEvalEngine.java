package com.abctime.businesslib;

import android.app.Activity;
import android.content.Context;

import com.abctime.lib_common.utils.LogUtil;
import com.constraint.CoreProvideTypeEnum;
import com.google.gson.Gson;
import com.tal.ailab.speech.TALVoiceEvalEngine;
import com.tal.ailab.speech.entity.SDKParam;
import com.tal.ailab.speech.entity.ServerTypeEnum;
import com.tal.ailab.speech.entity.VoiceEvalTypeEnum;
import com.tal.ailab.speech.result.XSEvalResultModel;
import com.xs.BaseSingEngine.ResultListener;
import com.xs.SingEngine;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * description: 语音工具引擎
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/7/5 下午3:59
 */

public class ABCVoiceEvalEngine {

    private final TALVoiceEvalEngine mVoiceEvalEngine;
    private TALVoiceEvalEngine.ResultListener mResultListener;
    private TALVoiceEvalEngine.OnRecordListener mOnRecordListener;
    private TALVoiceEvalEngine.OnVoiceEvalStopListener mOnVoiceEvalStopListener;
    private Activity mActivity;

    public ABCVoiceEvalEngine(Activity context, SDKParam param) {
        mActivity = context;
        mVoiceEvalEngine = new TALVoiceEvalEngine(context, param, 1L, 4L);
        mVoiceEvalEngine.setOpenVad(false);
        //设置评测引擎的配置、需要在newEngine方法前构造好
        mVoiceEvalEngine.setVoiceEvalType(VoiceEvalTypeEnum.EVAL_TYPE_LOCAL_SENT);
        mVoiceEvalEngine.setServerType(ServerTypeEnum.SERVER_TYPE_NATIVE);
        mVoiceEvalEngine.setFrontVadTime(2000);
        mVoiceEvalEngine.setBackVadTime(2000);
        mVoiceEvalEngine.setRealtimeEval(false);
        createListener();
        mVoiceEvalEngine.setListener(mResultListener);
        //构造一个新的引擎
        mVoiceEvalEngine.newEngine();
    }

    private void createListener() {
        if (mResultListener != null) {
            return;
        }
        mResultListener = new TALVoiceEvalEngine.ResultListener() {
            @Override
            public void onBegin() {

            }

            @Override
            public void onResult(final String result) {
                mActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        if (mOnVoiceEvalStopListener != null) {
                            try {
                                JSONObject object = new JSONObject(result);
                                String resultStr = object.optString("result");
                                mOnVoiceEvalStopListener.onVoiceEvalStop(result, resultStr);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }

            @Override
            public void onRealTimeEval(String s) {

            }

            @Override
            public void onEnd(final int code, final String msg) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (code != 0) {
                            Throwable t = new Throwable(msg);
                            mOnRecordListener.onRecordStop(t);
                        }
                    }
                });
            }

            @Override
            public void onUpdateVolume(int i) {

            }

            @Override
            public void onFrontVadTimeOut() {

            }

            @Override
            public void onBackVadTimeOut() {

            }

            @Override
            public void onRecordingBuffer(byte[] bytes, int i) {

            }

            @Override
            public void onRecordLengthOut() {

            }

            @Override
            public void onReady() {

            }

            @Override
            public void onPlayCompeleted() {

            }

            @Override
            public void onRecordStop() {
                mActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        if (mOnRecordListener != null)
                            mOnRecordListener.onRecordStop(null);
                    }
                });
            }
        };
    }

    public boolean isAvailable() {
        return mVoiceEvalEngine != null && mVoiceEvalEngine.isAvailable();
    }

    public boolean isRecording() {
        return mVoiceEvalEngine != null && mVoiceEvalEngine.isRecording();
    }

    public void stopRecord() {
        if (mVoiceEvalEngine != null)
            mVoiceEvalEngine.stopRecord();
    }

    public void startRecordEval(String spokenText, String filePath) {
        if (mVoiceEvalEngine != null)
            mVoiceEvalEngine.startRecordEval(spokenText, filePath);
    }

    public void cancelRecord() {
        if (mVoiceEvalEngine != null)
            mVoiceEvalEngine.cancel();
    }

    public void deleteRecord() {
        if (mVoiceEvalEngine != null)
            mVoiceEvalEngine.delete();
        if (mResultListener != null) {
            mResultListener = null;
        }
        if (mOnRecordListener != null)
            mOnRecordListener = null;
        if (mOnVoiceEvalStopListener != null)
            mOnVoiceEvalStopListener = null;
    }

    public void setOnRecordStopListener(TALVoiceEvalEngine.OnRecordListener onRecordListener) {
        mOnRecordListener = onRecordListener;
    }

    public void setOnVoiceEvalStopListener(TALVoiceEvalEngine.OnVoiceEvalStopListener onVoiceEvalStopListener) {
        mOnVoiceEvalStopListener = onVoiceEvalStopListener;
    }
}
