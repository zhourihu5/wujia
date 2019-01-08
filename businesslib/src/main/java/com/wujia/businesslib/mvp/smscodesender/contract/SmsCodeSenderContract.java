package com.wujia.businesslib.mvp.smscodesender.contract;

import com.wujia.businesslib.data.ApiResponse;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.base.BaseView;
import com.wujia.lib_common.base.IBaseModle;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by 15790 on 2018/5/21.
 */

public interface SmsCodeSenderContract {
    interface Model extends IBaseModle {
        public Flowable<ApiResponse<List<String>>> requestCode(String phoneNum);
        public Flowable<ApiResponse> verifyCode(String phoneNum,String code);
    }

    interface View extends BaseView {
        public void onGetCodeSucc();
        public void onGetCodeNetworkUnConnected();
        public void onGetCodeServerError();
    }

    interface Presenter extends BasePresenter<SmsCodeSenderContract.View> {
        public void requestCode(String phoneNum);
        public void verifyCode(String phoneNum,String code);
    }
}
