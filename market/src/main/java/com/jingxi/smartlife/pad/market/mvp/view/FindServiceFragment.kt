package com.jingxi.smartlife.pad.market.mvp.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.data.ServiceDto
import com.jingxi.smartlife.pad.market.mvp.model.MarketModel
import com.wujia.businesslib.base.WebViewFragment
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.data.CardDetailBean
import com.wujia.businesslib.event.EventBusUtil
import com.wujia.businesslib.event.EventSubscription
import com.wujia.businesslib.event.IMiessageInvoke
import com.wujia.lib.imageloader.ImageLoaderManager
import com.wujia.lib.widget.HorizontalTabBar
import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.BaseView
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter
import com.wujia.lib_common.base.baseadapter.wrapper.LoadMoreWrapper
import com.wujia.lib_common.base.view.ServiceCardDecoration
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException
import com.wujia.lib_common.utils.ScreenUtil
import java.util.*

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
class FindServiceFragment : ServiceBaseFragment<BasePresenter<BaseView>>(), HorizontalTabBar.OnTabSelectedListener, LoadMoreWrapper.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, MultiItemTypeAdapter.OnRVItemClickListener {
    override val layoutId: Int
        get() =R.layout.fragment_service_find
    private var recyclerView: RecyclerView? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var isLoading: Boolean = false
    private val pageSize = 12
    private var pageNo = 1
    //    private int totleSize = 0;
    private lateinit var  datas: ArrayList<CardDetailBean.ServicesBean>
    private var mLoadMoreWrapper: LoadMoreWrapper? = null
    private var ivBanner: ImageView? = null
    private var tvBanner: TextView? = null

    private val event = EventSubscription(object : IMiessageInvoke<EventSubscription> {
        override fun eventBus(event: EventSubscription) {
            if (event.type == EventSubscription.TYPE_NOTIFY) {
                pageNo = 1
                getList(false)
            } else if (isVisible && event.eventType != EventSubscription.PUSH_NOTIFY) {
                mLoadMoreWrapper!!.notifyDataSetChanged()
            } else {
                pageNo = 1
                getList(true)
            }
        }
    })

    internal lateinit var marketModel: MarketModel


    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        mSwipeRefreshLayout = `$`(R.id.swipe_container)
        recyclerView = `$`(R.id.rv1)
        ivBanner = `$`(R.id.img1)
        tvBanner = `$`(R.id.tv1)
        recyclerView!!.addItemDecoration(ServiceCardDecoration(ScreenUtil.dip2px(24f)))

        datas = ArrayList()

        val mAdapter = getAdapter(datas)
        mLoadMoreWrapper = LoadMoreWrapper(mAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
        recyclerView!!.adapter = mLoadMoreWrapper
        mLoadMoreWrapper!!.setOnLoadMoreListener(this)
        mSwipeRefreshLayout!!.setOnRefreshListener(this)

        mAdapter.setOnItemClickListener(this)
        marketModel = MarketModel()
        getList(true)
        EventBusUtil.register(event)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBusUtil.unregister(event)
    }

    private fun getList(isShowLoadingDialog: Boolean) {
        isLoading = true
        addSubscribe(marketModel.getServiceList("2", pageNo, pageSize).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<ServiceDto>>(this@FindServiceFragment, SimpleRequestSubscriber.ActionConfig(isShowLoadingDialog, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<ServiceDto>) {
                super.onResponse(response)
                isLoading = false
                setBanner(response)

                if (mSwipeRefreshLayout!!.isRefreshing) {
                    mSwipeRefreshLayout!!.isRefreshing = false
                }

                if (pageNo == 1)
                    datas!!.clear()

                datas!!.addAll(response.data?.page!!.content!!)

                if (response.data?.page!!.last) {
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

    protected fun setBanner(response: ApiResponse<ServiceDto>) {
        val list = response.data?.bannerList
        if (null == list || list.isEmpty()) {
            return
        }
        val banner = list[0]
        ImageLoaderManager.instance.loadImage(banner.cover, ivBanner)
        //                tvBanner.setText(TextUtils.isEmpty(banner.title) ? "" : banner.title);
        ivBanner!!.setOnClickListener {banner.url?.let { it1 -> parentStart( WebViewFragment.newInstance(it1) )} }
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

        fun newInstance(): FindServiceFragment {
            val fragment = FindServiceFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
