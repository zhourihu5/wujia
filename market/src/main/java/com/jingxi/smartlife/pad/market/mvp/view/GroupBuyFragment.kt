package com.jingxi.smartlife.pad.market.mvp.view

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.adapter.GroupBuyAdapter
import com.jingxi.smartlife.pad.market.mvp.data.ContentItem
import com.jingxi.smartlife.pad.market.mvp.data.GroupBuyVo
import com.jingxi.smartlife.pad.market.mvp.model.MarketModel
import com.wujia.businesslib.data.ApiResponse
import com.wujia.lib.widget.HorizontalTabBar
import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.BaseView
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter
import com.wujia.lib_common.base.baseadapter.wrapper.LoadMoreWrapper
import com.wujia.lib_common.base.view.ServiceCardDecoration
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException
import com.wujia.lib_common.utils.ScreenUtil
import kotlinx.android.synthetic.main.fragment_group_buy.*
import java.util.*

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
class GroupBuyFragment : ServiceBaseFragment<BasePresenter<BaseView>>(), HorizontalTabBar.OnTabSelectedListener, LoadMoreWrapper.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, MultiItemTypeAdapter.OnRVItemClickListener {
    override val layoutId: Int
        get() =R.layout.fragment_group_buy
    private var isLoading: Boolean = false
    private val pageSize = 12
    private var pageNo = 1
    private lateinit var  datas: ArrayList<ContentItem>
    private var mLoadMoreWrapper: LoadMoreWrapper? = null


    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        rvGroupBuy!!.addItemDecoration(ServiceCardDecoration(ScreenUtil.dip2px(12f)))

        datas = ArrayList()

        val mAdapter = GroupBuyAdapter(context!!, datas)
        mAdapter.setClickLisner{
            holder, t, position->
            run {
                val groupBuyDetailFragment = GroupBuyDetailFragment.newInstance();
                val bundle = Bundle()
                bundle.putString(GroupBuyDetailFragment.BUNDLE_KEY_ID, t.id)
                groupBuyDetailFragment.arguments = bundle
                start(groupBuyDetailFragment)
            }
        }

        mLoadMoreWrapper = LoadMoreWrapper(mAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
        rvGroupBuy!!.adapter = mLoadMoreWrapper
        mLoadMoreWrapper!!.setOnLoadMoreListener(this)
        swipeRefreshLayout!!.setOnRefreshListener(this)

        getList(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        EventBusUtil.unregister(event)
    }

    private fun getList(isShowLoadingDialog: Boolean) {
        isLoading = true
        addSubscribe(MarketModel().getGroupBuyList( pageNo, pageSize).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<GroupBuyVo>>(this@GroupBuyFragment, SimpleRequestSubscriber.ActionConfig(isShowLoadingDialog, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<GroupBuyVo>) {
                super.onResponse(response)
                isLoading = false
                swipeRefreshLayout!!.isRefreshing = false

                if (pageNo == 1)
                    datas!!.clear()

                datas!!.addAll(response.data!!.content!!)

                if (response.data!!.content!!.size<pageSize) {
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
                swipeRefreshLayout!!.isRefreshing = false
            }
        }))
    }


    override fun onTabSelected(position: Int, prePosition: Int) {

    }


    override fun onLoadMoreRequested() {
        if (swipeRefreshLayout!!.isRefreshing || isLoading)
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
//        toTarget(datas!![position])
    }

    override fun createPresenter(): BasePresenter<BaseView>? {
        return null
    }

    companion object {

        fun newInstance(): GroupBuyFragment {
            val fragment = GroupBuyFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
