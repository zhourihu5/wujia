package com.jingxi.smartlife.pad.mvp.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jingxi.smartlife.pad.mvp.home.contract.HomeContract;
import com.jingxi.smartlife.pad.mvp.home.data.Advert;
import com.jingxi.smartlife.pad.mvp.login.AdvertActivity;
import com.jingxi.smartlife.pad.safe.mvp.view.VideoCallActivity;
import com.jingxi.smartlife.pad.sdk.JXPadSdk;
import com.jingxi.smartlife.pad.sdk.push.OnPushedListener;
import com.jingxi.smartlife.pad.sdk.push.PushManager;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.wujia.businesslib.Constants;
import com.wujia.businesslib.DataBaseUtil;
import com.wujia.businesslib.base.DataManager;
import com.wujia.businesslib.base.MvpFragment;
import com.wujia.businesslib.base.WebViewFragment;
import com.wujia.businesslib.data.DBMessage;
import com.wujia.businesslib.data.LinkUrlBean;
import com.wujia.businesslib.event.EventBusUtil;
import com.wujia.businesslib.event.EventCardChange;
import com.wujia.businesslib.event.EventMemberChange;
import com.wujia.businesslib.event.EventMsg;
import com.wujia.businesslib.event.EventSafeState;
import com.wujia.businesslib.event.EventWakeup;
import com.wujia.businesslib.event.IMiessageInvoke;
import com.wujia.businesslib.listener.OnDialogListener;
import com.wujia.businesslib.listener.OnInputDialogListener;
import com.jingxi.smartlife.pad.R;
import com.jingxi.smartlife.pad.mvp.MainActivity;
import com.jingxi.smartlife.pad.mvp.home.adapter.HomeMemberAdapter;
import com.jingxi.smartlife.pad.mvp.home.adapter.HomeNotifyAdapter;
import com.jingxi.smartlife.pad.mvp.home.adapter.HomeCardAdapter;
import com.jingxi.smartlife.pad.mvp.home.contract.HomePresenter;
import com.jingxi.smartlife.pad.mvp.home.data.HomeMeberBean;
import com.jingxi.smartlife.pad.mvp.home.data.HomeNotifyBean;
import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean;
import com.wujia.businesslib.data.MessageBean;
import com.jingxi.smartlife.pad.mvp.home.data.WeatherBean;
import com.jingxi.smartlife.pad.mvp.home.view.AddMemberDialog;
import com.jingxi.smartlife.pad.mvp.home.view.MessageDialog;
import com.jingxi.smartlife.pad.mvp.setting.view.CardManagerFragment;
import com.wujia.lib.widget.HomeArcView;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.base.view.HomeCardDecoration;
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
public class HomeHomeFragment extends MvpFragment<HomePresenter> implements HomeContract.View, OnPushedListener {

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

    private PushManager pushManager;
    private boolean isRefreshCard = false;

    private EventSafeState eventSafeState = new EventSafeState(new IMiessageInvoke<EventSafeState>() {
        @Override
        public void eventBus(EventSafeState event) {

            homeArcView.setText(event.online ? "正常\n" : "异常\n");
        }
    });
    private EventMsg eventMsg = new EventMsg(new IMiessageInvoke<EventMsg>() {
        @Override
        public void eventBus(EventMsg event) {
            if (event.type == EventMsg.TYPE_READ) {
                setNotify();
            }
        }
    });
    private EventCardChange eventCardChange = new EventCardChange(new IMiessageInvoke<EventCardChange>() {
        @Override
        public void eventBus(EventCardChange event) {
            isRefreshCard = true;
        }
    });
    private EventMemberChange eventMemberChange = new EventMemberChange(new IMiessageInvoke<EventMemberChange>() {
        @Override
        public void eventBus(EventMemberChange event) {
            if (null != mems && null != memAdapter) {
                ArrayList<HomeMeberBean> temp = DataBaseUtil.query(HomeMeberBean.class);
                mems.clear();
                mems.addAll(temp);
                memAdapter.notifyDataSetChanged();
            }
        }
    });

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

        pushManager = JXPadSdk.getPushManager();
        pushManager.addCallback(this);
        LogUtil.i("bindTags  accid =" + DataManager.getAccid() + "  prod_homePad_" + DataManager.getCommunityId());
        pushManager.bindAccount(DataManager.getAccid());
        pushManager.bindTags(DataManager.getAccid(), "prod_homePad_" + DataManager.getCommunityId());

        mPresenter.getUserQuickCard(DataManager.getOpenid());
        mPresenter.getWeather(DataManager.getCommunityId());

