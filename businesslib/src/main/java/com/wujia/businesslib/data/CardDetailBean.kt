package com.wujia.businesslib.data

class CardDetailBean {


    /**
     * content : string
     * id : 0
     * services : [{"cover":"string","id":0,"memo":"string","title":"string"}]
     */

    var content: String? = null
    var id: Int = 0
    var services: List<ServicesBean>? = null

    class ServicesBean {

        var cover: String? = null
        var createDate: String? = null
        var flag: Int = 0
        var id: Int = 0
        var isSubscribe: Int = 0
        var memo: String? = null
        var status: Int = 0
        var title: String? = null
        var type: Int = 0
        var url: String? = null
        var packageName: String? = null
    }

    companion object {


        const val TYPE_WEB = 2
        const val TYPE_NATIVE = 1
    }
}
