package com.wujia.intellect.terminal.market.mvp.view;

import android.os.Bundle;

import com.wujia.businesslib.TitleFragment;
import com.wujia.intellect.terminal.market.R;

/**
 * Author: created by shenbingkai on 2019/2/24 16 05
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class CarFragment extends TitleFragment {

    public static CarFragment newInstance() {
        CarFragment fragment = new CarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_car;
    }

    @Override
    public int getTitle() {
        return R.string.buy_car;
    }
}
