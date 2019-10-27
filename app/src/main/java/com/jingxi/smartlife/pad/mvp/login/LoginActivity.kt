package com.jingxi.smartlife.pad.mvp.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import butterknife.OnClick
import butterknife.OnLongClick
import com.jingxi.jpushdemo.TagAliasOperatorHelper
import com.jingxi.smartlife.pad.R
import com.jingxi.smartlife.pad.mvp.MainActivity
import com.jingxi.smartlife.pad.mvp.login.contract.LoginContract
import com.jingxi.smartlife.pad.mvp.login.presenter.LoginPresenter
import com.sipphone.sdk.access.WebApiConstants
import com.sipphone.sdk.access.WebReponse
import com.sipphone.sdk.access.WebUserApi
import com.wujia.businesslib.HookUtil
import com.wujia.businesslib.base.DataManager
import com.wujia.businesslib.base.MvpActivity
import com.wujia.businesslib.data.LoginDTO
import com.wujia.businesslib.data.TokenBean
import com.wujia.lib.widget.util.ToastUtil
import com.wujia.lib_common.base.Constants
import com.wujia.lib_common.data.network.exception.ApiException
import com.wujia.lib_common.utils.*
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27 21:23
 * description ： 登录
 */
class LoginActivity : MvpActivity<LoginPresenter>(), LoginContract.View {

    override val layout: Int
        get() = R.layout.activity_login
    private var codeCountDownTimer: CountDownTimer? = null

    private var isShowPassword: Boolean = false


    override fun initEventAndData(savedInstanceState: Bundle?) {

        FontUtils.changeFontTypeface(login_time_tv, FontUtils.Font_TYPE_EXTRA_LIGHT)
        FontUtils.changeFontTypeface(login_temperature_tv, FontUtils.Font_TYPE_EXTRA_LIGHT)

        login_time_date_tv!!.text = StringUtil.format(getString(R.string.s_s), DateUtil.currentDate, DateUtil.currentWeekDay)
        mPresenter?.doTimeChange()

    }

    @OnClick(R.id.login_btn, R.id.login_btn_confim,  R.id.login_password_visibility, R.id.login_verify_code_btn)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.login_btn -> login()

            R.id.login_btn_confim -> {
                toActivity(MainActivity::class.java)
                finish()
            }

            R.id.login_password_visibility -> {
                isShowPassword = !isShowPassword
                if (isShowPassword) {
                    //如果选中，显示密码
                    login_password_visibility!!.setImageResource(R.mipmap.icon_input_hide)
                    login_password!!.transformationMethod = HideReturnsTransformationMethod.getInstance()
                } else {
                    //否则隐藏密码
                    login_password_visibility!!.setImageResource(R.mipmap.icon_input_display)
                    login_password!!.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }
            R.id.login_verify_code_btn -> {
                val phone = login_account!!.text.toString()
                if (!VerifyUtil.isPhone(phone)) {
                    login_phone_error!!.visibility = View.VISIBLE
                    return
                }
                login_phone_error!!.visibility = View.INVISIBLE
                mPresenter?.doGetCode(phone)
            }
        }//                startTimer();
    }

    @OnLongClick(R.id.login_btn)
    fun onViewLongClicked(view: View): Boolean {//for developers
        when (view.id) {
            R.id.login_btn -> startAdbWifi()
        }
        return true
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

    private fun startTimer() {
        login_verify_code_btn!!.isEnabled = false
        /** 倒计时60秒，一次1秒  */
        val text = getString(R.string.send_verify_code)
        codeCountDownTimer = object : CountDownTimer((60 * 1000).toLong(), 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                login_verify_code_btn!!.text = " (" + millisUntilFinished / 1000 + "s)"
            }

            override fun onFinish() {
                login_verify_code_btn!!.isEnabled = true
                login_verify_code_btn!!.text = text
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (codeCountDownTimer != null) {
            codeCountDownTimer!!.cancel()
        }
        HookUtil.fixInputMethodManagerLeak(this)//fixme memory leack,inputmethodmanager caused.
        if (login_password != null) {
            login_password!!.transformationMethod = null
        }
    }

    private fun login() {

        val phone = login_account!!.text.toString()
        if (!VerifyUtil.isPhone(phone)) {
            login_phone_error!!.visibility = View.VISIBLE
            return
        }
        val pwd = login_password!!.text.toString()
        if (TextUtils.isEmpty(pwd)) {
            return
        }

        val sn = SystemUtil.getSerialNum()
        LogUtil.i("sn $sn")
        sn?.let {
            val mHttpServer = "39.97.233.20"
            if (mHttpServer != null && mHttpServer.length > 0) {
                WebApiConstants.setHttpServer("http://$mHttpServer")
            }
            val mUUID = "CE6E92A0-47DE-474C-9588-0843C5BAC7EE"
            val mUsername = phone
            var mWebUserApi = WebUserApi(this)
            mWebUserApi.setOnAccessTokenListener(object : WebUserApi.onAccessTokenListener{
                override fun onPreAccessToken() {
                }

                override fun onPostAccessToken(reponse: WebReponse?) {
                    if(reponse != null && reponse.getStatusCode() == 200) {
                        mPresenter?.doLogin(phone, pwd, it)
                    }else{
                        ToastUtil.showShort(this@LoginActivity,"登录失败，${reponse?.reasonPhrase}")
                    }

                }

            })
            mWebUserApi.accessToken(mUUID, mUsername)

        }

    }


    override fun createPresenter(): LoginPresenter {
        return LoginPresenter()
    }


    override fun timeChange(time: String) {
        login_time_tv!!.text = time
    }

    override fun onDataLoadSucc(requestCode: Int, `object`: Any) {

        when (requestCode) {
            LoginPresenter.REQUEST_CDOE_GET_CODE -> startTimer()
            LoginPresenter.REQUEST_CDOE_LOGIN -> {
                val userBean = `object` as LoginDTO

                userBean.data?.let { SPHelper.saveObject(this@LoginActivity, Constants.SP_KEY_USER, it) }//todo 对象流兼容性不好，修改为json等格式保存。
                userBean.data?.token?.let { DataManager.saveToken(it) }

                //set push alias
                val tagAliasBean = TagAliasOperatorHelper.TagAliasBean()
                tagAliasBean.isAliasAction = true
                tagAliasBean.alias = userBean.data?.userInfo?.userName
                tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET
                TagAliasOperatorHelper.instance.handleAction(applicationContext, TagAliasOperatorHelper.sequence, tagAliasBean)

                val tagAliasBeanTag = TagAliasOperatorHelper.TagAliasBean()
                tagAliasBeanTag.isAliasAction = false
                val tags = HashSet<String>()
                tags.add("community_" + userBean.data?.userInfo?.communtityId)

                tagAliasBeanTag.tags = tags
                tagAliasBeanTag.action = TagAliasOperatorHelper.ACTION_SET
                TagAliasOperatorHelper.instance.handleAction(applicationContext, TagAliasOperatorHelper.sequence, tagAliasBeanTag)


                login_phone_error!!.visibility = View.INVISIBLE
                userBean.data?.userInfo?.nickName?.let { login_welcom_name!!.append(it) }

                login_layout_1!!.visibility = View.GONE
                login_layout_2!!.visibility = View.VISIBLE


            }
            LoginPresenter.REQUEST_CDOE_TOKEN -> {
                val bean = `object` as TokenBean
                LogUtil.i(bean.toString())
                bean.content?.let { DataManager.saveToken(it) }

            }
        }
    }


    override fun onDataLoadFailed(requestCode: Int, apiException: ApiException) {

    }
}
