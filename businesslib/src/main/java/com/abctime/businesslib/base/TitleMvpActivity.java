package com.abctime.businesslib.base;


import com.abctime.lib.viewdelegate.TitleStrategy;
import com.abctime.lib.viewdelegate.title.TitleView;
import com.abctime.lib_common.base.BasePresenter;
import com.abctime.lib_common.base.plug.IPlugStrategy;
import com.abctime.lib_common.base.plug.ITitlePlug;

/**
 * Created by KisenHuang on 2018/5/29.
 * 提供统一title布局，添加自定义Menu
 */

public abstract class TitleMvpActivity<T extends BasePresenter> extends MvpActivity<T> implements ITitlePlug{

    protected TitleView mTitleView;

    @Override
    public IPlugStrategy getTitlePlug() {
        TitleStrategy strategy = new TitleStrategy();
        mTitleView = strategy.getTitleView();
        return strategy;
    }

}
