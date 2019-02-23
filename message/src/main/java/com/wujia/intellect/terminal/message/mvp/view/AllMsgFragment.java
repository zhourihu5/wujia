package com.wujia.intellect.terminal.message.mvp.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioGroup;

import com.wujia.intellect.terminal.message.R;
import com.wujia.intellect.terminal.message.mvp.adapter.MessageAdapter;
import com.wujia.intellect.terminal.message.mvp.data.MsgBean;
import com.wujia.lib.widget.HorizontalTabBar;
import com.wujia.lib.widget.HorizontalTabItem;
import com.wujia.lib_common.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class AllMsgFragment extends BaseFragment implements HorizontalTabBar.OnTabSelectedListener {

    HorizontalTabBar tabBar;
    RecyclerView recyclerView;

    public AllMsgFragment() {

    }

    public static AllMsgFragment newInstance() {
        AllMsgFragment fragment = new AllMsgFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_msg_all;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        tabBar = $(R.id.tab_layout);
        recyclerView = $(R.id.rv1);

        tabBar.addItem(new HorizontalTabItem(mContext, R.string.all));
        tabBar.addItem(new HorizontalTabItem(mContext, R.string.readed));
        tabBar.addItem(new HorizontalTabItem(mContext, R.string.unread));

        tabBar.setOnTabSelectedListener(this);

        List<MsgBean> datas = new ArrayList<>();
        datas.add(new MsgBean());
        datas.add(new MsgBean());
        datas.add(new MsgBean());
        datas.add(new MsgBean());
        datas.add(new MsgBean());
        recyclerView.setAdapter(new MessageAdapter(mActivity, datas));
    }

    @Override
    public void onTabSelected(int position, int prePosition) {

    }
}
