package com.jingxi.smartlife.pad.family.mvp.view;

import android.graphics.Rect;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Author: created by shenbingkai on 2018/12/11 15 33
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class EqExpandGridDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public EqExpandGridDecoration(int space) {
        this.space = space;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

//        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();

        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        int span = gridLayoutManager.getSpanCount();
        if (span > 0) {
            outRect.top = space;
        }
//        GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
//        spanSizeLookup.getSpanIndex(position, gridLayoutManager.getSpanCount()) == 0;
    }
}
