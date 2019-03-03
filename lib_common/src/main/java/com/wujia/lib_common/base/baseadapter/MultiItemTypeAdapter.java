package com.wujia.lib_common.base.baseadapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.wujia.lib_common.base.baseadapter.base.ItemViewDelegate;
import com.wujia.lib_common.base.baseadapter.base.ItemViewDelegateManager;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected List<T> mDatas;

    protected ItemViewDelegateManager<T> mItemViewDelegateManager;
    protected OnItemClickListener mOnItemClickListener;
    protected OnRVItemClickListener mOnRVItemClickListener;
    protected OnRVItemLongClickListener mOnRVItemLongClickListener;


    public MultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder, holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    public void onViewHolderCreated(ViewHolder holder, View itemView) {

    }

    @Nullable
    public T getItemData(int pos) {
        if (mDatas == null ||mDatas.size()==0|| pos < 0 || pos > mDatas.size()) {
            return null;
        }
        return mDatas.get(pos);
    }

    public void convert(ViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                //通过tag方式传递Adapter防止RecyclerView嵌套复用时adapter对象错乱的问题。
                RecyclerView.Adapter adapter = (RecyclerView.Adapter) viewHolder.getConvertView().getTag();
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                }
                if (mOnRVItemClickListener != null) {
                    mOnRVItemClickListener.onItemClick(adapter, viewHolder, position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = viewHolder.getAdapterPosition();
                //通过tag方式传递Adapter防止RecyclerView嵌套复用时adapter对象错乱的问题。
                RecyclerView.Adapter adapter = (RecyclerView.Adapter) viewHolder.getConvertView().getTag();
                if (mOnItemClickListener != null) {
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                if (mOnRVItemLongClickListener != null) {
                    return mOnRVItemLongClickListener.onItemLongClick(adapter, viewHolder, position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getConvertView().setTag(this);
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    public List<T> getDatas() {
        return mDatas;
    }

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    @Deprecated
    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public interface OnRVItemLongClickListener {
        boolean onItemLongClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position);
    }

    public interface OnRVItemClickListener {
        void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position);
    }

    @Deprecated
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemClickListener(OnRVItemClickListener listener) {
        this.mOnRVItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnRVItemLongClickListener listener) {
        this.mOnRVItemLongClickListener = listener;
    }

    public boolean isLast(int pos) {
        return pos == mDatas.size() - 1;
    }
}
