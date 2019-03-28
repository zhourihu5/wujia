package com.wujia.intellect.terminal.mvp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wujia.businesslib.TitleFragment;
import com.wujia.intellect.terminal.R;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Author: created by shenbingkai on 2019/2/11 18 30
 * Email:  shenbingkai@gamil.com
 * Description: webview
 */
public class WebViewFragment extends TitleFragment {

    public static final String URL = "KEY_URL";
    private String url = "https://www.jianshu.com/p/dd99a10ef792";
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.web_progress)
    ProgressBar progressBar;
    @BindView(R.id.layout_back_btn)
    TextView layoutBackBtn;
    @BindView(R.id.layout_right_btn)
    TextView layoutRightBtn;

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

        url = getArguments().getString(URL);


        layoutRightBtn.setText("关闭");
        layoutRightBtn.setVisibility(View.VISIBLE);
        webView.loadUrl(url);

//        webView.setBackgroundColor(0);
//        webView.getBackground().setAlpha(0);

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


        layoutBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    pop();
                }
            }
        });
    }


//    @Override
//    public boolean onBackPressedSupport() {
//
//        if (webView.canGoBack()) {
//            webView.goBack();
//        } else {
//            pop();
//        }
//        return true;
//    }

    @OnClick(R.id.layout_right_btn)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_right_btn:
                pop();
                break;
        }
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
