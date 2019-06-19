package com.wujia.businesslib.data;

import java.io.Serializable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-20
 * description ：
 */

public class LoginDTO extends RootResponse {
    /**
     * code : 0
     * data : {"authorityList":[{"component":"string","id":0,"meta":{},"name":"string","path":"string","pid":0,"redirect":"string"}],"device":{"buttonKey":"string","deviceKey":"string"},"token":"string","userInfo":{"createDate":"2019-06-17T10:17:54.371Z","flag":0,"icon":"string","id":0,"nickName":"string","password":"string","status":0,"userName":"string"}}
     */

//    @SerializedName("code")
//    private int codeX;
    private DataBean data;

//    public int getCodeX() {
//        return codeX;
//    }
//
//    public void setCodeX(int codeX) {
//        this.codeX = codeX;
//    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * authorityList : [{"component":"string","id":0,"meta":{},"name":"string","path":"string","pid":0,"redirect":"string"}]
         * device : {"buttonKey":"string","deviceKey":"string"}
         * token : string
         * userInfo : {"createDate":"2019-06-17T10:17:54.371Z","flag":0,"icon":"string","id":0,"nickName":"string","password":"string","status":0,"userName":"string"}
         */

        private DeviceBean device;
        private String token;
        private UserInfoBean userInfo;
//        private List<AuthorityListBean> authorityList;

        public DeviceBean getDevice() {
            return device;
        }

        public void setDevice(DeviceBean device) {
            this.device = device;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

//        public List<AuthorityListBean> getAuthorityList() {
//            return authorityList;
//        }

//        public void setAuthorityList(List<AuthorityListBean> authorityList) {
//            this.authorityList = authorityList;
//        }

        public static class DeviceBean implements Serializable{
            /**
             * buttonKey : string
             * deviceKey : string
             */

            private String buttonKey;
            private String deviceKey;

            public String getButtonKey() {
                return buttonKey;
            }

            public void setButtonKey(String buttonKey) {
                this.buttonKey = buttonKey;
            }

            public String getDeviceKey() {
                return deviceKey;
            }

            public void setDeviceKey(String deviceKey) {
                this.deviceKey = deviceKey;
            }
        }

        public static class UserInfoBean implements Serializable{
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

            private String createDate;
            private int flag;
            private String icon;
            private int id;
            private String nickName;
            private String password;
            private int status;
            private String userName;

            private String fid;

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }

        public static class AuthorityListBean implements Serializable {
            /**
             * component : string
             * id : 0
             * meta : {}
             * name : string
             * path : string
             * pid : 0
             * redirect : string
             */

            private String component;
            private int id;
            private MetaBean meta;
            private String name;
            private String path;
            private int pid;
            private String redirect;

            public String getComponent() {
                return component;
            }

            public void setComponent(String component) {
                this.component = component;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public MetaBean getMeta() {
                return meta;
            }

            public void setMeta(MetaBean meta) {
                this.meta = meta;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public String getRedirect() {
                return redirect;
            }

            public void setRedirect(String redirect) {
                this.redirect = redirect;
            }

            public static class MetaBean {
            }
        }
    }


    /**
     * code : 0
     * data : {"authorityList":[{"component":"string","id":0,"meta":{},"name":"string","path":"string","pid":0,"redirect":"string"}],"device":{"buttonKey":"string","deviceKey":"string"},"token":"string","userInfo":{"createDate":"2019-06-17T10:17:54.371Z","flag":0,"icon":"string","id":0,"nickName":"string","password":"string","status":0,"userName":"string"}}
     */


}
