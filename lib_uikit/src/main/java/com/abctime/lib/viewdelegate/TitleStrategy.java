package com.abctime.lib.viewdelegate;


import android.view.View;

import com.abctime.lib.viewdelegate.title.TitleDelegate;
import com.abctime.lib.viewdelegate.title.TitleView;
import com.abctime.lib.viewdelegate.title.impl.DefaultTitleView;
import com.abctime.lib_common.base.plug.IPlugStrategy;

/**
 * Created by KisenHuang on 2018/5/30.
 * 标题策略
 */

public class TitleStrategy implements IPlugStrategy {

    private TitleView titleView;

    public TitleStrategy(){
        titleView = new DefaultTitleView();
    }

    @Override
    public View getContentWithTitle(View content, View.OnClickListener onClickListener) {
        TitleDelegate titleDelegate = new TitleDelegate(titleView, content);
        titleDelegate.setBackListener(onClickListener);
        return titleDelegate.getView();
    }

    public TitleView getTitleView() {
        return titleView;
    }
}

