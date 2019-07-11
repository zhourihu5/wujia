package com.jingxi.smartlife.pad.mvp.setting.view

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

import com.jingxi.smartlife.pad.R
import com.jingxi.smartlife.pad.mvp.home.contract.HomeContract
import com.jingxi.smartlife.pad.mvp.home.contract.HomePresenter
import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean
import com.jingxi.smartlife.pad.mvp.setting.adapter.HomeCardManagerAdapter
import com.wujia.businesslib.base.MvpFragment
import com.wujia.businesslib.event.EventBusUtil
import com.wujia.businesslib.event.EventCardChange
import com.wujia.businesslib.event.IMiessageInvoke
import com.wujia.lib_common.base.view.VerticallDecoration
import com.wujia.lib_common.data.network.exception.ApiException

import java.util.ArrayList

import butterknife.BindView
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_card_manager.*

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-19 23:56
 * description ：卡片管理
 */
class CardManagerFragment : MvpFragment<HomePresenter>(), HomeContract.View {


    private var addList: MutableList<HomeRecBean.Card>? = null
    private var unaddList: MutableList<HomeRecBean.Card>? = null
    private var addedAdapter: HomeCardManagerAdapter? = null
    private var unaddAdapter: HomeCardManagerAdapter? = null

    private val eventCardChange = EventCardChange(IMiessageInvoke { mPresenter.getUserQuickCard() })

    internal var isChanged = false
    internal var isUnregistered = false

    override fun getLayoutId(): Int {
        return R.layout.activity_card_manager
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用

        layout_right_btn!!.visibility = View.VISIBLE
        layout_back_btn!!.visibility = View.VISIBLE

        layout_title_tv!!.setText(R.string.manager_home_card)

        //        mPresenter.getUserQuickCard();
        mPresenter.getQuickCard()
        EventBusUtil.register(eventCardChange)

    }

    private fun setUserCard(list: ArrayList<HomeRecBean.Card>) {

        addList = list

        //已添加
        addedAdapter = HomeCardManagerAdapter(mContext, addList as ArrayList<HomeRecBean.Card>, HomeCardManagerAdapter.FORM_ADDED)
        rv_card_added!!.addItemDecoration(VerticallDecoration(24))

        rv_card_added!!.adapter = addedAdapter

        addedAdapter!!.setManagerCardListener(object : HomeCardManagerAdapter.OnManagerCardListener {
            override fun addCard(pos: Int) {

            }

            override fun removeCard(pos: Int) {
                isChanged = true
                mPresenter.removeUserQuickCard(addList!![pos].id)

                unaddList!!.add(addList!!.removeAt(pos))
                addedAdapter!!.notifyItemRemoved(pos)
                unaddAdapter!!.notifyItemInserted(unaddAdapter!!.itemCount)

                addedAdapter!!.notifyItemRangeChanged(0, addList!!.size)
                unaddAdapter!!.notifyItemRangeChanged(0, unaddList!!.size)
            }
        })
    }

    private fun setOtherCard(list: ArrayList<HomeRecBean.Card>) {

        unaddList = list

        addList?.let { unaddList!!.removeAll(it) }

        //未添加
        unaddAdapter = HomeCardManagerAdapter(mContext, unaddList as ArrayList<HomeRecBean.Card>, HomeCardManagerAdapter.FORM_UNADD)
        rv_card_unadd!!.addItemDecoration(VerticallDecoration(24))

        rv_card_unadd!!.adapter = unaddAdapter

        unaddAdapter!!.setManagerCardListener(object : HomeCardManagerAdapter.OnManagerCardListener {
            override fun addCard(pos: Int) {
                isChanged = true
                mPresenter.addUserQuickCard(unaddList!![pos].id)

                addList!!.add(unaddList!!.removeAt(pos))
                unaddAdapter!!.notifyItemRemoved(pos)
                addedAdapter!!.notifyItemInserted(addedAdapter!!.itemCount)

                addedAdapter!!.notifyItemRangeChanged(0, addList!!.size)
                unaddAdapter!!.notifyItemRangeChanged(0, unaddList!!.size)

            }

            override fun removeCard(pos: Int) {

            }
        })
    }

    @OnClick(R.id.layout_back_btn, R.id.layout_right_btn)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.layout_back_btn ->
                //                setFragmentResult(REQUEST_CODE_CARD_MANAGER_COMPLETE, null);
                pop()
            R.id.layout_right_btn ->
                //                setFragmentResult(REQUEST_CODE_CARD_MANAGER_COMPLETE, null);
                pop()
        }
    }

    override fun pop() {
        unRegisterCardChangeEvent()
        if (isChanged) {
            EventBusUtil.post(EventCardChange())
        }
        super.pop()
    }

    private fun unRegisterCardChangeEvent() {
        if (!isUnregistered) {
            EventBusUtil.unregister(eventCardChange)
            isUnregistered = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unRegisterCardChangeEvent()
    }

    override fun createPresenter(): HomePresenter {
        return HomePresenter()
    }

    override fun onDataLoadSucc(requestCode: Int, `object`: Any) {
        when (requestCode) {
            HomePresenter.REQUEST_CDOE_GET_CARD_OTHER -> {
                val cards = `object` as HomeRecBean
                if (cards.data != null && cards.data.size > 0) {
                    val toAddList = ArrayList<HomeRecBean.Card>()
                    for (card in cards.data) {
                        if ("NO" == card.userCards[0].isShow) {
                            toAddList.add(card)
                        }
                    }
                    cards.data.removeAll(toAddList)
                    setUserCard(cards.data)
                    setOtherCard(toAddList)
                }
            }
        }
    }

    override fun onDataLoadFailed(requestCode: Int, apiException: ApiException) {

    }

    companion object {

        val REQUEST_CODE_CARD_MANAGER = 0X1001
        val REQUEST_CODE_CARD_MANAGER_COMPLETE = 0X1002

        fun newInstance(): CardManagerFragment {
            val fragment = CardManagerFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
