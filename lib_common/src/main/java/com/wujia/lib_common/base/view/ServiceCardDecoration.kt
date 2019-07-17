package com.wujia.lib_common.base.view

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View

/**
 * Author: created by shenbingkai on 2018/12/11 15 33
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class ServiceCardDecoration(private val space: Int) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        val i = parent.getChildAdapterPosition(view)
        if (i < 4) {
            outRect.top = space
        }
    }
}
