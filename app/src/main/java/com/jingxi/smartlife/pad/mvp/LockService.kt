package com.jingxi.smartlife.pad.mvp

import android.content.Context
import android.content.Intent
import android.service.dreams.DreamService
import android.text.TextUtils
import android.view.View
import com.jingxi.smartlife.pad.R
import com.jingxi.smartlife.pad.mvp.home.adapter.HomeNotifyAdapter
import com.jingxi.smartlife.pad.mvp.home.contract.HomeContract
import com.jingxi.smartlife.pad.mvp.home.contract.HomeModel
import com.jingxi.smartlife.pad.mvp.home.data.LockADBean
import com.jingxi.smartlife.pad.mvp.home.data.WeatherInfoBean
import com.wujia.businesslib.base.DataManager
import com.wujia.businesslib.base.WebViewActivity
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.data.MsgDto
import com.wujia.businesslib.event.EventBusUtil
import com.wujia.businesslib.event.EventMsg
import com.wujia.businesslib.event.EventWakeup
import com.wujia.businesslib.event.IMiessageInvoke
import com.wujia.businesslib.model.BusModel
import com.wujia.businesslib.util.LoginUtil
import com.wujia.lib.imageloader.ImageLoaderManager
import com.wujia.lib_common.base.Constants
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException
import com.wujia.lib_common.utils.DateUtil
import com.wujia.lib_common.utils.FontUtils
import com.wujia.lib_common.utils.StringUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_lock.*
import java.util.concurrent.TimeUnit

/**
 * author ：shenbingkai@163.com
 * date ：2019-04-09
 * description ：
 */
class LockService : DreamService(), HomeContract.View ,LayoutContainer{
    override val containerView: View?
        get() =window.decorView

//    private var login_time_tv: TextView? = null
//    private var login_time_date_tv: TextView? = null
//    private var login_temperature_tv: TextView? = null
//    private var login_temperature_desc: TextView? = null
//    private var ivWeather: ImageView? = null
//    private var btn_details: TextView? = null
//    private var rv_home_msg: RecyclerView? = null
//    private var lock_img_bg: ImageView? = null


    private var model: HomeModel? = null


    private val mCompositeDisposable = CompositeDisposable()

    private val eventMsg = EventMsg(IMiessageInvoke { event ->
        if (event.type == EventMsg.TYPE_NEW_MSG) {
            setNotify()
        }
    })
    private val eventWakeup = EventWakeup(IMiessageInvoke { wakeUp() })

