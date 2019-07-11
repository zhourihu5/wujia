package com.wujia.lib_common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by xmren on 2017/8/1.
 */

public abstract class BaseFragment extends SupportFragment {
    protected BaseActivity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;
    protected View mView;

    protected CompositeDisposable mCompositeDisposable;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        mUnBinder = ButterKnife.bind(this, mView);
        interruptInject();

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDataWithSaveInstance(savedInstanceState);
    }

    protected void interruptInject() {
    }

    public void parentStart(ISupportFragment fragment) {
        BaseFragment parentFragment = (BaseFragment) getParentFragment();
        parentFragment.start(fragment);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
        mView = null;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    protected void initDataWithSaveInstance(@Nullable Bundle saveInstanceState) {
        initEventAndData();
    }

    protected abstract int getLayoutId();

    protected void initEventAndData() {

    }

    protected <T extends View> T $(int resId) {
        return (T) mView.findViewById(resId);
    }


    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public boolean onBackPressedSupport() {
//        if (mActivity.getSupportFragmentManager().getBackStackEntryCount() > 1) {
//            pop();
//        }
//        return super.onBackPressedSupport();
//    }
}
