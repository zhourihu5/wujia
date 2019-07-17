package com.jingxi.smartlife.pad.mvp.home

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import butterknife.OnClick
import com.jingxi.smartlife.pad.R
import com.jingxi.smartlife.pad.mvp.MainActivity
import com.jingxi.smartlife.pad.mvp.home.adapter.HomeCardAdapter
import com.jingxi.smartlife.pad.mvp.home.adapter.HomeMemberAdapter
import com.jingxi.smartlife.pad.mvp.home.adapter.HomeNotifyAdapter
import com.jingxi.smartlife.pad.mvp.home.contract.HomeContract
import com.jingxi.smartlife.pad.mvp.home.contract.HomePresenter
import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean
import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean
import com.jingxi.smartlife.pad.mvp.home.data.WeatherInfoBean
import com.jingxi.smartlife.pad.mvp.home.view.AddMemberDialog
import com.jingxi.smartlife.pad.mvp.home.view.MessageDialog
import com.jingxi.smartlife.pad.mvp.setting.model.FamilyMemberModel
import com.jingxi.smartlife.pad.mvp.setting.view.CardManagerFragment
import com.wujia.businesslib.base.DataManager
import com.wujia.businesslib.base.MvpFragment
import com.wujia.businesslib.base.WebViewFragment
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.data.MsgDto
import com.wujia.businesslib.event.*
import com.wujia.businesslib.listener.OnInputDialogListener
import com.wujia.businesslib.model.BusModel
import com.wujia.businesslib.util.LoginUtil
import com.wujia.lib.imageloader.ImageLoaderManager
import com.wujia.lib.widget.util.ToastUtil
import com.wujia.lib_common.base.view.HomeCardDecoration
import com.wujia.lib_common.base.view.HorizontalDecoration
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException
import com.wujia.lib_common.utils.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ： home
 */
class HomeHomeFragment : MvpFragment<HomePresenter>(), HomeContract.View {

    private var homeCardAdapter: HomeCardAdapter? = null
    private var batterReceiver: BatteryReceiver? = null
    private var networkReceiver: NetworkChangeReceiver? = null

    private var memAdapter: HomeMemberAdapter? = null
    private var cards: ArrayList<HomeRecBean.Card>? = null

    private var isRefreshUserData=true
    private var isRefreshCard = true
    private var isRefreshWeather = true

