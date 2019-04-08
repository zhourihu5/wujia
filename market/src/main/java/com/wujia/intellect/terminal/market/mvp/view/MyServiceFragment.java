package com.wujia.intellect.terminal.market.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.wujia.businesslib.DataBaseUtil;
import com.wujia.businesslib.event.EventBusUtil;
import com.wujia.businesslib.event.EventSubscription;
import com.wujia.businesslib.event.IMiessageInvoke;
import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.adapter.FindServiceChildAdapter;
import com.wujia.intellect.terminal.market.mvp.contract.MarketPresenter;
import com.wujia.intellect.terminal.market.mvp.data.ServiceBean;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.base.view.ServiceCardDecoration;
import com.wujia.lib_common.utils.ScreenUtil;

import java.util.ArrayList;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class MyServiceFragment extends ServiceBaseFragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<ServiceBean.Service> datas;
    private FindServiceChildAdapter mAdapter;

    private EventSubscription event = new EventSubscription(new IMiessageInvoke<EventSubscription>() {
        @Override
        public void eventBus(EventSubscription event) {
            getList();
        }
    });


    public MyServiceFragment() {

    }

    public static MyServiceFragment newInstance() {
        MyServiceFragment fragment = new MyServiceFragment();
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

        datas = new ArrayList<>();
        mAdapter = new FindServiceChildAdapter(mActivity, datas, FindServiceChildAdapter.TYPE_MY);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnRVItemClickListener() {
            @Override
            public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                toTarget(datas.get(position));
            }
        });

        getList();
        EventBusUtil.register(event);

    }

    private void getList() {

        ArrayList<ServiceBean.Service> temp = DataBaseUtil.query(ServiceBean.Service.class);
        datas.clear();
        if (null != temp && !temp.isEmpty()) {
            datas.addAll(temp);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(event);
    }

    @Override
    protected MarketPresenter createPresenter() {
        return null;
    }
}
