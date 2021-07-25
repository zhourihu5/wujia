package com.wujia.lib_common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import butterknife.ButterKnife
import butterknife.Unbinder
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment

/**
 * Created by xmren on 2017/8/1.
 */

abstract class BaseFragment : SupportFragment() {
    protected lateinit var mActivity: BaseActivity
    protected  var mContext: Context?=null
    private var mUnBinder: Unbinder? = null
    protected var mView: View? = null

    protected abstract val layoutId: Int

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as BaseActivity
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(layoutId, container, false)
        mUnBinder = ButterKnife.bind(this, mView!!)
        interruptInject()

        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDataWithSaveInstance(savedInstanceState)
    }

    protected open fun interruptInject() {}

    fun parentStart(fragment: ISupportFragment) {
        val parentFragment = parentFragment as BaseFragment?
        parentFragment!!.start(fragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mUnBinder!!.unbind()
        mView = null

    }

    private fun initDataWithSaveInstance(saveInstanceState: Bundle?) {
        initEventAndData()
    }

    protected open fun initEventAndData() {

    }

    protected fun <T : View> `$`(resId: Int): T {
        return mView!!.findViewById<View>(resId) as T
    }


    fun showToast(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }

}
