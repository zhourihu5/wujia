package com.wujia.businesslib

import android.view.View
import androidx.annotation.StringRes
import com.wujia.businesslib.base.MvpFragment
import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.BaseView

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-26
 * description ：
 */
abstract class TitleFragment : MvpFragment<BasePresenter<BaseView>>() {
    protected var showBack = true


    @get:StringRes
    abstract val title: Int

    override fun createPresenter(): BasePresenter<BaseView>? {
        return null
    }


    override fun interruptInject() {
        super.interruptInject()
        layout_title_tv?.setText(title)

        if (showBack) {
            showBack()
        }
    }

    fun showBack() {
        layout_back_btn?.apply{
            visibility = View.VISIBLE
            setOnClickListener{this@TitleFragment.pop()}
        }
    }
}
