package com.jingxi.smartlife.pad.mvp.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jingxi.jpushdemo.TagAliasOperatorHelper;
import com.jingxi.smartlife.pad.R;
import com.jingxi.smartlife.pad.mvp.MainActivity;
import com.jingxi.smartlife.pad.mvp.login.contract.LoginContract;
import com.jingxi.smartlife.pad.mvp.login.presenter.LoginPresenter;
import com.wujia.lib_common.base.Constants;
import com.wujia.businesslib.HookUtil;
import com.wujia.businesslib.base.DataManager;
import com.wujia.businesslib.base.MvpActivity;
import com.wujia.businesslib.data.LoginDTO;
import com.wujia.businesslib.data.TokenBean;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.AppUtil;
import com.wujia.lib_common.utils.DateUtil;
import com.wujia.lib_common.utils.FontUtils;
import com.wujia.lib_common.utils.LogUtil;
import com.wujia.lib_common.utils.SPHelper;
import com.wujia.lib_common.utils.StringUtil;
import com.wujia.lib_common.utils.SystemUtil;
import com.wujia.lib_common.utils.VerifyUtil;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27 21:23
 * description ： 登录
 */
public class LoginActivity extends MvpActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.login_time_tv)
    TextView loginTimeTv;
    @BindView(R.id.login_time_date_tv)
    TextView loginTimeDateTv;
    @BindView(R.id.login_temperature_tv)
    TextView loginTemperatureTv;
    @BindView(R.id.login_weather_icon)
    ImageView loginWeatherIcon;
    @BindView(R.id.login_weather_desc)
    TextView loginWeatherDesc;
    @BindView(R.id.login_account)
    EditText loginAccount;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_layout_1)
    LinearLayout loginLayout1;
    @BindView(R.id.login_welcom_name)
    TextView loginWelcomName;
    @BindView(R.id.login_btn_confim)
    TextView loginBtnConfim;
    @BindView(R.id.login_layout_2)
    LinearLayout loginLayout2;
    @BindView(R.id.login_phone_error)
    TextView loginPhoneError;
    @BindView(R.id.login_verify_code_btn)
    TextView loginGetCode;
    @BindView(R.id.login_password_visibility)
    ImageView loginPasswordVisibility;


    private CountDownTimer codeCountDownTimer;

    private boolean isShowPassword;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {


        FontUtils.changeFontTypeface(loginTimeTv, FontUtils.Font_TYPE_EXTRA_LIGHT);
        FontUtils.changeFontTypeface(loginTemperatureTv, FontUtils.Font_TYPE_EXTRA_LIGHT);

        loginTimeDateTv.setText(StringUtil.format(getString(R.string.s_s), DateUtil.getCurrentDate(), DateUtil.getCurrentWeekDay()));
        mPresenter.doTimeChange();

//        mPresenter.doGetAccessToken();
        //todo 验证本地是否有token，如果没有，则需要登录。如果有token，则接口验证token是否过期，如果未过期，直接进入主页，如果过期了则需要重新登录。
//        String token=DataManager.getToken();
//        if(!TextUtils.isEmpty(token)){
//
//        }
    }

    @OnClick({R.id.login_btn, R.id.login_btn_confim, R.id.login_password_visibility, R.id.login_verify_code_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                login();
                break;
            case R.id.login_btn_confim:
                toActivity(MainActivity.class);
                finish();
                break;

            case R.id.login_password_visibility:
                isShowPassword = !isShowPassword;
                if (isShowPassword) {
                    //如果选中，显示密码
                    loginPasswordVisibility.setImageResource(R.mipmap.icon_input_hide);
                    loginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    loginPasswordVisibility.setImageResource(R.mipmap.icon_input_display);
                    loginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.login_verify_code_btn:
                //TODO 验证手机号
                String phone = loginAccount.getText().toString();
                if (!VerifyUtil.isPhone(phone)) {
                    loginPhoneError.setVisibility(View.VISIBLE);
                    return;
                }
                loginPhoneError.setVisibility(View.INVISIBLE);
                mPresenter.doGetCode(phone);
//                startTimer();
                break;
        }
    }
    @OnLongClick({R.id.login_btn})
    public boolean onViewLongClicked(View view) {//for developers
        switch (view.getId()){
            case R.id.login_btn:
                startAdbWifi();
                break;
        }
        return true;
    }
    void startAdbWifi(){
        addSubscribe(Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                boolean install = AppUtil.startAdbWifi();
                emitter.onNext(install);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean install) throws Exception {
                        if (install) {
                            ToastUtil.showShort(mContext, "adb 开启成功");
                        } else {
                            ToastUtil.showShort(mContext, "adb 开启失败");
                        }
                    }
                }));
    }

    protected void startTimer() {
        loginGetCode.setEnabled(false);
        /** 倒计时60秒，一次1秒 */
        final String text = getString(R.string.send_verify_code);
        codeCountDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                loginGetCode.setText(" (" + millisUntilFinished / 1000 + "s)");
            }

            @Override
            public void onFinish() {
                loginGetCode.setEnabled(true);
                loginGetCode.setText(text);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (codeCountDownTimer != null) {
            codeCountDownTimer.cancel();
        }
        HookUtil.fixInputMethodManagerLeak(this);//fixme memory leack,inputmethodmanager caused.
        if (loginPassword != null) {
            loginPassword.setTransformationMethod(null);
            loginPassword = null;
        }
        if (loginAccount != null) {
            loginAccount = null;
        }
    }

    private void login() {

        //TODO 验证手机号
        String phone = loginAccount.getText().toString();
        if (!VerifyUtil.isPhone(phone)) {
            loginPhoneError.setVisibility(View.VISIBLE);
            return;
        }
        String pwd = loginPassword.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            return;
        }

        String sn = SystemUtil.getSerialNum();
        LogUtil.i("sn " + sn);
        mPresenter.doLogin(phone, pwd, sn);

    }


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }


    @Override
    public void timeChange(String time) {
        loginTimeTv.setText(time);
    }

    @Override
    public void onDataLoadSucc(int requestCode, Object object) {

        if (requestCode == LoginPresenter.REQUEST_CDOE_GET_CODE) {
            startTimer();

        } else if (requestCode == LoginPresenter.REQUEST_CDOE_LOGIN) {
            LoginDTO userBean = (LoginDTO) object;
//            if (TextUtils.isEmpty(userBean.data.accid) || TextUtils.isEmpty(userBean.data.familyId) || TextUtils.isEmpty(userBean.data.buttonkey)
//                    || TextUtils.isEmpty(userBean.data.dockkey) || TextUtils.isEmpty(userBean.data.communityId) || TextUtils.isEmpty(userBean.data.openId)) {
//                ToastUtil.showShort(LoginActivity.this, "缺少必要参数");
//                return;
//            }

            SPHelper.saveObject(LoginActivity.this, Constants.SP_KEY_USER, userBean.getData());//todo 对象流兼容性不好，修改为json等格式保存。
//            initSdkData(userBean.data);
            DataManager.saveToken(userBean.getData().getToken());

            //set push alias
            TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
            tagAliasBean.isAliasAction = true;
            tagAliasBean.alias = userBean.getData().getUserInfo().getUserName();
            tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
            TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), TagAliasOperatorHelper.sequence, tagAliasBean);

            TagAliasOperatorHelper.TagAliasBean tagAliasBeanTag = new TagAliasOperatorHelper.TagAliasBean();
            tagAliasBeanTag.isAliasAction = false;
            Set<String> tags = new HashSet<>();
            tags.add("community_" + userBean.getData().getUserInfo().getCommuntityId());

            tagAliasBeanTag.tags = tags;
            tagAliasBeanTag.action = TagAliasOperatorHelper.ACTION_SET;
            TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), TagAliasOperatorHelper.sequence, tagAliasBeanTag);


            loginPhoneError.setVisibility(View.INVISIBLE);
            loginWelcomName.append(userBean.getData().getUserInfo().getNickName());
            loginLayout1.setVisibility(View.GONE);
            loginLayout2.setVisibility(View.VISIBLE);


        } else if (requestCode == LoginPresenter.REQUEST_CDOE_TOKEN) {
            TokenBean bean = (TokenBean) object;
            LogUtil.i(bean.toString());
            DataManager.saveToken(bean.content);
        }
    }

//    private void initSdkData(UserBean.User user) {
//
////        JXPadSdk.setAccid(user.accid);
//        JXPadSdk.setAppKey(Constants.APPID, DataManager.getToken());
////        JXPadSdk.setCommunityId(user.communityId);
////        JXPadSdk.setFamilyInfoId(user.familyId);
//    }

    @Override
    public void onDataLoadFailed(int requestCode, ApiException apiException) {

    }
}
