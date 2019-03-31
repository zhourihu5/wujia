package com.wujia.intellect.terminal.property.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wujia.intellect.terminal.property.R;
import com.wujia.intellect.terminal.property.mvp.adapter.FixGroupAdapter;
import com.wujia.intellect.terminal.property.mvp.data.WyFixBean;
import com.wujia.lib_common.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class SimpleFixFragment extends BaseFragment implements View.OnClickListener {


    public SimpleFixFragment() {

    }

    public static SimpleFixFragment newInstance() {
        SimpleFixFragment fragment = new SimpleFixFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_propery_fix_simple;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        $(R.id.fix_electricity_btn).setOnClickListener(this);
        $(R.id.fix_water_btn).setOnClickListener(this);
        $(R.id.fix_public_btn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fix_electricity_btn) {

        } else if (v.getId() == R.id.fix_water_btn) {

        } else if (v.getId() == R.id.fix_public_btn) {

        }
    }
}
