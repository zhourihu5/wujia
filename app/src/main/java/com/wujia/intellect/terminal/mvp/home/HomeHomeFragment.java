package com.wujia.intellect.terminal.mvp.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wujia.businesslib.DataBaseUtil;
import com.wujia.businesslib.base.DataManager;
import com.wujia.businesslib.base.MvpFragment;
import com.wujia.businesslib.data.LinkUrlBean;
import com.wujia.businesslib.dialog.CallDialog;
import com.wujia.businesslib.listener.OnInputDialogListener;
import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.home.adapter.HomeMemberAdapter;
import com.wujia.intellect.terminal.mvp.home.adapter.HomeNotifyAdapter;
import com.wujia.intellect.terminal.mvp.home.adapter.HomeCardAdapter;
import com.wujia.intellect.terminal.mvp.home.contract.HomeContract;
import com.wujia.intellect.terminal.mvp.home.contract.HomePresenter;
import com.wujia.intellect.terminal.mvp.home.data.HomeMeberBean;
import com.wujia.intellect.terminal.mvp.home.data.HomeNotifyBean;
import com.wujia.intellect.terminal.mvp.home.data.HomeRecBean;
import com.wujia.intellect.terminal.mvp.home.data.WeatherBean;
import com.wujia.intellect.terminal.mvp.home.view.AddMemberDialog;
import com.wujia.intellect.terminal.mvp.home.view.MessageDialog;
import com.wujia.intellect.terminal.mvp.setting.view.CardManagerFragment;
import com.wujia.lib.widget.HomeArcView;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.base.view.HorizontalDecoration;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.DateUtil;
import com.wujia.lib_common.utils.FontUtils;
import com.wujia.lib_common.utils.GsonUtil;
import com.wujia.lib_common.utils.LogUtil;
import com.wujia.lib_common.utils.NetworkUtil;
import com.wujia.lib_common.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ： home
 */
