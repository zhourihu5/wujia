package com.jingxi.smartlife.pad.market.mvp.view

import android.graphics.Paint
import android.os.Bundle
import com.jingxi.smartlife.pad.market.mvp.data.GroupBuyDetailVo
import com.wujia.businesslib.TitleFragment
import com.wujia.lib.imageloader.ImageLoaderManager
import kotlinx.android.synthetic.main.fragment_group_buy_confirm.*


/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：
 */
class OrderConfirmFragment : TitleFragment() {
    override val layoutId: Int
        get() =  com.jingxi.smartlife.pad.market.R.layout.fragment_group_buy_confirm
    override val title: Int
        get() = com.jingxi.smartlife.pad.market.R.string.group_buy_confirm
    var groupBuyDetailVo:GroupBuyDetailVo?=null

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        val ac=groupBuyDetailVo!!.activity
        tvGoodsTitle.setText(ac.title)
        ImageLoaderManager.instance.loadImage(ac.cover, 0, ivCover)


        val priceArr=ac.price.split('.')
        tvPriceInt.setText(priceArr[0])
        if(priceArr.size>1){
            tvFloat.setText(".${priceArr[1]}")
        }
        tvPriceOld.setText(ac.commodity.price)
        tvPriceOld.paint.flags= Paint.STRIKE_THRU_TEXT_FLAG
//        ivQrCode//todo 生成二维码

    }


    companion object {

        fun newInstance(groupBuyDetailVo:GroupBuyDetailVo): OrderConfirmFragment {
            val fragment = OrderConfirmFragment()
            fragment.groupBuyDetailVo=groupBuyDetailVo
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}

