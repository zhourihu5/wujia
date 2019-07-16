package com.wujia.businesslib.data

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-29
 * description ：
 */
class LinkUrlBean {

    //  二级  {"code":"realtyService","name":"物业服务","children":{"code":"3","name":"家政服务","children":{}}}
    //  三级  {"code":"realtyService","name":"物业服务","children":{"code":"report","name":"报事报修","children":{"code":"4","name":"搬家服务"}}}

    var code: String? = null
    var name: String? = null
    var children: Children1? = null


    class Children1 {

        var code: String? = null
        var name: String? = null
        var children: Children2? = null

        companion object {
            val CODE_TYPE_FIND = "discover"//发现
            val CODE_TYPE_GOV = "goverment"//政务
            val CODE_TYPE_ALL = "all"//全部

            val CODE_TYPE_REPORT = "report"//报事报修
            val CODE_TYPE_MONEY = "money"//物业缴费
            val CODE_TYPE_ORDER = "order"//订单管理
            val CODE_TYPE_PHONE = "phone"//电话查询
        }

    }

    class Children2 {
        var code: String? = null
        var name: String? = null


    }

    companion object {

        val CODE_TYPE_MARKET = "serviceMarket"//服务市场
        val CODE_TYPE_PROPERTY = "realtyService"//物业服务
    }
}
