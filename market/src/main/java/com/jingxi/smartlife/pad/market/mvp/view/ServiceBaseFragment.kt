package com.jingxi.smartlife.pad.market.mvp.view

import com.jingxi.smartlife.pad.market.mvp.adapter.FindServiceChildAdapter
import com.liulishuo.okdownload.DownloadTask
import com.wujia.businesslib.DownloadUtil
import com.wujia.businesslib.ThirdPermissionUtil
import com.wujia.businesslib.base.MvpFragment
import com.wujia.businesslib.base.WebViewFragment
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.data.CardDetailBean
import com.wujia.businesslib.dialog.LoadingProgressDialog
import com.wujia.businesslib.event.EventBusUtil
import com.wujia.businesslib.event.EventSubscription
import com.wujia.businesslib.listener.DownloadListener
import com.wujia.businesslib.model.BusModel
import com.wujia.lib.widget.util.ToastUtil
import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.BaseView
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException
import com.wujia.lib_common.utils.AppUtil
import com.wujia.lib_common.utils.DoubleClickUtils
import com.wujia.lib_common.utils.FileUtil
import com.wujia.lib_common.utils.LogUtil
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
abstract class ServiceBaseFragment<T : BasePresenter<*>> : MvpFragment<BasePresenter<BaseView>>() {
    internal var mTask: DownloadTask? = null

    protected fun getAdapter(datas: List<CardDetailBean.ServicesBean>): FindServiceChildAdapter {
        val busModel = BusModel()
        val mAdapter = mContext?.let { FindServiceChildAdapter(it, datas) }
        mAdapter?.setSubsribeClickCallback(object : FindServiceChildAdapter.SubsribeClickCallback {
            override fun subscibe(item: CardDetailBean.ServicesBean) {
                addSubscribe(busModel.subscribe(item.id.toString() + "", "1").subscribeWith(object : SimpleRequestSubscriber<ApiResponse<Any>>(this@ServiceBaseFragment, SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
                    override fun onResponse(response: ApiResponse<Any>) {
                        super.onResponse(response)
                        item.isSubscribe = 1
                        mAdapter?.notifyDataSetChanged()
                        EventBusUtil.post(EventSubscription(item.type))
                    }

                    override fun onFailed(apiException: ApiException) {
                        super.onFailed(apiException)
                    }
                }))
            }

            override fun unsubscibe(item: CardDetailBean.ServicesBean, pos: Int) {
                addSubscribe(busModel.subscribe(item.id.toString() + "", "0").subscribeWith(object : SimpleRequestSubscriber<ApiResponse<Any>>(this@ServiceBaseFragment, SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
                    override fun onResponse(response: ApiResponse<Any>) {
                        super.onResponse(response)
                        item.isSubscribe = 0
                        mAdapter?.notifyDataSetChanged()
                        EventBusUtil.post(EventSubscription(item.type))
                        if (item.flag == CardDetailBean.TYPE_NATIVE) {
                            uninstall(item)
                        }
                    }

                    override fun onFailed(apiException: ApiException) {
                        super.onFailed(apiException)
                    }
                }))
            }
        })
        mAdapter?.setOnItemClickListener { adapter, holder, position -> toTarget(datas[position]) }
        return mAdapter!!
    }

    private fun uninstall(item: CardDetailBean.ServicesBean) {
        LogUtil.i("uninstall " + item.packageName)

        //        final LoadingDialog loadDialog = new LoadingDialog(mContext);
        //        loadDialog.setTitle("正在卸载");
        addSubscribe(
                Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
                     item.packageName?.let { val uninstall =AppUtil.uninstall(it)
                         emitter.onNext(uninstall)}
                    //                boolean uninstall = true;

                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { uninstall ->
                            if (uninstall!!) {
                                //                            ToastUtil.showShort(mContext, "卸载完成");
                                LogUtil.i("卸载完成")
                            } else {
                                LogUtil.i("卸载失败")
                            }
                            //                        if (null != loadDialog) {
                            //                            loadDialog.dismiss();
                            //                        }
                        }
        )
    }

    protected fun toTarget(item: CardDetailBean.ServicesBean) {
        if (DoubleClickUtils.isDoubleClick) {
            return
        }
        if (item.flag == CardDetailBean.TYPE_WEB) {
            if (this is FindServiceFragment || this is AllServiceFragment) {
                item.url?.let {parentStart( WebViewFragment.newInstance(it)) }
            } else {
                item.url?.let  { start(WebViewFragment.newInstance(it)) }
            }
        } else if (item.flag == CardDetailBean.TYPE_NATIVE) {
            item.packageName?.let {
                val result =  AppUtil.startAPPByPackageName(it)
                if (!result) {//未找到应用
                    downloadAndInstall(item)
                }
            }

        }
    }

    fun downloadAndInstall(item: CardDetailBean.ServicesBean) {
        LogUtil.i("downloadAndInstall")

        if (item.flag == CardDetailBean.TYPE_NATIVE) {

            val apkPath = mContext?.let { FileUtil.getDowndloadApkPath(it) }
            LogUtil.i("apk path = $apkPath")

            val loadDialog = mContext?.let { LoadingProgressDialog(it) }
            mTask = item.url?.let {
                DownloadUtil.download(it, object : DownloadListener {
                    override fun onTaskStart() {

                        loadDialog?.show()
                    }

                    override fun onTaskProgress(percent: Int, currentOffset: Long, totalLength: Long) {
                        loadDialog?.updateProgress(percent)
                    }

                    override fun onTaskComplete(state: Int, filePath: String) {

                        when (state) {

                            DownloadUtil.STATE_COMPLETE -> {
                                LogUtil.i("下载完成 $filePath")
                                loadDialog?.setTvTitle("正在安装,请勿离开...")
                                Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
                                    LogUtil.i("install " + item.packageName)
                                    val install = AppUtil.install(filePath)
                                    //                                    boolean install = true;
                                    emitter.onNext(install)
                                }).subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe { install ->
                                            loadDialog?.dismiss()
                                            if (install!!) {
                                                ToastUtil.showShort(mContext, "安装完成")
                                                //安装成功，本地记录
                                                item.packageName?.let { ThirdPermissionUtil.requestDefaultPermissions(it) }
                                            } else {
                                                ToastUtil.showShort(mContext, "安装失败")
                                            }
                                            item.packageName?.let { if (!AppUtil.startAPPByPackageName(it)) {
                                                ToastUtil.showShort(mContext, "应用打开失败")
                                            } }

                                        }
                            }
                            DownloadUtil.STATE_CANCELED -> {
                                LogUtil.i("download canceled")
                                LogUtil.i("unknown cause")
                                loadDialog?.dismiss()
                                ToastUtil.showShort(mContext, "下载失败")
                            }
                            DownloadUtil.STATE_OTHER -> {
                                LogUtil.i("unknown cause")
                                loadDialog?.dismiss()
                                ToastUtil.showShort(mContext, "下载失败")
                            }
                        }
                    }
                })
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (null != mTask) {
            mTask!!.cancel()
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
