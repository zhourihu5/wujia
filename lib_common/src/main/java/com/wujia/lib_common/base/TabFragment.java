package com.wujia.lib_common.base;

/**
 * 懒加载
 * Created by YoKeyword on 16/6/5.
 */
public abstract class TabFragment extends BaseFragment {

    protected int currentTab = 0;


    public abstract void switchTab(int pos);

}
