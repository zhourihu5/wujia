package com.wujia.intellect.terminal.family.mvp.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author: created by shenbingkai on 2018/12/11 15 33
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class EqExpandGridDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public EqExpandGridDecoration(int space) {
        this.space=space;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

//        outRect.right = spaceHorizontal;
//        outRect.bottom = spaceVertical;
    }
}