public class HomeHomeFragment extends MvpFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.home_room_tv)
    TextView homeRoomTv;
    @BindView(R.id.rv_home_member)
    RecyclerView rvHomeMember;
    @BindView(R.id.home_chat_btn)
    ImageView homeChatBtn;
    @BindView(R.id.home_arc_view)
    HomeArcView homeArcView;
    @BindView(R.id.home_home_img)
    ImageView homeHomeImg;
    @BindView(R.id.home_batter_img)
    ImageView homeBatterImg;
    @BindView(R.id.home_wifi_img)
    ImageView homeWifiImg;
    @BindView(R.id.home_camera_img)
    ImageView homeCameraImg;
    @BindView(R.id.home_eq_status_layout)
    LinearLayout homeEqStatusLayout;
    @BindView(R.id.home_date_tv)
    TextView homeDateTv;
    @BindView(R.id.home_car_num_tv)
    TextView homeCarNumTv;
    @BindView(R.id.home_air_tv)
    TextView homeAirTv;
    @BindView(R.id.home_status_layout)
    LinearLayout homeStatusLayout;
    @BindView(R.id.home_weather_desc_tv)
    TextView homeWeatherDescTv;
    @BindView(R.id.home_weather_num_tv)
    TextView homeWeatherNumTv;
    @BindView(R.id.rv_home_card)
    RecyclerView rvHomeCard;
    @BindView(R.id.rv_home_msg)
    RecyclerView rvHomeMsg;
    @BindView(R.id.home_call_service_btn)
    ImageView homeCallServiceBtn;
    private HomeCardAdapter homeCardAdapter;
    private BatteryReceiver batterReceiver;
    private NetworkChangeReceiver networkReceiver;

    private List<HomeMeberBean> mems;
    private HomeMemberAdapter memAdapter;
    private ArrayList<HomeRecBean.Card> cards;

    public HomeHomeFragment() {
    }

    public static HomeHomeFragment newInstance() {
        HomeHomeFragment fragment = new HomeHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        FontUtils.changeFontTypeface(homeWeatherNumTv, FontUtils.Font_TYPE_EXTRA_LIGHT);

        homeRoomTv.setText(DataManager.getUser().nickName);
        homeDateTv.setText(StringUtil.format(getString(R.string.s_s), DateUtil.getCurrentDate(), DateUtil.getCurrentWeekDay()));

        setCardView();

        setMemberView();

        setNotify();

        setState();


        mPresenter.getUserQuickCard(DataManager.getOpenid());
        mPresenter.getWeather(DataManager.getCommunityId());

    }


    private void setMemberView() {
        mems = DataBaseUtil.getMemberList(HomeMeberBean.class);

        rvHomeMember.addItemDecoration(new HorizontalDecoration(10));
        memAdapter = new HomeMemberAdapter(mActivity, mems);
        rvHomeMember.setAdapter(memAdapter);

    }

    private void setCardView() {

        cards = new ArrayList<>();
        homeCardAdapter = new HomeCardAdapter(mActivity, cards);
//        rvHomeCard.addItemDecoration(new HorizontalDecoration(13));
        rvHomeCard.setAdapter(homeCardAdapter);
        homeCardAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnRVItemClickListener() {
            @Override
            public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {

                HomeRecBean.Card card = cards.get(position);
                switch (card.type) {
                    case HomeRecBean.TYPE_LINK:
                        if (HomeRecBean.TYPE_LINK_EXTERNAL.equals(card.linkType)) {//外链
                            start(WebViewFragment.newInstance(card.linkUrl));
                        } else if (HomeRecBean.TYPE_LINK_INTERNALL.equals(card.linkType)) {//内链
                            parseLinkUrl(card.linkUrl);
                        }
                        break;

                    case HomeRecBean.TYPE_FUN:
                        break;

                    case HomeRecBean.TYPE_IMAGE:
                        start(ImageTxtFragment.newInstance(card.explain, card.subscriptions));
                        break;

                    case HomeRecBean.TYPE_ADD:
                        startForResult(CardManagerFragment.newInstance(), CardManagerFragment.REQUEST_CODE_CARD_MANAGER);

                        break;

                }
            }
        });
    }

    private void parseLinkUrl(String json) {
        LinkUrlBean linkUrl = GsonUtil.GsonToBean(json, LinkUrlBean.class);
        switch (linkUrl.code) {
            case LinkUrlBean.CODE_TYPE_MARKET://服务市场

                break;

            case LinkUrlBean.CODE_TYPE_PROPERTY://务业服务

                break;
        }
    }

    private void setNotify() {
        List<HomeNotifyBean> notifys = new ArrayList<>();
        notifys.add(new HomeNotifyBean());
        notifys.add(new HomeNotifyBean());
        notifys.add(new HomeNotifyBean());

//        rvHomeMsg.addItemDecoration(new HorizontalDecoration(25));
        HomeNotifyAdapter notifyAdapter = new HomeNotifyAdapter(mActivity, notifys);
        rvHomeMsg.setAdapter(notifyAdapter);
        notifyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnRVItemClickListener() {
            @Override
            public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                new MessageDialog(mContext).show();
            }
        });
    }

    private void setState() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batterReceiver = new BatteryReceiver(homeBatterImg);
        networkReceiver = new NetworkChangeReceiver(homeWifiImg);

        mActivity.registerReceiver(batterReceiver, filter);
        mActivity.registerReceiver(networkReceiver, filter);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == CardManagerFragment.REQUEST_CODE_CARD_MANAGER && resultCode == CardManagerFragment.REQUEST_CODE_CARD_MANAGER_COMPLETE) {
            //刷新首页或卡片
            mPresenter.getUserQuickCard(DataManager.getOpenid());
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("HomeHomeFragment  可见");

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        LogUtil.i("HomeHomeFragment  不可见");

        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
    }

    @OnClick({R.id.home_chat_btn, R.id.home_call_service_btn, R.id.home_member_add_btn, R.id.home_arc_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_chat_btn:
                ToastUtil.showShort(mContext, getString(R.string.chat_is_developing));

                break;
            case R.id.home_member_add_btn:
                new AddMemberDialog(mActivity).setListener(new OnInputDialogListener() {
                    @Override
                    public void dialogSureClick(String input) {
                        mems.add(new HomeMeberBean());
                        memAdapter.notifyDataSetChanged();
                    }
                }).show();
                break;
            case R.id.home_call_service_btn:
                new CallDialog(mContext).show();
                break;

            case R.id.home_arc_view:
//                start(ExceptionStatusFragment.newInstance());
                break;
        }
    }

    @Override
    public void onDataLoadSucc(int requestCode, Object object) {
        switch (requestCode) {
            case HomePresenter.REQUEST_CDOE_GET_CARD_MY:

                HomeRecBean homeRecBean = (HomeRecBean) object;
                cards.clear();
                cards.addAll(homeRecBean.content);
                cards.add(new HomeRecBean.Card(HomeRecBean.TYPE_ADD));
                homeCardAdapter.notifyDataSetChanged();
                break;

            case HomePresenter.REQUEST_CDOE_WEATHER:
                WeatherBean weatherBean = (WeatherBean) object;
                if (null != weatherBean.content && weatherBean.content.size() > 0) {
                    WeatherBean.Weather weather = weatherBean.content.get(weatherBean.content.size() / 2);
                    homeWeatherNumTv.setText(weather.temperature + "°");
                    homeWeatherDescTv.setText(weather.weather);
                }
                break;
        }
    }

    @Override
    public void onDataLoadFailed(int requestCode, ApiException apiException) {

    }


    @Override
    public void onDestroyView() {
        //销毁广播
        if (null != batterReceiver) {
            mActivity.unregisterReceiver(batterReceiver);
            batterReceiver = null;
        }
        if (null != networkReceiver) {
            mActivity.unregisterReceiver(networkReceiver);
            networkReceiver = null;
        }
        super.onDestroyView();
    }

    /**
     * 监听获取手机系统剩余电量
     */
    class BatteryReceiver extends BroadcastReceiver {
        private ImageView icon;

        public BatteryReceiver(ImageView icon) {
            this.icon = icon;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            int current = intent.getExtras().getInt("level");// 获得当前电量
            int total = intent.getExtras().getInt("scale");// 获得总电量
            int percent = current * 100 / total;

            if (percent < 20) {
                icon.getDrawable().setLevel(1);
            } else {
                icon.getDrawable().setLevel(0);
            }

            LogUtil.i(" == level == " + current);
            LogUtil.i(" == scale == " + total);
        }
    }

    class NetworkChangeReceiver extends BroadcastReceiver {

        private ImageView icon;

        public NetworkChangeReceiver(ImageView icon) {
            this.icon = icon;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (NetworkUtil.getNetWork(context)) {
                icon.getDrawable().setLevel(0);
            } else {
                icon.getDrawable().setLevel(1);
            }
        }
    }


    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }
}
