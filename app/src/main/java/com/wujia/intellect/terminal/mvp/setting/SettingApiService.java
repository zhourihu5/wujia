package com.wujia.intellect.terminal.mvp.setting;


import com.wujia.intellect.terminal.mvp.setting.data.VersionBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface SettingApiService {


//    http://api.house-keeper.cn/api/v2/romServer/checkVersion?sn=GR8081707000092&type=pad

    @GET("/api/v2/romServer/checkVersion")
    Flowable<VersionBean> checkVersion(@Query("type") String type,@Query("sn") String sn);

}
