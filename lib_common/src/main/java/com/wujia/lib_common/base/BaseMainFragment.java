package com.wujia.lib_common.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;

/**
 * 懒加载
 * Created by YoKeyword on 16/6/5.
 */
public abstract class BaseMainFragment extends BaseFragment {

    private static final String KEY_TAB_POSITION = "tabPosition";
    protected int currentTab = 0;

    protected OnBackToFirstListener _mBackToFirstListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_TAB_POSITION,currentTab);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
           currentTab= savedInstanceState.getInt(KEY_TAB_POSITION,currentTab);
        }
    }

    /**
     * 处理回退事件
     *
     * @return
     */
//    @Override
//    public boolean onBackPressedSupport() {
//        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
//            popChild();
//        } else {
//            _mActivity.finish();
//        }
//        return true;
//    }

    public interface OnBackToFirstListener {
        void onBackToFirstFragment();
    }


    public void switchTab(int pos) {
        currentTab = pos;
    }
}
