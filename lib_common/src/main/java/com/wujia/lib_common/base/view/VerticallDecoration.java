package com.wujia.lib_common.base.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author: created by shenbingkai on 2018/12/11 15 33
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class VerticallDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public VerticallDecoration(int spaceHorizontal) {
        this.space = spaceHorizontal;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.bottom = space;
    }
}
