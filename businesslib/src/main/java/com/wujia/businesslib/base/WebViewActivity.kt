package com.wujia.businesslib.base

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.TextView

import com.wujia.businesslib.R
import com.wujia.lib_common.base.BaseActivity
import com.wujia.lib_common.base.Constants
import com.wujia.lib_common.utils.WebViewUtil

/**
 * author ：shenbingkai@163.com
 * date ：2019-04-13
 * description ：
 */
class WebViewActivity : BaseActivity(), View.OnClickListener {
    override val layout: Int
        get() = R.layout.layout_webview
    private var mWebView: WebView? = null
    private var progressBar: ProgressBar? = null


    override fun initEventAndData(savedInstanceState: Bundle?) {

        val url = intent.getStringExtra(Constants.INTENT_KEY_1)

        mWebView = `$`(R.id.webview)
        progressBar = `$`(R.id.web_progress)
        val layoutBackBtn = `$`<TextView>(R.id.layout_back_btn)
        val layoutRightBtn = `$`<TextView>(R.id.layout_right_btn)

        if (TextUtils.isEmpty(url))
            return

        layoutRightBtn.text = "关闭"
        layoutBackBtn.visibility = View.VISIBLE
        layoutRightBtn.visibility = View.VISIBLE
        if (url != null) {
            mWebView!!.loadUrl(url)
        }

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

    public override fun onPause() {
        mWebView!!.onPause()
        super.onPause()
    }

    public override fun onResume() {
        mWebView!!.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        WebViewUtil.onDestroy(mWebView)
        super.onDestroy()
        android.os.Process.killProcess(android.os.Process.myPid())//webview 内存泄漏，单独放一个进程
    }

    override fun onClick(v: View) {
        if (v.id == R.id.layout_right_btn) {
            finish()
        } else if (v.id == R.id.layout_back_btn) {

            if (mWebView!!.canGoBack()) {
                mWebView!!.goBack()
            } else {
                finish()
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
}
