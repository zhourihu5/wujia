package com.jingxi.smartlife.pad.market.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.jingxi.smartlife.pad.market.R;
import com.jingxi.smartlife.pad.market.mvp.adapter.FindServiceChildAdapter;
import com.jingxi.smartlife.pad.market.mvp.data.ServiceDto;
import com.jingxi.smartlife.pad.market.mvp.model.MarketModel;
import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.data.CardDetailBean;
import com.wujia.businesslib.event.EventBusUtil;
import com.wujia.businesslib.event.EventSubscription;
import com.wujia.businesslib.event.IMiessageInvoke;
import com.wujia.lib.widget.HorizontalTabBar;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.base.baseadapter.wrapper.LoadMoreWrapper;
import com.wujia.lib_common.base.view.ServiceCardDecoration;
import com.wujia.lib_common.data.network.SimpleRequestSubscriber;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.LogUtil;
import com.wujia.lib_common.utils.ScreenUtil;

import java.util.ArrayList;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class AllServiceFragment extends ServiceBaseFragment implements HorizontalTabBar.OnTabSelectedListener, LoadMoreWrapper.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, MultiItemTypeAdapter.OnRVItemClickListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isLoading;
    private int pageSize = 12;
    private int pageNo = 1;
//    private int totleSize = 0;
    private ArrayList<CardDetailBean.ServicesBean> datas;
    private LoadMoreWrapper mLoadMoreWrapper;


    public static final String TYPE_ALL = "4";
    public static final String TYPE_MY = "1";
    public static final String TYPE_GOV = "3";

    private static final String KEY_TYPE = "type";

    String type = TYPE_MY;

    public void setType(String type) {
        this.type = type;
        if(mLoadMoreWrapper!=null){
            datas.clear();
            mLoadMoreWrapper.notifyDataSetChanged();
            mLoadMoreWrapper.setLoadMoreView(0);
            pageNo = 1;
            getList(true);
        }
    }

    MarketModel marketModel;


    private EventSubscription event = new EventSubscription(new IMiessageInvoke<EventSubscription>() {
        @Override
        public void eventBus(EventSubscription event) {
            if (event.getType() == EventSubscription.TYPE_NOTIFY) {
                pageNo = 1;
                getList(false);
            } else if (isVisible && event.eventType != EventSubscription.PUSH_NOTIFY) {
                mLoadMoreWrapper.notifyDataSetChanged();
            } else {
                switch (event.getType()) {
                    case EventSubscription.TYPE_GOV:
                        break;
                    case EventSubscription.TYPE_FIND:
                        if (type.equals(TYPE_GOV)) {
                            return;
                        }
                        break;

                }
                pageNo = 1;
                getList(true);
            }
        }
    });

    public AllServiceFragment() {

    }

    public static AllServiceFragment newInstance(String type) {
        AllServiceFragment fragment = new AllServiceFragment();
//        Bundle args = new Bundle();
//        args.putString(KEY_TYPE, type);
//        fragment.setArguments(args);
        fragment.type=type;
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service_all;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TYPE,type);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if(savedInstanceState!=null){
            type =savedInstanceState.getString(KEY_TYPE);
            LogUtil.i("Allservice from savedInstanceState type="+type);
        }

        mSwipeRefreshLayout = $(R.id.swipe_container);
        recyclerView = $(R.id.rv1);
        recyclerView.addItemDecoration(new ServiceCardDecoration(ScreenUtil.dip2px(84)));

        datas = new ArrayList<>();

        FindServiceChildAdapter mAdapter = getAdapter(datas);
        mLoadMoreWrapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(mLoadMoreWrapper);
        mLoadMoreWrapper.setOnLoadMoreListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        marketModel = new MarketModel();
        getList(true);
//        if(type.equals(TYPE_MY)){
        EventBusUtil.register(event);
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if(type.equals(TYPE_MY)){
        EventBusUtil.unregister(event);
//        }
    }

    boolean isVisible;

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        isVisible = true;
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        isVisible = false;
    }

    private void getList(boolean isShowLoadingDialog) {
        isLoading = true;
        addSubscribe(marketModel.getServiceList(type, pageNo, pageSize).subscribeWith(new SimpleRequestSubscriber<ApiResponse<ServiceDto>>(this,
                new SimpleRequestSubscriber.ActionConfig(isShowLoadingDialog, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(ApiResponse<ServiceDto> response) {
                super.onResponse(response);
                if (mSwipeRefreshLayout.isRefreshing())
                    mSwipeRefreshLayout.setRefreshing(false);
                isLoading = false;

                if (pageNo == 1)
                    datas.clear();

                datas.addAll(response.data.getPage().getContent());

                if (response.data.getPage().isLast()) {
                    mLoadMoreWrapper.setLoadMoreView(0);
                } else {
                    mLoadMoreWrapper.setLoadMoreView(R.layout.view_loadmore);
                }

                mLoadMoreWrapper.notifyDataSetChanged();
                pageNo++;
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                isLoading = false;
                if (mSwipeRefreshLayout.isRefreshing())
                    mSwipeRefreshLayout.setRefreshing(false);
            }
        }));
    }

    @Override
    public void onTabSelected(int position, int prePosition) {

    }


    @Override
    public void onLoadMoreRequested() {
        if (mSwipeRefreshLayout.isRefreshing() || isLoading)
            return;
        getList(false);
    }

    @Override
    public void onRefresh() {
        if (isLoading)
            return;
        mLoadMoreWrapper.setLoadMoreView(0);
        pageNo = 1;
        getList(false);
    }

    @Override
    public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        toTarget(datas.get(position));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
