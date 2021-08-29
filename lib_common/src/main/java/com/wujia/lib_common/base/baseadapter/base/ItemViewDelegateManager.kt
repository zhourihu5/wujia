package com.wujia.lib_common.base.baseadapter.base

import androidx.collection.SparseArrayCompat

class ItemViewDelegateManager<T> {
    internal var delegates: SparseArrayCompat<ItemViewDelegate<T>> = SparseArrayCompat()

    val itemViewDelegateCount: Int
        get() = delegates.size()

    fun addDelegate(delegate: ItemViewDelegate<T>?): ItemViewDelegateManager<T> {
        var viewType = delegates.size()
        if (delegate != null) {
            delegates.put(viewType, delegate)
            viewType++
        }
        return this
    }

    fun addDelegate(viewType: Int, delegate: ItemViewDelegate<T>): ItemViewDelegateManager<T> {
        if (delegates.get(viewType) != null) {
            throw IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewDelegate is "
                            + delegates.get(viewType))
        }
        delegates.put(viewType, delegate)
        return this
    }

    fun getItemViewType(item: T, position: Int): Int {
        val delegatesCount = delegates.size()
        for (i in delegatesCount - 1 downTo 0) {
            val delegate = delegates.valueAt(i)
            if (delegate.isForViewType(item, position)) {
                return delegates.keyAt(i)
            }
        }
        throw IllegalArgumentException(
                "No ItemViewDelegate added that matches position=$position in data source")
    }

    fun convert(holder: ViewHolder, item: T, position: Int) {
        val delegatesCount = delegates.size()
        for (i in 0 until delegatesCount) {
            val delegate = delegates.valueAt(i)

            if (delegate.isForViewType(item, position)) {
                delegate.convert(holder, item, position)
                return
            }
        }
        throw IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=$position in data source")
    }


    fun getItemViewDelegate(viewType: Int): ItemViewDelegate<*>? {
        return delegates.get(viewType)
    }

}
