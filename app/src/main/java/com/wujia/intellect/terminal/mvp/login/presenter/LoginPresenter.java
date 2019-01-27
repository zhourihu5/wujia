package com.wujia.intellect.terminal.mvp.login.presenter;

import com.wujia.businesslib.base.RxPresenter;
import com.wujia.intellect.terminal.mvp.login.contract.LoginContract;
import com.wujia.lib_common.utils.DateUtil;

import io.reactivex.Flowable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public class LoginPresenter extends RxPresenter implements LoginContract.Presenter {
    private TimeChangeListener timeChangeListener;

    public void setTimeChangeListener(TimeChangeListener timeChangeListener) {
        this.timeChangeListener = timeChangeListener;
    }


    public void currentTime(){

        if (null!=timeChangeListener){
            timeChangeListener.timeChange(DateUtil.getCurrentTimeHHMM());
        }

    }

    public interface TimeChangeListener{
        void timeChange(String time);
    }
}
