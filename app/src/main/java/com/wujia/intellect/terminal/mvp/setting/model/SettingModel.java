package com.wujia.intellect.terminal.mvp.setting.model;

import com.wujia.businesslib.Constants;
import com.wujia.businesslib.base.BaseModel;
import com.wujia.businesslib.base.NetConfigWrapper;
import com.wujia.intellect.terminal.mvp.MainAppApiService;
import com.wujia.intellect.terminal.mvp.home.data.HomeRecBean;
import com.wujia.intellect.terminal.mvp.setting.SettingApiService;
import com.wujia.intellect.terminal.mvp.setting.contract.FamilyMemberContract;
import com.wujia.intellect.terminal.mvp.setting.contract.SettingContract;
import com.wujia.intellect.terminal.mvp.setting.data.VersionBean;
import com.wujia.lib_common.data.network.HttpHelper;
import com.wujia.lib_common.data.network.RxUtil;
import com.wujia.lib_common.utils.LogUtil;
import com.wujia.lib_common.utils.SystemUtil;

import io.reactivex.Flowable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-26
 * description ：
 */
public class SettingModel extends BaseModel implements SettingContract.Model {

    @Override
    protected void createHttp() {
        mHttpHelper = new HttpHelper.Builder(NetConfigWrapper.create(Constants.BASE_URL_UPDATE))
                .build();
    }

    @Override
    public Flowable<VersionBean> checkVersion() {
        String sn = SystemUtil.getSerialNum();
        return mHttpHelper.create(SettingApiService.class).checkVersion("pad",sn).compose(RxUtil.<VersionBean>rxSchedulerHelper());
    }
}
