package com.wujia.businesslib.base

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.TextView

import com.wujia.businesslib.R
import com.wujia.businesslib.TitleFragment
import com.wujia.lib_common.base.Constants
import com.wujia.lib_common.utils.WebViewUtil


/**
 * Author: created by shenbingkai on 2019/2/11 18 30
 * Email:  shenbingkai@gamil.com
 * Description: webview
 */
class WebViewFragment : TitleFragment(), View.OnClickListener {

    private var mWebView: WebView? = null
    private var progressBar: ProgressBar? = null

    override val title: Int
        get() = R.string._empty

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_webview
    }

    override fun initEventAndData() {
        super.initEventAndData()

        mWebView = `$`<WebView>(R.id.webview)
        progressBar = `$`<ProgressBar>(R.id.web_progress)
        val layoutBackBtn = `$`<TextView>(R.id.layout_back_btn)
        val layoutRightBtn = `$`<TextView>(R.id.layout_right_btn)

        val url = arguments!!.getString(Constants.ARG_PARAM_1)
        if (TextUtils.isEmpty(url))
            return

        layoutRightBtn.setText("关闭")
        layoutRightBtn.visibility = View.VISIBLE
        mWebView!!.loadUrl(url)

        //        mWebView.setBackgroundColor(0);
        //        mWebView.getBackground().setAlpha(0);

        //        if (!NetWorkUtil.hasNet) {
        //            ToastUtils.showToast("网络未连接");
        //            return;
        //        }

        val settings = mWebView!!.settings
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

        mWebView!!.webChromeClient = WebChromeClient()
        mWebView!!.webViewClient = WebViewClient()


        layoutBackBtn.setOnClickListener(this)
        layoutRightBtn.setOnClickListener(this)
    }


    //    @Override
    //    public boolean onBackPressedSupport() {
    //
    //        if (mWebView.canGoBack()) {
    //            mWebView.goBack();
    //        } else {
    //            pop();
    //        }
    //        return true;
    //    }


    override fun onPause() {
        mWebView!!.onPause()
        super.onPause()
    }

    override fun onResume() {
        mWebView!!.onResume()
        super.onResume()
    }

    override fun onDestroyView() {
        WebViewUtil.onDestroy(mWebView)
        super.onDestroyView()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.layout_right_btn) {
            pop()
        } else if (v.id == R.id.layout_back_btn) {

            if (mWebView!!.canGoBack()) {
                mWebView!!.goBack()
            } else {
                pop()
            }
        }
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
            if (null == progressBar)
                return
            progressBar!!.progress = newProgress
            if (newProgress == 100) {
                hideProgress()
            } else {
                showProgress()
            }
        }
    }

    private fun showProgress() {}

    private fun hideProgress() {}

    companion object {

        fun newInstance(url: String): WebViewFragment {
            val fragment = WebViewFragment()
            val args = Bundle()
            args.putString(Constants.ARG_PARAM_1, url)
            fragment.arguments = args
            return fragment
        }
    }
}
