package com.jingxi.smartlife.pad.market.mvp.view

import android.os.Bundle
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.adapter.CarGroupAdapter
import com.jingxi.smartlife.pad.market.mvp.data.GoodsBean
import com.wujia.businesslib.TitleFragment
import kotlinx.android.synthetic.main.fragment_car.*
import java.util.*

/**
 * Author: created by shenbingkai on 2019/2/24 16 05
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class CarFragment : TitleFragment() {
    override val title: Int
        get() = R.string.buy_car
    private var mAdapter: CarGroupAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_car
    }

    override fun initEventAndData() {
        super.initEventAndData()


        val datas = ArrayList<GoodsBean>()
        datas.add(GoodsBean())
        datas.add(GoodsBean())

        mAdapter = CarGroupAdapter(mActivity, datas)
        rv1!!.adapter = mAdapter
    }

    companion object {

        fun newInstance(): CarFragment {
            val fragment = CarFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
