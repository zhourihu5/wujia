package com.jingxi.smartlife.pad.mvp;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.jingxi.smartlife.pad.R;
import com.wujia.lib_common.utils.LogUtil;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by dongzhong on 2018/5/30.
 */

public class FloatingButtonService extends Service {
    private static final String TAG = "FloatingButtonService";
    public static boolean isStarted = false;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private View floatingView;
    private long startTime;
    @Override
    public void onCreate() {
        super.onCreate();
        startTime=System.currentTimeMillis();
        isStarted = true;
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        layoutParams.width = 200;
//        layoutParams.height = 200;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.x = 0;
        layoutParams.y = 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isStarted=false;
        removeFloatingWindow();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }
    void removeFloatingWindow(){
        windowManager.removeView(floatingView);
//        windowManager.removeViewImmediate(floatingView);
    }
    private void showFloatingWindow() {
        if (Settings.canDrawOverlays(this)) {
            floatingView=View.inflate(getApplicationContext(),R.layout.floating_back,null);
//           floatingView = new Button(getApplicationContext());
//            floatingView.setText("go home");
//            floatingView.setBackgr oundColor(Color.BLUE);
//            floatingView.setBackgroundResource(R.mipmap.ic_launcher_round);
            windowManager.addView(floatingView, layoutParams);

            floatingView.setOnTouchListener(new FloatingOnTouchListener());

        }
    }

    private void closeWPS(String packageName) {
        try {
            ActivityManager m = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            Method method = m.getClass().getMethod("forceStopPackage", String.class);
            method.setAccessible(true);
            method.invoke(m, packageName);
//            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String getTopActivityPackage(){
        String topActivity = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager m = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
            if (m != null) {
                long now = System.currentTimeMillis();
                //获取60秒之内的应用数据
                List<UsageStats> stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, startTime-50*1000, now);
                LogUtil.info(TAG, "Running app number in last 60 seconds : " + stats.size());

                //取得最近运行的一个app，即当前运行的app
                if ((stats != null) && (!stats.isEmpty())) {
                    int j = 0;
                    for (int i = 0; i < stats.size(); i++) {
                        if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
                            j = i;
                        }
                        topActivity = stats.get(j).getPackageName();
//                        LogUtil.info(TAG, "top running app is : "+topActivity);
                    }

                }else {

                }
//                return stats.size();
            }
        }else {
            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = activityManager.getRunningTasks(1).get(0).topActivity;
            topActivity = cn.getPackageName();
        }
        LogUtil.info(TAG, "top running app is : "+topActivity);
        return topActivity;
    }


    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;
        GestureDetector gestureDetector=new GestureDetector(FloatingButtonService.this,new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                LogUtil.i("onDown");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                LogUtil.i("onShowPress");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                LogUtil.i("onSingleTapUp");
                if(getPackageName().equals(getTopActivityPackage())){
                    stopSelf();
                    return false;
                }
                closeWPS(getTopActivityPackage());//todo optimize it to go to the same page  before.
//                    closeWPS("com.jingxi.smartlife.pad");
//                    try {
//                        //虚拟返回按钮
//                        Runtime.getRuntime().exec("adb shell input keyevent 4");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    onKeyDown(KeyEvent.KEYCODE_BACK, null);
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                LogUtil.i("onScroll");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                LogUtil.i("onLongPress");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                LogUtil.i("onFling");
                return false;
            }
        });
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    windowManager.updateViewLayout(view, layoutParams);
                    break;
                default:
                    break;
            }
            gestureDetector.onTouchEvent(event);
            return false;
        }
    }
}
