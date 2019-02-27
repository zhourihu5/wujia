package com.wujia.intellect.terminal.mvp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.wujia.businesslib.TitleFragment;
import com.wujia.intellect.terminal.R;

import butterknife.BindView;


/**
 * Author: created by shenbingkai on 2019/2/11 18 30
 * Email:  shenbingkai@gamil.com
 * Description: webview
 */
public class WebViewFragment extends TitleFragment {

    public static final String URL = "KEY_URL";
    private String url = "https://www.baidu.com";
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.web_progress)
    ProgressBar progressBar;

    public WebViewFragment() {

    }

    public static WebViewFragment newInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        url = savedInstanceState.getString(URL);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_webview;
    }

    @Override
    public int getTitle() {
        return R.string._empty;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        webView.loadUrl(url);

        webView.setBackgroundColor(0);
        webView.getBackground().setAlpha(0);

//        if (!NetWorkUtil.hasNet) {
//            ToastUtils.showToast("网络未连接");
//            return;
//        }

        WebSettings settings = webView.getSettings();
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowFileAccessFromFileURLs(true);

        settings.setUseWideViewPort(false);// 设置此属性，可任意比例缩放
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true); // 支持自动加载图片
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);


        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
    }


    @Override
    public boolean onBackPressedSupport() {

        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            pop();
        }
        return true;
    }

    @Override
    public void onPause() {
        webView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        webView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        if (null != webView)
            webView.destroy();
        super.onDestroyView();
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
        // 而不是新开Android的系统browser中响应该链接，必须覆盖 webview的WebViewClient对象。
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (null == progressBar)
                return;
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                hideProgress();
            } else {
                showProgress();
            }
        }
    }

    private void showProgress() {
    }

    private void hideProgress() {
    }
}
