package com.jingxi.smartlife.pad.market.mvp.view

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.View

import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.adapter.MarketGoodsAdapter
import com.jingxi.smartlife.pad.market.mvp.adapter.TagAdapter
import com.jingxi.smartlife.pad.market.mvp.data.GoodsBean
import com.jingxi.smartlife.pad.market.mvp.data.TagBean
import com.wujia.lib_common.base.BaseFragment
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter
import com.wujia.lib_common.base.view.HorizontalDecoration

import java.util.ArrayList

/**
 * Author: created by shenbingkai on 2019/2/23 14 38
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class ShopDetailsFragment : BaseFragment(), View.OnClickListener {

    private var rvTag: RecyclerView? = null
    private var rvGoods: RecyclerView? = null
    private var goodsAdapter: MarketGoodsAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_shop_details
    }

    override fun initEventAndData() {
        super.initEventAndData()

        rvTag = `$`(R.id.rv1)
        rvGoods = `$`(R.id.rv2)
        `$`<View>(R.id.tv1).setOnClickListener(this)
        `$`<View>(R.id.btn1).setOnClickListener(this)
        `$`<View>(R.id.btn2).setOnClickListener(this)
        `$`<View>(R.id.btn3).setOnClickListener(this)

        val tags = ArrayList<TagBean>()
        tags.add(TagBean())
        tags.add(TagBean())
        tags.add(TagBean())

        rvTag!!.addItemDecoration(HorizontalDecoration(20))
        rvTag!!.adapter = TagAdapter(mActivity, tags)


        val datas = ArrayList<GoodsBean>()
        datas.add(GoodsBean())
        datas.add(GoodsBean())
        datas.add(GoodsBean())
        datas.add(GoodsBean())
        datas.add(GoodsBean())

        goodsAdapter = MarketGoodsAdapter(mActivity, datas)
        rvGoods!!.adapter = goodsAdapter
        goodsAdapter!!.setOnItemClickListener { adapter, holder, position ->
            val dialog = GoodsDetailsDialog(mActivity)

            dialog.show()
        }
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.tv1) {
            pop()
        } else if (id == R.id.btn1) {
            //搜索
        } else if (id == R.id.btn2) {
            //购物车
            start(CarFragment.newInstance())
        } else if (id == R.id.btn3) {
            //订单
            start(OrderListFragment.newInstance())
        }
    }

    companion object {

        fun newInstance(id: String): ShopDetailsFragment {
            val fragment = ShopDetailsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
