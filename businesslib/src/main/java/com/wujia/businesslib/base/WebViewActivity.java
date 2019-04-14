package com.wujia.businesslib.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wujia.businesslib.Constants;
import com.wujia.businesslib.R;
import com.wujia.lib_common.base.BaseActivity;

/**
 * author ：shenbingkai@163.com
 * date ：2019-04-13
 * description ：
 */
public class WebViewActivity extends BaseActivity implements View.OnClickListener {

    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected int getLayout() {
        return R.layout.layout_webview;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

        String url = getIntent().getStringExtra(Constants.INTENT_KEY_1);

        webView = $(R.id.webview);
        progressBar = $(R.id.web_progress);
        TextView layoutBackBtn = $(R.id.layout_back_btn);
        TextView layoutRightBtn = $(R.id.layout_right_btn);

        if (TextUtils.isEmpty(url))
            return;

        layoutRightBtn.setText("关闭");
        layoutBackBtn.setVisibility(View.VISIBLE);
        layoutRightBtn.setVisibility(View.VISIBLE);
        webView.loadUrl(url);

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
//        settings.setBlockNetworkImage(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.setWebChromeClient(new WebViewActivity.WebChromeClient());
        webView.setWebViewClient(new WebViewActivity.WebViewClient());

        layoutBackBtn.setOnClickListener(this);
        layoutRightBtn.setOnClickListener(this);
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
    protected void onDestroy() {
        if (null != webView)
            webView.destroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layout_right_btn) {
            finish();
        } else if (v.getId() == R.id.layout_back_btn) {

            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
        }
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
