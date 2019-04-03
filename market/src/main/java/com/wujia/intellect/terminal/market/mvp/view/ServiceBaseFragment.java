package com.wujia.intellect.terminal.market.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.wujia.businesslib.base.DataManager;
import com.wujia.businesslib.base.MvpFragment;
import com.wujia.businesslib.base.WebViewFragment;
import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.adapter.FindServiceChildAdapter;
import com.wujia.intellect.terminal.market.mvp.contract.MarketContract;
import com.wujia.intellect.terminal.market.mvp.contract.MarketPresenter;
import com.wujia.intellect.terminal.market.mvp.data.ServiceBean;
import com.wujia.lib.widget.HorizontalTabBar;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.base.baseadapter.wrapper.LoadMoreWrapper;
import com.wujia.lib_common.data.network.exception.ApiException;

import java.util.ArrayList;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public abstract class ServiceBaseFragment<T extends BasePresenter> extends MvpFragment<T> {


    protected void toTarget(ServiceBean.Service item) {
        if (item.app_type == ServiceBean.TYPE_WEB) {
            parentStart(WebViewFragment.newInstance(item.app_url));
        } else if (item.app_type == ServiceBean.TYPE_NATIVE) {
            //TODO 订阅app
        }
    }
}
