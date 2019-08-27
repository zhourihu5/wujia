package com.jingxi.smartlife.pad.market.mvp.view

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.MarketHomeFragment
import com.jingxi.smartlife.pad.market.mvp.adapter.OrderAdapter
import com.jingxi.smartlife.pad.market.mvp.data.OrderItemVo
import com.jingxi.smartlife.pad.market.mvp.data.OrderVo
import com.jingxi.smartlife.pad.market.mvp.model.MarketModel
import com.wujia.businesslib.base.DataManager
import com.wujia.businesslib.base.MvpFragment
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.util.LoginUtil
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
        orderList?.let { it.clear();mLoadMoreWrapper?.notifyDataSetChanged() }
    }


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
        mAdapter = OrderAdapter(mActivity, orderList!!)
        mAdapter.btnClickLisner={holder, t, position->
            when(t.status){
                "1"->{//待付款 todo 弹小程序二维码框付款
                }
                "2"->{//待配送
                }
                "3"->{//已收货
                    (parentFragment as MarketHomeFragment).switchTab(MarketHomeFragment.TAB_GROUP_BUY)
                }
                "4"->{//已过期
                    (parentFragment as MarketHomeFragment).switchTab(MarketHomeFragment.TAB_GROUP_BUY)
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
                    orderList!!.clear()
                }
                if (temp != null && temp.size > 0) {
                    orderList!!.addAll(temp)
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
