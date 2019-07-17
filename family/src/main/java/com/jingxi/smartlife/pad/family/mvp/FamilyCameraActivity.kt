package com.jingxi.smartlife.pad.family.mvp

import android.os.Bundle
import android.view.View

import com.jingxi.smartlife.pad.family.R
import com.wujia.lib_common.base.BaseActivity

class FamilyCameraActivity : BaseActivity(), View.OnClickListener {
    override val layout: Int
        get() =  R.layout.activity_framily_camera


    override fun initEventAndData(savedInstanceState: Bundle?) {

        findViewById<View>(R.id.img1).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.img1) {
            finish()
        }
    }
}
