package com.jingxi.smartlife.pad.mvp.setting.view

import android.os.Bundle
import android.view.View
import butterknife.OnClick
import com.jingxi.smartlife.pad.R
import com.liulishuo.okdownload.DownloadTask
import com.wujia.businesslib.DownloadUtil
import com.wujia.businesslib.TitleFragment
import com.wujia.businesslib.data.VersionBean
import com.wujia.businesslib.listener.DownloadListener
import com.wujia.lib.widget.util.ToastUtil
import com.wujia.lib_common.utils.AppUtil
import com.wujia.lib_common.utils.LogUtil
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_update.*
import java.math.BigDecimal

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-26
 * description ：检查更新
 */
class UpdateFragment : TitleFragment() {


    private var mVersion: VersionBean.Version? = null
    private var mTask: DownloadTask? = null

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        mVersion = arguments!!.getSerializable("version") as VersionBean.Version
        val remark = arguments!!.getString("remark")
        tv_version_desc!!.text = remark
        tv_version!!.text = mVersion!!.versionName

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_update
    }

    override fun getTitle(): Int {
        return R.string.check_update
    }

    @OnClick(R.id.btn_update_now)
    fun onViewClicked() {
        update_check_layout!!.visibility = View.GONE
        update_ing_layout!!.visibility = View.VISIBLE

        download()

        //        install();
    }

    private fun download() {
        mTask = DownloadUtil.download(mVersion!!.imageurl, object : DownloadListener {
            override fun onTaskStart() {

            }

            override fun onTaskProgress(percent: Int, currentOffset: Long, totalLength: Long) {
                if (null != progress_update) {
                    progress_update!!.progress = percent
                }
                if (null != tv_update_downloaded) {
                    tv_update_downloaded!!.text = getFormatSize(currentOffset.toDouble()) + "/" + getFormatSize(totalLength.toDouble())
                }
            }

            override fun onTaskComplete(state: Int, filePath: String) {
                when (state) {

                    DownloadUtil.STATE_COMPLETE -> {
                        LogUtil.i("tv_update_downloaded $filePath")
                        if (null != tv_update_downloaded) {
                            tv_update_downloaded!!.text = "正在安装"
                        }
                        Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
                            LogUtil.i("install $filePath")
                            val install = AppUtil.install(filePath)
                            //                                    boolean install = true;
                            emitter.onNext(install)
                        }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe { install ->
                                    update_check_layout!!.visibility = View.VISIBLE
                                    update_ing_layout!!.visibility = View.GONE
                                    ToastUtil.showShort(mContext, "安装失败")
                                    if (install!!) {
                                        ToastUtil.showShort(mContext, "安装完成")
                                        //安装成功，本地记录
                                        //                                            ThirdPermissionUtil.requestDefaultPermissions(mVersion.packageName);

                                    } else {
                                        if (null != tv_update_downloaded) {
                                            tv_update_downloaded!!.text = "安装失败"
                                        }
                                        ToastUtil.showShort(mContext, "安装失败")
                                    }
                                }
                    }
                    DownloadUtil.STATE_CANCELED, DownloadUtil.STATE_OTHER -> {
                        update_check_layout!!.visibility = View.VISIBLE
                        update_ing_layout!!.visibility = View.GONE
                        if (null != tv_update_downloaded) {
                            tv_update_downloaded!!.text = "安装失败"
                        }
                        ToastUtil.showShort(mContext, "安装失败")
                    }
                }
            }
        })
    }

    //测试安装
    private fun install() {
        Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
            //                LogUtil.i("install " + mVersion.packageName);
            val install = AppUtil.install("/storage/emulated/0/Android/data/com.jingxi.smartlife.pad/files/Download/apk/e4283230-33a9-47ce-9131-40b819538515.apk")
            //                                    boolean install = true;
            emitter.onNext(install)
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { install ->
                    if (install!!) {
                        LogUtil.i("install 安装成功")

                        ToastUtil.showShort(mContext, "安装完成")
                        //安装成功，本地记录
                        //                                            ThirdPermissionUtil.requestDefaultPermissions(mVersion.packageName);

                    } else {
                        ToastUtil.showShort(mContext, "安装失败")
                        LogUtil.i("install 安装失败")
                    }
                }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (null != mTask) {
            mTask!!.cancel()
        }
    }

    companion object {

        fun newInstance(version: VersionBean.Version, remark: String): UpdateFragment {
            val fragment = UpdateFragment()
            val args = Bundle()
            args.putSerializable("version", version)
            args.putString("remark", remark)
            fragment.arguments = args
            return fragment
        }

        /**
         * 格式化单位
         *
         * @param size
         * @return
         */
        fun getFormatSize(size: Double): String {
            val kiloByte = size / 1024
            if (kiloByte < 1) {
                return size.toString() + "B"
            }

            val megaByte = kiloByte / 1024
            if (megaByte < 1) {
                val result1 = BigDecimal(kiloByte.toString())
                return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB"
            }

            val gigaByte = megaByte / 1024
            if (gigaByte < 1) {
                val result2 = BigDecimal(java.lang.Double.toString(megaByte))
                return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB"
            }

            val teraBytes = gigaByte / 1024
            if (teraBytes < 1) {
                val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
                return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB"
            }
            val result4 = BigDecimal(teraBytes)
            return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
        }
    }
}
