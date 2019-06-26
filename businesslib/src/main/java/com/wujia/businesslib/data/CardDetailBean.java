package com.wujia.businesslib.data;

import java.util.List;

public class CardDetailBean {


    public static final int TYPE_WEB = 2;
    public static final int TYPE_NATIVE = 1;


    /**
     * content : string
     * id : 0
     * services : [{"cover":"string","id":0,"memo":"string","title":"string"}]
     */

    private String content;
    private int id;
    private List<ServicesBean> services;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ServicesBean> getServices() {
        return services;
    }

    public void setServices(List<ServicesBean> services) {
        this.services = services;
    }

    public static class ServicesBean {

        /**
         * category : 0
         * cover : string
         * createDate : 2019-06-19T11:02:59.856Z
         * flag : 0
         * id : 0
         * isSubscribe : 0
         * memo : string
         * status : 0
         * subscribeNum : 0
         * title : string
         * type : 0
         * url : string
         */

        private int category;
        private String cover;
        private String createDate;
        private int flag;
        private int id;
        private int isSubscribe;
        private String memo;
        private int status;
        private int subscribeNum;
        private String title;
        private int type;
        private String url;
        private String packageName;

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsSubscribe() {
            return isSubscribe;
        }

        public void setIsSubscribe(int isSubscribe) {
            this.isSubscribe = isSubscribe;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSubscribeNum() {
            return subscribeNum;
        }

        public void setSubscribeNum(int subscribeNum) {
            this.subscribeNum = subscribeNum;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
