package com.wujia.intellect.terminal.mvp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wujia.businesslib.TitleFragment;
import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.home.data.HomeRecBean;
import com.wujia.lib_common.base.BaseMainFragment;

import java.util.ArrayList;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-29
 * description ：
 */
public class ImageTxtFragment extends TitleFragment {

    public static final String KEY_TXT = "txt";
    public static final String KEY_SUBCRIPTION = "subscriptions";

    private String txt = "";
    private ArrayList<HomeRecBean.Subscriptions> subscriptions;

    public ImageTxtFragment() {
    }

    public static ImageTxtFragment newInstance(String txt, ArrayList<HomeRecBean.Subscriptions> subscriptions) {
        ImageTxtFragment fragment = new ImageTxtFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TXT, txt);
        args.putSerializable(KEY_SUBCRIPTION, subscriptions);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_img_txt;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        txt = getArguments().getString(KEY_TXT);
        subscriptions = (ArrayList<HomeRecBean.Subscriptions>) getArguments().getSerializable(KEY_SUBCRIPTION);

    }

    @Override
    public int getTitle() {
        return R.string.setting;
    }
}
