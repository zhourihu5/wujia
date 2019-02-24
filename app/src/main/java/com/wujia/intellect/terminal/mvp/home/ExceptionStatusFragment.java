package com.wujia.intellect.terminal.mvp.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.wujia.businesslib.TitleFragment;
import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.home.adapter.HomeExceptionAdapter;
import com.wujia.intellect.terminal.mvp.home.data.HomeExceptionBean;
import com.wujia.lib.widget.HorizontalTabBar;
import com.wujia.lib.widget.HorizontalTabItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Author: created by shenbingkai on 2019/2/11 18 30
 * Email:  shenbingkai@gamil.com
 * Description: 异常状态详情
 */
public class ExceptionStatusFragment extends TitleFragment implements HorizontalTabBar.OnTabSelectedListener{


    @BindView(R.id.tab_layout)
    HorizontalTabBar tabBar;
    @BindView(R.id.rv_exception)
    RecyclerView rvException;

    public ExceptionStatusFragment() {

    }

    public static ExceptionStatusFragment newInstance() {
        ExceptionStatusFragment fragment = new ExceptionStatusFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_exception;
    }

    @Override
    public int getTitle() {
        return R.string.exception_status_info;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        tabBar.addItem(new HorizontalTabItem(mContext, com.wujia.intellect.terminal.message.R.string.all));
        tabBar.addItem(new HorizontalTabItem(mContext, com.wujia.intellect.terminal.message.R.string.readed));
        tabBar.addItem(new HorizontalTabItem(mContext, com.wujia.intellect.terminal.message.R.string.unread));

        tabBar.setOnTabSelectedListener(this);

        List<HomeExceptionBean> datas=new ArrayList<>();
        datas.add(new HomeExceptionBean());
        datas.add(new HomeExceptionBean());
        datas.add(new HomeExceptionBean());
        datas.add(new HomeExceptionBean());
        rvException.setAdapter(new HomeExceptionAdapter(mActivity,datas));
    }

    @Override
    public void onTabSelected(int position, int prePosition) {

    }
}
