package com.wujia.intellect.terminal.family;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.wujia.intellect.terminal.family.mvp.adapter.EquipmentAdapter;
import com.wujia.intellect.terminal.family.mvp.data.EquipmentBean;
import com.wujia.intellect.terminal.family.mvp.view.EquipmentDecoration;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：服务市场 home
 */
public class AllFragment extends BaseFragment {

    private RecyclerView rvUsually,rvAll;
    private EquipmentAdapter useAdapter,allAdapter;
    private List<EquipmentBean> useList;


    public AllFragment() {
    }

    public static AllFragment newInstance() {
        AllFragment fragment = new AllFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_family_all;
    }

    @Override
    protected void initEventAndData() {
        rvUsually = $(R.id.rv_usually);
        rvAll = $(R.id.rv_all);

        useList = new ArrayList<>();
        useList.add(new EquipmentBean());
        useList.add(new EquipmentBean());
        useList.add(new EquipmentBean());
        useList.add(new EquipmentBean());

        useAdapter = new EquipmentAdapter(mActivity, useList);
        allAdapter = new EquipmentAdapter(mActivity, useList);

        rvUsually.addItemDecoration(new EquipmentDecoration(25));
        rvAll.addItemDecoration(new EquipmentDecoration(25));

        rvUsually.setAdapter(useAdapter);
        rvAll.setAdapter(allAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！

    }
}
