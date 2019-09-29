package com.wujia.businesslib.data

import com.wujia.lib_common.base.RootResponse

import java.io.Serializable

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-20
 * description ：
 */

class LoginDTO : RootResponse() {
    /**
     * code : 0
     * data : {"authorityList":[{"component":"string","id":0,"meta":{},"name":"string","path":"string","pid":0,"redirect":"string"}],"device":{"buttonKey":"string","deviceKey":"string"},"token":"string","userInfo":{"createDate":"2019-06-17T10:17:54.371Z","flag":0,"icon":"string","id":0,"nickName":"string","password":"string","status":0,"userName":"string"}}
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

    class DataBean : Serializable {
        /**
         * authorityList : [{"component":"string","id":0,"meta":{},"name":"string","path":"string","pid":0,"redirect":"string"}]
         * device : {"buttonKey":"string","deviceKey":"string"}
         * token : string
         * userInfo : {"createDate":"2019-06-17T10:17:54.371Z","flag":0,"icon":"string","id":0,"nickName":"string","password":"string","status":0,"userName":"string"}
         */

        //        private List<AuthorityListBean> authorityList;

        var device: DeviceBean? = null
        var sip: SipDTO? = null
        var token: String? = null
        var userInfo: UserInfoBean? = null

        //        public List<AuthorityListBean> getAuthorityList() {
        //            return authorityList;
        //        }

        //        public void setAuthorityList(List<AuthorityListBean> authorityList) {
        //            this.authorityList = authorityList;
        //        }

        class DeviceBean : Serializable {
            /**
             * buttonKey : string
             * deviceKey : string
             */

            var buttonKey: String? = null
            var deviceKey: String? = null
        }
        class SipDTO : Serializable {
            /**
             * buttonKey : string
             * deviceKey : string
             */

            var sipAddr: String? = null
            var sipDisplayname: String? = null
        }


        class UserInfoBean : Serializable {

            /**
             * createDate : 2019-06-17T10:17:54.371Z
             * flag : 0
             * icon : string
             * id : 0
             * nickName : string
             * password : string
             * status : 0
             * userName : string
             */

            var communtityId: Int = 0
            var createDate: String? = null
            var flag: Int = 0
            var icon: String? = null
            var id: Int = 0
            var nickName: String? = null
            var password: String? = null
            var status: Int = 0
            var userName: String? = null

            var fid: String? = null
        }

        class AuthorityListBean : Serializable {
            /**
             * component : string
             * id : 0
             * meta : {}
             * name : string
             * path : string
             * pid : 0
             * redirect : string
             */

            var component: String? = null
            var id: Int = 0
            var meta: MetaBean? = null
            var name: String? = null
            var path: String? = null
            var pid: Int = 0
            var redirect: String? = null

            class MetaBean
        }
    }


    /**
     * code : 0
     * data : {"authorityList":[{"component":"string","id":0,"meta":{},"name":"string","path":"string","pid":0,"redirect":"string"}],"device":{"buttonKey":"string","deviceKey":"string"},"token":"string","userInfo":{"createDate":"2019-06-17T10:17:54.371Z","flag":0,"icon":"string","id":0,"nickName":"string","password":"string","status":0,"userName":"string"}}
     */


}
