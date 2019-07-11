package com.jingxi.smartlife.pad.message.mvp.view

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.text.TextUtils

import com.jingxi.smartlife.pad.message.R
import com.jingxi.smartlife.pad.message.mvp.adapter.MessageAdapter
import com.wujia.businesslib.base.DataManager
import com.wujia.businesslib.base.MvpFragment
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.data.MsgDto
import com.wujia.businesslib.event.EventBusUtil
import com.wujia.businesslib.event.EventMsg
import com.wujia.businesslib.event.IMiessageInvoke
import com.wujia.businesslib.model.BusModel
import com.wujia.businesslib.util.LoginUtil
import com.wujia.lib.widget.HorizontalTabBar
import com.wujia.lib.widget.HorizontalTabItem
import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.BaseView
import com.wujia.lib_common.base.baseadapter.wrapper.LoadMoreWrapper
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException
import kotlinx.android.synthetic.main.fragment_msg_all.*

import java.util.ArrayList
import java.util.HashMap

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
class AllMsgFragment : MvpFragment<BasePresenter<BaseView>>(), HorizontalTabBar.OnTabSelectedListener, LoadMoreWrapper.OnLoadMoreListener, MessageAdapter.ReadMsgCallback {

    private var msgList: ArrayList<MsgDto.ContentBean>? = null
    private var mAdapter: MessageAdapter? = null
    private var currentState = 0
    private var mLoadMoreWrapper: LoadMoreWrapper? = null
    private var page = 1
    private val pageSize = 15
    //    private ArrayList<DBMessage> allList;//所有数据
    private var type = ""//默认所有
//    private var isVisible: Boolean = false

    private val eventMsg = EventMsg(IMiessageInvoke { event ->
        if (event.type == EventMsg.TYPE_NEW_MSG) {
            reset()
            getData(false)
        } else if (event.type == EventMsg.TYPE_READ) {
            if (!isVisible) {//本页也会发送TYPE_READ,adapter已处理，所以页面显示时不处理
                reset()
                getData(false)
            }
        }
    })

    internal var busModel: BusModel? = null

    private val map: Map<String, Any>
        get() {
            val map = HashMap<String, Any>()
            map["_read_state"] = 0
            if (!TextUtils.isEmpty(type)) {
                map["_type"] = type
            }
            return map
        }

    private val mapAll: Map<String, Any>
        get() {
            val map = HashMap<String, Any>()
            if (!TextUtils.isEmpty(type)) {
                map["_type"] = type
            }
            return map
        }

    fun setType(type: String) {
        this.type = type
        if (tab_layout != null) {
            //        currentState = 0;
            reset()
            getData(true)

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_TYPE, type)
    }

    private fun reset() {
        page = 1
        if (msgList != null) {
            msgList!!.clear()
            mLoadMoreWrapper!!.notifyDataSetChanged()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_msg_all
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        if (savedInstanceState != null) {
            type = savedInstanceState.getString(KEY_TYPE, type)
        }
//        tab_layout = `$`(R.id.tab_layout)
//        rv1 = `$`(R.id.rv1)

        tab_layout!!.addItem(HorizontalTabItem(mContext, R.string.all))
        tab_layout!!.addItem(HorizontalTabItem(mContext, R.string.readed))
        tab_layout!!.addItem(HorizontalTabItem(mContext, R.string.unread))

        tab_layout!!.setOnTabSelectedListener(this)

        msgList = ArrayList()
        mAdapter = MessageAdapter(mActivity, msgList, this)
        mLoadMoreWrapper = LoadMoreWrapper(mAdapter)
        rv1?.adapter = mLoadMoreWrapper
        mLoadMoreWrapper!!.setOnLoadMoreListener(this)
        if (busModel == null) {
            busModel = BusModel()
        }
        getData(true)

        EventBusUtil.register(eventMsg)
    }

    override fun onTabSelected(position: Int, prePosition: Int) {
        currentState = position
        reset()
        getData(true)

    }

    private fun getData(isShowLoadingDialog: Boolean) {

        var status = ""//全部
        when (currentState) {
            0//全部
            -> status = ""
            1//已读
            -> status = "1"
            2//未读
            -> status = "0"
        }
        var familyId: String? = null
        try {
            familyId = DataManager.getFamilyId()
        } catch (e: Exception) {
            e.printStackTrace()
            LoginUtil.toLoginActivity()
            return
        }

        addSubscribe(busModel!!.getMsg(familyId, type, status, page, pageSize).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<MsgDto>>(this, SimpleRequestSubscriber.ActionConfig(isShowLoadingDialog, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<MsgDto>) {
                super.onResponse(response)

                val temp = response.data.content
                if (page == 1) {
                    msgList!!.clear()
                }
                if (temp != null && temp.size > 0) {
                    msgList!!.addAll(temp)
                }
                if (response.data.isLast) {
                    mLoadMoreWrapper!!.setLoadMoreView(0)
                } else {
                    mLoadMoreWrapper!!.setLoadMoreView(R.layout.view_loadmore)
                }

                mLoadMoreWrapper!!.notifyDataSetChanged()
                //        pageSize = temp.size();
                page++
            }

            override fun onFailed(apiException: ApiException) {
                super.onFailed(apiException)
            }
        }))


    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBusUtil.unregister(eventMsg)
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
//        isVisible = true
        //        if (null == msgList) {
        //            msgList = new ArrayList();
        //        }
        //        if (null != mLoadMoreWrapper) {
        //            getData();
        //        }
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
//        isVisible = false
    }

    override fun onLoadMoreRequested() {
        getData(false)
    }

    override fun createPresenter(): BasePresenter<BaseView>? {
        return null
    }

    override fun onMsgReadClick(item: MsgDto.ContentBean) {//todo
        addSubscribe(busModel!!.readMsg(item.id.toString() + "").subscribeWith(object : SimpleRequestSubscriber<ApiResponse<Any>>(this, SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<Any>) {
                super.onResponse(response)
                item.isRead = MsgDto.STATUS_READ
                EventBusUtil.post(EventMsg(EventMsg.TYPE_READ))
            }

            override fun onFailed(apiException: ApiException) {
                super.onFailed(apiException)
            }
        }))


    }

    companion object {

        private val KEY_TYPE = "type"

        fun newInstance(): AllMsgFragment {
            val fragment = AllMsgFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
