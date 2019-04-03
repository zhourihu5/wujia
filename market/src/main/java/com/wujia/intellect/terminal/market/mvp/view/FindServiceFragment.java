package com.wujia.intellect.terminal.market.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wujia.businesslib.Constants;
import com.wujia.businesslib.base.DataManager;
import com.wujia.businesslib.base.MvpFragment;
import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.adapter.FindServiceChildAdapter;
import com.wujia.intellect.terminal.market.mvp.adapter.FindServiceGroupAdapter;
import com.wujia.intellect.terminal.market.mvp.contract.MarketContract;
import com.wujia.intellect.terminal.market.mvp.contract.MarketPresenter;
import com.wujia.intellect.terminal.market.mvp.data.ServiceBean;
import com.wujia.lib.widget.HorizontalTabBar;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.base.baseadapter.wrapper.LoadMoreWrapper;
import com.wujia.lib_common.base.view.ServiceCardDecoration;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class FindServiceFragment extends ServiceBaseFragment<MarketPresenter> implements HorizontalTabBar.OnTabSelectedListener, MarketContract.View, LoadMoreWrapper.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, MultiItemTypeAdapter.OnRVItemClickListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isLoading;
    private int pageSize = 12;
    private int pageNo = 1;
    private int totleSize = 0;
    private ArrayList<ServiceBean.Service> datas;
    private LoadMoreWrapper mLoadMoreWrapper;


    public FindServiceFragment() {

    }

    public static FindServiceFragment newInstance() {
        FindServiceFragment fragment = new FindServiceFragment();
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

        datas = new ArrayList<>();

        FindServiceChildAdapter mAdapter = new FindServiceChildAdapter(mActivity, datas);
        mLoadMoreWrapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(mLoadMoreWrapper);
        mLoadMoreWrapper.setOnLoadMoreListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        getList();
    }

    private void getList() {
        isLoading = true;
        mPresenter.getServiceClassification(DataManager.getCommunityId(), "discover", pageNo, pageSize);
    }

    @Override
    public void onTabSelected(int position, int prePosition) {

    }

    @Override
    protected MarketPresenter createPresenter() {
        return new MarketPresenter();
    }

    @Override
    public void onDataLoadSucc(int requestCode, Object object) {
        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
        isLoading = false;
        ServiceBean bean = (ServiceBean) object;
        totleSize = bean.totalSize;

        if (pageNo == 1)
            datas.clear();

        datas.addAll(bean.content);

        if (datas.size() < totleSize) {
            mLoadMoreWrapper.setLoadMoreView(R.layout.view_loadmore);
        } else {
            mLoadMoreWrapper.setLoadMoreView(0);
        }

        mLoadMoreWrapper.notifyDataSetChanged();
        pageNo++;
    }

    @Override
    public void onDataLoadFailed(int requestCode, ApiException apiException) {
        isLoading = false;
        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onLoadMoreRequested() {
        if (mSwipeRefreshLayout.isRefreshing() || isLoading)
            return;
        if (datas.size() < totleSize) {
            getList();
        }
    }

    @Override
    public void onRefresh() {
        if (isLoading)
            return;
        mLoadMoreWrapper.setLoadMoreView(0);
        pageNo = 1;
        getList();
    }

    @Override
    public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        toTarget(datas.get(position));
    }
}
