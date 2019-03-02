package com.wujia.intellect.terminal.property.mvp.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.wujia.intellect.terminal.message.mvp.adapter.WySectionAdapter;
import com.wujia.intellect.terminal.property.R;
import com.wujia.intellect.terminal.property.mvp.adapter.FixGroupAdapter;
import com.wujia.intellect.terminal.property.mvp.adapter.WyOtherTelAdapter;
import com.wujia.intellect.terminal.property.mvp.data.WyChildBean;
import com.wujia.intellect.terminal.property.mvp.data.WyFixBean;
import com.wujia.intellect.terminal.property.mvp.data.WySectionBean;
import com.wujia.lib_common.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class FixFragment extends BaseFragment {

    RecyclerView rv1;

    public FixFragment() {

    }

    public static FixFragment newInstance() {
        FixFragment fragment = new FixFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_propery_fix;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        rv1 = $(R.id.rv1);

        List<WyFixBean> datas = new ArrayList<>();
        datas.add(new WyFixBean());
        datas.add(new WyFixBean());

        rv1.setAdapter(new FixGroupAdapter(mActivity, datas));

    }
}
