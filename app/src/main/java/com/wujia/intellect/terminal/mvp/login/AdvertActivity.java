package com.wujia.intellect.terminal.mvp.login;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.MainActivity;
import com.wujia.lib_common.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27 11:57
 * description ：广告页
 */
public class AdvertActivity extends BaseActivity {


    @BindView(R.id.img_advert)
    ImageView imgAdvert;
    @BindView(R.id.btn_details)
    TextView btnDetails;

    @Override
    protected int getLayout() {
        return R.layout.activity_advert;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_details)
    public void onViewClicked() {
        showToast("look details");
        toActivity(MainActivity.class);
        finish();
    }
}
