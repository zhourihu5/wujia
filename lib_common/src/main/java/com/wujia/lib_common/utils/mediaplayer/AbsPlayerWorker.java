package com.wujia.lib_common.utils.mediaplayer;

/**
 * author:Created by xmren on 2018/7/2.
 * email :renxiaomin@100tal.com
 */

public abstract class AbsPlayerWorker implements IPlayerWorker {

    @Override
    public void play() {
        createWorker().play();
    }

    @Override
    public void pause() {
        createWorker().pause();
    }

    @Override
    public void stop() {
        createWorker().stop();
    }

    @Override
    public void release() {
        createWorker().release();
    }

    public abstract PlayTask createWorker();
}

