package com.jingxi.smartlife.pad.message.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.wujia.businesslib.model.BusModel;
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
    private String type = "";//默认所有
    private boolean isVisible;

    public void setType(String type) {
        this.type = type;
//        currentState = 0;
        reset();
        getData(true);
    }

    private EventMsg eventMsg = new EventMsg(new IMiessageInvoke<EventMsg>() {
        @Override
        public void eventBus(EventMsg event) {
            if (event.type == EventMsg.TYPE_NEW_MSG) {
                reset();
                getData(false);
            } else if (event.type == EventMsg.TYPE_READ) {
                if (!isVisible) {//本页也会发送TYPE_READ,adapter已处理，所以页面显示时不处理
                    reset();
                    getData(false);
                }
            }
        }
    });

    private void reset() {
        page = 1;
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
        if(busModel ==null){
            busModel =new BusModel();
        }
        getData(true);

        EventBusUtil.register(eventMsg);
    }

    @Override
    public void onTabSelected(int position, int prePosition) {
        currentState = position;
        reset();
        getData(true);

    }
    BusModel busModel;
    private void getData(boolean isShowLoadingDialog) {

        String status="";//全部
        switch (currentState) {
            case 0://全部
                status="";
                break;
            case 1://已读
               status="1";
                break;
            case 2://未读
                status="0";
                break;
        }

        addSubscribe(busModel.getMsg(type,status,page,pageSize).subscribeWith(new SimpleRequestSubscriber<ApiResponse<MsgDto>>(this, new SimpleRequestSubscriber.ActionConfig(isShowLoadingDialog, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(ApiResponse<MsgDto> response) {
                super.onResponse(response);

                    List<MsgDto.ContentBean> temp = response.data.getContent();
                    if(page==1){
                        msgList.clear();
                    }
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
        getData(false);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onMsgReadClick(final MsgDto.ContentBean item) {//todo
        addSubscribe(busModel.readMsg(item.getId()+"").subscribeWith(new SimpleRequestSubscriber<ApiResponse<Object>>(this, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(ApiResponse<Object> response) {
                super.onResponse(response);
                item.setIsRead(MsgDto.STATUS_READ);
                EventBusUtil.post(new EventMsg(EventMsg.TYPE_READ));
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
            }
        }));


    }
}
