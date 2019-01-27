package com.wujia.lib_common.base.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author: created by shenbingkai on 2018/12/11 15 33
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class GridDecoration extends RecyclerView.ItemDecoration {

    private int spaceHorizontal;
    private int spaceVertical;

    public GridDecoration(int spaceHorizontal,int spaceVertical) {
        this.spaceHorizontal = spaceHorizontal;
        this.spaceVertical=spaceVertical;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.right = spaceHorizontal;
        outRect.bottom = spaceVertical;
    }
}
