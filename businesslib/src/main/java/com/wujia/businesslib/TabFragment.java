package com.wujia.businesslib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.wujia.lib.widget.VerticalTabBar;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.base.BaseMainFragment;

/**
 * 懒加载
 * Created by YoKeyword on 16/6/5.
 */
public abstract class TabFragment extends BaseFragment {

    protected static final String KEY_TAB_POSITION = "tabPosition";
    protected int currentTab = 0;
    protected VerticalTabBar mTabBar;
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_TAB_POSITION,currentTab);
    }
    protected void getCurrentTab(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            currentTab = savedInstanceState.getInt(KEY_TAB_POSITION,currentTab);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCurrentTab(savedInstanceState);
    }

    protected void parentSwitchTab(){
        Fragment fragment= getParentFragment();
        if(fragment instanceof BaseMainFragment){
            ((BaseMainFragment)fragment).switchTab(currentTab);
        }
    }


    public  void switchTab(int pos){
        currentTab=pos;
        parentSwitchTab();
        if(mTabBar!=null){
            mTabBar.getChildAt(pos).performClick();
        }
    }


}
