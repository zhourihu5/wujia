package com.abctime.businesslib.mvp.smscodesender.presenter;

import com.abctime.businesslib.data.ApiResponse;
import com.abctime.businesslib.mvp.smscodesender.contract.SmsCodeSenderContract;
import com.abctime.businesslib.mvp.smscodesender.model.SmsCodeSenderModel;
import com.abctime.businesslib.base.RxPresenter;
import com.abctime.lib_common.data.network.RxUtil;
import com.abctime.lib_common.data.network.SimpleRequestSubscriber;
import com.abctime.lib_common.data.network.exception.ApiException;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by 15790 on 2018/5/21.
 */

public class SmsCodeSenderPresenter extends RxPresenter<SmsCodeSenderContract.View> implements SmsCodeSenderContract.Presenter {

    SmsCodeSenderModel smsCodeSenderModel;

    @Inject
    public SmsCodeSenderPresenter(SmsCodeSenderModel smsCodeSenderModel) {
        this.smsCodeSenderModel = smsCodeSenderModel;
    }

    @Override
    public void requestCode(String phoneNum) {
        addSubscribe(smsCodeSenderModel.requestCode(phoneNum).compose(RxUtil.<ApiResponse<List<String>>>rxSchedulerHelper()).subscribeWith(new SimpleRequestSubscriber<ApiResponse<List<String>>>(mView){
            @Override
            public void onResponse(ApiResponse<List<String>> response) {
                super.onResponse(response);
                if(response.isSuccess()){
                    mView.onGetCodeSucc();
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                if(apiException.isNetworkConnectError())
                {
                    mView.onGetCodeNetworkUnConnected();
                }
                else
                {
                    mView.onGetCodeServerError();
                }
            }
        }));
    }

    @Override
    public void verifyCode(String phoneNum, String code) {

    }
}
