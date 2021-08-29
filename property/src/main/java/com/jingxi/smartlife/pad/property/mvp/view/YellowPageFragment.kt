package com.jingxi.smartlife.pad.property.mvp.view

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jingxi.smartlife.pad.property.mvp.model.YellowPageModel
import com.jingxi.smartlife.pad.property.R
import com.jingxi.smartlife.pad.property.mvp.adapter.YellowPageAdapter
import com.jingxi.smartlife.pad.property.mvp.data.YellowPage
import com.wujia.businesslib.base.DataManager
import com.wujia.businesslib.base.MvpFragment
import com.wujia.businesslib.data.ApiResponse
import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.BaseView
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException
import com.wujia.lib_common.utils.LogUtil
import kotlinx.android.synthetic.main.fragment_yellow_page.*


/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
class YellowPageFragment :  MvpFragment<BasePresenter<BaseView>>() {
    override fun createPresenter(): BasePresenter<BaseView>? {
        return null
    }

    override val layoutId: Int
        get() = R.layout.fragment_yellow_page

    var adapter:YellowPageAdapter?=null

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        addSubscribe(YellowPageModel().getYellowPageList(DataManager.communtityId!!).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<List<YellowPage>>>(this@YellowPageFragment, ActionConfig(true, SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<List<YellowPage>>) {
                super.onResponse(response)
                adapter= YellowPageAdapter(context!!,response.data!!)
                LogUtil.e("response.data!!"+response.data!!)
                rvYellowPg.layoutManager=LinearLayoutManager(context)
                rvYellowPg.adapter=adapter

            }

        }))

    }


    companion object {

        fun newInstance(): YellowPageFragment {
            val fragment = YellowPageFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
