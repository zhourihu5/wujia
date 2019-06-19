package com.jingxi.smartlife.pad.mvp.home.data;

import com.wujia.businesslib.data.RootResponse;

import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-27
 * description ：
 */
public class HomeUserInfoBean extends RootResponse {


    /**
     * code : 0
     * data : {"baseDeviceList":[{"buttonKey":"string","deviceKey":"string","familyId":0,"flag":0,"id":0,"type":0}],"communtity":{"address":"string","area":0,"city":0,"id":0,"name":"string","province":0},"userInfo":{"createDate":"2019-06-18T01:58:25.183Z","flag":0,"icon":"string","id":0,"nickName":"string","password":"string","status":0,"userName":"string"},"userInfoList":[{"createDate":"2019-06-18T01:58:25.183Z","flag":0,"icon":"string","id":0,"nickName":"string","password":"string","status":0,"userName":"string"}],"weather":{}}
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

    public static class DataBean {
        /**
         * baseDeviceList : [{"buttonKey":"string","deviceKey":"string","familyId":0,"flag":0,"id":0,"type":0}]
         * communtity : {"address":"string","area":0,"city":0,"id":0,"name":"string","province":0}
         * userInfo : {"createDate":"2019-06-18T01:58:25.183Z","flag":0,"icon":"string","id":0,"nickName":"string","password":"string","status":0,"userName":"string"}
         * userInfoList : [{"createDate":"2019-06-18T01:58:25.183Z","flag":0,"icon":"string","id":0,"nickName":"string","password":"string","status":0,"userName":"string"}]
         * weather : {}
         */

        private CommuntityBean communtity;
        private SysUserInfoBean userInfo;
        private WeatherBean weather;
        private List<BaseDeviceListBean> baseDeviceList;
        private List<UserInfoListBean> userInfoList;

        public CommuntityBean getCommuntity() {
            return communtity;
        }

        public void setCommuntity(CommuntityBean communtity) {
            this.communtity = communtity;
        }

        public SysUserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(SysUserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public WeatherBean getWeather() {
            return weather;
        }

        public void setWeather(WeatherBean weather) {
            this.weather = weather;
        }

        public List<BaseDeviceListBean> getBaseDeviceList() {
            return baseDeviceList;
        }

        public void setBaseDeviceList(List<BaseDeviceListBean> baseDeviceList) {
            this.baseDeviceList = baseDeviceList;
        }

        public List<UserInfoListBean> getUserInfoList() {
            return userInfoList;
        }

        public void setUserInfoList(List<UserInfoListBean> userInfoList) {
            this.userInfoList = userInfoList;
        }

        public static class CommuntityBean {
            /**
             * address : string
             * area : 0
             * city : 0
             * id : 0
             * name : string
             * province : 0
             */

            private String address;
            private int area;
            private int city;
            private int id;
            private String name;
            private int province;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getArea() {
                return area;
            }

            public void setArea(int area) {
                this.area = area;
            }

            public int getCity() {
                return city;
            }

            public void setCity(int city) {
                this.city = city;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getProvince() {
                return province;
            }

            public void setProvince(int province) {
                this.province = province;
            }
        }

        public static class SysUserInfoBean {
        }

        public static class WeatherBean {
        }

        public static class BaseDeviceListBean {
            /**
             * buttonKey : string
             * deviceKey : string
             * familyId : 0
             * flag : 0
             * id : 0
             * type : 0
             */

            private String buttonKey;
            private String deviceKey;
            private int familyId;
            private int flag;
            private int id;
            private int type;

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

            public int getFamilyId() {
                return familyId;
            }

            public void setFamilyId(int familyId) {
                this.familyId = familyId;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class UserInfoListBean {
            /**
             * createDate : 2019-06-18T01:58:25.183Z
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
    }
}
