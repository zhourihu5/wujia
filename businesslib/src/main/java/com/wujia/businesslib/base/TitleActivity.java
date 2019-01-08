package com.wujia.businesslib.base;


import com.wujia.lib.viewdelegate.TitleStrategy;
import com.wujia.lib.viewdelegate.title.TitleView;
import com.wujia.lib_common.base.BaseActivity;
import com.wujia.lib_common.base.plug.IPlugStrategy;
import com.wujia.lib_common.base.plug.ITitlePlug;

/**
 * Created by KisenHuang on 2018/5/31.
 * 基类
 */

public abstract class   TitleActivity extends BaseActivity implements ITitlePlug {

    protected TitleView mTitleView;

    @Override
    public IPlugStrategy getTitlePlug() {
        TitleStrategy strategy = new TitleStrategy();
        mTitleView = strategy.getTitleView();
        return strategy;
    }
}
