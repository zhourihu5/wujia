package com.jingxi.smartlife.pad.market.mvp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.jingxi.smartlife.pad.market.mvp.data.ServiceBean;
import com.liulishuo.okdownload.DownloadTask;
import com.wujia.businesslib.DataBaseUtil;
import com.wujia.businesslib.DownloadUtil;
import com.wujia.businesslib.ThirdPermissionUtil;
import com.wujia.businesslib.dialog.LoadingDialog;
import com.wujia.businesslib.dialog.LoadingProgressDialog;
import com.wujia.businesslib.event.EventBusUtil;
import com.wujia.businesslib.event.EventSubscription;
import com.wujia.businesslib.listener.DownloadListener;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.utils.AppUtil;
import com.wujia.lib_common.utils.FileUtil;
import com.wujia.lib_common.utils.LogUtil;
import com.wujia.lib_common.utils.DoubleClickUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public abstract class ServiceBaseAdapter<T> extends CommonAdapter<T> {

    public static final int TYPE_MY = 0;
    public static final int TYPE_FIND = 1;
    public static final int TYPE_GOV = 2;
    public static final int TYPE_ALL = 3;
    public static final int TYPE_RECOMMEND = 4;//首页的软文推荐，包含已订阅和未订阅的数据

    private DownloadTask mTask;
    private AdatperCallback adapterCallback;

    public void setAdapterCallback(AdatperCallback adapterCallback) {
        this.adapterCallback = adapterCallback;
    }

    public ServiceBaseAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
    }

    protected void subscibe(ServiceBean.Service item) {
        LogUtil.i("订阅-click");
        if (DoubleClickUtils.isDoubleClick()) {
            return;
        }
        if (exist(item)) {
            ToastUtil.showShort(mContext, "订阅成功");
            return;
        }
        if (item.app_type == ServiceBean.TYPE_WEB) {

            DataBaseUtil.insert(item);

            EventBusUtil.post(new EventSubscription());
            LogUtil.i("订阅成功");
            ToastUtil.showShort(mContext, "订阅成功");
        } else if (item.app_type == ServiceBean.TYPE_NATIVE) {
            downloadAndInstall(item);
        }
    }

    protected void unsubscibe(ServiceBean.Service item, int pos) {
        LogUtil.i("取消订阅-click");
        if (DoubleClickUtils.isDoubleClick()) {
            return;
        }
        if (item.app_type == ServiceBean.TYPE_WEB) {
            mDatas.remove(pos);
            DataBaseUtil.delete(item);
            notifyDataSetChanged();
        } else if (item.app_type == ServiceBean.TYPE_NATIVE) {
            uninstall(item, pos);
        }
    }

    protected boolean exist(ServiceBean.Service item) {
        List<ServiceBean.Service> list = DataBaseUtil.queryEquals("service_id", item.service_id, ServiceBean.Service.class);
        if (null != list && !list.isEmpty()) {

            LogUtil.i("已经订阅过");

            return true;
        }
        return false;
    }

    public void downloadAndInstall(final ServiceBean.Service item) {
        LogUtil.i("downloadAndInstall");

        if (item.app_type == ServiceBean.TYPE_NATIVE) {

            final String apkPath = FileUtil.getDowndloadApkPath(mContext);
            LogUtil.i("apk path = " + apkPath);

            final LoadingProgressDialog loadDialog = new LoadingProgressDialog(mContext);
            mTask = DownloadUtil.download(item.app_url, new DownloadListener() {
                @Override
                public void onTaskStart() {

                    loadDialog.show();
                }

                @Override
                public void onTaskProgress(int percent,long currentOffset, long totalLength) {
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
                                    LogUtil.i("install " + item.packageName);
                                    boolean install = AppUtil.install(filePath);
//                                    boolean install = true;
                                    emitter.onNext(install);
                                }
                            }).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean install) throws Exception {
                                            if (install) {
                                                ToastUtil.showShort(mContext, "安装完成");
                                                //安装成功，本地记录
                                                ThirdPermissionUtil.requestDefaultPermissions(item.packageName);
                                                DataBaseUtil.insert(item);
                                                if (null != adapterCallback) {
                                                    adapterCallback.notifydatachange();
                                                } else {
                                                    notifyDataSetChanged();
                                                }
                                                EventBusUtil.post(new EventSubscription());
                                            } else {
                                                ToastUtil.showShort(mContext, "安装失败");
                                            }
                                            if (null != loadDialog) {
                                                loadDialog.dismiss();
                                            }
                                        }
                                    });
                            break;
                        case DownloadUtil.STATE_CANCELED:
                        case DownloadUtil.STATE_OTHER:
                            if (null != loadDialog) {
                                loadDialog.dismiss();
                            }
                            break;
                    }
                }
            });
        }

    }

    @SuppressLint("CheckResult")
    protected void uninstall(final ServiceBean.Service item, final int pos) {
        LogUtil.i("uninstall " + item.packageName);

        final LoadingDialog loadDialog = new LoadingDialog(mContext);
        loadDialog.setTitle("正在卸载");
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                boolean uninstall = AppUtil.uninstall(item.packageName);
//                boolean uninstall = true;
                emitter.onNext(uninstall);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean uninstall) throws Exception {
                        if (uninstall) {
                            ToastUtil.showShort(mContext, "卸载完成");
                            LogUtil.i("卸载完成");
                            //卸载成功，本地记录
                            mDatas.remove(pos);
                            DataBaseUtil.delete(item);
                            notifyDataSetChanged();
                        } else {
                            LogUtil.i("卸载失败");
                        }
                        if (null != loadDialog) {
                            loadDialog.dismiss();
                        }
                    }
                });
    }

    public void exitDownload() {
        if (null != mTask) {
            mTask.cancel();
        }
    }

    public interface AdatperCallback {
        void notifydatachange();
    }
}
