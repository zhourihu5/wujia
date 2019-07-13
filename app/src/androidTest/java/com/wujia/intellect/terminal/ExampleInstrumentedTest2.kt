package com.wujia.intellect.terminal

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.jingxi.smartlife.pad.safe.mvp.view.VideoCallActivity
import com.wujia.lib.widget.util.ToastUtil
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest2 {
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


//    @KotlinAn
//    fun test(@KotlinAn mode:Int):Int{
//        ScreenManager.screenMode=5
//        test2(1)
//        return  1
//    }
//    @LightMode
//    fun test2(@LightMode mode:Int):Int{
//        ScreenManager.screenMode=5
//        test(1)
//        return  1
//    }
//
//    @Retention(AnnotationRetention.SOURCE)
//    @IntDef(ScreenManager.LIGHT_MODE_AUTO, ScreenManager.LIGHT_MODE_MANUAL, ScreenManager.LIGHT_MODE_FAILED)
//    annotation class KotlinAn

}
