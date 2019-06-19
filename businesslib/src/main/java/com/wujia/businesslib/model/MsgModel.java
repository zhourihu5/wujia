package com.wujia.businesslib.model;

import com.wujia.businesslib.MsgApiService;
import com.wujia.businesslib.base.BaseModel;
import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.data.MsgDto;
import com.wujia.lib_common.data.network.RxUtil;

import java.util.List;

import io.reactivex.Flowable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public class MsgModel extends BaseModel {


    public Flowable<ApiResponse<MsgDto>> getMsg(String pageNo,  String type,  String status ) {
        return mHttpHelper.create(MsgApiService.class).getMsg(pageNo,type,status).compose(RxUtil.<ApiResponse<MsgDto>>rxSchedulerHelper());

    }
    public Flowable<ApiResponse<List<MsgDto.ContentBean>>> getTop3UnReadMsg() {
        return mHttpHelper.create(MsgApiService.class).getTop3UnReadMsg().compose(RxUtil.<ApiResponse<List<MsgDto.ContentBean>>>rxSchedulerHelper());

    }
    public Flowable<ApiResponse<Object>> readMsg(String id) {
        return mHttpHelper.create(MsgApiService.class).readMsg(id).compose(RxUtil.<ApiResponse<Object>>rxSchedulerHelper());

    }
    public Flowable<ApiResponse<Boolean>> isUnReadMessage() {
        return mHttpHelper.create(MsgApiService.class).isUnReadMsg().compose(RxUtil.<ApiResponse<Boolean>>rxSchedulerHelper());

    }

}
