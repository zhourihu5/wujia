package com.wujia.intellect.terminal.message.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.wujia.businesslib.DataBaseUtil;
import com.wujia.businesslib.data.DBMessage;
import com.wujia.businesslib.event.EventMsg;
import com.wujia.businesslib.event.IMiessageInvoke;
import com.wujia.intellect.terminal.message.R;
import com.wujia.intellect.terminal.message.mvp.adapter.MessageAdapter;
import com.wujia.lib.widget.HorizontalTabBar;
import com.wujia.lib.widget.HorizontalTabItem;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.utils.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class AllMsgFragment extends BaseFragment implements HorizontalTabBar.OnTabSelectedListener {

    HorizontalTabBar tabBar;
    RecyclerView recyclerView;
    private ArrayList<DBMessage> msgList;
    private MessageAdapter mAdapter;
    private int currentState = 0;

    private EventMsg eventMsg = new EventMsg(new IMiessageInvoke<EventMsg>() {
        @Override
        public void eventBus(EventMsg event) {

        }
    });

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
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        tabBar = $(R.id.tab_layout);
        recyclerView = $(R.id.rv1);

        tabBar.addItem(new HorizontalTabItem(mContext, R.string.all));
        tabBar.addItem(new HorizontalTabItem(mContext, R.string.readed));
        tabBar.addItem(new HorizontalTabItem(mContext, R.string.unread));

        tabBar.setOnTabSelectedListener(this);

        mAdapter = new MessageAdapter(mActivity, msgList);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onTabSelected(int position, int prePosition) {
        currentState = position;

        getData();

    }

    private void getData() {
        ArrayList<DBMessage> temp = null;
        switch (currentState) {
            case 0://全部
                temp = DataBaseUtil.query(DBMessage.class);
                break;
            case 1://已读
                temp = DataBaseUtil.queryNotEquals(getMap(), DBMessage.class);
                break;
            case 2://未读
                temp = DataBaseUtil.queryEquals(getMap(), DBMessage.class);
                break;
        }

        msgList.clear();
        msgList.addAll(temp);
        if (null != mAdapter) {
            mAdapter.notifyDataSetChanged();
        }
    }

    private Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("_read_state", 0);
        return map;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();

        if (null == msgList) {
            msgList = new ArrayList<>();
        }
        getData();
    }
}
