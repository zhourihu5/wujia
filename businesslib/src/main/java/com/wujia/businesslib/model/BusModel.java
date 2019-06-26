package com.wujia.businesslib.model;

import com.wujia.businesslib.BusApiService;
import com.wujia.businesslib.base.BaseModel;
import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.data.MsgDto;
import com.wujia.businesslib.data.VersionBean;
import com.wujia.lib_common.data.network.RxUtil;

import java.util.List;

import io.reactivex.Flowable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public class BusModel extends BaseModel {


    public Flowable<ApiResponse<MsgDto>> getMsg(String type, String status, int pageNo, int pageSize) {
        return mHttpHelper.create(BusApiService.class).getMsg(type, status, pageNo, pageSize).compose(RxUtil.<ApiResponse<MsgDto>>rxSchedulerHelper());

    }

    public Flowable<ApiResponse<List<MsgDto.ContentBean>>> getTop3UnReadMsg() {
        return mHttpHelper.create(BusApiService.class).getTop3UnReadMsg().compose(RxUtil.<ApiResponse<List<MsgDto.ContentBean>>>rxSchedulerHelper());

    }

    public Flowable<ApiResponse<Object>> readMsg(String id) {
        return mHttpHelper.create(BusApiService.class).readMsg(id).compose(RxUtil.<ApiResponse<Object>>rxSchedulerHelper());

    }

    public Flowable<ApiResponse<Boolean>> isUnReadMessage() {
        return mHttpHelper.create(BusApiService.class).isUnReadMsg().compose(RxUtil.<ApiResponse<Boolean>>rxSchedulerHelper());

    }

    public Flowable<ApiResponse<Object>> subscribe(String serviceId, String isSubscribe) {
        return mHttpHelper.create(BusApiService.class).subscribe(serviceId, isSubscribe).compose(RxUtil.<ApiResponse<Object>>rxSchedulerHelper());

    }

    public Flowable<VersionBean> checkVersion() {
        return mHttpHelper.create(BusApiService.class).checkVersion().compose(RxUtil.<VersionBean>rxSchedulerHelper());
    }


}
