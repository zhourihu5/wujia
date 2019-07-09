package com.wujia.businesslib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wujia.lib_common.base.Constants;
import com.wujia.businesslib.R;
import com.wujia.businesslib.TitleFragment;
import com.wujia.lib_common.utils.WebViewUtil;


/**
 * Author: created by shenbingkai on 2019/2/11 18 30
 * Email:  shenbingkai@gamil.com
 * Description: webview
 */
public class WebViewFragment extends TitleFragment implements View.OnClickListener {

    private WebView mWebView;
    private ProgressBar progressBar;

    public WebViewFragment() {

    }

    public static WebViewFragment newInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM_1, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        mWebView = $(R.id.webview);
        progressBar = $(R.id.web_progress);
        TextView layoutBackBtn = $(R.id.layout_back_btn);
        TextView layoutRightBtn = $(R.id.layout_right_btn);

        String url = getArguments().getString(Constants.ARG_PARAM_1);
        if (TextUtils.isEmpty(url))
            return;

        layoutRightBtn.setText("关闭");
        layoutRightBtn.setVisibility(View.VISIBLE);
        mWebView.loadUrl(url);

//        mWebView.setBackgroundColor(0);
//        mWebView.getBackground().setAlpha(0);

//        if (!NetWorkUtil.hasNet) {
//            ToastUtils.showToast("网络未连接");
//            return;
//        }

        WebSettings settings = mWebView.getSettings();
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

        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());


        layoutBackBtn.setOnClickListener(this);
        layoutRightBtn.setOnClickListener(this);
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


    @Override
    public void onPause() {
        mWebView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mWebView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        WebViewUtil.onDestroy(mWebView);
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layout_right_btn) {
            pop();
        } else if (v.getId() == R.id.layout_back_btn) {

            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                pop();
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