        EventBusUtil.register(eventSafeState);
        EventBusUtil.register(eventMsg);
        EventBusUtil.register(eventCardChange);
        EventBusUtil.register(eventMemberChange);
    }


    private void setMemberView() {
        mems = new ArrayList<>();
        ArrayList<HomeMeberBean> temp = DataBaseUtil.query(HomeMeberBean.class);
        mems.clear();
        mems.addAll(temp);
        rvHomeMember.addItemDecoration(new HorizontalDecoration(10));
        memAdapter = new HomeMemberAdapter(mActivity, mems);
        rvHomeMember.setAdapter(memAdapter);

    }

    private void setCardView() {

        cards = new ArrayList<>();
        homeCardAdapter = new HomeCardAdapter(mActivity, cards);
        rvHomeCard.addItemDecoration(new HomeCardDecoration(68));
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
                        start(CardManagerFragment.newInstance());

                        break;

                }
            }
        });
    }

    private void parseLinkUrl(String json) {
        LinkUrlBean linkUrl = GsonUtil.GsonToBean(json, LinkUrlBean.class);
        switch (linkUrl.code) {
            case LinkUrlBean.CODE_TYPE_MARKET://服务市场
                switch (linkUrl.children.code) {
                    case LinkUrlBean.Children1.CODE_TYPE_FIND:
                        ((MainActivity) mActivity).switchHomeTab(MainActivity.POSITION_MARKET, 1);
                        break;

                    case LinkUrlBean.Children1.CODE_TYPE_GOV:
                        ((MainActivity) mActivity).switchHomeTab(MainActivity.POSITION_MARKET, 2);
                        break;

                    case LinkUrlBean.Children1.CODE_TYPE_ALL:
                        ((MainActivity) mActivity).switchHomeTab(MainActivity.POSITION_MARKET, 3);
                        break;
                }
                break;

            case LinkUrlBean.CODE_TYPE_PROPERTY://物业服务
                switch (linkUrl.children.code) {
                    case LinkUrlBean.Children1.CODE_TYPE_REPORT:
                        ((MainActivity) mActivity).switchHomeTab(MainActivity.POSITION_PROPERTY, 0);
                        break;

                    case LinkUrlBean.Children1.CODE_TYPE_PHONE:
                        ((MainActivity) mActivity).switchHomeTab(MainActivity.POSITION_PROPERTY, 1);
                        break;
                }
                break;
        }
    }

    private void setNotify() {
//        List<HomeNotifyBean> notifys = new ArrayList<>();
//        notifys.add(new HomeNotifyBean());
//        notifys.add(new HomeNotifyBean());
//        notifys.add(new HomeNotifyBean());

        QueryBuilder builder = new QueryBuilder<>(DBMessage.class);
        builder.limit(0, 3).whereEquals("_read_state", 0).appendOrderDescBy("_id");

        final ArrayList<DBMessage> notifys = DataBaseUtil.query(builder);

//        rvHomeMsg.addItemDecoration(new HorizontalDecoration(25));
        HomeNotifyAdapter notifyAdapter = new HomeNotifyAdapter(mActivity, notifys);
        rvHomeMsg.setAdapter(notifyAdapter);
        notifyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnRVItemClickListener() {
            @Override
            public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                new MessageDialog(mContext, notifys.get(position))
                        .setListener(new OnDialogListener() {
                            @Override
                            public void dialogSureClick() {
                                setNotify();
                                EventBusUtil.post(new EventMsg(EventMsg.TYPE_READ));
                            }
                        }).show();
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

//    @Override
//    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
//        super.onFragmentResult(requestCode, resultCode, data);
//        if (requestCode == CardManagerFragment.REQUEST_CODE_CARD_MANAGER && resultCode == CardManagerFragment.REQUEST_CODE_CARD_MANAGER_COMPLETE) {
//            //刷新首页或卡片
//            mPresenter.getUserQuickCard(DataManager.getOpenid());
//        }
//    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("HomeHomeFragment  可见 " + DataManager.getFamilyId());
        if (null != homeCardAdapter && homeCardAdapter.getDatas().size() == 0) {
            isRefreshCard = true;
        }
        if (isRefreshCard) {
            mPresenter.getUserQuickCard(DataManager.getOpenid());
        }
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
//                mPresenter.getManagerMessageById(HomeNotifyBean.TYPE_NOTIFY, "200");
                break;
            case R.id.home_member_add_btn:
                new AddMemberDialog(mActivity).setListener(new OnInputDialogListener() {
                    @Override
                    public void dialogSureClick(String input) {
                        ArrayList<HomeMeberBean> temp = DataBaseUtil.query(HomeMeberBean.class);
                        mems.clear();
                        mems.addAll(temp);
                        memAdapter.notifyDataSetChanged();
                    }
                }).show();
                break;
            case R.id.home_call_service_btn:
//                new CallDialog(mContext).show();
                ToastUtil.showShort(mContext, getString(R.string.not_join));
                //TODO 物业联系方式弹框
                break;

            case R.id.home_arc_view:
//                start(ExceptionStatusFragment.newInstance());
//                mPresenter.getPropertyMessageById(HomeNotifyBean.TYPE_PROPERTY, "1");

//                Advert advert = new Advert();
//                advert.href = "http://politics.cntv.cn/leaders/person/xijinping/index.shtml";
//                advert.url = "http://image.house-keeper.cn/groupBuy/decoration/2019-02-01/0f2ca07a-48b2-42a1-b59f-3a581a087565.jpg";
//                Intent intent = new Intent(mActivity, AdvertActivity.class);
//                intent.putExtra(Constants.INTENT_KEY_1, advert);
//                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDataLoadSucc(int requestCode, Object object) {
        switch (requestCode) {
            case HomePresenter.REQUEST_CDOE_GET_CARD_MY:
                isRefreshCard = false;
                HomeRecBean homeRecBean = (HomeRecBean) object;
                cards.clear();
                cards.addAll(homeRecBean.content);
                cards.add(new HomeRecBean.Card(HomeRecBean.TYPE_ADD));
                homeCardAdapter.notifyDataSetChanged();

                break;

            case HomePresenter.REQUEST_CDOE_WEATHER:
                WeatherBean weatherBean = (WeatherBean) object;
                String curdate = DateUtil.getCurrentyyyymmddhh() + "00";

                for (WeatherBean.Weather weather : weatherBean.content) {
                    if (weather.time.equals(curdate)) {
                        homeWeatherNumTv.setText(weather.temperature + "°");
                        homeWeatherDescTv.setText(weather.weather);
                    }
                }

                break;

            case HomePresenter.REQUEST_CDOE_MESSAGE:

                MessageBean temp = (MessageBean) object;
                MessageBean.Message bean = temp.content;

                DBMessage m = new DBMessage();

                m._type = temp._type;
                m.title = bean.propertyMessage.title;
                m.communityId = bean.propertyMessage.communityId;
                m.createDate = bean.propertyMessage.createDate;
                m.id = bean.propertyMessage.id;
                m.pureText = bean.propertyMessage.pureText;
                m.senderAccId = bean.propertyMessage.senderAccId;
                m.type = bean.propertyMessage.type;
                if (TextUtils.equals(m._type, DBMessage.TYPE_NOTIFY)) {
                    m.typeText = bean.propertyMessage.typeName;
                } else if (TextUtils.equals(m._type, DBMessage.TYPE_PROPERTY)) {
                    m.typeText = bean.propertyMessage.typeMsg;
                }

                DataBaseUtil.insert(m);

                setNotify();
                EventBusUtil.post(new EventMsg(EventMsg.TYPE_NEW_MSG));
                break;
        }
    }

//    private void setWeather() {
//        String curdate = DateUtil.getCurrentyyyymmddhh();
////        ArrayList<WeatherBean.Weather> weatherlist = DataBaseUtil.queryEquals("time", curdate, WeatherBean.Weather.class);
////
////        if (null != weatherlist && weatherlist.size() > 0) {
////            WeatherBean.Weather weather = weatherlist.get(0);
////            homeWeatherNumTv.setText(weather.temperature + "°");
////            homeWeatherDescTv.setText(weather.weather);
////        }
//    }

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

    @Override
    public void onReceiverMessage(String content) {

        if (TextUtils.isEmpty(content))
            return;
        try {
            LogUtil.i("收到新消息 " + content);
            HomeNotifyBean notify = GsonUtil.GsonToBean(content, HomeNotifyBean.class);
            if (TextUtils.equals(notify.type, HomeNotifyBean.TYPE_PROPERTY)) {
                mPresenter.getPropertyMessageById(HomeNotifyBean.TYPE_PROPERTY, notify.propertyMessage);
            } else if (TextUtils.equals(notify.type, HomeNotifyBean.TYPE_NOTIFY)) {
                mPresenter.getManagerMessageById(HomeNotifyBean.TYPE_NOTIFY, notify.propertyMessage);
            } else if (TextUtils.equals(notify.type, HomeNotifyBean.TYPE_HOME_CARD)) {
                mPresenter.getUserQuickCard(DataManager.getOpenid());
            } else if (TextUtils.equals(notify.type, HomeNotifyBean.TYPE_HOME_AD)) {
                Advert advert = notify.adLeapInfo;
                Intent intent = new Intent(mActivity, AdvertActivity.class);
                intent.putExtra(Constants.INTENT_KEY_1, advert);
                startActivity(intent);
                EventBusUtil.post(new EventWakeup());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i("消息数据 json解析异常" + content);
        }

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
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(eventSafeState);
        EventBusUtil.unregister(eventMsg);
        EventBusUtil.unregister(eventCardChange);
        EventBusUtil.unregister(eventMemberChange);
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }
}
