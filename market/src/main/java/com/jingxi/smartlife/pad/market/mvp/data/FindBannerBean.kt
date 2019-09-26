package com.jingxi.smartlife.pad.market.mvp.data

import com.wujia.lib_common.base.RootResponse

import java.util.ArrayList

/**
 * author ：shenbingkai@163.com
 * date ：2019-04-23
 * description ：
 */
class FindBannerBean : RootResponse() {

    var content: ArrayList<FindBanner>? = null

    class FindBanner {
        var title: String? = null
        var imgPic: String? = null
        var links: String? = null
        var isShow: String? = null
    }
}
