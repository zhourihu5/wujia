package com.wujia.lib_common.base.baseadapter.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

object WrapperUtils {
    interface SpanSizeCallback {
        fun getSpanSize(layoutManager: GridLayoutManager, oldLookup: GridLayoutManager.SpanSizeLookup, position: Int): Int
    }

    fun onAttachedToRecyclerView(innerAdapter: RecyclerView.Adapter<*>, recyclerView: RecyclerView, callback: SpanSizeCallback) {
        innerAdapter.onAttachedToRecyclerView(recyclerView)

        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val gridLayoutManager = layoutManager as GridLayoutManager?
            val spanSizeLookup = gridLayoutManager!!.spanSizeLookup

            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return callback.getSpanSize(gridLayoutManager, spanSizeLookup, position)
                }
            }
            gridLayoutManager.spanCount = gridLayoutManager.spanCount
        }
    }

    fun setFullSpan(holder: RecyclerView.ViewHolder) {
        val lp = holder.itemView.layoutParams

        if (lp is StaggeredGridLayoutManager.LayoutParams) {

            lp.isFullSpan = true
        }
    }
}
