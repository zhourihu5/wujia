package com.wujia.intellect.terminal.mvp.home.contract;

import com.wujia.businesslib.data.RootResponse;
import com.wujia.intellect.terminal.mvp.home.data.HomeRecBean;
import com.wujia.businesslib.data.MessageBean;
import com.wujia.intellect.terminal.mvp.home.data.WeatherBean;
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

        Flowable<HomeRecBean> getQuickCard(String communityId);

        Flowable<HomeRecBean> getUserQuickCard(String openid);

        Flowable<RootResponse> addUserQuickCard(String openid, String quickCardId);

        Flowable<RootResponse> removeUserQuickCard(String openid, String quickCardId);

        Flowable<WeatherBean> getWeather(String communityId);

        Flowable<MessageBean> getPropertyMessageById(String id);

        Flowable<MessageBean> getManagerMessageById(String id);


    }

    interface View extends CommonDataLoadView {
    }

    interface Presenter extends BasePresenter<View> {

        void getQuickCard(String communityId);

        void getWeather(String communityId);

        void getUserQuickCard(String openid);

        void addUserQuickCard(String openid, String quickCardId);

        void removeUserQuickCard(String openid, String quickCardId);

        void getPropertyMessageById(String type, String id);

        void getManagerMessageById(String type, String id);
    }
}
