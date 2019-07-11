package com.jingxi.smartlife.pad.mvp.home

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.TextView
import com.jingxi.smartlife.pad.R
import com.jingxi.smartlife.pad.market.mvp.adapter.FindServiceChildAdapter
import com.jingxi.smartlife.pad.market.mvp.view.ServiceBaseFragment
import com.jingxi.smartlife.pad.mvp.home.contract.HomeModel
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.data.CardDetailBean
import com.wujia.businesslib.model.BusModel
import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.BaseView
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException
import com.wujia.lib_common.utils.WebViewUtil
import kotlinx.android.synthetic.main.fragment_img_txt.*
import java.util.*


/**
 * author ：shenbingkai@163.com
 * date ：2019-03-29
 * description ：
 */
class ImageTxtFragment : ServiceBaseFragment<BasePresenter<BaseView>>() {
    //    public static final String KEY_SUBCRIPTION = "subscriptions";
    private var mModel: HomeModel? = null

    private var datas: ArrayList<CardDetailBean.ServicesBean>? = null
    private var mAdapter: FindServiceChildAdapter? = null

    internal lateinit var busModel: BusModel

    override fun getLayoutId(): Int {
        return R.layout.fragment_img_txt
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        val cardId = arguments!!.getString(KEY_TXT)

        val rv1 = `$`<RecyclerView>(R.id.rv1)
        val tvTitle = `$`<TextView>(R.id.layout_title_tv)
        val btnBack = `$`<TextView>(R.id.layout_back_btn)

        btnBack.visibility = View.VISIBLE
        tvTitle.text = "详情"
        btnBack.setOnClickListener { pop() }

        webview!!.setBackgroundColor(0)
        webview!!.background.alpha = 0


        val settings = webview!!.settings
        settings.allowUniversalAccessFromFileURLs = true
        settings.allowFileAccessFromFileURLs = true

        settings.useWideViewPort = false// 设置此属性，可任意比例缩放
        settings.loadWithOverviewMode = true
        settings.loadsImagesAutomatically = true // 支持自动加载图片
        settings.setAppCacheEnabled(true)
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.allowFileAccess = true
        //        settings.setBlockNetworkImage(true);
        settings.cacheMode = WebSettings.LOAD_DEFAULT

        webview!!.webChromeClient = WebChromeClient()
        webview!!.webViewClient = WebViewClient()

        datas = ArrayList()

        busModel = BusModel()
        mAdapter = getAdapter(datas)
        rv1.adapter = mAdapter

        mModel = HomeModel()
        getData(cardId)

    }


    private fun getData(cardId: String?) {

        addSubscribe(cardId?.let {
            mModel!!.getCardDetail(it).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<CardDetailBean>>(this, SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<CardDetailBean>) {
                super.onResponse(response)
                val txt = response.data.content
                webview!!.loadData(txt, "text/html; charset=UTF-8", null)
                datas!!.clear()
                if (response.data.services != null) {
                    datas!!.addAll(response.data.services)
                }
                mAdapter!!.notifyDataSetChanged()
            }

            override fun onFailed(apiException: ApiException) {
                super.onFailed(apiException)
            }
        })
        })

    }

    override fun onDestroyView() {
        WebViewUtil.onDestroy(webview)
        super.onDestroyView()
    }


    inner class WebViewClient : android.webkit.WebViewClient() {
        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
        // 而不是新开Android的系统browser中响应该链接，必须覆盖 webview的WebViewClient对象。
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    inner class WebChromeClient : android.webkit.WebChromeClient() {

        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }
    }

    override fun createPresenter(): BasePresenter<BaseView>? {
        return null
    }

    companion object {

        val KEY_TXT = "txt"

        fun newInstance(cardId: String): ImageTxtFragment {
            val fragment = ImageTxtFragment()
            val args = Bundle()
            args.putString(KEY_TXT, cardId)

            fragment.arguments = args
            return fragment
        }
    }
}
