package com.wujia.intellect.terminal.mvp.setting.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wujia.businesslib.Constants;
import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.home.data.HomeRecBean;
import com.wujia.intellect.terminal.mvp.setting.adapter.HomeCardManagerAdapter;
import com.wujia.lib_common.base.BaseActivity;
import com.wujia.lib_common.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
* author ：shenbingkai@163.com
* date ：2019-02-19 23:56
* description ：卡片管理
*/
public class CardManagerFragment extends BaseFragment {

    public static final int REQUEST_CODE_CARD_MANAGER = 0X1001;
    public static final int REQUEST_CODE_CARD_MANAGER_COMPLETE = 0X1002;


    @BindView(R.id.layout_title_tv)
    TextView layoutTitleTv;
    @BindView(R.id.layout_back_btn)
    TextView layoutBackBtn;
    @BindView(R.id.layout_right_btn)
    TextView layoutRightBtn;
    @BindView(R.id.rv_card_added)
    RecyclerView rvCardAdded;
    @BindView(R.id.rv_card_unadd)
    RecyclerView rvCardUnadd;

    public CardManagerFragment() {
    }

    public static CardManagerFragment newInstance() {
        CardManagerFragment fragment = new CardManagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_manager;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用

        layoutBackBtn.setVisibility(View.VISIBLE);
        layoutRightBtn.setVisibility(View.VISIBLE);

        layoutTitleTv.setText(R.string.manager_home_card);

        //已添加
        final List<HomeRecBean> addList = new ArrayList<>();
        addList.add(new HomeRecBean(0));
        addList.add(new HomeRecBean(0));


        final HomeCardManagerAdapter addedAdapter = new HomeCardManagerAdapter(mContext, addList, HomeCardManagerAdapter.FORM_ADDED);
        rvCardAdded.setAdapter(addedAdapter);

        //未添加
        final List<HomeRecBean> unaddList = new ArrayList<>();
        unaddList.add(new HomeRecBean(1));
        unaddList.add(new HomeRecBean(1));

        final HomeCardManagerAdapter unaddAdapter = new HomeCardManagerAdapter(mContext, unaddList, HomeCardManagerAdapter.FORM_UNADD);
        rvCardUnadd.setAdapter(unaddAdapter);


        addedAdapter.setManagerCardListener(new HomeCardManagerAdapter.OnManagerCardListener() {
            @Override
            public void addCard(int pos) {

            }

            @Override
            public void removeCard(int pos) {
                unaddList.add(addList.remove(pos));
                addedAdapter.notifyItemRemoved(pos);
                unaddAdapter.notifyItemInserted(unaddAdapter.getItemCount());

                addedAdapter.notifyItemRangeChanged(0, addList.size());
                unaddAdapter.notifyItemRangeChanged(0, unaddList.size());
            }
        });

        unaddAdapter.setManagerCardListener(new HomeCardManagerAdapter.OnManagerCardListener() {
            @Override
            public void addCard(int pos) {
                addList.add(unaddList.remove(pos));
                unaddAdapter.notifyItemRemoved(pos);
                addedAdapter.notifyItemInserted(addedAdapter.getItemCount());

                addedAdapter.notifyItemRangeChanged(0, addList.size());
                unaddAdapter.notifyItemRangeChanged(0, unaddList.size());

            }

            @Override
            public void removeCard(int pos) {

            }
        });
    }

    @OnClick({R.id.layout_back_btn, R.id.layout_right_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_back_btn:
                pop();
                break;
            case R.id.layout_right_btn:
                setFragmentResult(REQUEST_CODE_CARD_MANAGER_COMPLETE, null);
                pop();
                break;
        }
    }
}
