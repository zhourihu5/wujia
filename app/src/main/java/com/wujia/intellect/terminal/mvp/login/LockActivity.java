package com.wujia.intellect.terminal.mvp.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wujia.intellect.terminal.R;
import com.wujia.lib_common.base.BaseActivity;

public class LockActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }
}
