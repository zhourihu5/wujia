package com.wujia.intellect.terminal.market.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.wujia.businesslib.Constants;
import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.adapter.FindServiceGroupAdapter;
import com.wujia.intellect.terminal.market.mvp.data.MarketBean;
import com.wujia.lib.widget.HorizontalTabBar;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class FindServiceFragment extends BaseFragment implements HorizontalTabBar.OnTabSelectedListener {

    RecyclerView recyclerView;
    FindServiceGroupAdapter mAdapter;

    public FindServiceFragment() {

    }

    public static FindServiceFragment newInstance(String id) {
        FindServiceFragment fragment = new FindServiceFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM_1, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service_find;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        String id = getArguments().getString(Constants.ARG_PARAM_1);
        if ("2".equals(id)) {
            $(R.id.l1).setVisibility(View.GONE);
        }
        recyclerView = $(R.id.rv1);

        List<MarketBean> datas = new ArrayList<>();
        datas.add(new MarketBean());
        datas.add(new MarketBean());
        datas.add(new MarketBean());
        datas.add(new MarketBean());
        datas.add(new MarketBean());

        mAdapter = new FindServiceGroupAdapter(mActivity, datas);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onTabSelected(int position, int prePosition) {

    }
}
