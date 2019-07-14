package com.jingxi.smartlife.pad.mvp.setting.view

import android.app.WallpaperManager
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import butterknife.OnClick
import butterknife.OnLongClick
import com.jingxi.jpushdemo.MyReceiver
import com.jingxi.smartlife.pad.R
import com.jingxi.smartlife.pad.mvp.setting.contract.SettingContract
import com.jingxi.smartlife.pad.mvp.setting.presenter.SettingPresenter
import com.jingxi.smartlife.pad.mvp.util.ScreenManager
import com.wujia.businesslib.base.MvpFragment
import com.wujia.businesslib.data.VersionBean
import com.wujia.businesslib.dialog.SimpleDialog
import com.wujia.businesslib.util.LoginUtil
import com.wujia.lib.widget.util.ToastUtil
import com.wujia.lib_common.data.network.exception.ApiException
import com.wujia.lib_common.utils.AppUtil
import com.wujia.lib_common.utils.FileUtil
import com.wujia.lib_common.utils.LogUtil
import com.wujia.lib_common.utils.VersionUtil
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_setting_home.*

//import kotlinx.android.synthetic.main.layout_title.view.*
//import com.wujia.businesslib.R.id
//import com.jingxi.smartlife.pad.R.layout.layout_title
//import kotlinx.android.synthetic.main.layout_title.*

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：物业服务 home
 */
class SettingHomeFragment : MvpFragment<SettingPresenter>(), SettingContract.View {


    override fun getLayoutId(): Int {
        return R.layout.fragment_setting_home
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        layout_title_tv!!.setText(R.string.setting)
        layout_title!!.setOnLongClickListener {
            val versionCode = VersionUtil.getVersionCode()
            val versionName = VersionUtil.getVersionName()

            ToastUtil.showShort(mContext, "$versionName - $versionCode")
            true
        }

    }

    @OnClick(R.id.item_set_member, R.id.item_manager_card, R.id.item_set_lock_pic, R.id.item_wifi_connection, R.id.item_allow_look_door_num, R.id.item_clear_cache, R.id.item_check_update)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.item_set_member ->

                start(FamilyMemberFragment.newInstance())
            R.id.item_manager_card -> startForResult(CardManagerFragment.newInstance(), CardManagerFragment.REQUEST_CODE_CARD_MANAGER)
            R.id.item_set_lock_pic -> {
                //                startActivity(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
                val wallpaperManager = WallpaperManager.getInstance(activity)
                try {
                    wallpaperManager.setResource(R.raw.bg_lockscreen)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            R.id.item_wifi_connection -> startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
            R.id.item_allow_look_door_num -> item_allow_look_door_num_switch!!.toggle()
            R.id.item_clear_cache -> SimpleDialog.Builder().title(getString(R.string.clear_cache)).listener {
                ToastUtil.showShort(mContext, getString(R.string.cache_clear_ed))
                FileUtil.deleteFile(FileUtil.getDowndloadApkPath(mContext))
            }.build(mContext).show()
            R.id.item_check_update -> {
                showLoadingDialog(getString(R.string.check_update_ing))

                mPresenter.checkVersion()
            }
        }
    }

    @OnLongClick(R.id.item_check_update, R.id.item_wifi_connection,R.id.item_clear_cache)
    fun onViewLongClicked(view: View): Boolean {//for developers
        when (view.id) {
            R.id.item_wifi_connection -> startAdbWifi()
            R.id.item_check_update -> LoginUtil.toLoginActivity()
            R.id.item_clear_cache -> setBrightMode()
        }
        return true
    }
    private fun setBrightMode() {
       val mode= ScreenManager.screenMode
       val brightNess= ScreenManager.screenBrightness
        LogUtil.i("getScreenMode==$mode,getScreenBrightness=$brightNess")
        when(mode){
            ScreenManager.LIGHT_MODE_AUTO->{
                ToastUtil.showShort(mContext, "1 为自动调节屏幕亮度")
                ScreenManager.screenMode = ScreenManager.LIGHT_MODE_MANUAL
                ScreenManager.screenBrightness = 50
            }
            ScreenManager.LIGHT_MODE_MANUAL->{
                ScreenManager.screenBrightness = 255
                ToastUtil.showShort(mContext, "0 为手动调节屏幕亮度")
                ScreenManager.screenMode = ScreenManager.LIGHT_MODE_AUTO
            }
            ScreenManager.LIGHT_MODE_FAILED->{
                ToastUtil.showShort(mContext, "-1 获取失败")
                ScreenManager.screenBrightness = 100
            }
        }
    }



    private fun startAdbWifi() {
        addSubscribe(Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
            val install = AppUtil.startAdbWifi()
            emitter.onNext(install)
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { install ->
                    if (install!!) {
                        ToastUtil.showShort(mContext, "adb 开启成功")
                    } else {
                        ToastUtil.showShort(mContext, "adb 开启失败")
                    }
                })
    }


    override fun createPresenter(): SettingPresenter {
        return SettingPresenter()
    }

    override fun onDataLoadSucc(requestCode: Int, `object`: Any) {
        hideLoadingDialog()
        val bean = `object` as VersionBean


        if (MyReceiver.isUpdate(bean.data)) {
            start(UpdateFragment.newInstance(bean.data, bean.data.desc))
        } else {
            ToastUtil.showShort(mContext, "已是最新版本")
        }
    }

    override fun onDataLoadFailed(requestCode: Int, apiException: ApiException) {
        hideLoadingDialog()
    }

    companion object {

        fun newInstance(): SettingHomeFragment {
            val fragment = SettingHomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}


//----------------------------------------test code -------------------------------------------------------------------------------------------------------------------------

//@KotlinAn
//fun test(@KotlinAn mode:Int):Int{
//    ScreenManager.screenMode=5
//    test2(1)
////        return  ScreenManager.screenMode
//    return  1
//}
//@LightMode
//fun test2(@LightMode mode:Int):Int{
//    ScreenManager.screenMode=5
//    when(ScreenManager.screenMode){
//        1->{}
//    }
//
//    test(1)
////        return  ScreenManager.screenMode
//    return  1
//}
//
//@Retention(AnnotationRetention.SOURCE)
//@IntDef(ScreenManager.LIGHT_MODE_AUTO, ScreenManager.LIGHT_MODE_MANUAL, ScreenManager.LIGHT_MODE_FAILED)
//annotation class KotlinAn
