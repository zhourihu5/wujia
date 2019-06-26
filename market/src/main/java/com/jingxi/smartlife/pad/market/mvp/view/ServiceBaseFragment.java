package com.jingxi.smartlife.pad.market.mvp.view;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.jingxi.smartlife.pad.market.mvp.adapter.FindServiceChildAdapter;
import com.liulishuo.okdownload.DownloadTask;
import com.wujia.businesslib.DownloadUtil;
import com.wujia.businesslib.ThirdPermissionUtil;
import com.wujia.businesslib.base.MvpFragment;
import com.wujia.businesslib.base.WebViewFragment;
import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.data.CardDetailBean;
import com.wujia.businesslib.dialog.LoadingProgressDialog;
import com.wujia.businesslib.event.EventBusUtil;
import com.wujia.businesslib.event.EventSubscription;
import com.wujia.businesslib.listener.DownloadListener;
import com.wujia.businesslib.model.BusModel;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.data.network.SimpleRequestSubscriber;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.AppUtil;
import com.wujia.lib_common.utils.DoubleClickUtils;
import com.wujia.lib_common.utils.FileUtil;
import com.wujia.lib_common.utils.LogUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public abstract class ServiceBaseFragment<T extends BasePresenter> extends MvpFragment<T> {
    DownloadTask mTask;

    protected FindServiceChildAdapter getAdapter(final List<CardDetailBean.ServicesBean> datas) {
        final BusModel busModel = new BusModel();
        final FindServiceChildAdapter mAdapter = new FindServiceChildAdapter(mContext, datas);
        mAdapter.setSubsribeClickCallback(new FindServiceChildAdapter.SubsribeClickCallback() {
            @Override
            public void subscibe(final CardDetailBean.ServicesBean item) {
                addSubscribe(busModel.subscribe(item.getId() + "", "1").subscribeWith(new SimpleRequestSubscriber<ApiResponse<Object>>(ServiceBaseFragment.this, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
                    @Override
                    public void onResponse(ApiResponse<Object> response) {
                        super.onResponse(response);
                        item.setIsSubscribe(1);
                        mAdapter.notifyDataSetChanged();
                        EventBusUtil.post(new EventSubscription(item.getType()));
                    }

                    @Override
                    public void onFailed(ApiException apiException) {
                        super.onFailed(apiException);
                    }
                }));
            }

            @Override
            public void unsubscibe(final CardDetailBean.ServicesBean item, int pos) {
                addSubscribe(busModel.subscribe(item.getId() + "", "0").subscribeWith(new SimpleRequestSubscriber<ApiResponse<Object>>(ServiceBaseFragment.this, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
                    @Override
                    public void onResponse(ApiResponse<Object> response) {
                        super.onResponse(response);
                        item.setIsSubscribe(0);
                        mAdapter.notifyDataSetChanged();
                        EventBusUtil.post(new EventSubscription(item.getType()));
                        if (item.getFlag() == CardDetailBean.TYPE_NATIVE) {
                            uninstall(item);
                        }
                    }

                    @Override
                    public void onFailed(ApiException apiException) {
                        super.onFailed(apiException);
                    }
                }));
            }
        });
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnRVItemClickListener() {
            @Override
            public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                toTarget(datas.get(position));
            }
        });
        return mAdapter;
    }

    private void uninstall(final CardDetailBean.ServicesBean item) {
        LogUtil.i("uninstall " + item.getPackageName());

//        final LoadingDialog loadDialog = new LoadingDialog(mContext);
//        loadDialog.setTitle("正在卸载");
        addSubscribe(
                Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                        boolean uninstall = AppUtil.uninstall(item.getPackageName());
//                boolean uninstall = true;
                        emitter.onNext(uninstall);
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean uninstall) throws Exception {
                                if (uninstall) {
//                            ToastUtil.showShort(mContext, "卸载完成");
                                    LogUtil.i("卸载完成");
                                } else {
                                    LogUtil.i("卸载失败");
                                }
//                        if (null != loadDialog) {
//                            loadDialog.dismiss();
//                        }
                            }
                        })
        );
    }

    protected void toTarget(CardDetailBean.ServicesBean item) {
        if (DoubleClickUtils.isDoubleClick()) {
            return;
        }
        if (item.getFlag() == CardDetailBean.TYPE_WEB) {
            if (this instanceof FindServiceFragment
//                    || this instanceof MyServiceFragment
//                    || this instanceof GovServiceFragment
                    || this instanceof AllServiceFragment) {
                parentStart(WebViewFragment.newInstance(item.getUrl()));
            } else {
                start(WebViewFragment.newInstance(item.getUrl()));
            }
        } else if (item.getFlag() == CardDetailBean.TYPE_NATIVE) {
            boolean result = AppUtil.startAPPByPackageName(item.getPackageName());
            if (!result) {//未找到应用
                downloadAndInstall(item);
            }
        }
    }

    public void downloadAndInstall(final CardDetailBean.ServicesBean item) {
        LogUtil.i("downloadAndInstall");

        if (item.getFlag() == CardDetailBean.TYPE_NATIVE) {

            final String apkPath = FileUtil.getDowndloadApkPath(mContext);
            LogUtil.i("apk path = " + apkPath);

            final LoadingProgressDialog loadDialog = new LoadingProgressDialog(mContext);
            mTask = DownloadUtil.download(item.getUrl(), new DownloadListener() {
                @Override
                public void onTaskStart() {

                    loadDialog.show();
                }

                @Override
                public void onTaskProgress(int percent, long currentOffset, long totalLength) {
                    if (null != loadDialog) {
                        loadDialog.updateProgress(percent);
                    }
                }

                @Override
                public void onTaskComplete(int state, final String filePath) {

                    switch (state) {

                        case DownloadUtil.STATE_COMPLETE:
                            LogUtil.i("下载完成 " + filePath);
                            if (null != loadDialog) {
                                loadDialog.setTvTitle("正在安装,请勿离开...");
                            }
                            Observable.create(new ObservableOnSubscribe<Boolean>() {
                                @Override
                                public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                                    LogUtil.i("install " + item.getPackageName());
                                    boolean install = AppUtil.install(filePath);
//                                    boolean install = true;
                                    emitter.onNext(install);
                                }
                            }).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean install) throws Exception {
                                            if (null != loadDialog) {
                                                loadDialog.dismiss();
                                            }
                                            if (install) {
                                                ToastUtil.showShort(mContext, "安装完成");
                                                //安装成功，本地记录
                                                ThirdPermissionUtil.requestDefaultPermissions(item.getPackageName());
                                            } else {
                                                ToastUtil.showShort(mContext, "安装失败");
                                            }
                                            if (!AppUtil.startAPPByPackageName(item.getPackageName())) {
                                                ToastUtil.showShort(mContext, "应用打开失败");
                                            }

                                        }
                                    });
                            break;
                        case DownloadUtil.STATE_CANCELED:
                            LogUtil.i("download canceled");
                        case DownloadUtil.STATE_OTHER:
                            LogUtil.i("unknown cause");
                            if (null != loadDialog) {
                                loadDialog.dismiss();
                            }
                            ToastUtil.showShort(mContext, "下载失败");
                            break;
                    }
                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mTask) {
            mTask.cancel();
        }
    }

//    public boolean exist(ServiceBean.Service item) {
//        List<ServiceBean.Service> list = DataBaseUtil.queryEquals("service_id", item.service_id, ServiceBean.Service.class);
//        if (null != list && !list.isEmpty()) {
//
//            LogUtil.i("已经订阅过");
//
//            return true;
//        }
//        return false;
//    }
}
