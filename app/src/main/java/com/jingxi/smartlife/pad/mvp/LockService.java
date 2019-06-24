package com.jingxi.smartlife.pad.mvp;

import android.content.Context;
import android.content.Intent;
import android.service.dreams.DreamService;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jingxi.smartlife.pad.R;
import com.jingxi.smartlife.pad.mvp.home.adapter.HomeNotifyAdapter;
import com.jingxi.smartlife.pad.mvp.home.contract.HomeContract;
import com.jingxi.smartlife.pad.mvp.home.contract.HomeModel;
import com.jingxi.smartlife.pad.mvp.home.data.LockADBean;
import com.jingxi.smartlife.pad.mvp.home.data.WeatherInfoBean;
import com.wujia.businesslib.Constants;
import com.wujia.businesslib.base.WebViewActivity;
import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.data.MsgDto;
import com.wujia.businesslib.event.EventBusUtil;
import com.wujia.businesslib.event.EventMsg;
import com.wujia.businesslib.event.EventWakeup;
import com.wujia.businesslib.event.IMiessageInvoke;
import com.wujia.businesslib.model.BusModel;
import com.wujia.lib.imageloader.ImageLoaderManager;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.data.network.SimpleRequestSubscriber;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.DateUtil;
import com.wujia.lib_common.utils.FontUtils;
import com.wujia.lib_common.utils.StringUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
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
    private ImageView ivWeather;
    private TextView btnDetails;
    private RecyclerView rvHomeMsg;
    private ImageView bgImg;
    private HomeModel model;


    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private EventMsg eventMsg = new EventMsg(new IMiessageInvoke<EventMsg>() {
        @Override
        public void eventBus(EventMsg event) {
            if (event.type == EventMsg.TYPE_NEW_MSG) {
                setNotify();
            }
        }
    });
    private EventWakeup eventWakeup = new EventWakeup(new IMiessageInvoke<EventWakeup>() {
        @Override
        public void eventBus(EventWakeup event) {
            wakeUp();
        }
    });

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        setInteractive(true);
        setFullscreen(true);
        setContentView(R.layout.activity_lock);
        init();
        EventBusUtil.register(eventMsg);
        EventBusUtil.register(eventWakeup);

    }

    private void init() {

        loginTimeDateTv = findViewById(R.id.login_time_date_tv);
        loginTimeTv = findViewById(R.id.login_time_tv);
        loginTemperatureTv = findViewById(R.id.login_temperature_tv);
        ivWeather = findViewById(R.id.ivWeather);
        loginTemperatureDesc = findViewById(R.id.login_temperature_desc);
        rvHomeMsg = findViewById(R.id.rv_home_msg);
        bgImg = findViewById(R.id.lock_img_bg);

        bgImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wakeUp();
            }
        });

        btnDetails = findViewById(R.id.btn_details);

        FontUtils.changeFontTypeface(loginTimeTv, FontUtils.Font_TYPE_EXTRA_LIGHT);
        FontUtils.changeFontTypeface(loginTemperatureTv, FontUtils.Font_TYPE_EXTRA_LIGHT);

        if (model == null) {
            model = new HomeModel();
        }

        setScreenBg();

        setWeather();

        setTime();

        setNotify();
//        testWakeup();//todo just for test

    }
    private void testWakeup(){//todo just for test
        btnDetails.setVisibility(View.VISIBLE);
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(LockService.this, WebViewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constants.INTENT_KEY_1, "www.baidu.com");
                    startActivity(intent);
                    wakeUp();
            }
        });
    }

    private void setScreenBg() {
        mCompositeDisposable.add(model.getScreenSaverByCommunityId().subscribeWith(new SimpleRequestSubscriber<LockADBean>(LockService.this, new SimpleRequestSubscriber.ActionConfig(false, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(LockADBean bean) {
                super.onResponse(bean);
                    if (bean.data != null ) {
                        if (bgImg != null) {
                            final LockADBean.AD ad=bean.data;
                            ImageLoaderManager.getInstance().loadImage(ad.image, R.mipmap.bg_lockscreen, bgImg);
                            btnDetails.setVisibility(View.VISIBLE);
                            btnDetails.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (!TextUtils.isEmpty(ad.url)) {
                                        Intent intent = new Intent(LockService.this, WebViewActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra(Constants.INTENT_KEY_1, ad.url);
                                        startActivity(intent);
                                        wakeUp();
                                    }
                                }
                            });
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
        mCompositeDisposable.add(Observable.interval(3, 60 * 60, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                        mCompositeDisposable.add(model.getWeather().subscribeWith(new SimpleRequestSubscriber<WeatherInfoBean>(LockService.this, new SimpleRequestSubscriber.ActionConfig(false, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
                            @Override
                            public void onResponse(WeatherInfoBean weatherInfoBean) {
                                super.onResponse(weatherInfoBean);
                                if (weatherInfoBean.isSuccess()) {

                                    String curdate = DateUtil.getCurrentyyyymmddhh() + "00";
                                    List<WeatherInfoBean.DataBean.WeatherBean.ShowapiResBodyBean.HourListBean>weatherList= null;
                                    try {
                                        weatherList = weatherInfoBean.getData().getWeather().getShowapi_res_body().getHourList();
                                    } catch (Exception e) {
//                    e.printStackTrace();
                                    }
                                    if(weatherList!=null){
                                        for (WeatherInfoBean.DataBean.WeatherBean.ShowapiResBodyBean.HourListBean weather : weatherList) {
                                            if (weather.getTime().equals(curdate)) {
                                                loginTemperatureTv.setText(weather.getTemperature() + "°");
                                                loginTemperatureDesc.setText(weather.getWeather());
                                                ImageLoaderManager.getInstance().loadImage(weather.getWeather_code(),ivWeather);
                                            }
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

    BusModel busModel;
    //消息
    private void setNotify() {

        if(busModel ==null){
            busModel =new BusModel();
        }


        mCompositeDisposable.add(busModel.getTop3UnReadMsg().subscribeWith(new SimpleRequestSubscriber<ApiResponse<List<MsgDto.ContentBean>>>(this, new SimpleRequestSubscriber.ActionConfig(false, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(ApiResponse<List<MsgDto.ContentBean>> response) {
                super.onResponse(response);
                final List<MsgDto.ContentBean> notifys = response.data;
                HomeNotifyAdapter notifyAdapter = new HomeNotifyAdapter(LockService.this, notifys);
                rvHomeMsg.setAdapter(notifyAdapter);

                notifyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnRVItemClickListener() {
                    @Override
                    public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                        wakeUp();
                    }
                });
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
            }
        }));


    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != mCompositeDisposable) mCompositeDisposable.clear();
        EventBusUtil.unregister(eventMsg);
        EventBusUtil.unregister(eventWakeup);
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