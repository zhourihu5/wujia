package com.wujia.intellect.terminal.market.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.wujia.businesslib.base.DataManager;
import com.wujia.businesslib.base.MvpFragment;
import com.wujia.businesslib.base.WebViewFragment;
import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.adapter.FindServiceChildAdapter;
import com.wujia.intellect.terminal.market.mvp.contract.MarketContract;
import com.wujia.intellect.terminal.market.mvp.contract.MarketPresenter;
import com.wujia.intellect.terminal.market.mvp.data.ServiceBean;
import com.wujia.lib.widget.HorizontalTabBar;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.base.BaseMainFragment;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.base.baseadapter.wrapper.LoadMoreWrapper;
import com.wujia.lib_common.base.view.ServiceCardDecoration;
import com.wujia.lib_common.base.view.VerticallDecoration;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.ScreenUtil;

import java.util.ArrayList;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class GovServiceFragment extends ServiceBaseFragment<MarketPresenter> {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    public GovServiceFragment() {

    }

    public static GovServiceFragment newInstance() {
        GovServiceFragment fragment = new GovServiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service_all;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        mSwipeRefreshLayout = $(R.id.swipe_container);
        recyclerView = $(R.id.rv1);

        recyclerView.addItemDecoration(new ServiceCardDecoration(ScreenUtil.dip2px(84)));
        mSwipeRefreshLayout.setEnabled(false);
        getList();

    }

    private void getList() {

        final ArrayList<ServiceBean.Service> datas = new ArrayList<>();
        String[] titleList = mActivity.getResources().getStringArray(R.array.gov_service_data_title);
        String[] urlList = mActivity.getResources().getStringArray(R.array.gov_service_data_url);

        for (int i = 0; i < titleList.length; i++) {
            ServiceBean.Service service = new ServiceBean.Service();
            service.explain = titleList[i];
            service.app_url = urlList[i];
            service.app_type = ServiceBean.TYPE_WEB;
            service.service_id = String.valueOf(-i);
            datas.add(service);
        }

        FindServiceChildAdapter mAdapter = new FindServiceChildAdapter(mActivity, datas);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnRVItemClickListener() {
            @Override
            public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                toTarget(datas.get(position));
            }
        });
    }

    @Override
    protected MarketPresenter createPresenter() {
        return null;
    }
}
