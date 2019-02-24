package com.wujia.intellect.terminal.mvp.setting.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wujia.businesslib.TitleFragment;
import com.wujia.intellect.terminal.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-26
 * description ：检查更新
 */
public class UpdateFragment extends TitleFragment {

    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_version_desc)
    TextView tvVersionDesc;
    @BindView(R.id.btn_update_now)
    TextView btnUpdateNow;
    @BindView(R.id.update_check_layout)
    LinearLayout updateCheckLayout;
    @BindView(R.id.progress_update)
    ProgressBar progressUpdate;
    @BindView(R.id.tv_update_downloaded)
    TextView tvUpdateDownloaded;
    @BindView(R.id.tv_update_apik_count)
    TextView tvUpdateApikCount;
    @BindView(R.id.update_ing_layout)
    LinearLayout updateIngLayout;

    public static UpdateFragment newInstance() {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_update;
    }

    @Override
    public int getTitle() {
        return R.string.check_update;
    }

    @OnClick(R.id.btn_update_now)
    public void onViewClicked() {
        updateCheckLayout.setVisibility(View.GONE);
        updateIngLayout.setVisibility(View.VISIBLE);
    }
}
