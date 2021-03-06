package com.jingxi.smartlife.pad.mvp.home.data

import com.wujia.lib_common.base.RootResponse

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-27
 * description ：
 */
class HomeUserInfoBean : RootResponse() {


    /**
     * code : 0
     * data : {"baseDeviceList":[{"buttonKey":"string","deviceKey":"string","familyId":0,"flag":0,"id":0,"type":0}],"communtity":{"address":"string","area":0,"city":0,"id":0,"name":"string","province":0},"userInfo":{"createDate":"2019-06-18T01:58:25.183Z","flag":0,"icon":"string","id":0,"nickName":"string","password":"string","status":0,"userName":"string"},"userInfoList":[{"createDate":"2019-06-18T01:58:25.183Z","flag":0,"icon":"string","id":0,"nickName":"string","password":"string","status":0,"userName":"string"}],"weather":{}}
     */

    //    @SerializedName("code")
    //    private int codeX;
    //    public int getCodeX() {
    //        return codeX;
    //    }
    //
    //    public void setCodeX(int codeX) {
    //        this.codeX = codeX;
    //    }

    var data: DataBean? = null

    class DataBean {
        /**
         * baseDeviceList : [{"buttonKey":"string","deviceKey":"string","familyId":0,"flag":0,"id":0,"type":0}]
         * communtity : {"address":"string","area":0,"city":0,"id":0,"name":"string","province":0}
         * userInfo : {"createDate":"2019-06-18T01:58:25.183Z","flag":0,"icon":"string","id":0,"nickName":"string","password":"string","status":0,"userName":"string"}
         * userInfoList : [{"createDate":"2019-06-18T01:58:25.183Z","flag":0,"icon":"string","id":0,"nickName":"string","password":"string","status":0,"userName":"string"}]
         * weather : {}
         */

        var communtity: CommuntityBean? = null
        var userInfoList: List<UserInfoListBean>? = null

        class CommuntityBean {
            var city: Int = 0
            var id: Int = 0
            var name: String? = null
        }

        class SysUserInfoBean

        class WeatherBean

        class BaseDeviceListBean {
            var familyId: Int = 0
            var flag: Int = 0
            var id: Int = 0
            var type: Int = 0
        }

        class UserInfoListBean {
            var flag: Int = 0
            var icon: String? = null
            var id: Int = 0
            var password: String? = null
            var status: Int = 0
            var userName: String? = null
        }
    }
}
