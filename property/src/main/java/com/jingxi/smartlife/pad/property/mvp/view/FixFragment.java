package com.jingxi.smartlife.pad.property.mvp.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.jingxi.smartlife.pad.property.R;
import com.jingxi.smartlife.pad.property.mvp.adapter.FixGroupAdapter;
import com.jingxi.smartlife.pad.property.mvp.data.WyFixBean;
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
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        rv1 = $(R.id.rv1);

        List<WyFixBean> datas = new ArrayList<>();
        datas.add(new WyFixBean());
        datas.add(new WyFixBean());

        rv1.setAdapter(new FixGroupAdapter(mActivity, datas));

    }
}
