package com.wujia.intellect.terminal.mvp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.liulishuo.okdownload.DownloadTask;
import com.wujia.businesslib.DataBaseUtil;
import com.wujia.businesslib.DownloadUtil;
import com.wujia.businesslib.TitleFragment;
import com.wujia.businesslib.data.AppPackageBean;
import com.wujia.businesslib.dialog.LoadingProgressDialog;
import com.wujia.businesslib.listener.DownloadListener;
import com.wujia.businesslib.listener.ItemClickListener;
import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.home.adapter.HomeSubscibeAdapter;
import com.wujia.intellect.terminal.mvp.home.data.HomeRecBean;
import com.wujia.lib_common.utils.AppUtil;
import com.wujia.lib_common.utils.FileUtil;
import com.wujia.lib_common.utils.LogUtil;

import java.util.ArrayList;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-29
 * description ：
 */
public class ImageTxtFragment extends TitleFragment implements ItemClickListener {

    public static final String KEY_TXT = "txt";
    public static final String KEY_SUBCRIPTION = "subscriptions";

    private String txt = "";
    private ArrayList<HomeRecBean.Subscriptions> subscriptions;
    private ArrayList<AppPackageBean> applist;
    private WebView webView;
    private DownloadTask mTask;
    private LoadingProgressDialog loadDialog;
    private HomeSubscibeAdapter mAdapter;

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

        txt = getArguments().getString(KEY_TXT);
        subscriptions = (ArrayList<HomeRecBean.Subscriptions>) getArguments().getSerializable(KEY_SUBCRIPTION);

        webView = $(R.id.webview);
        RecyclerView rv = $(R.id.rv1);

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

        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

//        webView.loadData(txt, "text/html", "UTF-8");
        webView.loadData(txt, "text/html; charset=UTF-8", null);

        applist = DataBaseUtil.query(AppPackageBean.class);

//        rv.addItemDecoration(new GridDecoration(0, 12));
        mAdapter = new HomeSubscibeAdapter(mContext, subscriptions, applist);
        rv.setAdapter(mAdapter);

        mAdapter.setItemClickListener(this);
    }

    @Override
    public void onDestroyView() {
        if (null != webView)
            webView.destroy();
        super.onDestroyView();
    }

    @Override
    public void onItemClick(final int pos) {
        final HomeRecBean.Subscriptions item = subscriptions.get(pos);

        //判断本地是否存在
        if (item._installed) {

            showToast("打开" + item.servicePackage);
            AppUtil.startAPPByPackageName(item.servicePackage);

            return;
        }

        final String apkPath = FileUtil.getDowndloadApkPath(mContext);
        LogUtil.i("apk path = " + apkPath);

        mTask = DownloadUtil.download(item.serviceUrl, new DownloadListener() {
            @Override
            public void onTaskStart() {

                loadDialog = new LoadingProgressDialog(mActivity);
                loadDialog.show();
            }

            @Override
            public void onTaskProgress(int percent) {
                if (null != loadDialog) {
                    loadDialog.updateProgress(percent);
                }
            }

            @Override
            public void onTaskComplete(int state, String filePath) {

                if (state == 0) {
                    LogUtil.i("下载完成 " + filePath);
                    if (null != loadDialog) {
                        loadDialog.setTvTitle("正在安装");
                    }
                    //TODO 安装需要系统签名
//                            boolean install = AppUtil.install(filePath);
                    boolean install = true;
                    if (install) {
                        showToast("安装完成");

                        //假设安装成功，本地记录
                        DataBaseUtil.insert(new AppPackageBean(item.servicePackage));
                        if (null != mAdapter) {
                            mAdapter.updateAppList(DataBaseUtil.query(AppPackageBean.class));
                            mAdapter.notifyItemChanged(pos);
                        }
                    } else {
                        showToast("安装失败");
                    }

                } else {

                }
                if (null != loadDialog) {
                    loadDialog.dismiss();
                }
            }
        });
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
    public int getTitle() {
        return R.string._empty;
    }
}
