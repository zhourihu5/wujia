package com.abctime.lib_common.utils.mediaplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmren on 2018/5/19.
 */

public class MediaPlayerHelper {

    private static final String TAG = MediaPlayerHelper.class.getSimpleName();
    private static volatile MediaPlayerHelper mPlayerHelper = null;
    private List<PlayWorker> workerList = new ArrayList<>();

    private MediaPlayerHelper() {
    }

    public static MediaPlayerHelper getInstance() {
        if (mPlayerHelper == null) {
            synchronized (MediaPlayerHelper.class) {
                if (mPlayerHelper == null) {
                    mPlayerHelper = new MediaPlayerHelper();
                }
            }
        }
        return mPlayerHelper;
    }

    public static void release() {
        if (mPlayerHelper != null)
            mPlayerHelper.destroyWorkers();
        mPlayerHelper = null;
    }

    private void destroyWorkers() {
        for (PlayWorker worker : workerList) {
            worker.release();
        }
    }

    public PlayWorker createAudioWorker() {
        PlayWorker worker = new PlayWorker(PlayWorker.TYPE_AUDIO);
//        workerList.add(worker);
        return worker;
    }

    public PlayWorker createVideoWorker() {
        return new PlayWorker(PlayWorker.TYPE_VIDEO);
    }

    void removeWorker(PlayWorker worker) {
        if (workerList.contains(worker)) {
            workerList.remove(worker);
        }
    }
}
