package com.wujia.lib_common.base.baseadapter

import android.content.Context
import android.view.LayoutInflater

import com.wujia.lib_common.base.baseadapter.base.ItemViewDelegate
import com.wujia.lib_common.base.baseadapter.base.ViewHolder

abstract class CommonAdapter<T>(  mContext: Context, protected var mLayoutId: Int, datas: List<T>) : MultiItemTypeAdapter<T>(mContext, datas) {
    private var mInflater: LayoutInflater = LayoutInflater.from(mContext)

    init {
        this.datas = datas

        addItemViewDelegate(object : ItemViewDelegate<T> {
            override val itemViewLayoutId: Int
                get() = mLayoutId

            override fun isForViewType(item: T, position: Int): Boolean {
                return true
            }

            override fun convert(holder: ViewHolder, t: T, position: Int) {
                this@CommonAdapter.convert(holder, t, position)
            }
        })
    }

    protected abstract fun convert(holder: ViewHolder, t: T, position: Int)

}
