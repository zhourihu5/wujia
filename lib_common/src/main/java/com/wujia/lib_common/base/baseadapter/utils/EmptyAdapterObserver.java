package com.wujia.lib_common.base.baseadapter.utils;


import android.support.v7.widget.RecyclerView;
import android.view.View;
/**
 * Author: shenbingkai
 * CreateDate: 2019-04-06 00:51
 * Description:
 */

public class EmptyAdapterObserver extends RecyclerView.AdapterDataObserver {

    private RecyclerView.Adapter mAdapter;
    private View mEmptyView;

    public EmptyAdapterObserver(RecyclerView.Adapter adapter, View emptyView) {
        mAdapter = adapter;
        mEmptyView = emptyView;
    }

    public void onChanged() {
        onDataChanged();
    }

    public void onItemRangeChanged(int positionStart, int itemCount) {
        onDataChanged();
    }

    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        onDataChanged();
    }

    public void onItemRangeInserted(int positionStart, int itemCount) {
        onDataChanged();
    }

    public void onItemRangeRemoved(int positionStart, int itemCount) {
        onDataChanged();
    }

    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        onDataChanged();
    }

    private void onDataChanged() {
        if (mAdapter == null || mEmptyView == null)
            return;
        if (mAdapter.getItemCount() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
    }
}
