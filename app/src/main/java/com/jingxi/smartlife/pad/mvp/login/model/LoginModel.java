package com.jingxi.smartlife.pad.mvp.login.model;

import com.jingxi.smartlife.pad.mvp.login.contract.LoginContract;
import com.wujia.businesslib.BusAppApiService;
import com.wujia.businesslib.Constants;
import com.wujia.businesslib.base.BaseModel;
import com.wujia.businesslib.base.NetConfigWrapper;
import com.wujia.businesslib.data.LoginDTO;
import com.wujia.businesslib.data.RootResponse;
import com.wujia.businesslib.data.TokenBean;
import com.wujia.businesslib.data.UserBean;
import com.jingxi.smartlife.pad.mvp.MainAppApiService;
import com.jingxi.smartlife.pad.mvp.login.contract.LoginContract;
import com.wujia.lib_common.data.network.HttpHelper;
import com.wujia.lib_common.data.network.RxUtil;
import com.wujia.lib_common.utils.SystemUtil;

import io.reactivex.Flowable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public class LoginModel extends BaseModel implements LoginContract.Model {



    @Override
    public Flowable<LoginDTO> login(String mobile, String captcha, String padSn) {
        return mHttpHelper.create(MainAppApiService.class).login(mobile, captcha, padSn).compose(RxUtil.<LoginDTO>rxSchedulerHelper());
    }

    @Override
    public Flowable<RootResponse> getCode(String mobile) {
        return mHttpHelper.create(MainAppApiService.class).getCode(mobile, SystemUtil.getSerialNum()).compose(RxUtil.<RootResponse>rxSchedulerHelper());
    }


}
