package com.jingxi.smartlife.pad.market.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jingxi.smartlife.pad.market.mvp.adapter.FindServiceChildAdapter;
import com.wujia.businesslib.base.DataManager;
import com.jingxi.smartlife.pad.market.R;
import com.jingxi.smartlife.pad.market.mvp.contract.MarketContract;
import com.jingxi.smartlife.pad.market.mvp.contract.MarketPresenter;
import com.jingxi.smartlife.pad.market.mvp.data.ServiceBean;
import com.wujia.businesslib.base.WebViewFragment;
import com.wujia.lib.widget.HorizontalTabBar;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.base.baseadapter.wrapper.LoadMoreWrapper;
import com.wujia.lib_common.base.view.ServiceCardDecoration;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.ScreenUtil;

import java.util.ArrayList;

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
    private ImageView ivBanner;


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
        return R.layout.fragment_service_find;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        mSwipeRefreshLayout = $(R.id.swipe_container);
        recyclerView = $(R.id.rv1);
        ivBanner = $(R.id.img1);
        recyclerView.addItemDecoration(new ServiceCardDecoration(ScreenUtil.dip2px(24)));

        datas = new ArrayList<>();

        FindServiceChildAdapter mAdapter = new FindServiceChildAdapter(mActivity, datas, FindServiceChildAdapter.TYPE_FIND);
        mLoadMoreWrapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(mLoadMoreWrapper);
        mLoadMoreWrapper.setOnLoadMoreListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mAdapter.setOnItemClickListener(this);
        getList();
        mPresenter.getBanner(DataManager.getCommunityId());


        ivBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app_url = "http://www.sz.gov.cn/cn/";
                parentStart(WebViewFragment.newInstance(app_url));
            }
        });
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
        switch (requestCode) {
            case MarketPresenter.REQUEST_CDOE_GET_BANNER:

                break;

            case MarketPresenter.REQUEST_CDOE_GET_SERVICE_FIND:
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
                break;
        }

    }

    @Override
    public void onDataLoadFailed(int requestCode, ApiException apiException) {
        switch (requestCode) {
            case MarketPresenter.REQUEST_CDOE_GET_BANNER:
                ivBanner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String app_url = "http://www.sz.gov.cn/cn/";
                        parentStart(WebViewFragment.newInstance(app_url));
                    }
                });
                break;

            case MarketPresenter.REQUEST_CDOE_GET_SERVICE_FIND:
                isLoading = false;
                if (mSwipeRefreshLayout.isRefreshing())
                    mSwipeRefreshLayout.setRefreshing(false);
                break;
        }
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
        mPresenter.getBanner(DataManager.getCommunityId());
    }

    @Override
    public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        toTarget(datas.get(position));
    }
}
