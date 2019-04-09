package com.jingxi.smartlife.pad.mvp;

import android.content.Context;
import android.service.dreams.DreamService;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.jingxi.smartlife.pad.R;
import com.jingxi.smartlife.pad.mvp.home.adapter.HomeNotifyAdapter;
import com.jingxi.smartlife.pad.mvp.home.contract.HomeContract;
import com.jingxi.smartlife.pad.mvp.home.contract.HomeModel;
import com.jingxi.smartlife.pad.mvp.home.data.WeatherBean;
import com.jingxi.smartlife.pad.mvp.home.view.MessageDialog;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.wujia.businesslib.DataBaseUtil;
import com.wujia.businesslib.base.DataManager;
import com.wujia.businesslib.data.DBMessage;
import com.wujia.businesslib.event.EventBusUtil;
import com.wujia.businesslib.event.EventMsg;
import com.wujia.businesslib.event.IMiessageInvoke;
import com.wujia.businesslib.listener.OnDialogListener;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.data.network.SimpleRequestSubscriber;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.DateUtil;
import com.wujia.lib_common.utils.FontUtils;
import com.wujia.lib_common.utils.StringUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author ：shenbingkai@163.com
 * date ：2019-04-09
 * description ：
 */
public class LockService extends DreamService implements HomeContract.View {

    private TextView loginTimeTv;
    private TextView loginTimeDateTv;
    private TextView loginTemperatureTv;
    private TextView loginTemperatureDesc;
    private RecyclerView rvHomeMsg;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private EventMsg eventMsg = new EventMsg(new IMiessageInvoke<EventMsg>() {
        @Override
        public void eventBus(EventMsg event) {
            setNotify();
        }
    });

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        setInteractive(false);
        setFullscreen(true);
        setContentView(R.layout.activity_lock);
        init();
        EventBusUtil.register(eventMsg);

    }

    private void init() {

        loginTimeDateTv = findViewById(R.id.login_time_date_tv);
        loginTimeTv = findViewById(R.id.login_time_tv);
        loginTemperatureTv = findViewById(R.id.login_temperature_tv);
        loginTemperatureDesc = findViewById(R.id.login_temperature_desc);
        rvHomeMsg = findViewById(R.id.rv_home_msg);

        FontUtils.changeFontTypeface(loginTimeTv, FontUtils.Font_TYPE_EXTRA_LIGHT);
        FontUtils.changeFontTypeface(loginTemperatureTv, FontUtils.Font_TYPE_EXTRA_LIGHT);

        setWeather();

        setTime();

        setNotify();

    }

    //时间
    private void setTime() {
        loginTimeDateTv.setText(StringUtil.format(getString(R.string.s_s), DateUtil.getCurrentDate(), DateUtil.getCurrentWeekDay()));
        loginTimeTv.setText(DateUtil.getCurrentTimeHHMM());

        mCompositeDisposable.add(Observable.interval(10, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (null != loginTimeDateTv && null != loginTimeTv) {
                            loginTimeDateTv.setText(StringUtil.format(getString(R.string.s_s), DateUtil.getCurrentDate(), DateUtil.getCurrentWeekDay()));
                            loginTimeTv.setText(DateUtil.getCurrentTimeHHMM());
                        }
                    }
                }));
    }

    //天气
    private void setWeather() {
        final HomeModel model = new HomeModel();
        mCompositeDisposable.add(Observable.interval(0, 60 * 60, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                        mCompositeDisposable.add(model.getWeather(DataManager.getCommunityId()).subscribeWith(new SimpleRequestSubscriber<WeatherBean>(LockService.this, new SimpleRequestSubscriber.ActionConfig(false, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
                            @Override
                            public void onResponse(WeatherBean weatherBean) {
                                super.onResponse(weatherBean);
                                if (weatherBean.isSuccess()) {

                                    String curdate = DateUtil.getCurrentyyyymmddhh() + "00";

                                    for (WeatherBean.Weather weather : weatherBean.content) {
                                        if (weather.time.equals(curdate) && null != loginTemperatureTv && null != loginTemperatureDesc) {
                                            loginTemperatureTv.setText(weather.temperature + "°");
                                            loginTemperatureDesc.setText(weather.weather);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailed(ApiException apiException) {
                                super.onFailed(apiException);
                                onDataLoadFailed(0, apiException);
                            }
                        }));
                    }
                }));

    }


    //消息
    private void setNotify() {

        QueryBuilder builder = new QueryBuilder<>(DBMessage.class);
        builder.limit(0, 3).appendOrderDescBy("_id");

        final ArrayList<DBMessage> notifys = DataBaseUtil.query(builder);

        HomeNotifyAdapter notifyAdapter = new HomeNotifyAdapter(this, notifys);
        rvHomeMsg.setAdapter(notifyAdapter);

    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != mCompositeDisposable) mCompositeDisposable.clear();
        EventBusUtil.unregister(eventMsg);
    }

    @Override
    public void onDataLoadSucc(int requestCode, Object object) {

    }

    @Override
    public void onDataLoadFailed(int requestCode, ApiException apiException) {

    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void showLoadingDialog(String text) {

    }

    @Override
    public void hideLoadingDialog() {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onLoginStatusError() {

    }
}