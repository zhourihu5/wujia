package com.wujia.lib_common.base

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import butterknife.ButterKnife
import butterknife.Unbinder
import me.yokeyword.fragmentation.SupportActivity

/**
 * Created by xmren on 2017/7/31.
 */

abstract class BaseActivity : SupportActivity() {
    private var currentName: String? = null
    protected var mContext: Activity?=null
    protected var mUnBinder: Unbinder?=null


    protected abstract val layout: Int

    val isScreenOff: Boolean
        get() {
            val pm = mContext?.getSystemService(Context.POWER_SERVICE) as PowerManager
            return !pm.isScreenOn
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentName = javaClass.simpleName
        if (intent != null) {
            onPrepareIntent(intent)
        }

        setContentView()
        mUnBinder = ButterKnife.bind(this)
        mContext = this
        onViewCreated()
        initEventAndData(savedInstanceState)
    }


    open fun setContentView() {

        setContentView(layout)

    }


    protected fun onPrepareIntent(intent: Intent) {}

    protected open fun onViewCreated() {

    }


    override fun onDestroy() {
        super.onDestroy()
        mUnBinder?.unbind()
    }


    protected abstract fun initEventAndData(savedInstanceState: Bundle?)


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onResume() {
        super.onResume()
        //        TCAgent.onPageStart(mContext, currentName);
    }

    override fun onPause() {
        super.onPause()
        //        TCAgent.onPageEnd(mContext, currentName);
        if (isFinishing)
            onPreDestroy()
    }

    protected fun onPreDestroy() {

    }

    protected fun isActivityTop(cls: Class<*>, context: Context): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val name = manager.getRunningTasks(1)[0].topActivity.className
        return name == cls.name
    }

    protected fun <T : View> `$`(resId: Int): T {
        return super.findViewById<View>(resId) as T
    }

    protected fun hideNavigationBar() {
        val flags: Int
        val curApiVersion = android.os.Build.VERSION.SDK_INT
        // This work only for android 4.4+
        if (curApiVersion >= Build.VERSION_CODES.KITKAT) {
            // This work only for android 4.4+
            // hide navigation bar permanently in android activity
            // touch the screen, the navigation bar will not show
            flags = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)

        } else {
            // touch the screen, the navigation bar will show
            flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }

        // must be executed in main thread


        window.decorView.systemUiVisibility = flags
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideNavigationBar()
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }

    @JvmOverloads
    fun toActivity(targetActivity: Class<*>, bundle: Bundle? = null) {
        val intent = Intent(this, targetActivity)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        toActivity(intent)
    }

    fun toActivityResultNormal(toClsActivity: Class<*>, bundle: Bundle?, requestCode: Int) {
        val intent = Intent(this, toClsActivity)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    fun toActivity(intent: Intent) {
        startActivity(intent)
    }
    //    activity跳转 end
}//    activity跳转 start