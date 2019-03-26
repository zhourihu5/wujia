package com.wujia.intellect.terminal.mvp.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wujia.businesslib.Constants;
import com.wujia.businesslib.base.DataManager;
import com.wujia.businesslib.base.MvpActivity;
import com.wujia.businesslib.data.TokenBean;
import com.wujia.businesslib.data.UserBean;
import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.MainActivity;
import com.wujia.intellect.terminal.mvp.login.contract.LoginContract;
import com.wujia.intellect.terminal.mvp.login.presenter.LoginPresenter;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.DateUtil;
import com.wujia.lib_common.utils.FontUtils;
import com.wujia.lib_common.utils.LogUtil;
import com.wujia.lib_common.utils.SPHelper;
import com.wujia.lib_common.utils.StringUtil;
import com.wujia.lib_common.utils.VerifyUtil;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.login_password_visibility)
    ImageView loginPasswordVisibility;

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

        if (!TextUtils.isEmpty(DataManager.getFamilyId())) {
            toActivity(MainActivity.class);
            finish();
        }
    }

    @OnClick({R.id.login_btn, R.id.login_btn_confim, R.id.login_password_visibility})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                login();
//                mPresenter.doGetAccessToken();


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

        loginLayout1.setVisibility(View.GONE);
        loginLayout2.setVisibility(View.VISIBLE);

//        http://openapi.house-keeper.cn/openapi/v1/user/padLogin?appid=test&accessToken=4c239ad0-59b1-4306-b437-c8a8e79baea7&padSn=HS1JXY6M12D2900034&mobile=17712239874&captcha=jingxikeji
        mPresenter.doLogin(phone, pwd, "HS1JXY6M12D2900034");

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

        if (requestCode == 1) {

            TokenBean bean = (TokenBean) object;
            LogUtil.i(bean.toString());
        } else if (requestCode == 2) {
            UserBean userBean = (UserBean) object;
            SPHelper.saveObject(LoginActivity.this, Constants.SP_KEY_USER, userBean.content);
            loginPhoneError.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDataLoadFailed(int requestCode, ApiException apiException) {

    }
}
