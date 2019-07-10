package com.jingxi.smartlife.pad.mvp.login

import android.os.Bundle

import com.jingxi.smartlife.pad.R
import com.wujia.lib_common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_lock.*

class LockActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayout(): Int {
        return R.layout.activity_lock
    }

    override fun initEventAndData(savedInstanceState: Bundle) {
        lock_img_bg!!.setOnClickListener { }
    }
}
