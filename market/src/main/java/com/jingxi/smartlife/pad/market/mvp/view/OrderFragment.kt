package com.jingxi.smartlife.pad.market.mvp.view

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.PromoteHomeFragment
import com.jingxi.smartlife.pad.market.mvp.adapter.OrderAdapter
import com.jingxi.smartlife.pad.market.mvp.data.OrderItemVo
import com.jingxi.smartlife.pad.market.mvp.data.OrderVo
import com.jingxi.smartlife.pad.market.mvp.model.MarketModel
import com.wujia.businesslib.base.DataManager
import com.wujia.businesslib.base.MvpFragment
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.event.EventBusUtil
import com.wujia.businesslib.event.EventOrder
import com.wujia.businesslib.event.EventToGroupBuy
import com.wujia.businesslib.event.IMiessageInvoke
import com.wujia.businesslib.util.LoginUtil
import com.wujia.lib.imageloader.ImageLoaderManager
import com.wujia.lib.widget.HorizontalTabBar
import com.wujia.lib.widget.HorizontalTabItem
import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.BaseView
import com.wujia.lib_common.base.baseadapter.wrapper.LoadMoreWrapper
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException
import kotlinx.android.synthetic.main.fragment_order.*
import java.util.*

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
class OrderFragment : MvpFragment<BasePresenter<BaseView>>(), HorizontalTabBar.OnTabSelectedListener, LoadMoreWrapper.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    override fun onRefresh() {
        if (isLoading)
            return
        mLoadMoreWrapper!!.setLoadMoreView(0)
        pageNo = 1
        getData(false)
    }
    var isLoading:Boolean=false
    private lateinit var mAdapter: OrderAdapter
    override val layoutId: Int
        get() = R.layout.fragment_order
    private lateinit var orderList: ArrayList<OrderItemVo>
    private var currentState = 0
    private var mLoadMoreWrapper: LoadMoreWrapper? = null
    private var pageNo = 1
    private val pageSize = 15

    private fun reset() {
        pageNo = 1
        orderList.let {
            orderList.clear();
            mLoadMoreWrapper?.setLoadMoreView(0)
            mLoadMoreWrapper?.notifyDataSetChanged()
        }
    }

    private val eventOrder = EventOrder(object : IMiessageInvoke<EventOrder> {
        override fun eventBus(event: EventOrder) {
            reset()
            getData(false)
        }
    })

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)


        tab_layout.addItem(HorizontalTabItem(mContext!!, R.string.all_order))
        tab_layout.addItem(HorizontalTabItem(mContext!!, R.string.wait_pay))
//        tab_layout.addItem(HorizontalTabItem(mContext!!, R.string.wait_send))
        tab_layout.addItem(HorizontalTabItem(mContext!!, R.string.wait_receive))
        tab_layout.addItem(HorizontalTabItem(mContext!!, R.string.order_received))
        tab_layout.addItem(HorizontalTabItem(mContext!!, R.string.order_expired))

        tab_layout!!.setOnTabSelectedListener(this)

        orderList = ArrayList()
        mAdapter = OrderAdapter(mActivity, orderList)
        mAdapter.btnClickLisner={holder, t, position->
            when(t.status){
                "1"->{//待付款
                    addSubscribe(
                            MarketModel().generateOrderDetailQrCode(t.id)
                                    .subscribeWith(
                                            object : SimpleRequestSubscriber<ApiResponse<String>>(
                                                    this@OrderFragment, ActionConfig(true, SHOWERRORMESSAGE)) {
                                                override fun onResponse(response: ApiResponse<String>) {
                                                    showQrCodeDialog(response.data!!)
                                                }
                                            })
                    )
                }
                "2"->{//待配送
                }
                "3"->{//已收货
                    (parentFragment?.parentFragment as PromoteHomeFragment).switchTab(0)
                    EventBusUtil.post(EventToGroupBuy())
                }
                "4"->{//已过期
                    (parentFragment?.parentFragment as PromoteHomeFragment).switchTab(0)
                    EventBusUtil.post(EventToGroupBuy())
                }
                "5"->{//配送中
                }

            }
        }
        mAdapter.setOnItemClickListener { adapter, holder, position -> start(OrderDetailFragment.newInstance(orderList[position].id)) }
        mLoadMoreWrapper = LoadMoreWrapper(mAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
        rvOrder.adapter = mLoadMoreWrapper
        mLoadMoreWrapper!!.setOnLoadMoreListener(this)
        swipeRefreshLayout!!.setOnRefreshListener(this)
        getData(true)
        EventBusUtil.register(eventOrder)
    }
    private val handler:Handler=object:Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            mLoadMoreWrapper?.notifyDataSetChanged()
            if(isSupportVisible){
                this.sendEmptyMessageDelayed(0,1000)
            }else{
                this.removeCallbacksAndMessages(null)
            }
        }
    }
    override fun onSupportVisible() {
        super.onSupportVisible()
        handler.sendEmptyMessage(0)
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
        EventBusUtil.unregister(eventOrder)
    }

    fun showQrCodeDialog(qrCodeImg: String) {
        val dialog = Dialog(mActivity)
        val conv = LayoutInflater.from(mActivity).inflate(R.layout.dialog_wxapp_qrcode, null)

        val ivQrcode = conv.findViewById<ImageView>(R.id.ivQrCode)
        ImageLoaderManager.instance.loadImage(qrCodeImg,0,ivQrcode)


        dialog.setContentView(conv)
        val dialogWindow = dialog.window
        dialogWindow!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialogWindow.setGravity(Gravity.CENTER)
        dialog.show()
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
            1//待付款
            -> status = "1"
            2//待收货
            -> status = "2,5"
            3//已收货
            -> status = "3"
            4//已过期
            -> status = "4"
        }
        var familyId: String? = null
        try {
            familyId = DataManager.familyId
        } catch (e: Exception) {
            e.printStackTrace()
            LoginUtil.toLoginActivity()
            return
        }
        isLoading=true
        addSubscribe(MarketModel().getOrderList( status, pageNo, pageSize).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<OrderVo>>(this@OrderFragment, ActionConfig(isShowLoadingDialog, SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<OrderVo>) {
                super.onResponse(response)
                isLoading=false
                swipeRefreshLayout!!.isRefreshing = false
                val temp = response.data?.content
                if (pageNo == 1) {
                    orderList.clear()
                }
                if (temp != null && temp.isNotEmpty()) {
                    orderList.addAll(temp)
                }
                if (response.data?.last!!) {
                    mLoadMoreWrapper!!.setLoadMoreView(0)
                } else {
                    mLoadMoreWrapper!!.setLoadMoreView(R.layout.view_loadmore)
                }

                mLoadMoreWrapper!!.notifyDataSetChanged()
                //        pageSize = temp.size();
                pageNo++
            }

            override fun onFailed(apiException: ApiException) {
                super.onFailed(apiException)
                isLoading=false
                swipeRefreshLayout!!.isRefreshing = false
            }
        }))


    }


    override fun onLoadMoreRequested() {
        getData(false)
    }

    override fun createPresenter(): BasePresenter<BaseView>? {
        return null
    }


}