    private val eventSafeState = EventSafeState(object : IMiessageInvoke<EventSafeState> {
        override fun eventBus(event: EventSafeState) {
            home_arc_view!!.text = if (event.online) "正常\n" else "异常\n"
            home_arc_view!!.setColor(if (event.online) R.color.colorAccent else R.color.cdd6767)
        }
    })
    private val eventMsg = EventMsg(object : IMiessageInvoke<EventMsg> {
        override fun eventBus(event: EventMsg) {
            setNotify(false)
        }
    })
    private val eventCardChange = EventCardChange(object : IMiessageInvoke<EventCardChange> {
        override fun eventBus(event: EventCardChange) {
            isRefreshCard = true
            if (isVisible) {
                mPresenter?.getUserQuickCard()
            }
        }
    })
    private val eventMemberChange = EventMemberChange(object : IMiessageInvoke<EventMemberChange> {
        override fun eventBus(event: EventMemberChange) {
            var familyId: String? = null
            try {
                familyId = DataManager.familyId
            } catch (e: Exception) {
                LogUtil.t("get familyId failed", e)
                LoginUtil.toLoginActivity()
                return
            }

            addSubscribe(FamilyMemberModel().getFamilyMemberList(familyId!!).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<List<HomeUserInfoBean.DataBean.UserInfoListBean>>>(this@HomeHomeFragment, ActionConfig(false, SHOWERRORMESSAGE)) {
                override fun onResponse(response: ApiResponse<List<HomeUserInfoBean.DataBean.UserInfoListBean>>) {
                    super.onResponse(response)
                    response.data?.let { memAdapter!!.setmDatas(it) }
                    memAdapter!!.notifyDataSetChanged()
                }

            }))
        }
    })

    override val layoutId: Int
        get(){
            return R.layout.fragment_home
        }


    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        LogUtil.i("homehome onLazyInitView")

        FontUtils.changeFontTypeface(home_weather_num_tv, FontUtils.Font_TYPE_EXTRA_LIGHT)

        home_date_tv!!.text = StringUtil.format(getString(R.string.s_s), DateUtil.currentDate, DateUtil.currentWeekDay)

        setCardView()

        setNotify(true)

        setState()

        rv_home_member!!.addItemDecoration(HorizontalDecoration(10))
        memAdapter = HomeMemberAdapter(context!!, ArrayList())
        rv_home_member!!.adapter = memAdapter

        EventBusUtil.register(eventSafeState)
        EventBusUtil.register(eventMsg)
        EventBusUtil.register(eventCardChange)
        EventBusUtil.register(eventMemberChange)
    }

    private fun setCardView() {

        cards = ArrayList()
        homeCardAdapter = HomeCardAdapter(mActivity, cards!!)
        rv_home_card!!.addItemDecoration(HomeCardDecoration(68))
        rv_home_card!!.adapter = homeCardAdapter
        homeCardAdapter!!.setOnItemClickListener { adapter, holder, position ->
            val card = cards!![position]
            when (card.type) {
                HomeRecBean.TYPE_LINK -> card.linkUrl?.let { start(WebViewFragment.newInstance(it)) }

                HomeRecBean.TYPE_APP_PAGE -> card.linkUrl?.let { parseLinkUrl(it) }

                HomeRecBean.TYPE_IMAGE -> start(card.id?.let { ImageTxtFragment.newInstance(it) })

                HomeRecBean.TYPE_ADD -> start(CardManagerFragment.newInstance())
            }
        }
    }

    private fun parseLinkUrl(url: String) {
        when (url) {
            "FWSC_WDFW" -> (mActivity as MainActivity).switchHomeTab(MainActivity.POSITION_MARKET, 0)

            "WYFW_BSBX" -> (mActivity as MainActivity).switchHomeTab(MainActivity.POSITION_PROPERTY, 0)

            "XXTZ_QBXX" -> (mActivity as MainActivity).switchHomeTab(MainActivity.POSITION_MESSAGE, 0)

            "ZNJJ_SY" -> (mActivity as MainActivity).switchHomeTab(MainActivity.POSITION_FAMILY, 0)
            "KSAF_SW" -> (mActivity as MainActivity).switchHomeTab(MainActivity.POSITION_SAFE, 0)
        }
    }

    private fun setNotify(isShowLoadingDialog: Boolean) {
        val familyId = try {
            DataManager.familyId
        } catch (e: Exception) {
            e.printStackTrace()
            LoginUtil.toLoginActivity()
            return
        }
        addSubscribe(BusModel().getTop3UnReadMsg(familyId!!).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<List<MsgDto.ContentBean>>>(this@HomeHomeFragment, ActionConfig(isShowLoadingDialog, SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<List<MsgDto.ContentBean>>) {
                super.onResponse(response)
                val notifys = response.data
                notifys?.let {
                    val notifyAdapter = HomeNotifyAdapter(mActivity, notifys)
                    rv_home_msg!!.adapter = notifyAdapter
                    notifyAdapter.setOnItemClickListener { adapter, holder, position ->
                        MessageDialog(mContext!!, notifys[position])
                                .setListener { item ->
                                    addSubscribe(BusModel().readMsg(item.id.toString() + "").subscribeWith(object : SimpleRequestSubscriber<ApiResponse<Any>>(this@HomeHomeFragment, ActionConfig(true, SHOWERRORMESSAGE)) {
                                        override fun onResponse(response: ApiResponse<Any>) {
                                            super.onResponse(response)
                                            item.isRead = MsgDto.STATUS_READ
                                            //                                                setNotify();//在消息里处理了
                                            EventBusUtil.post(EventMsg(EventMsg.TYPE_READ))
                                        }
                                    }))
                                }
                                .show()
                    } }

            }
        }))


    }

    private fun setState() {
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        batterReceiver = BatteryReceiver(home_batter_img)
        networkReceiver = NetworkChangeReceiver(home_wifi_img)

        mActivity.registerReceiver(batterReceiver, filter)
        mActivity.registerReceiver(networkReceiver, filter)

    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        LogUtil.i("Homehome onSupportVisible")
        getDataIfNeeded()
    }

    private fun getDataIfNeeded() {
        if (isRefreshCard) {
            mPresenter?.getUserQuickCard()
        }
        if (isRefreshUserData) {
            mPresenter?.getHomeUserInfo(SystemUtil.getSerialNum()!!)
        }
        if (isRefreshWeather) {
            mPresenter?.getWeather()
        }
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        LogUtil.i("HomeHomeFragment  不可见")
    }

    @OnClick(R.id.home_chat_btn, R.id.home_call_service_btn, R.id.home_member_add_btn, R.id.home_arc_view)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.home_chat_btn -> ToastUtil.showShort(mContext, getString(R.string.chat_is_developing))
            R.id.home_member_add_btn -> memAdapter!!.datas?.let {
                AddMemberDialog(mActivity, it).setListener(object : OnInputDialogListener {
                    override fun dialogSureClick(input: String) {
                        var familyId: String? = null
                        try {
                            familyId = DataManager.familyId
                        } catch (e: Exception) {
                            LoginUtil.toLoginActivity()
                            LogUtil.t("get familyid failed", e)
                            return
                        }

                        addSubscribe(FamilyMemberModel().addFamilyMember(input, familyId!!).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<String>>(this@HomeHomeFragment, ActionConfig(true, SHOWERRORMESSAGE)) {
                            override fun onResponse(response: ApiResponse<String>) {
                                super.onResponse(response)
                                val userInfoListBean = HomeUserInfoBean.DataBean.UserInfoListBean()
                                userInfoListBean.userName = input
                                (memAdapter!!.datas as MutableList).add(userInfoListBean)
                                memAdapter!!.notifyDataSetChanged()
                            }

                        }))
                    }
                }).show()
            }
            R.id.home_call_service_btn ->
                //                new CallDialog(mContext).show();
                ToastUtil.showShort(mContext, getString(R.string.not_join))

            R.id.home_arc_view -> {
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onDataLoadSucc(requestCode: Int, `object`: Any) {
        when (requestCode) {
            HomePresenter.REQUEST_CDOE_GET_CARD_MY -> {
                isRefreshCard = false
                val homeRecBean = `object` as HomeRecBean
                cards!!.clear()
                homeRecBean.data?.let { cards!!.addAll(it) }
                cards!!.add(HomeRecBean.Card(HomeRecBean.TYPE_ADD))
                homeCardAdapter!!.notifyDataSetChanged()
            }

            HomePresenter.REQUEST_CDOE_HOME_USER -> {
                isRefreshUserData=false
                val homeUserInfoBean = `object` as HomeUserInfoBean

                home_room_tv!!.text = homeUserInfoBean.data?.communtity?.name

                homeUserInfoBean.data?.userInfoList?.let { memAdapter!!.setmDatas(it) }
                memAdapter!!.notifyDataSetChanged()
            }
            HomePresenter.REQUEST_CDOE_WEATHER -> {
                isRefreshWeather=false
                val weatherInfoBean = `object` as WeatherInfoBean
                val dataBean = weatherInfoBean.data
                dataBean?.token?.let { DataManager.saveToken(it) }
                try {
                    home_car_num_tv!!.text = String.format("今日限行：%s", dataBean?.restrict?.num?:"暂无数据")
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                val curdate = DateUtil.currentyyyymmddhh + "00"

                var weatherList: List<WeatherInfoBean.DataBean.WeatherBean.ShowapiResBodyBean.HourListBean>? = null
                try {
                    weatherList = dataBean!!.weather!!.showapi_res_body!!.hourList
                } catch (e: Exception) {
                    //                    e.printStackTrace();
                }

                if (weatherList != null) {
                    for (weather in weatherList) {
                        if (weather.time == curdate) {
                            home_weather_num_tv!!.text = weather.temperature + "°"
                            home_weather_desc_tv!!.text = weather.weather
                            ImageLoaderManager.getInstance().loadImage(weather.weather_code, ivWeather)
                        }
                    }
                }
            }
        }
    }


    override fun onDataLoadFailed(requestCode: Int, apiException: ApiException) {

    }


    override fun onDestroyView() {
        mActivity.apply {
            batterReceiver?.let { unregisterReceiver(batterReceiver) }
            networkReceiver?.let {unregisterReceiver(networkReceiver) }
        }
        super.onDestroyView()
    }


    /**
     * 监听获取手机系统剩余电量
     */
    internal inner class BatteryReceiver(private val icon: ImageView) : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val current = intent.extras!!.getInt("level")// 获得当前电量
            val total = intent.extras!!.getInt("scale")// 获得总电量
            val percent = current * 100 / total
            icon.drawable.level = if (percent < 20) 1 else 0
            LogUtil.i(" == level == $current")
            LogUtil.i(" == scale == $total")
        }
    }

    internal inner class NetworkChangeReceiver(private val icon: ImageView) : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            icon.drawable.level=if (NetworkUtil.getNetWork(context))  0 else  1
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBusUtil.unregister(eventSafeState)
        EventBusUtil.unregister(eventMsg)
        EventBusUtil.unregister(eventCardChange)
        EventBusUtil.unregister(eventMemberChange)
    }

    override fun createPresenter(): HomePresenter {
        return HomePresenter()
    }

    companion object {

        fun newInstance(): HomeHomeFragment {
            val fragment = HomeHomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
