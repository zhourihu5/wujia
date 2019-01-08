package com.wujia.lib_common.utils.mediaplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.wujia.lib_common.utils.AppContext;
import com.wujia.lib_common.utils.FileConfig;
import com.wujia.lib_common.utils.FileUtils;
import com.wujia.lib_common.utils.LogUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * description:
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/6/28 下午8:09
 */

public class PlayWorker {
    static final int TYPE_AUDIO = 101;
    static final int TYPE_VIDEO = 102;
    private static final int MSG_WHAT_PLAY = 1000;
    private MediaPlayer mPlayer = null;
    private String mCurrentUrl = "";
    private Handler mHandler = null;
    private int mMediaType;
    Handler.Callback downloadCallback = new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == MSG_WHAT_PLAY) {
                if (msg.obj instanceof File) {
                    File localFile = (File) msg.obj;
                    playFromLocal(localFile.getAbsolutePath());
                }
            }
            return false;
        }
    };
    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            preparedToStart(mp);
        }
    };
    private MediaProgressTimer mediaProgressTimer;
    private ListenerInfo mListenerInfo;
    private int mIntervalMs;

    PlayWorker(int type) {
        mPlayer = new MediaPlayer();
        mHandler = new Handler(downloadCallback);
        mMediaType = type;
        mListenerInfo = new ListenerInfo();
    }

    public void play(String url) {

        if (isPlaying()) {
            mPlayer.stop();
            cancelTimer();
        }

        //1暂停后继续播放操作 2在此播放
        if (TextUtils.equals(mCurrentUrl, url)) {
            preparedToStart(mPlayer);
            return;
        }

        mCurrentUrl = url;

        if (mPlayer != null) {
            // ----------------------------------
            // - 系统MediaPlayer在音频为播放完成时调用暂停方法可能回执行onCompletion方法，
            // - 此时MediaPlayer的currentPosition属性是不准的，需要执行seekTo（0）操作，
            // - 调用者需要在onCompletion回调中重置UI和数据，与MediaPlayer保持一致。
            // -----------------------------------
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.seekTo(0);
                    if (mListenerInfo != null && mListenerInfo.mOnMediaCompleteListener != null) {
                        mListenerInfo.mOnMediaCompleteListener.onMediaComplete(mCurrentUrl);
                    }
                    cancelTimer();
                }
            });

            mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    newPlayer();
                    if (mListenerInfo != null && mListenerInfo.mOnMediaErrorListener != null) {
                        mListenerInfo.mOnMediaErrorListener.onMediaError(mCurrentUrl);
                    }
                    cancelTimer();
                    return true;
                }
            });
        }

        if (mCurrentUrl.startsWith("file")) {
            playFromLocal(mCurrentUrl);
        } else if (mCurrentUrl.startsWith("http")) {
            File localDir = new File(AppContext.get().getCacheDir(), FileConfig.DIR_CHAT_VOICE_CACHE);
            String fileName = FileUtils.getDownloadFileName(mCurrentUrl);
            final File localFile = new File(localDir, fileName);
            if (localFile.exists() && localFile.isFile()) {
                playFromLocal(localFile.getAbsolutePath());
            } else {
                playFromNetwork(mCurrentUrl, localFile);
            }
        } else if (mCurrentUrl.startsWith("android.resource")) {
            playFromRaw(mCurrentUrl);
        } else {
            playFromLocal(mCurrentUrl);
        }
    }

    public void playNet(String url) {

        if (isPlaying()) {
            mPlayer.stop();
            cancelTimer();
        }

        //1暂停后继续播放操作 2在此播放
        if (TextUtils.equals(mCurrentUrl, url)) {
            preparedToStart(mPlayer);
            return;
        }

        mCurrentUrl = url;

        if (mPlayer != null) {
            // ----------------------------------
            // - 系统MediaPlayer在音频为播放完成时调用暂停方法可能回执行onCompletion方法，
            // - 此时MediaPlayer的currentPosition属性是不准的，需要执行seekTo（0）操作，
            // - 调用者需要在onCompletion回调中重置UI和数据，与MediaPlayer保持一致。
            // -----------------------------------
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.seekTo(0);
                    if (mListenerInfo != null && mListenerInfo.mOnMediaCompleteListener != null) {
                        mListenerInfo.mOnMediaCompleteListener.onMediaComplete(mCurrentUrl);
                    }
                    cancelTimer();
                }
            });

            mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    newPlayer();
                    if (mListenerInfo != null && mListenerInfo.mOnMediaErrorListener != null) {
                        mListenerInfo.mOnMediaErrorListener.onMediaError(mCurrentUrl);
                    }
                    cancelTimer();
                    return true;
                }
            });
        }

        playFromLocal(mCurrentUrl);

    }

    private void newPlayer() {
        if (mPlayer != null)
            mPlayer.release();
        mPlayer = new MediaPlayer();
    }

    public boolean isPlaying() {
        return mPlayer != null && mPlayer.isPlaying();
    }

    public void resume() {
        if (mPlayer != null) {
            preparedToStart(mPlayer);
        }
    }

    public void pause() {
        if (mPlayer != null) {
            mPlayer.pause();
            if (mListenerInfo != null && mListenerInfo.mOnMediaPauseListener != null)
                mListenerInfo.mOnMediaPauseListener.onMediaPause(mCurrentUrl);
        }
        cancelTimer();
    }

    public void stop() {
        if (mPlayer != null) {
            mPlayer.stop();
            if (mListenerInfo != null && mListenerInfo.mOnMediaCompleteListener != null) {
                mListenerInfo.mOnMediaCompleteListener.onMediaComplete(mCurrentUrl);
            }
        }
        cancelTimer();
        mCurrentUrl = null;
    }

    public void release() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
            mHandler.removeMessages(MSG_WHAT_PLAY);
