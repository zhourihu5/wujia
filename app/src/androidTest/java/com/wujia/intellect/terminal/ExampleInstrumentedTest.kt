package com.jingxi.smartlife.pad

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4

import com.jingxi.smartlife.pad.safe.mvp.view.VideoCallActivity
import com.wujia.lib.widget.util.ToastUtil

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.assertEquals

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        val intent = Intent(appContext, VideoCallActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("sessionId", "aaaa")
        appContext.startActivity(intent)
        assertEquals("com.jingxi.smartlife.pad", appContext.packageName)
    }

    @Test
    fun toastUtil() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        val handler = Handler(Looper.getMainLooper())
        for (i in 0..4) {
            handler.postDelayed({ ToastUtil.showShort(appContext, "toastUtilTest$i") }, (1000 * i).toLong())

        }

        assertEquals("com.jingxi.smartlife.pad", appContext.packageName)
    }

}
