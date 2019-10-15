package com.jingxi.smartlife.pad.message.mvp.view

import android.os.Bundle
import androidx.annotation.StringDef
import androidx.recyclerview.widget.RecyclerView
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
import kotlinx.android.synthetic.main.fragment_msg_all.*
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.*

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
class AllMsgFragment : MvpFragment<BasePresenter<BaseView>>(), HorizontalTabBar.OnTabSelectedListener, LoadMoreWrapper.OnLoadMoreListener, MessageAdapter.ReadMsgCallback {
    override val layoutId: Int
        get() = R.layout.fragment_msg_all
    private  var msgList: ArrayList<MsgDto.ContentBean>?=null
    private var mAdapter: MessageAdapter? = null
    private var currentState = 0
    private var mLoadMoreWrapper: LoadMoreWrapper? = null
    private var page = 1
    private val pageSize = 15

    private val eventMsg = EventMsg(object : IMiessageInvoke<EventMsg> {
        override fun eventBus(event: EventMsg) {
            if (event.type == EventMsg.TYPE_NEW_MSG) {
                reset()
                getData(false)
            } else if (event.type == EventMsg.TYPE_READ) {
                if (!isSupportVisible) {//本页也会发送TYPE_READ,adapter已处理，所以页面显示时不处理
                    reset()
                    getData(false)
                }
            }
        }
    })

//    internal var busModel: BusModel? = null


     var type: String = MSG_TYPE_ALL
        set(@MsgType type){
//            this.type=type//this is recursive invoke,stack trace error
            field=type
            if (tab_layout != null) {
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
        msgList?.let { it.clear();mLoadMoreWrapper?.notifyDataSetChanged() }
    }


    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        if (savedInstanceState != null) {
            type = savedInstanceState.getString(KEY_TYPE, type)
        }

        tab_layout!!.addItem(HorizontalTabItem(mContext!!, R.string.all))
        tab_layout!!.addItem(HorizontalTabItem(mContext!!, R.string.readed))
        tab_layout!!.addItem(HorizontalTabItem(mContext!!, R.string.unread))

        tab_layout!!.setOnTabSelectedListener(this)

        msgList = ArrayList()
        mAdapter = MessageAdapter(mActivity, msgList!!, this)
        mLoadMoreWrapper = LoadMoreWrapper(mAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
        rv1?.adapter = mLoadMoreWrapper
        mLoadMoreWrapper!!.setOnLoadMoreListener(this)
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
            familyId = DataManager.familyId
        } catch (e: Exception) {
            e.printStackTrace()
            LoginUtil.toLoginActivity()
            return
        }

        addSubscribe(BusModel().getMsg(familyId!!, type, status, page, pageSize).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<MsgDto>>(this@AllMsgFragment, SimpleRequestSubscriber.ActionConfig(isShowLoadingDialog, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<MsgDto>) {
                super.onResponse(response)

                val temp = response.data?.content
                if (page == 1) {
                    msgList!!.clear()
                }
                if (temp != null && temp.size > 0) {
                    msgList!!.addAll(temp)
                }
                if (response.data?.last!!) {
                    mLoadMoreWrapper!!.setLoadMoreView(0)
                } else {
                    mLoadMoreWrapper!!.setLoadMoreView(R.layout.view_loadmore)
                }

                mLoadMoreWrapper!!.notifyDataSetChanged()
                //        pageSize = temp.size();
                page++
            }

        }))


    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBusUtil.unregister(eventMsg)
    }

    override fun onLoadMoreRequested() {
        getData(false)
    }

    override fun createPresenter(): BasePresenter<BaseView>? {
        return null
    }

    override fun onMsgReadClick(item: MsgDto.ContentBean) {//todo
        addSubscribe(BusModel().readMsg(item.id.toString() + "").subscribeWith(object : SimpleRequestSubscriber<ApiResponse<Any>>(this@AllMsgFragment, SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<Any>) {
                super.onResponse(response)
                item.isRead = MsgDto.STATUS_READ
                EventBusUtil.post(EventMsg(EventMsg.TYPE_READ))
            }

        }))


    }

    companion object {

        const val MSG_TYPE_ALL: String=""
        const val MSG_TYPE_PROPERTY: String="1"
        const val MSG_TYPE_COMMUNITY: String="2"
        const val  MSG_TYPE_SYSTEM: String = "0"
        const val  MSG_TYPE_ORDER: String = "4"
        const val  MSG_TYPE_GOV: String = "3"
        private val KEY_TYPE = "type"

        fun newInstance(): AllMsgFragment {
            val fragment = AllMsgFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}

@StringDef(AllMsgFragment.MSG_TYPE_ALL, AllMsgFragment.MSG_TYPE_COMMUNITY, AllMsgFragment.MSG_TYPE_PROPERTY, AllMsgFragment.MSG_TYPE_SYSTEM)
@Retention(RetentionPolicy.SOURCE)
internal annotation class MsgType