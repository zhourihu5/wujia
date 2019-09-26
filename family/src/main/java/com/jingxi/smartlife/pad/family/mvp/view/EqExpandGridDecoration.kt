package com.jingxi.smartlife.pad.family.mvp.view

import android.graphics.Rect
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View

/**
 * Author: created by shenbingkai on 2018/12/11 15 33
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class EqExpandGridDecoration(private val space: Int) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        //        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();

        val gridLayoutManager = parent.layoutManager as GridLayoutManager?
        val span = gridLayoutManager!!.spanCount
        if (span > 0) {
            outRect.top = space
        }
        //        GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
        //        spanSizeLookup.getSpanIndex(position, gridLayoutManager.getSpanCount()) == 0;
    }
}
