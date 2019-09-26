package com.wujia.lib_common.base.baseadapter.base


interface ItemViewDelegate<T> {

    val itemViewLayoutId: Int

    fun isForViewType(item: T, position: Int): Boolean

    fun convert(holder: ViewHolder, t: T, position: Int)

}
