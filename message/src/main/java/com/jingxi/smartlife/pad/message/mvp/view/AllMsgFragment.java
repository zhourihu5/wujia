package com.jingxi.smartlife.pad.message.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.litesuits.orm.db.assit.QueryBuilder;
import com.wujia.businesslib.DataBaseUtil;
import com.wujia.businesslib.data.DBMessage;
import com.wujia.businesslib.event.EventBusUtil;
import com.wujia.businesslib.event.EventMsg;
import com.wujia.businesslib.event.IMiessageInvoke;
import com.jingxi.smartlife.pad.message.R;
import com.jingxi.smartlife.pad.message.mvp.adapter.MessageAdapter;
import com.wujia.lib.widget.HorizontalTabBar;
import com.wujia.lib.widget.HorizontalTabItem;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.base.baseadapter.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class AllMsgFragment extends BaseFragment implements HorizontalTabBar.OnTabSelectedListener, LoadMoreWrapper.OnLoadMoreListener {

    HorizontalTabBar tabBar;
    RecyclerView recyclerView;
    private ArrayList<DBMessage> msgList;
    private MessageAdapter mAdapter;
    private int currentState = 0;
    private LoadMoreWrapper mLoadMoreWrapper;
    private int page = 0, pageSize = 15;
    private ArrayList<DBMessage> allList;//所有数据
    private String type = "";//默认所有

    public void setType(String type) {
        this.type = type;
//        currentState = 0;
        reset();
        getData();
    }

    private EventMsg eventMsg = new EventMsg(new IMiessageInvoke<EventMsg>() {
        @Override
        public void eventBus(EventMsg event) {
            reset();
            getData();
        }
    });

    private void reset() {
        page = 0;
        msgList.clear();
        allList.clear();
    }

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

        msgList = new ArrayList<>();
        mAdapter = new MessageAdapter(mActivity, msgList);
        mLoadMoreWrapper = new LoadMoreWrapper(mAdapter);
        recyclerView.setAdapter(mLoadMoreWrapper);
        mLoadMoreWrapper.setOnLoadMoreListener(this);

        getData();

        EventBusUtil.register(eventMsg);
    }

    @Override
    public void onTabSelected(int position, int prePosition) {
        currentState = position;
        reset();
        getData();

    }

    private void getData() {
        ArrayList<DBMessage> temp = null;
        QueryBuilder builder = new QueryBuilder<DBMessage>(DBMessage.class);
        switch (currentState) {
            case 0://全部
                allList = DataBaseUtil.queryEquals(getMapAll(), DBMessage.class);
                break;
            case 1://已读
                allList = DataBaseUtil.queryNotEquals(getMap(), DBMessage.class);
                builder.whereNoEquals("_read_state", 0);
                break;
            case 2://未读
                allList = DataBaseUtil.queryEquals(getMap(), DBMessage.class);
                builder.whereEquals("_read_state", 0);
                break;
        }
        if (!TextUtils.isEmpty(type)) {
            builder.whereAppendAnd();
            builder.whereEquals("_type", type);
        }
        builder.limit(page * pageSize, pageSize).appendOrderDescBy("_id");
        temp = DataBaseUtil.query(builder);

        if (temp.size() > 0) {
            msgList.addAll(temp);
        }
        if (msgList.size() < allList.size()) {
            mLoadMoreWrapper.setLoadMoreView(R.layout.view_loadmore);
        } else {
            mLoadMoreWrapper.setLoadMoreView(0);
        }

        mLoadMoreWrapper.notifyDataSetChanged();
//        pageSize = temp.size();
        page++;
    }

    private Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("_read_state", 0);
        if (!TextUtils.isEmpty(type)) {
            map.put("_type", type);
        }
        return map;
    }

    private Map<String, Object> getMapAll() {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(type)) {
            map.put("_type", type);
        }
        return map;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(eventMsg);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();

//        if (null == msgList) {
//            msgList = new ArrayList<>();
//        }
//        if (null != mLoadMoreWrapper) {
//            getData();
//        }
    }

    @Override
    public void onLoadMoreRequested() {
//        if (page != 0 && msgList.size() >= 15)
        if (msgList.size() < allList.size() && msgList.size() > 0)
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getData();
                }
            }, 2000);
    }
}
