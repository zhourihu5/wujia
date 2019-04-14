package com.jingxi.smartlife.pad.mvp.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jingxi.smartlife.pad.R;
import com.jingxi.smartlife.pad.mvp.MainActivity;
import com.jingxi.smartlife.pad.mvp.home.data.Advert;
import com.wujia.businesslib.Constants;
import com.wujia.businesslib.base.WebViewActivity;
import com.wujia.lib.imageloader.ImageLoaderManager;
import com.wujia.lib_common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    private Advert advert;

    @Override
    protected int getLayout() {
        return R.layout.activity_advert;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        advert = (Advert) getIntent().getSerializableExtra(Constants.INTENT_KEY_1);
        if (advert != null) {
            ImageLoaderManager.getInstance().loadImage(advert.url, imgAdvert);
            btnDetails.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.img_advert, R.id.btn_details})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_advert) {

        } else if (view.getId() == R.id.btn_details) {
            if (advert != null && !TextUtils.isEmpty(advert.href)) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.INTENT_KEY_1, advert.href);
                toActivity(WebViewActivity.class, bundle);
            }
        }
        finish();
    }
}
