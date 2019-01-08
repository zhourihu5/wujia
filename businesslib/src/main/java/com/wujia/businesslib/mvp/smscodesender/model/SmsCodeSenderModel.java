package com.wujia.businesslib.mvp.smscodesender.model;

import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.data.CommonApiService;
import com.wujia.businesslib.mvp.smscodesender.contract.SmsCodeSenderContract;
import com.wujia.lib_common.data.network.HttpHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by 15790 on 2018/5/21.
 */

public class SmsCodeSenderModel implements SmsCodeSenderContract.Model {
    HttpHelper httpHelper;

    @Inject
    public SmsCodeSenderModel(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    @Override
    public Flowable<ApiResponse<List<String>>> requestCode(String phoneNum) {
        return httpHelper.create(CommonApiService.class).requestCode(phoneNum);
    }

    @Override
    public Flowable<ApiResponse> verifyCode(String phoneNum, String code) {
        return null;
    }
}
