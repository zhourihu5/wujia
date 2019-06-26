package com.jingxi.smartlife.pad;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jingxi.smartlife.pad.safe.mvp.view.VideoCallActivity;
import com.wujia.lib.widget.util.ToastUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Intent intent = new Intent(appContext, VideoCallActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sessionId", "aaaa");
        appContext.startActivity(intent);
        assertEquals("com.jingxi.smartlife.pad", appContext.getPackageName());
    }

    @Test
    public void toastUtil() {
        // Context of the app under test.
        final Context appContext = InstrumentationRegistry.getTargetContext();
        Handler handler = new Handler(Looper.getMainLooper());
        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showShort(appContext, "toastUtilTest" + finalI);
                }
            }, 1000 * finalI);

        }

        assertEquals("com.jingxi.smartlife.pad", appContext.getPackageName());
    }

}
