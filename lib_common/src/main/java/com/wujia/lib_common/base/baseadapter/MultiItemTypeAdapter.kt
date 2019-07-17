package com.wujia.lib_common.base.baseadapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wujia.lib_common.base.baseadapter.base.ItemViewDelegate
import com.wujia.lib_common.base.baseadapter.base.ItemViewDelegateManager
import com.wujia.lib_common.base.baseadapter.base.ViewHolder

/**
 * Created by zhy on 16/4/9.
 */
open class MultiItemTypeAdapter<T>(protected var mContext: Context, datas: List<T>) : RecyclerView.Adapter<ViewHolder>() {
    private var listener: ((adapter: RecyclerView.Adapter<*>?, holder: RecyclerView.ViewHolder, position: Int) -> Unit)?=null
     var datas: List<T>
        protected set

    protected var mItemViewDelegateManager: ItemViewDelegateManager<T>
    protected var mOnItemClickListener: OnItemClickListener? = null
    protected var mOnRVItemClickListener: OnRVItemClickListener? = null
    protected var mOnRVItemLongClickListener: OnRVItemLongClickListener? = null


    init {
        this.datas = datas
        mItemViewDelegateManager = ItemViewDelegateManager()
    }

    override fun getItemViewType(position: Int): Int {
        return if (!useItemViewDelegateManager()) super.getItemViewType(position) else mItemViewDelegateManager.getItemViewType(datas!![position], position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType)
        val layoutId = itemViewDelegate!!.itemViewLayoutId
        val holder = ViewHolder.createViewHolder(mContext, parent, layoutId)
        onViewHolderCreated(holder, holder.convertView)
        setListener(parent, holder, viewType)
        return holder
    }

    fun onViewHolderCreated(holder: ViewHolder, itemView: View) {

    }

    fun getItemData(pos: Int): T? {
        return if (datas == null || datas!!.size == 0 || pos < 0 || pos > datas!!.size) {
            null
        } else datas!![pos]
    }

    fun convert(holder: ViewHolder, t: T) {
        mItemViewDelegateManager.convert(holder, t, holder.adapterPosition)
    }

    protected fun isEnabled(viewType: Int): Boolean {
        return true
    }


    protected fun setListener(parent: ViewGroup, viewHolder: ViewHolder, viewType: Int) {
        if (!isEnabled(viewType)) return
        viewHolder.convertView.setOnClickListener { v ->
            val position = viewHolder.adapterPosition
            //通过tag方式传递Adapter防止RecyclerView嵌套复用时adapter对象错乱的问题。
            val adapter = viewHolder.convertView.tag as RecyclerView.Adapter<*>
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(v, viewHolder, position)
            }
            if (mOnRVItemClickListener != null) {
                mOnRVItemClickListener!!.onItemClick(adapter, viewHolder, position)
            }
            listener?.invoke(adapter, viewHolder, position)
        }

        viewHolder.convertView.setOnLongClickListener(View.OnLongClickListener { v ->
            val position = viewHolder.adapterPosition
            //通过tag方式传递Adapter防止RecyclerView嵌套复用时adapter对象错乱的问题。
            val adapter = viewHolder.convertView.tag as RecyclerView.Adapter<*>
            if (mOnItemClickListener != null) {
                return@OnLongClickListener mOnItemClickListener!!.onItemLongClick(v, viewHolder, position)
            }
            if (mOnRVItemLongClickListener != null) {
                mOnRVItemLongClickListener!!.onItemLongClick(adapter, viewHolder, position)
            } else false
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.convertView.tag = this
        convert(holder, datas!![position])
    }

    override fun getItemCount(): Int {
        return if (datas == null) 0 else datas!!.size
    }

    fun setmDatas(mDatas: List<T>) {
        this.datas = mDatas
    }

    fun addItemViewDelegate(itemViewDelegate: ItemViewDelegate<T>): MultiItemTypeAdapter<*> {
        mItemViewDelegateManager.addDelegate(itemViewDelegate)
        return this
    }

    fun addItemViewDelegate(viewType: Int, itemViewDelegate: ItemViewDelegate<T>): MultiItemTypeAdapter<*> {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate)
        return this
    }

    protected fun useItemViewDelegateManager(): Boolean {
        return mItemViewDelegateManager.itemViewDelegateCount > 0
    }

    @Deprecated("")
    interface OnItemClickListener {
        fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int)

        fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean
    }

    interface OnRVItemLongClickListener {
        fun onItemLongClick(adapter: RecyclerView.Adapter<*>?, holder: RecyclerView.ViewHolder, position: Int): Boolean
    }

    interface OnRVItemClickListener {
        fun onItemClick(adapter: RecyclerView.Adapter<*>?, holder: RecyclerView.ViewHolder, position: Int)
    }

    @Deprecated("")
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = onItemClickListener
    }

    fun setOnItemClickListener(listener: OnRVItemClickListener) {
        this.mOnRVItemClickListener = listener
    }
    fun setOnItemClickListener(listener: ((adapter: RecyclerView.Adapter<*>?, holder: RecyclerView.ViewHolder, position: Int)->Unit)?) {
        this.listener = listener
    }

    fun setOnItemLongClickListener(listener: OnRVItemLongClickListener) {
        this.mOnRVItemLongClickListener = listener
    }

    fun isLast(pos: Int): Boolean {
        return pos == datas!!.size - 1
    }
}
