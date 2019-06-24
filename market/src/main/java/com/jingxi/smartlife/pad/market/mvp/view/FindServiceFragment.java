package com.jingxi.smartlife.pad.market.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jingxi.smartlife.pad.market.R;
import com.jingxi.smartlife.pad.market.mvp.adapter.FindServiceChildAdapter;
import com.jingxi.smartlife.pad.market.mvp.data.ServiceDto;
import com.jingxi.smartlife.pad.market.mvp.model.MarketModel;
import com.wujia.businesslib.base.WebViewFragment;
import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.data.CardDetailBean;
import com.wujia.businesslib.event.EventBusUtil;
import com.wujia.businesslib.event.EventSubscription;
import com.wujia.businesslib.event.IMiessageInvoke;
import com.wujia.lib.imageloader.ImageLoaderManager;
import com.wujia.lib.widget.HorizontalTabBar;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.base.baseadapter.wrapper.LoadMoreWrapper;
import com.wujia.lib_common.base.view.ServiceCardDecoration;
import com.wujia.lib_common.data.network.SimpleRequestSubscriber;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class FindServiceFragment extends ServiceBaseFragment implements HorizontalTabBar.OnTabSelectedListener, LoadMoreWrapper.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, MultiItemTypeAdapter.OnRVItemClickListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isLoading;
    private int pageSize = 12;
    private int pageNo = 1;
//    private int totleSize = 0;
    private ArrayList<CardDetailBean.ServicesBean> datas;
    private LoadMoreWrapper mLoadMoreWrapper;
    private ImageView ivBanner;
    private TextView tvBanner;

    private EventSubscription event = new EventSubscription(new IMiessageInvoke<EventSubscription>() {
        @Override
        public void eventBus(EventSubscription event) {
            if(event.getType()==EventSubscription.TYPE_FIND
                    ||event.getType()==EventSubscription.TYPE_NOTIFY
            ){//如果未 发现服务或者推送过来的通知服务就需要刷新界面
                if(event.eventType==EventSubscription.PUSH_NOTIFY ||!isVisible()){
                    pageNo=1;
                    getList(false);
                }else {//非推送消息并且当前界面可见状态为本页面发送的消息
                    mLoadMoreWrapper.notifyDataSetChanged();
                }

            }
        }
    });

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

    MarketModel marketModel;
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        mSwipeRefreshLayout = $(R.id.swipe_container);
        recyclerView = $(R.id.rv1);
        ivBanner = $(R.id.img1);
        tvBanner = $(R.id.tv1);
        recyclerView.addItemDecoration(new ServiceCardDecoration(ScreenUtil.dip2px(24)));

        datas = new ArrayList<>();

        FindServiceChildAdapter mAdapter =getAdapter(datas);
        mLoadMoreWrapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(mLoadMoreWrapper);
        mLoadMoreWrapper.setOnLoadMoreListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mAdapter.setOnItemClickListener(this);
        marketModel=new MarketModel();
        getList(true);
        EventBusUtil.register(event);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(event);
    }

    private void getList(boolean isShowLoadingDialog) {
        isLoading = true;
        addSubscribe(marketModel.getServiceList("2", pageNo, pageSize).subscribeWith(new SimpleRequestSubscriber<ApiResponse<ServiceDto>>(this, new SimpleRequestSubscriber.ActionConfig(isShowLoadingDialog, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(ApiResponse<ServiceDto> response) {
                super.onResponse(response);
                isLoading = false;
                setBanner(response);

                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }

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

    protected void setBanner(ApiResponse<ServiceDto> response) {
        List<ServiceDto.BannerListBean> list= response.data.getBannerList();
        if (null == list || list.isEmpty()) {
            return;
        }
        final ServiceDto.BannerListBean banner = list.get(0);
        ImageLoaderManager.getInstance().loadImage(banner.getCover(), ivBanner);
//                tvBanner.setText(TextUtils.isEmpty(banner.title) ? "" : banner.title);
        ivBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentStart(WebViewFragment.newInstance(banner.getUrl()));
            }
        });
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
