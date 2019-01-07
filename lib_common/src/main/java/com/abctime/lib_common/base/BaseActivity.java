package com.abctime.lib_common.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.abctime.lib_common.base.plug.ITitlePlug;
import com.abctime.lib_common.base.plug.PlugHelper;
import com.abctime.lib_common.data.SPHelper;
import com.abctime.lib_common.utils.NoDoubleClickUtils;
import com.abctime.lib_common.utils.SoundPoolManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.abctime.lib_common.utils.SoundPoolManager.SOUND_BACK_ID;

/**
 * Created by xmren on 2017/7/31.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private  String currentName ;
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
        if (getLayout() != 0) {
            if (this instanceof ITitlePlug) {
                View content = LayoutInflater.from(this).inflate(getLayout(), null);
                View view = PlugHelper.setupTitle((ITitlePlug) this, content, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (NoDoubleClickUtils.isDoubleClick())
                            return;
                        finish();
                    }
                });
                setContentView(view);
            } else {
                setContentView(LayoutInflater.from(this).inflate(getLayout(), null));
            }
        }
    }


    @Override
    public void finish() {
        boolean slient = (boolean) SPHelper.get(this, "slient", false);
        if (!slient) {
            SoundPoolManager.getInstance().play(SOUND_BACK_ID);
        }
        super.finish();
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

    @Override
    public void onBackPressed() {
        finish();
    }
}