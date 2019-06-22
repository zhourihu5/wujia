package com.jingxi.smartlife.pad.mvp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.jingxi.smartlife.pad.R;
import com.jingxi.smartlife.pad.market.mvp.adapter.FindServiceChildAdapter;
import com.jingxi.smartlife.pad.market.mvp.view.ServiceBaseFragment;
import com.jingxi.smartlife.pad.mvp.home.contract.HomeModel;
import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.data.CardDetailBean;
import com.wujia.businesslib.model.BusModel;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.data.network.SimpleRequestSubscriber;
import com.wujia.lib_common.data.network.exception.ApiException;

import java.util.ArrayList;


/**
 * author ：shenbingkai@163.com
 * date ：2019-03-29
 * description ：
 */
public class ImageTxtFragment extends ServiceBaseFragment {

    public static final String KEY_TXT = "txt";
//    public static final String KEY_SUBCRIPTION = "subscriptions";
    private HomeModel mModel;

    private ArrayList<CardDetailBean.ServicesBean> datas;
    private WebView webView;
    private FindServiceChildAdapter mAdapter;

    public ImageTxtFragment() {
    }

    public static ImageTxtFragment newInstance(String cardId) {
        ImageTxtFragment fragment = new ImageTxtFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TXT, cardId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_img_txt;
    }

    BusModel busModel;
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        final String cardId = getArguments().getString(KEY_TXT);

        webView = $(R.id.webview);
        RecyclerView rv = $(R.id.rv1);
        TextView tvTitle = $(R.id.layout_title_tv);
        TextView btnBack = $(R.id.layout_back_btn);

        btnBack.setVisibility(View.VISIBLE);
        tvTitle.setText("详情");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        webView.setBackgroundColor(0);
        webView.getBackground().setAlpha(0);


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

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        datas = new ArrayList<CardDetailBean.ServicesBean>();

        busModel=new BusModel();
        mAdapter=getAdapter(datas);
        rv.setAdapter(mAdapter);

        mModel=new HomeModel();
        getData(cardId);

    }



    private void getData(String cardId) {

        addSubscribe(mModel.getCardDetail(cardId).subscribeWith(new SimpleRequestSubscriber<ApiResponse<CardDetailBean>>(this, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(ApiResponse<CardDetailBean> response) {
                super.onResponse(response);
                String txt= response.data.getContent();
                webView.loadData(txt, "text/html; charset=UTF-8", null);
                datas.clear();
                datas.addAll(response.data.getServices());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
            }
        }));

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
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
