package com.jingxi.smartlife.pad.mvp.home.contract;

import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean;
import com.jingxi.smartlife.pad.mvp.home.data.LockADBean;
import com.wujia.businesslib.data.RootResponse;
import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean;
import com.wujia.businesslib.data.MessageBean;
import com.jingxi.smartlife.pad.mvp.home.data.WeatherInfoBean;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.base.CommonDataLoadView;
import com.wujia.lib_common.base.IBaseModle;

import io.reactivex.Flowable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public interface HomeContract {
    interface Model extends IBaseModle {

        Flowable<HomeRecBean> getQuickCard();

        Flowable<HomeRecBean> getUserQuickCard();

        Flowable<RootResponse> addUserQuickCard( String quickCardId);

        Flowable<RootResponse> removeUserQuickCard( String quickCardId);

        Flowable<WeatherInfoBean> getWeather();
        public Flowable<HomeUserInfoBean> getHomeUserInfo(String key);

        Flowable<MessageBean> getPropertyMessageById(String id);

        Flowable<MessageBean> getManagerMessageById(String id);

        Flowable<LockADBean> getScreenSaverByCommunityId();

    }

    interface View extends CommonDataLoadView {
    }

    interface Presenter extends BasePresenter<View> {

        void getQuickCard();

        void getWeather();

        void getHomeUserInfo(String key);

        void getUserQuickCard();

        void addUserQuickCard( String quickCardId);

        void removeUserQuickCard( String quickCardId);

        void getPropertyMessageById(String type, String id);

        void getManagerMessageById(String type, String id);

        void getScreenSaverByCommunityId(String communityId);
    }
}
