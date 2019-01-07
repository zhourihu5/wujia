package com.abctime.lib_common.utils.mediaplayer;

/**
 * author:Created by xmren on 2018/7/2.
 * email :renxiaomin@100tal.com
 */

public abstract class BasePlayEngine {
    public abstract BasePlayEngine createEngine();

    public void play() {
        createEngine().play();
    }

    public void pause() {
        createEngine().pause();
    }

    public void stop() {
        createEngine().stop();
    }
}
