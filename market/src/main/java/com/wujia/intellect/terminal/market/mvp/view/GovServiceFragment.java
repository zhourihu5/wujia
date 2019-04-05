package com.wujia.intellect.terminal.market.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.wujia.businesslib.data.DBService;
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
public class GovServiceFragment extends ServiceBaseFragment {

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
        String[] nameList = mActivity.getResources().getStringArray(R.array.gov_service_data_name);
        String[] explainList = mActivity.getResources().getStringArray(R.array.gov_service_data_explain);
        String[] urlList = mActivity.getResources().getStringArray(R.array.gov_service_data_url);
        String[] imgList = mActivity.getResources().getStringArray(R.array.gov_service_data_image);

//        int id = -100;
//        ServiceBean.Service s1 = new ServiceBean.Service();
//        s1.explain = "人民政府办公厅主办网站";
//        s1.name = "广东政务服务网";
//        s1.app_url = "http://www.gdzwfw.gov.cn/portal/index?region=440300";
//        s1.app_type = ServiceBean.TYPE_WEB;
//        s1.image = "file:///android_asset/icon_guangdong";
//        s1.service_id = String.valueOf(id--);
//        datas.add(s1);

        for (int i = 0; i < nameList.length; i++) {
            ServiceBean.Service service = new ServiceBean.Service();
            service.name = nameList[i];
            service.explain = explainList[i];
            service.app_url = urlList[i];
            service.image = imgList[i];
            service.app_type = ServiceBean.TYPE_WEB;
            datas.add(service);
        }

        FindServiceChildAdapter mAdapter = new FindServiceChildAdapter(mActivity, datas, FindServiceChildAdapter.TYPE_GOV);
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
