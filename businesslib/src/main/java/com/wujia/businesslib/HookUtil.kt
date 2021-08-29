package com.wujia.businesslib

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.os.*
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import com.wujia.lib_common.utils.LogUtil
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * Author: created by shenbingkai on 2019/4/5 00 09
 * Email:  shenbingkai@gamil.com
 * Description:
 * visit https://www.cnblogs.com/genggeng/p/7716482.html
 */
object HookUtil {


    fun hookWebView() {
        val sdkInt = Build.VERSION.SDK_INT
        try {
            val factoryClass = Class.forName("android.webkit.WebViewFactory")
            val field = factoryClass.getDeclaredField("sProviderInstance")
            field.isAccessible = true
            var sProviderInstance: Any? = field.get(null)
            if (sProviderInstance != null) {
                LogUtil.i("sProviderInstance isn't null")
                return
            }
            val getProviderClassMethod: Method
            getProviderClassMethod = if (sdkInt > 22) {
                factoryClass.getDeclaredMethod("getProviderClass")
            } else if (sdkInt == 22) {
                factoryClass.getDeclaredMethod("getFactoryClass")
            } else {
                LogUtil.i("Don't need to Hook WebView")
                return
            }
            getProviderClassMethod.isAccessible = true
            val providerClass = getProviderClassMethod.invoke(factoryClass) as Class<*>
            val delegateClass = Class.forName("android.webkit.WebViewDelegate")
            val providerConstructor = providerClass.getConstructor(delegateClass)
            if (providerConstructor != null) {
                providerConstructor.isAccessible = true
                val declaredConstructor = delegateClass.getDeclaredConstructor()
                declaredConstructor.isAccessible = true
                sProviderInstance = providerConstructor.newInstance(declaredConstructor.newInstance())
                field.set("sProviderInstance", sProviderInstance)
            }
            LogUtil.i("Hook done!")

        } catch (e: Throwable) {
            e.printStackTrace()
        }

    }

    internal class ReferenceCleaner(private val inputMethodManager: InputMethodManager, private val mHField: Field, private val mServedViewField: Field,
                                    private val finishInputLockedMethod: Method) : MessageQueue.IdleHandler, View.OnAttachStateChangeListener, ViewTreeObserver.OnGlobalFocusChangeListener {

        override fun onGlobalFocusChanged(oldFocus: View?, newFocus: View?) {
            if (newFocus == null) {
                return
            }
            oldFocus?.removeOnAttachStateChangeListener(this)
            Looper.myQueue().removeIdleHandler(this)
            newFocus.addOnAttachStateChangeListener(this)
        }

        override fun onViewAttachedToWindow(v: View) {}

        override fun onViewDetachedFromWindow(v: View) {
            v.removeOnAttachStateChangeListener(this)
            Looper.myQueue().removeIdleHandler(this)
            Looper.myQueue().addIdleHandler(this)
        }

        override fun queueIdle(): Boolean {
            clearInputMethodManagerLeak()
            return false
        }

        private fun clearInputMethodManagerLeak() {
            try {
                val lock = mHField.get(inputMethodManager)
                // This is highly dependent on the InputMethodManager implementation.
                synchronized(lock) {
                    val servedView = mServedViewField.get(inputMethodManager) as View
                    if (servedView != null) {

                        val servedViewAttached = servedView.windowVisibility != View.GONE

                        if (servedViewAttached) {
                            // The view held by the IMM was replaced without a global focus change. Let's make
                            // sure we get notified when that view detaches.

                            // Avoid double registration.
                            servedView.removeOnAttachStateChangeListener(this)
                            servedView.addOnAttachStateChangeListener(this)
                        } else {
                            // servedView is not attached. InputMethodManager is being stupid!
                            val activity = extractActivity(servedView.context)
                            if (activity == null || activity.window == null) {
                                // Unlikely case. Let's finish the input anyways.
                                finishInputLockedMethod.invoke(inputMethodManager)
                            } else {
                                val decorView = activity.window.peekDecorView()
                                val windowAttached = decorView.windowVisibility != View.GONE
                                if (!windowAttached) {
                                    finishInputLockedMethod.invoke(inputMethodManager)
                                } else {
                                    decorView.requestFocusFromTouch()
                                }
                            }
                        }
                    }
                }
            } catch (unexpected: IllegalAccessException) {
                LogUtil.tr("IMMLeaks", "Unexpected reflection exception", unexpected)
            } catch (unexpected: InvocationTargetException) {
                LogUtil.tr("IMMLeaks", "Unexpected reflection exception", unexpected)
            }

        }

        private fun extractActivity(context: Context): Activity? {
            var context = context
            while (true) {
                if (context is Application) {
                    return null
                } else if (context is Activity) {
                    return context
                } else if (context is ContextWrapper) {
                    val baseContext = context.baseContext
                    // Prevent Stack Overflow.
                    if (baseContext === context) {
                        return null
                    }
                    context = baseContext
                } else {
                    return null
                }
            }
        }
    }

    fun fixInputMethodManagerLeak(destContext: Context?) {//fixme not effect.
        if (destContext == null) {
            return
        }

        val imm = destContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        try {
            InputMethodManager::class.java.getDeclaredMethod("windowDismissed", IBinder::class.java).invoke(imm,
                    (destContext as Activity).window.decorView.windowToken)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val arr = arrayOf("mCurRootView", "mServedView", "mNextServedView")
        var f: Field? = null
        var obj_get: Any? = null
        for (i in arr.indices) {
            val param = arr[i]
            try {
                f = imm.javaClass.getDeclaredField(param)
                if (!f!!.isAccessible) {
                    f.isAccessible = true
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm)
                if (obj_get is View) {
                    val v_get = obj_get as View?
                    if (v_get!!.context === destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null) // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        //                        if (QLog.isColorLevel()) {
                        LogUtil.i("fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get!!.context + " dest_context=" + destContext)
                        //                        }
                        break
                    }
                }
            } catch (t: Throwable) {
                t.printStackTrace()
            }

        }
    }
}