    internal var busModel: BusModel? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        isInteractive = true
        isFullscreen = true
        setContentView(R.layout.activity_lock)
        init()
        EventBusUtil.register(eventMsg)
        EventBusUtil.register(eventWakeup)

    }

    private fun init() {

//        login_time_date_tv = findViewById(R.id.login_time_date_tv)
//        login_time_tv = findViewById(R.id.login_time_tv)
//        login_temperature_tv = findViewById(R.id.login_temperature_tv)
//        ivWeather = findViewById(R.id.ivWeather)
//        login_temperature_desc = findViewById(R.id.login_temperature_desc)
//        rv_home_msg = findViewById(R.id.rv_home_msg)
//        lock_img_bg = findViewById(R.id.lock_img_bg)
//        btn_details = findViewById(R.id.btn_details)

        lock_img_bg!!.setOnClickListener { wakeUp() }


        FontUtils.changeFontTypeface(login_time_tv, FontUtils.Font_TYPE_EXTRA_LIGHT)
        FontUtils.changeFontTypeface(login_temperature_tv, FontUtils.Font_TYPE_EXTRA_LIGHT)

        if (model == null) {
            model = HomeModel()
        }

        setScreenBg()

        setWeather()

        setTime()

        setNotify()
        //        testWakeup();//todo just for test

    }

    private fun testWakeup() {//todo just for test
        btn_details!!.visibility = View.VISIBLE
        btn_details!!.setOnClickListener {
            val intent = Intent(this@LockService, WebViewActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra(Constants.INTENT_KEY_1, "www.baidu.com")
            startActivity(intent)
            wakeUp()
        }
    }

    private fun setScreenBg() {
        mCompositeDisposable.add(model!!.screenSaverByCommunityId.subscribeWith(object : SimpleRequestSubscriber<LockADBean>(this@LockService, SimpleRequestSubscriber.ActionConfig(false, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(bean: LockADBean) {
                super.onResponse(bean)
                if (bean.data != null) {
                    if (lock_img_bg != null) {
                        val ad = bean.data
                        if (ad != null) {
                            ImageLoaderManager.getInstance().loadImage(ad.image, R.mipmap.bg_lockscreen, lock_img_bg)
                        }
                        btn_details!!.visibility = View.VISIBLE
                        btn_details!!.setOnClickListener {
                            if (ad != null) {
                                if (!TextUtils.isEmpty(ad.url)) {
                                    val intent = Intent(this@LockService, WebViewActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    intent.putExtra(Constants.INTENT_KEY_1, ad.url)
                                    startActivity(intent)
                                    wakeUp()
                                }
                            }
                        }
                    }
                }
            }

            override fun onFailed(apiException: ApiException) {
                super.onFailed(apiException)
                onDataLoadFailed(0, apiException)
            }
        }))
    }

    //时间
    private fun setTime() {
        login_time_date_tv!!.text = StringUtil.format(getString(R.string.s_s), DateUtil.getCurrentDate(), DateUtil.getCurrentWeekDay())
        login_time_tv!!.text = DateUtil.getCurrentTimeHHMM()

        mCompositeDisposable.add(Observable.interval(10, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (null != login_time_date_tv && null != login_time_tv) {
                        login_time_date_tv!!.text = StringUtil.format(getString(R.string.s_s), DateUtil.getCurrentDate(), DateUtil.getCurrentWeekDay())
                        login_time_tv!!.text = DateUtil.getCurrentTimeHHMM()
                    }
                })
    }

    //天气
    private fun setWeather() {
        mCompositeDisposable.add(Observable.interval(3, (60 * 60).toLong(), TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mCompositeDisposable.add(model!!.weather.subscribeWith(object : SimpleRequestSubscriber<WeatherInfoBean>(this@LockService, SimpleRequestSubscriber.ActionConfig(false, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
                        override fun onResponse(weatherInfoBean: WeatherInfoBean) {
                            super.onResponse(weatherInfoBean)
                            if (weatherInfoBean.isSuccess) {

                                val curdate = DateUtil.getCurrentyyyymmddhh() + "00"
                                var weatherList: List<WeatherInfoBean.DataBean.WeatherBean.ShowapiResBodyBean.HourListBean>? = null
                                try {
                                    weatherList = weatherInfoBean.data.weather.showapi_res_body.hourList
                                } catch (e: Exception) {
                                    //                    e.printStackTrace();
                                }

                                if (weatherList != null) {
                                    for (weather in weatherList) {
                                        if (weather.time == curdate) {
                                            login_temperature_tv!!.text = weather.temperature + "°"
                                            login_temperature_desc!!.text = weather.weather
                                            ImageLoaderManager.getInstance().loadImage(weather.weather_code, ivWeather)
                                        }
                                    }
                                }
                            }
                        }

                        override fun onFailed(apiException: ApiException) {
                            super.onFailed(apiException)
                            onDataLoadFailed(0, apiException)
                        }
                    }))
                })

    }

    //消息
    private fun setNotify() {

        if (busModel == null) {
            busModel = BusModel()
        }
        var familyId = try {
            DataManager.getFamilyId()
        } catch (e: Exception) {
            e.printStackTrace()
            LoginUtil.toLoginActivity()
            return
        }

        mCompositeDisposable.add(busModel!!.getTop3UnReadMsg(familyId)!!.subscribeWith(object : SimpleRequestSubscriber<ApiResponse<List<MsgDto.ContentBean>>>(this, SimpleRequestSubscriber.ActionConfig(false, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<List<MsgDto.ContentBean>>) {
                super.onResponse(response)
                val notifys = response.data
                val notifyAdapter = HomeNotifyAdapter(this@LockService, notifys)
                rv_home_msg!!.adapter = notifyAdapter

                notifyAdapter.setOnItemClickListener { adapter, holder, position -> wakeUp() }
            }

            override fun onFailed(apiException: ApiException) {
                super.onFailed(apiException)
            }
        }))


    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mCompositeDisposable?.clear()
        EventBusUtil.unregister(eventMsg)
        EventBusUtil.unregister(eventWakeup)
    }

    override fun onDataLoadSucc(requestCode: Int, `object`: Any) {

    }

    override fun onDataLoadFailed(requestCode: Int, apiException: ApiException) {

    }

    override fun showErrorMsg(msg: String) {

    }

    override fun showLoadingDialog(text: String) {

    }

    override fun hideLoadingDialog() {

    }

    override fun getContext(): Context? {
        return null
    }

    override fun onLoginStatusError() {

    }
}