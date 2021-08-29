package com.jingxi.smartlife.pad.market.mvp.view

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.data.ServiceDto
import com.jingxi.smartlife.pad.market.mvp.model.MarketModel
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.data.CardDetailBean
import com.wujia.businesslib.event.EventBusUtil
import com.wujia.businesslib.event.EventSubscription
import com.wujia.businesslib.event.IMiessageInvoke
import com.wujia.lib.widget.HorizontalTabBar
import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.BaseView
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter
import com.wujia.lib_common.base.baseadapter.wrapper.LoadMoreWrapper
import com.wujia.lib_common.base.view.ServiceCardDecoration
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException
import com.wujia.lib_common.utils.LogUtil
import com.wujia.lib_common.utils.ScreenUtil
import java.util.*

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
open class AllServiceFragment : ServiceBaseFragment<BasePresenter<BaseView>>(), HorizontalTabBar.OnTabSelectedListener, LoadMoreWrapper.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, MultiItemTypeAdapter.OnRVItemClickListener {
    override val layoutId: Int
        get() = R.layout.fragment_service_all
    private var recyclerView: RecyclerView? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var isLoading: Boolean = false
    private val pageSize = 12
    private var pageNo = 1
    //    private int totleSize = 0;
    private var datas: ArrayList<CardDetailBean.ServicesBean>? = null
    private var mLoadMoreWrapper: LoadMoreWrapper? = null

     protected open var type: String= TYPE_ALL

    private val event = EventSubscription(object : IMiessageInvoke<EventSubscription> {
        override fun eventBus(event: EventSubscription) {
            if (event.type == EventSubscription.TYPE_NOTIFY) {
                pageNo = 1
                getList(false)
            } else if (isSupportVisible && event.eventType != EventSubscription.PUSH_NOTIFY) {
                mLoadMoreWrapper!!.notifyDataSetChanged()
            } else {
                pageNo = 1
                getList(true)
            }
        }
    })

    internal var isVisible: Boolean = false




    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_TYPE, type)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        if (savedInstanceState != null) {
            type = savedInstanceState.getString(KEY_TYPE)!!
            LogUtil.i("Allservice from savedInstanceState type=" + type)
        }

        mSwipeRefreshLayout = `$`(R.id.swipe_container)
        recyclerView = `$`(R.id.rv1)
        recyclerView!!.addItemDecoration(ServiceCardDecoration(ScreenUtil.dip2px(84f)))

        datas = ArrayList()

        val mAdapter = getAdapter(datas!!)
        mLoadMoreWrapper = LoadMoreWrapper(mAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
        recyclerView!!.adapter = mLoadMoreWrapper
        mLoadMoreWrapper!!.setOnLoadMoreListener(this)
        mSwipeRefreshLayout!!.setOnRefreshListener(this)
        mAdapter.setOnItemClickListener(this)
        getList(true)
        //        if(type.equals(TYPE_MY)){
        EventBusUtil.register(event)
        //        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //        if(type.equals(TYPE_MY)){
        EventBusUtil.unregister(event)
        //        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        isVisible = true
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        isVisible = false
    }

    private fun getList(isShowLoadingDialog: Boolean) {
        isLoading = true
        addSubscribe(MarketModel().getServiceList(type, pageNo, pageSize).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<ServiceDto>>(this@AllServiceFragment,
                ActionConfig(isShowLoadingDialog, SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<ServiceDto>) {
                super.onResponse(response)
                if (mSwipeRefreshLayout!!.isRefreshing)
                    mSwipeRefreshLayout!!.isRefreshing = false
                isLoading = false

                if (pageNo == 1)
                    datas!!.clear()

                datas!!.addAll(response.data?.page!!.content!!)

                if (response.data!!.page!!.last) {
                    mLoadMoreWrapper!!.setLoadMoreView(0)
                } else {
                    mLoadMoreWrapper!!.setLoadMoreView(R.layout.view_loadmore)
                }

                mLoadMoreWrapper!!.notifyDataSetChanged()
                pageNo++
            }

            override fun onFailed(apiException: ApiException) {
                super.onFailed(apiException)
                isLoading = false
                if (mSwipeRefreshLayout!!.isRefreshing)
                    mSwipeRefreshLayout!!.isRefreshing = false
            }
        }))
    }

    override fun onTabSelected(position: Int, prePosition: Int) {

    }


    override fun onLoadMoreRequested() {
        if (mSwipeRefreshLayout!!.isRefreshing || isLoading)
            return
        getList(false)
    }

    override fun onRefresh() {
        if (isLoading)
            return
        mLoadMoreWrapper!!.setLoadMoreView(0)
        pageNo = 1
        getList(false)
    }

    override fun onItemClick(adapter: RecyclerView.Adapter<*>?, holder: RecyclerView.ViewHolder, position: Int) {
        toTarget(datas!![position])
    }

    override fun createPresenter(): BasePresenter<BaseView>? {
        return null
    }

    companion object {


       const val TYPE_ALL = "4"
       const val TYPE_MY = "1"
       const val TYPE_GOV = "3"

        private const val KEY_TYPE = "type"

    }

}
