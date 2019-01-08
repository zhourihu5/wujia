package com.wujia.lib_common.utils.mediaplayer;

/**
 * author:Created by xmren on 2018/7/2.
 * email :renxiaomin@100tal.com
 */

public interface IPlayerListener {
    public void onPrepared();

    public void onSart();

    public void onPause();

    public void onStop();

    public void onComplete();

    public void onProgress(float current, float total);

}
