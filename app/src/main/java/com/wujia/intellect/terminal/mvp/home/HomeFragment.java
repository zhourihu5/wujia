package com.wujia.intellect.terminal.mvp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.home.adapter.HomeMemberAdapter;
import com.wujia.intellect.terminal.mvp.home.adapter.HomeNotifyAdapter;
import com.wujia.intellect.terminal.mvp.home.adapter.HomeRecAdapter;
import com.wujia.intellect.terminal.mvp.home.data.HomeMeberBean;
import com.wujia.intellect.terminal.mvp.home.data.HomeNotifyBean;
import com.wujia.intellect.terminal.mvp.home.data.HomeRecBean;
import com.wujia.lib.widget.HomeArcView;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.base.baseadapter.base.ItemViewDelegate;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;
import com.wujia.lib_common.base.view.HorizontalDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ： home
 */
public class HomeFragment extends BaseFragment {

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

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initEventAndData() {
        List<HomeMeberBean> mems=new ArrayList<>();
        mems.add(new HomeMeberBean());
        mems.add(new HomeMeberBean());
        mems.add(new HomeMeberBean());
        mems.add(new HomeMeberBean());

        rvHomeMember.addItemDecoration(new HorizontalDecoration(20));
        rvHomeMember.setAdapter(new HomeMemberAdapter(mActivity,mems));

        List<HomeRecBean> cards=new ArrayList<>();
        cards.add(new HomeRecBean(0));
        cards.add(new HomeRecBean(1));
        cards.add(new HomeRecBean(1));
        cards.add(new HomeRecBean(1));
        cards.add(new HomeRecBean(2));
        cards.add(new HomeRecBean(2));

        HomeRecAdapter homeCardAdapter =new HomeRecAdapter(mActivity,cards);
        rvHomeCard.addItemDecoration(new HorizontalDecoration(25));
        rvHomeCard.setAdapter(homeCardAdapter);

        List<HomeNotifyBean> notifys=new ArrayList<>();
        notifys.add(new HomeNotifyBean());
        notifys.add(new HomeNotifyBean());
        notifys.add(new HomeNotifyBean());

        rvHomeMsg.addItemDecoration(new HorizontalDecoration(25));
        rvHomeMsg.setAdapter(new HomeNotifyAdapter(mActivity,notifys));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
    }

    @OnClick({R.id.home_chat_btn, R.id.home_call_service_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_chat_btn:
                break;
            case R.id.home_call_service_btn:
                break;
        }
    }
}
