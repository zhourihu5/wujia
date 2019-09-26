package com.jingxi.smartlife.pad.property.mvp.view

import android.os.Bundle
import com.jingxi.smartlife.pad.property.R
import com.jingxi.smartlife.pad.property.mvp.adapter.WyOtherTelAdapter
import com.jingxi.smartlife.pad.property.mvp.adapter.WyTelGroupAdapter
import com.jingxi.smartlife.pad.property.mvp.data.WyChildBean
import com.jingxi.smartlife.pad.property.mvp.data.WySectionBean
import com.wujia.lib_common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_propery_tel.*
import java.util.*

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
class TelFragment : BaseFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_propery_tel


    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)


        //        layoutManager.setSmoothScrollbarEnabled(true);
        //        layoutManager.setAutoMeasureEnabled(true);
        rv1.setHasFixedSize(true)
        rv1.isNestedScrollingEnabled = false

        rv2.setHasFixedSize(true)
        rv2.isNestedScrollingEnabled = false

        val datas = ArrayList<WySectionBean>()
        datas.add(WySectionBean())
        datas.add(WySectionBean())
        datas.add(WySectionBean())
        datas.add(WySectionBean())

        rv1.adapter = WyTelGroupAdapter(mActivity, datas)


        val others = ArrayList<WyChildBean>()
        others.add(WyChildBean())
        others.add(WyChildBean())
        others.add(WyChildBean())
        others.add(WyChildBean())
        others.add(WyChildBean())
        rv2.adapter = WyOtherTelAdapter(mActivity, others)
    }

    companion object {

        fun newInstance(): TelFragment {
            val fragment = TelFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
