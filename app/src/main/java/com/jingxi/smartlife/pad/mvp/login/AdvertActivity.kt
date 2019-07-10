package com.jingxi.smartlife.pad.mvp.login

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.jingxi.smartlife.pad.R
import com.wujia.lib_common.base.Constants
import com.wujia.businesslib.base.WebViewActivity
import com.wujia.businesslib.data.Advert
import com.wujia.lib.imageloader.ImageLoaderManager
import com.wujia.lib_common.base.BaseActivity

import butterknife.BindView
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_advert.*

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27 11:57
 * description ：广告页
 */
class AdvertActivity : BaseActivity() {


    private var advert: Advert? = null

    override fun getLayout(): Int {
        return R.layout.activity_advert
    }

    override fun initEventAndData(savedInstanceState: Bundle?) {
        advert = intent.getSerializableExtra(Constants.INTENT_KEY_1) as Advert
        if (advert != null) {
            ImageLoaderManager.getInstance().loadImage(advert!!.url, img_advert)
            btn_details!!.visibility = View.VISIBLE
        }
    }

    @OnClick(R.id.img_advert, R.id.btn_details)
    fun onViewClicked(view: View) {
        if (view.id == R.id.img_advert) {

        } else if (view.id == R.id.btn_details) {
            if (advert != null && !TextUtils.isEmpty(advert!!.href)) {
                val bundle = Bundle()
                bundle.putString(Constants.INTENT_KEY_1, advert!!.href)
                toActivity(WebViewActivity::class.java, bundle)
            }
        }
        finish()
    }
}
