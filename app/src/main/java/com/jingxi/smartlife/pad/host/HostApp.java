package com.jingxi.smartlife.pad.host;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import com.jingxi.smartlife.pad.BuildConfig;
import com.jingxi.smartlife.pad.mvp.FloatingButtonService;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.commonsdk.UMConfigure;
import com.wujia.businesslib.HookUtil;
import com.wujia.businesslib.base.BaseApplication;
import com.wujia.lib_common.utils.NetworkUtil;

import cn.jpush.android.api.JPushInterface;
//import xcrash.TombstoneParser;

/**
 * author ：shenbingkai
 * date ：2019-01-08
 * description ：
 */
public class HostApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        NetworkUtil.getNetWork(instance);
        HookUtil.hookWebView();
        HookUtil.fixFocusedViewLeak(this);
        JPushInterface.setDebugMode(BuildConfig.DEBUG); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

        //方便错误统计，方便查看错误日志，便于修复bug
        UMConfigure.init(this, "5d0ddf284ca357c8dc000dd6", "mychanel", UMConfigure.DEVICE_TYPE_PHONE, null);//友盟统计，周日虎的账号


//        if(BuildConfig.DEBUG){
//            // The callback when App process crashed.
//            ICrashCallback callback = new ICrashCallback() {
//                @Override
//                public void onCrash(String logPath, String emergency) {
//                    if (emergency != null) {
//                        debug(logPath, emergency);
//
//                        // Disk is exhausted, send crash report immediately.
//                        sendThenDeleteCrashLog(logPath, emergency);
//                    } else {
//                        // Add some expanded sections. Send crash report at the next time APP startup.
//
//                        // OK
//                        TombstoneManager.appendSection(logPath, "expanded_key_1", "expanded_content");
//                        TombstoneManager.appendSection(logPath, "expanded_key_2", "expanded_content_row_1\nexpanded_content_row_2");
//
//                        // Invalid. (Do NOT include multiple consecutive newline characters ("\n\n") in the content string.)
//                        // TombstoneManager.appendSection(logPath, "expanded_key_3", "expanded_content_row_1\n\nexpanded_content_row_2");
//
//                        debug(logPath, null);
//                    }
//                }
//            };
//
//            // Initialize xCrash.
//            XCrash.init(this, new XCrash.InitParameters()
//                    .setAppVersion("1.2.3-beta456-patch789")
//                    .setJavaRethrow(true)
//                    .setJavaLogCountMax(10)
//                    .setJavaDumpAllThreadsWhiteList(new String[]{"^main$", "^Binder:.*", ".*Finalizer.*"})
//                    .setJavaDumpAllThreadsCountMax(10)
//                    .setJavaCallback(callback)
//                    .setNativeRethrow(true)
//                    .setNativeLogCountMax(10)
//                    .setNativeDumpAllThreadsWhiteList(new String[]{"^com\\.jingxi", "^Signal Catcher$", "^Jit thread pool$", ".*(R|r)ender.*", ".*Chrome.*"})
//                    .setNativeDumpAllThreadsCountMax(10)
//                    .setNativeCallback(callback)
//                    .setPlaceholderCountMax(3)
//                    .setPlaceholderSizeKb(512)
//                    .setLogFileMaintainDelayMs(1000));
//        }





//        Fragmentation.builder()
//                // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
//                .stackViewMode(Fragmentation.BUBBLE)
//                .debug(true) // 实际场景建议.debug(BuildConfig.DEBUG)
//                /**
//                 * 可以获取到{@link me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning}
//                 * 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
//                 */
//                .handleException(new ExceptionHandler() {
//                    @Override
//                    public void onException(Exception e) {
//                        // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
//                        // Bugtags.sendException(e);
//                    }
//                })
//                .install();
    }

    @Override
    protected void runInbackGround() {
        if (FloatingButtonService.isStarted) {
            stopService(new Intent(this, FloatingButtonService.class));
            return;
        }
        if (!Settings.canDrawOverlays(this)) {
//            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT);
//            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
        } else {
            startService(new Intent(this, FloatingButtonService.class));
        }
    }

    @Override
    protected void runInForeGround() {
        stopService(new Intent(this, FloatingButtonService.class));
    }

    /*private void sendThenDeleteCrashLog(String logPath, String emergency) {
        // Parse
        //Map<String, String> map = TombstoneParser.parse(logPath, emergency);
        //String crashReport = new JSONObject(map).toString();

        // Send the crash report to server-side.
        // ......

        // If the server-side receives successfully, delete the log file.
        //
        // Note: When you use the placeholder file feature,
        //       please always use this method to delete tombstone files.
        //
        //TombstoneManager.deleteTombstone(logPath);
    }

    private void debug(String logPath, String emergency) {
        LogUtil.e( "logPath: " + (logPath != null ? logPath : "(null)") + ", emergency: " + (emergency != null ? emergency : "(null)"));

        // Parse and save the crash info to a JSON file for debugging.
        FileWriter writer = null;
        try {
            File debug = new File(getApplicationContext().getFilesDir() + "/tombstones/debug.json");
            debug.createNewFile();
            writer = new FileWriter(debug, false);
            writer.write(new JSONObject(TombstoneParser.parse(logPath, emergency)).toString());
        } catch (Exception e) {
            LogUtil.t( "debug failed", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception ignored) {
                }
            }
        }
    }*/
}
