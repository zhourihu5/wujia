package com.wujia.lib_common.utils;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;

/**
 * Created by xmren on 2018/5/19.
 */
public class RecorderHelper {

    private String mOutputFileName = FileConfig.DIR_CHAT_VOICE_CACHE;
    private MediaRecorder mRecorder = null;
    private long mStartTime = 0L;
    private static volatile RecorderHelper mRecorderHelper = null;

    private RecorderHelper() {
    }

    public static RecorderHelper getInstance() {
        if (mRecorderHelper == null) {
            synchronized (RecorderHelper.class) {
                if (mRecorderHelper == null) {
                    mRecorderHelper = new RecorderHelper();
                }
            }
        }
        return mRecorderHelper;
    }

    public void initRecorder() {
        mRecorder = new MediaRecorder();
    }

    public void startRecord(String outputFileName) {
        if (mRecorder == null) {
            initRecorder();
        }
        this.mOutputFileName = outputFileName;
        File parentFile = new File(outputFileName).getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mRecorder.setOutputFile(mOutputFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecorder.start();
        mStartTime = System.currentTimeMillis();
    }


    public long stopRecord() {
        if (mRecorder != null) {
            mRecorder.stop();
            return System.currentTimeMillis() - mStartTime;
        }
        return 0L;
    }

    public void exitRecorder() {
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
    }

    public String getOutputFileName() {
        return mOutputFileName;
    }

    public double getAmplitude() {
        if (mRecorder != null) {
            return (mRecorder.getMaxAmplitude() / 2700.0);
        } else {
            return 0;
        }
    }
}