//            if (mListenerInfo != null && mListenerInfo.mOnMediaCompleteListener != null) {
//                mListenerInfo.mOnMediaCompleteListener.onMediaComplete(mCurrentUrl);
//            }
        }
        MediaPlayerHelper.getInstance().removeWorker(this);
        mListenerInfo = null;
        cancelTimer();
        mCurrentUrl = null;
    }

    public int getDuration() {
        return mPlayer == null ? 0 : mPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mPlayer == null ? 0 : mPlayer.getCurrentPosition();
    }

    public PlayWorker setProgressListener(int intervalMs, OnMediaProgressListener listener) {
        mIntervalMs = intervalMs;
        if (mListenerInfo != null)
            mListenerInfo.mOnMediaTimerListener = listener;
        return this;
    }

    public PlayWorker setOnMediaStartListener(OnMediaStartListener listener) {
        if (mListenerInfo != null)
            mListenerInfo.mOnMediaStartListener = listener;
        return this;
    }

    public PlayWorker setOnMediaPauseListener(OnMediaPauseListener listener) {
        if (mListenerInfo != null)
            mListenerInfo.mOnMediaPauseListener = listener;
        return this;
    }

    public PlayWorker setOnMediaCompleteListener(OnMediaCompleteListener listener) {
        if (mListenerInfo != null)
            mListenerInfo.mOnMediaCompleteListener = listener;
        return this;
    }

    public PlayWorker setOnMediaErrorListener(OnMediaErrorListener listener) {
        if (mListenerInfo != null)
            mListenerInfo.mOnMediaErrorListener = listener;
        return this;
    }

    private void cancelTimer() {
        if (mediaProgressTimer != null) {
//            mediaProgressTimer.onFinish();
            mediaProgressTimer.cancel();
        }
        mediaProgressTimer = null;
    }

    private void preparedToStart(MediaPlayer mp) {
        mp.start();
        if (mListenerInfo != null && mListenerInfo.mOnMediaStartListener != null) {
            mListenerInfo.mOnMediaStartListener.onMediaStart(mCurrentUrl);
        }
        if (mListenerInfo != null && mListenerInfo.mOnMediaTimerListener != null) {
            int currentTime = mp.getCurrentPosition();
            int duration = mp.getDuration();
            mediaProgressTimer = new MediaProgressTimer(duration, mIntervalMs, currentTime, mListenerInfo.mOnMediaTimerListener);
            mediaProgressTimer.start();
        }
    }

    private void playFromNetwork(final String source, final File localFile) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                int count;
                try {
                    File parentFile = localFile.getParentFile();
                    if (!parentFile.exists())
                        parentFile.mkdirs();
                    if (localFile.isDirectory())
                        localFile.delete();
                    if (!localFile.exists()) {
                        localFile.createNewFile();
                    }
                    URL url = new URL(source);
                    URLConnection conn = url.openConnection();
                    conn.connect();

                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(localFile);

                    byte data[] = new byte[1024];

                    while ((count = input.read(data)) != -1) {
                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e( " error " + e.getMessage());
                }
                Message msg = mHandler.obtainMessage();
                msg.what = MSG_WHAT_PLAY;
                msg.obj = localFile;
                mHandler.sendMessage(msg);

            }
        }).start();
    }

    private void playFromLocal(File localFile, Context context) {
        if (mPlayer == null) {
            return;
        }
        try {
            Uri uri = Uri.fromFile(localFile);
            mPlayer.reset();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(context.getApplicationContext(), uri);
            mPlayer.prepareAsync();
            mPlayer.setOnPreparedListener(mOnPreparedListener);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playFromLocal(String localFilePath) {
        if (mPlayer == null) {
            return;
        }
        try {
            mPlayer.reset();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(localFilePath);
            mPlayer.prepareAsync();
            mPlayer.setOnPreparedListener(mOnPreparedListener);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playFromRaw(String localFilePath) {
        if (mPlayer == null) {
            return;
        }
        try {
            mPlayer.reset();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(AppContext.get(), Uri.parse(localFilePath));
            mPlayer.prepareAsync();
            mPlayer.setOnPreparedListener(mOnPreparedListener);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放进度监听
     */
    public interface OnMediaProgressListener {
        void onMediaProgress(long currentPosition, long total);
    }

    /**
     * 开始播放监听
     */
    public interface OnMediaStartListener {
        void onMediaStart(String url);
    }

    /**
     * 暂停监听
     */
    public interface OnMediaPauseListener {
        void onMediaPause(String url);
    }

    /**
     * 播放完成监听
     */
    public interface OnMediaCompleteListener {
        void onMediaComplete(String url);
    }

    /**
     * 播放异常监听
     */
    public interface OnMediaErrorListener {
        void onMediaError(String url);
    }

    /**
     * 自定义倒计时器，用于触发播放器时间进度
     */
    private static class MediaProgressTimer extends CountDownTimer {

        private long mTotalMs;
        private OnMediaProgressListener mOnMediaProgressListener;


        /**
         * @param duration         当前文件总时长
         * @param progressInterval 通知时间间隔
         * @param currentPosition  当前播放进度位置
         * @param listener         进度监听
         */
        MediaProgressTimer(long duration, long progressInterval, int currentPosition, OnMediaProgressListener listener) {
            super(duration - currentPosition, progressInterval);
            mTotalMs = duration;
            mOnMediaProgressListener = listener;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (mOnMediaProgressListener != null)
                mOnMediaProgressListener.onMediaProgress(mTotalMs - millisUntilFinished, mTotalMs);
        }

        @Override
        public void onFinish() {
            if (mOnMediaProgressListener != null)
                mOnMediaProgressListener.onMediaProgress(mTotalMs, mTotalMs);
        }
    }

    private class ListenerInfo {
        private OnMediaStartListener mOnMediaStartListener;
        private OnMediaPauseListener mOnMediaPauseListener;
        private OnMediaCompleteListener mOnMediaCompleteListener;
        private OnMediaErrorListener mOnMediaErrorListener;
        private OnMediaProgressListener mOnMediaTimerListener;
    }
}
