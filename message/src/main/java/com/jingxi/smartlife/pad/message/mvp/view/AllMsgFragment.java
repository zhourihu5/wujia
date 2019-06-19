package com.jingxi.smartlife.pad.message.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.wujia.businesslib.model.MsgModel;
import com.wujia.businesslib.base.MvpFragment;
import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.data.MsgDto;
import com.wujia.businesslib.event.EventBusUtil;
import com.wujia.businesslib.event.EventMsg;
import com.wujia.businesslib.event.IMiessageInvoke;
import com.jingxi.smartlife.pad.message.R;
import com.jingxi.smartlife.pad.message.mvp.adapter.MessageAdapter;
import com.wujia.lib.widget.HorizontalTabBar;
import com.wujia.lib.widget.HorizontalTabItem;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.base.baseadapter.wrapper.LoadMoreWrapper;
import com.wujia.lib_common.data.network.SimpleRequestSubscriber;
import com.wujia.lib_common.data.network.exception.ApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class AllMsgFragment extends MvpFragment implements HorizontalTabBar.OnTabSelectedListener, LoadMoreWrapper.OnLoadMoreListener, MessageAdapter.ReadMsgCallback {

    HorizontalTabBar tabBar;
    RecyclerView recyclerView;
    private ArrayList<MsgDto.ContentBean> msgList;
    private MessageAdapter mAdapter;
    private int currentState = 0;
    private LoadMoreWrapper mLoadMoreWrapper;
    private int page = 1, pageSize = 15;
//    private ArrayList<DBMessage> allList;//所有数据
    private String type = "99";//默认所有
    private boolean isVisible;

    public void setType(String type) {
        this.type = type;
//        currentState = 0;
        reset();
        getData();
    }

    private EventMsg eventMsg = new EventMsg(new IMiessageInvoke<EventMsg>() {
        @Override
        public void eventBus(EventMsg event) {
            if (event.type == EventMsg.TYPE_NEW_MSG) {
                reset();
                getData();
            } else if (event.type == EventMsg.TYPE_READ) {
                if (!isVisible) {//本页也会发送TYPE_READ,adapter已处理，所以页面显示时不处理
                    reset();
                    getData();
                }
            }
        }
    });

    private void reset() {
        page = 1;
        msgList.clear();
//        allList.clear();
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
        mAdapter = new MessageAdapter(mActivity, msgList,this);
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
    MsgModel msgModel;
    private void getData() {
        if(msgModel==null){
            msgModel=new MsgModel();
        }

        String status="99";//全部
        switch (currentState) {
            case 0://全部
                status="99";
                break;
            case 1://已读
               status="1";
                break;
            case 2://未读
                status="0";
                break;
        }

        addSubscribe(msgModel.getMsg(page+"",type,status).subscribeWith(new SimpleRequestSubscriber<ApiResponse<MsgDto>>(this, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(ApiResponse<MsgDto> response) {
                super.onResponse(response);

                    List<MsgDto.ContentBean> temp = response.data.getContent();

                    if (temp!=null&&temp.size() > 0) {
                        msgList.addAll(temp);
                    }
                    if(response.data.isLast()){
                        mLoadMoreWrapper.setLoadMoreView(0);
                    } else {
                        mLoadMoreWrapper.setLoadMoreView(R.layout.view_loadmore);
                    }

                    mLoadMoreWrapper.notifyDataSetChanged();
//        pageSize = temp.size();
                    page++;
                }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
            }
        }));


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
        isVisible = true;
//        if (null == msgList) {
//            msgList = new ArrayList<>();
//        }
//        if (null != mLoadMoreWrapper) {
//            getData();
//        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        isVisible = false;
    }

    @Override
    public void onLoadMoreRequested() {
//        if (page != 0 && msgList.size() >= 15)
//        if (msgList.size() < allList.size() && msgList.size() > 0)
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getData();
                }
            }, 2000);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onMsgReadClick(final MsgDto.ContentBean item) {//todo
        addSubscribe(msgModel.readMsg(item.getId()+"").subscribeWith(new SimpleRequestSubscriber<ApiResponse<Object>>(this, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(ApiResponse<Object> response) {
                super.onResponse(response);
                item.setStatus(MsgDto.STATUS_READ);
                EventBusUtil.post(new EventMsg(EventMsg.TYPE_READ));
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
            }
        }));


    }
}
