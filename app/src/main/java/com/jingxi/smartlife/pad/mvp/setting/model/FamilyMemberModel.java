package com.jingxi.smartlife.pad.mvp.setting.model;

import com.jingxi.smartlife.pad.mvp.MainAppApiService;
import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean;
import com.wujia.businesslib.base.BaseModel;
import com.wujia.businesslib.data.ApiResponse;
import com.wujia.lib_common.data.network.RxUtil;

import java.util.List;

import io.reactivex.Flowable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-26
 * description ：
 */
public class FamilyMemberModel extends BaseModel  {

    public Flowable<ApiResponse<String>> addFamilyMember(String userName, String familyId) {
        return mHttpHelper.create(MainAppApiService.class).addFamilyMember(userName, familyId).compose(RxUtil.<ApiResponse<String>>rxSchedulerHelper());
    }

    public Flowable<ApiResponse<List<HomeUserInfoBean.DataBean.UserInfoListBean>>> getFamilyMemberList(String familyId) {
        return mHttpHelper.create(MainAppApiService.class).getFamilyMemberList(familyId).compose(RxUtil.<ApiResponse<List<HomeUserInfoBean.DataBean.UserInfoListBean>>>rxSchedulerHelper());
    }


}
