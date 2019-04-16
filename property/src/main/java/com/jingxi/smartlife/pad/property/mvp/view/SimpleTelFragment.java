package com.jingxi.smartlife.pad.property.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.jingxi.smartlife.pad.property.R;
import com.jingxi.smartlife.pad.property.mvp.adapter.WyOtherTelAdapter;
import com.jingxi.smartlife.pad.property.mvp.adapter.WyTelGroupAdapter;
import com.jingxi.smartlife.pad.property.mvp.data.WyChildBean;
import com.jingxi.smartlife.pad.property.mvp.data.WySectionBean;
import com.wujia.lib_common.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class SimpleTelFragment extends BaseFragment {


    public SimpleTelFragment() {

    }

    public static SimpleTelFragment newInstance() {
        SimpleTelFragment fragment = new SimpleTelFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_propery_tel_simple;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }
}
