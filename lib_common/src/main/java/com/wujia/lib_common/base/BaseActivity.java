package com.wujia.lib_common.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import androidx.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by xmren on 2017/7/31.
 */

public abstract class BaseActivity extends SupportActivity {
    private String currentName;
    protected Activity mContext;
    protected Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentName = getClass().getSimpleName();
        if (getIntent() != null) {
            onPrepareIntent(getIntent());
        }

        setContentView();
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        onViewCreated();
        initEventAndData(savedInstanceState);
    }


    public void setContentView() {

        setContentView(getLayout());

    }


    protected void onPrepareIntent(Intent intent) {
    }

    protected void onViewCreated() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }


    protected abstract int getLayout();


    protected abstract void initEventAndData(Bundle savedInstanceState);


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        TCAgent.onPageStart(mContext, currentName);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        TCAgent.onPageEnd(mContext, currentName);
        if (isFinishing())
            onPreDestroy();
    }

    protected void onPreDestroy() {

    }

    protected boolean isActivityTop(Class cls, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return name.equals(cls.getName());
    }

    public boolean isScreenOff() {
        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        return !pm.isScreenOn();
    }

    protected <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    protected void hideNavigationBar() {
        int flags;
        int curApiVersion = android.os.Build.VERSION.SDK_INT;
        // This work only for android 4.4+
        if (curApiVersion >= Build.VERSION_CODES.KITKAT) {
            // This work only for android 4.4+
            // hide navigation bar permanently in android activity
            // touch the screen, the navigation bar will not show
            flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;

        } else {
            // touch the screen, the navigation bar will show
            flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // must be executed in main thread


        getWindow().getDecorView().setSystemUiVisibility(flags);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideNavigationBar();
        }
    }

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    //    activity跳转 start
    public void toActivity(Class targetActivity) {
        toActivity(targetActivity, null);
    }

    public void toActivity(Class targetActivity, Bundle bundle) {
        Intent intent = new Intent(this, targetActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        toActivity(intent);
    }

    public void toActivityResultNormal(Class<?> toClsActivity, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, toClsActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void toActivity(Intent intent) {
        startActivity(intent);
    }
    //    activity跳转 end
}