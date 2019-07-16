package com.jingxi.smartlife.pad.market.mvp.view

import android.os.Bundle
import android.view.View
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.adapter.OrderAdapter
import com.jingxi.smartlife.pad.market.mvp.data.OrderBean
import com.wujia.businesslib.TitleFragment
import com.wujia.lib.widget.HorizontalTabBar
import com.wujia.lib.widget.HorizontalTabItem
import com.wujia.lib_common.base.baseadapter.wrapper.EmptyWrapper
import kotlinx.android.synthetic.main.fragment_market_order.*
import java.util.*

/**
 * Author: created by shenbingkai on 2019/2/23 14 38
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class OrderListFragment : TitleFragment(), View.OnClickListener, HorizontalTabBar.OnTabSelectedListener {

    internal lateinit var mAdapter: EmptyWrapper

    override fun getLayoutId(): Int {
        return R.layout.fragment_market_order
    }

    override fun initEventAndData() {
        super.initEventAndData()

        tab_layout.addItem(HorizontalTabItem(mContext, R.string.all_order))
        tab_layout.addItem(HorizontalTabItem(mContext, R.string.wait_pay))
        tab_layout.addItem(HorizontalTabItem(mContext, R.string.wait_send))
        tab_layout.addItem(HorizontalTabItem(mContext, R.string.wait_receive))
        tab_layout.addItem(HorizontalTabItem(mContext, R.string.wait_evaluate))

        tab_layout.setOnTabSelectedListener(this)

        val datas = ArrayList<OrderBean>()
        datas.add(OrderBean())
        datas.add(OrderBean())
        datas.add(OrderBean())
        datas.add(OrderBean())
        datas.add(OrderBean())

        val adapter = OrderAdapter(mActivity, datas)
        mAdapter = EmptyWrapper(adapter)
        mAdapter.setEmptyView(R.layout.layout_empty)
        rv1.adapter = mAdapter
        adapter.setOnItemClickListener { adapter, holder, position -> start(OrderDetailsFragment.newInstance("id")) }
    }

    override fun getTitle(): Int {
        return R.string.family_order
    }

    override fun onClick(v: View) {
        val id = v.id

    }

    override fun onTabSelected(position: Int, prePosition: Int) {

    }

    companion object {

        fun newInstance(): OrderListFragment {
            val fragment = OrderListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
