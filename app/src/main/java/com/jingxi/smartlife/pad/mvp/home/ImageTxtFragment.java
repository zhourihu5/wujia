package com.jingxi.smartlife.pad.mvp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean;
import com.jingxi.smartlife.pad.R;
import com.jingxi.smartlife.pad.market.mvp.adapter.FindServiceChildAdapter;
import com.jingxi.smartlife.pad.market.mvp.adapter.ServiceBaseAdapter;
import com.jingxi.smartlife.pad.market.mvp.data.ServiceBean;
import com.jingxi.smartlife.pad.market.mvp.view.ServiceBaseFragment;
import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;

import java.util.ArrayList;


/**
 * author ：shenbingkai@163.com
 * date ：2019-03-29
 * description ：
 */
public class ImageTxtFragment extends ServiceBaseFragment {

    public static final String KEY_TXT = "txt";
    public static final String KEY_SUBCRIPTION = "subscriptions";

    private ArrayList<ServiceBean.Service> datas;
    private WebView webView;
    private FindServiceChildAdapter mAdapter;
    private ArrayList<HomeRecBean.Subscriptions> subscriptions;

    public ImageTxtFragment() {
    }

    public static ImageTxtFragment newInstance(String txt, ArrayList<HomeRecBean.Subscriptions> subscriptions) {
        ImageTxtFragment fragment = new ImageTxtFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TXT, txt);
        args.putSerializable(KEY_SUBCRIPTION, subscriptions);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_img_txt;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        String txt = getArguments().getString(KEY_TXT);
        subscriptions = (ArrayList<HomeRecBean.Subscriptions>) getArguments().getSerializable(KEY_SUBCRIPTION);

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

        webView.loadData(txt, "text/html; charset=UTF-8", null);

        datas = new ArrayList<>();

        getData();


        mAdapter = new FindServiceChildAdapter(mContext, datas, FindServiceChildAdapter.TYPE_RECOMMEND);
        mAdapter.setAdapterCallback(new ServiceBaseAdapter.AdatperCallback() {
            @Override
            public void notifydatachange() {
                getData();
                mAdapter.notifyDataSetChanged();
            }
        });
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnRVItemClickListener() {
            @Override
            public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                toTarget(datas.get(position));
            }
        });
        rv.setAdapter(mAdapter);

    }

    private void getData() {
        datas.clear();
        for (HomeRecBean.Subscriptions sub : subscriptions) {
            ServiceBean.Service service = new ServiceBean.Service();
            service.name = sub.serviceTitle;
            service.image = sub.serviceImage;
            service.app_url = sub.serviceUrl;
            service.explain = sub.serviceDesc;
            service.packageName = sub.servicePackage;
            service.service_id = sub.serviceId;

            if (sub.type.equals("app")) {
                service.app_type = ServiceBean.TYPE_NATIVE;
                //TODO 临时处理，需要后台修改html的type
                if (sub.serviceUrl.endsWith(".html")) {
                    service.app_type = ServiceBean.TYPE_WEB;
                }
            }

            //匹配已订阅数据
//            for (int i = 0; i < mySub.size(); i++) {
//                if (service.service_id.equals(mySub.get(i).service_id)) {
//                    service._installed = true;
//                    break;
//                }
//            }

            datas.add(service);
        }
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
