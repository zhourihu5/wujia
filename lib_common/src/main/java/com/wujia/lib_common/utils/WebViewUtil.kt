package com.wujia.lib_common.utils

import android.view.ViewGroup
import android.view.ViewParent
import android.webkit.WebView

object WebViewUtil {
    fun onDestroy(mWebView: WebView?) {
        if (mWebView != null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            val parent = mWebView.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(mWebView)
            }

            mWebView.stopLoading()
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mWebView.settings.javaScriptEnabled = false
            mWebView.clearHistory()
            mWebView.clearView()
            mWebView.removeAllViews()
            mWebView.destroy()

        }
    }

}
