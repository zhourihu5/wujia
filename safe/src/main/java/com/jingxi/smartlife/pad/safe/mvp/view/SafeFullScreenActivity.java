package com.jingxi.smartlife.pad.safe.mvp.view;

import android.os.Bundle;
import android.view.View;

import com.jingxi.smartlife.pad.safe.R;
import com.wujia.lib_common.base.BaseActivity;

public class SafeFullScreenActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected int getLayout() {
        return R.layout.activity_safe_full_screen;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

        findViewById(R.id.img1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img1) {
            finish();
        }
    }
}
