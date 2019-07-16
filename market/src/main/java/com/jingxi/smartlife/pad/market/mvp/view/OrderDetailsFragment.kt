package com.jingxi.smartlife.pad.market.mvp.view

import android.os.Bundle
import android.view.View
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.adapter.OrderGoodsAdapter
import com.jingxi.smartlife.pad.market.mvp.data.OrderBean
import com.wujia.businesslib.TitleFragment
import kotlinx.android.synthetic.main.fragment_order_details.*
import java.util.*

/**
 * Author: created by shenbingkai on 2019/2/23 14 38
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class OrderDetailsFragment : TitleFragment(), PayTypeDialog.PayListener, View.OnClickListener {
    override val title: Int
        get() = R.string.family_order
    private var mAdapter: OrderGoodsAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_order_details
    }

    override fun initEventAndData() {
        super.initEventAndData()
        btn3.setOnClickListener(this)

        val datas = ArrayList<OrderBean>()
        datas.add(OrderBean())
        datas.add(OrderBean())
        datas.add(OrderBean())
        datas.add(OrderBean())
        datas.add(OrderBean())

        mAdapter = OrderGoodsAdapter(mActivity, datas)
        rv1!!.adapter = mAdapter

        //        删除订单dialog
        //        new SimpleDialog.Builder().title(getString(R.string.delete_order)).message(getString(R.string.order_delete_desc)).listener(new OnDialogListener() {
        //            @Override
        //            public void dialogSureClick() {
        //                ToastUtil.showShort(mContext, getString(R.string.order_delete_ed));
        //            }
        //        }).build(mContext).show();


        //        支付方式dialog
        //        PayTypeDialog payTypeDialog = new PayTypeDialog(mContext);
        //        payTypeDialog.setListener(this);
        //        payTypeDialog.show();

        //二维码dialog
        //        new PayQRcodeDialog(mContext).show();
    }


    override fun choosePayType(type: Int) {

    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.btn3) {
            //支付方式dialog
            val payTypeDialog = PayTypeDialog(mContext)
            payTypeDialog.setListener(this)
            payTypeDialog.show()
        }
    }

    companion object {

        fun newInstance(id: String): OrderDetailsFragment {
            val fragment = OrderDetailsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
