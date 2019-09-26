package com.wujia.lib_common.base.baseadapter.utils


import androidx.recyclerview.widget.RecyclerView
import android.view.View

/**
 * Author: shenbingkai
 * CreateDate: 2019-04-06 00:51
 * Description:
 */

class EmptyAdapterObserver(private val mAdapter: RecyclerView.Adapter<*>?, private val mEmptyView: View?) : RecyclerView.AdapterDataObserver() {

    override fun onChanged() {
        onDataChanged()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        onDataChanged()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
        onDataChanged()
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        onDataChanged()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        onDataChanged()
    }

    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        onDataChanged()
    }

    private fun onDataChanged() {
        if (mAdapter == null || mEmptyView == null)
            return
        if (mAdapter.itemCount == 0) {
            mEmptyView.visibility = View.VISIBLE
        } else {
            mEmptyView.visibility = View.GONE
        }
    }
}
