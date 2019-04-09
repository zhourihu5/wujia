package com.jingxi.smartlife.pad.family.mvp;

import android.os.Bundle;
import android.view.View;

import com.jingxi.smartlife.pad.family.R;
import com.wujia.lib_common.base.BaseActivity;

public class FamilyCameraActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected int getLayout() {
        return R.layout.activity_framily_camera;
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
