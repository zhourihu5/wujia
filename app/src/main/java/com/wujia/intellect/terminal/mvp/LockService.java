package com.wujia.intellect.terminal.mvp;

import android.service.dreams.DreamService;

import com.wujia.intellect.terminal.R;

/**
 * author ：shenbingkai@163.com
 * date ：2019-04-09
 * description ：
 */
public class LockService extends DreamService {

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        setInteractive(false);
        setFullscreen(true);
        setContentView(R.layout.activity_lock);
        init();

    }

    private void init() {

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }
}