package com.wujia.intellect.terminal.market.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.liulishuo.okdownload.DownloadTask;
import com.wujia.businesslib.DataBaseUtil;
import com.wujia.businesslib.DownloadUtil;
import com.wujia.businesslib.ThirdPermissionUtil;
import com.wujia.businesslib.base.DataManager;
import com.wujia.businesslib.base.MvpFragment;
import com.wujia.businesslib.base.WebViewFragment;
import com.wujia.businesslib.data.DBService;
import com.wujia.businesslib.dialog.LoadingProgressDialog;
import com.wujia.businesslib.listener.DownloadListener;
import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.adapter.FindServiceChildAdapter;
import com.wujia.intellect.terminal.market.mvp.contract.MarketContract;
import com.wujia.intellect.terminal.market.mvp.contract.MarketPresenter;
import com.wujia.intellect.terminal.market.mvp.data.ServiceBean;
import com.wujia.lib.widget.HorizontalTabBar;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.base.baseadapter.wrapper.LoadMoreWrapper;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.AppUtil;
import com.wujia.lib_common.utils.DoubleClickUtils;
import com.wujia.lib_common.utils.FileUtil;
import com.wujia.lib_common.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public abstract class ServiceBaseFragment<T extends BasePresenter> extends MvpFragment<T> {

    protected void toTarget(ServiceBean.Service item) {
        if (DoubleClickUtils.isDoubleClick()) {
            return;
        }
        if (item.app_type == ServiceBean.TYPE_WEB) {
            if (this instanceof FindServiceFragment
                    || this instanceof MyServiceFragment
                    || this instanceof GovServiceFragment
                    || this instanceof AllServiceFragment) {
                parentStart(WebViewFragment.newInstance(item.app_url));
            } else {
                start(WebViewFragment.newInstance(item.app_url));
            }
        } else if (item.app_type == ServiceBean.TYPE_NATIVE) {
            //TODO 未订阅时点击卡片，逻辑需问产品
            if (!exist(item)) {
                LogUtil.i("未订阅");
                ToastUtil.showShort(mContext, "请先订阅" + item.packageName);
                return;
            }
            LogUtil.i("已订阅，去打开");
            ToastUtil.showShort(mContext, "测试打开" + item.packageName);
            boolean result = AppUtil.startAPPByPackageName(item.packageName);
            if (!result) {//未找到应用
                LogUtil.i("已订阅，但未找到应用");
                ToastUtil.showShort(mContext, "未找到应用" + item.packageName);
            }
        }
    }

    public boolean exist(ServiceBean.Service item) {
        List<ServiceBean.Service> list = DataBaseUtil.queryEquals("service_id", item.service_id, ServiceBean.Service.class);
        if (null != list && !list.isEmpty()) {

            LogUtil.i("已经订阅过");

            return true;
        }
        return false;
    }
}
