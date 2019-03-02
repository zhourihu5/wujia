package com.wujia.intellect.terminal.property.mvp.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.wujia.intellect.terminal.message.mvp.adapter.WySectionAdapter;
import com.wujia.intellect.terminal.property.R;
import com.wujia.intellect.terminal.property.mvp.adapter.WyOtherTelAdapter;
import com.wujia.intellect.terminal.property.mvp.data.WyChildBean;
import com.wujia.intellect.terminal.property.mvp.data.WySectionBean;
import com.wujia.lib_common.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class TelFragment extends BaseFragment {

    RecyclerView rv1, rv2;

    public TelFragment() {

    }

    public static TelFragment newInstance() {
        TelFragment fragment = new TelFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_propery_tel;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        rv1 = $(R.id.rv1);
        rv2 = $(R.id.rv2);

//        layoutManager.setSmoothScrollbarEnabled(true);
//        layoutManager.setAutoMeasureEnabled(true);
        rv1.setHasFixedSize(true);
        rv1.setNestedScrollingEnabled(false);

        rv2.setHasFixedSize(true);
        rv2.setNestedScrollingEnabled(false);

        List<WySectionBean> datas = new ArrayList<>();
        datas.add(new WySectionBean());
        datas.add(new WySectionBean());
        datas.add(new WySectionBean());
        datas.add(new WySectionBean());

        rv1.setAdapter(new WySectionAdapter(mActivity, datas));


        List<WyChildBean> others = new ArrayList<>();
        others.add(new WyChildBean());
        others.add(new WyChildBean());
        others.add(new WyChildBean());
        others.add(new WyChildBean());
        others.add(new WyChildBean());
        rv2.setAdapter(new WyOtherTelAdapter(mActivity, others));
    }
}
