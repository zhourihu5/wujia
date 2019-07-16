package com.jingxi.smartlife.pad.property.mvp.view

import android.os.Bundle
import android.view.View

import com.jingxi.smartlife.pad.property.R
import com.wujia.lib.widget.util.ToastUtil
import com.wujia.lib_common.base.BaseFragment


/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
class SimpleFixFragment : BaseFragment(), View.OnClickListener {

    override fun getLayoutId(): Int {
        return R.layout.fragment_propery_fix_simple
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        `$`<View>(R.id.fix_electricity_btn).setOnClickListener(this)
        `$`<View>(R.id.fix_water_btn).setOnClickListener(this)
        `$`<View>(R.id.fix_public_btn).setOnClickListener(this)
        `$`<View>(R.id.fix_call_service_btn).setOnClickListener(this)

    }

    override fun onClick(v: View) {
        if (v.id == R.id.fix_electricity_btn) {

        } else if (v.id == R.id.fix_water_btn) {

        } else if (v.id == R.id.fix_public_btn) {

        } else if (v.id == R.id.fix_call_service_btn) {
            ToastUtil.showShort(mContext, getString(R.string.not_join))
        }
    }

    companion object {

        fun newInstance(): SimpleFixFragment {
            val fragment = SimpleFixFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
