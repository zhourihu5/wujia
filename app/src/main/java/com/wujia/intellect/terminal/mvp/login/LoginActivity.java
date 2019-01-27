package com.wujia.intellect.terminal.mvp.login;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wujia.businesslib.base.MvpActivity;
import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.MainActivity;
import com.wujia.intellect.terminal.mvp.login.contract.LoginContract;
import com.wujia.intellect.terminal.mvp.login.presenter.LoginPresenter;
import com.wujia.lib_common.utils.DateUtil;
import com.wujia.lib_common.utils.StringUtil;
import com.wujia.lib_common.utils.VerifyUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27 21:23
 * description ： 登录
 */
public class LoginActivity extends MvpActivity<LoginPresenter> implements LoginContract.View,LoginPresenter.TimeChangeListener {


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

        mPresenter.setTimeChangeListener(this);
        mPresenter.currentTime();
        loginTimeDateTv.setText(StringUtil.format(getString(R.string.s_s), DateUtil.getCurrentDate(), DateUtil.getCurrentWeekDay()));
    }

    @OnClick({R.id.login_btn, R.id.login_btn_confim, R.id.login_password_visibility})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                loginPhoneError.setVisibility(View.INVISIBLE);

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
        }
    }

    private void login() {
        String phone = loginAccount.getText().toString();
        if (!VerifyUtil.isPhone(phone)) {
            loginPhoneError.setVisibility(View.VISIBLE);
            return;
        }

        loginLayout1.setVisibility(View.GONE);
        loginLayout2.setVisibility(View.VISIBLE);

    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }


    @Override
    public void timeChange(String time) {
        loginTimeTv.setText(time);
    }
}
