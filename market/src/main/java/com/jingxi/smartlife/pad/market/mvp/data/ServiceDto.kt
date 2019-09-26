package com.jingxi.smartlife.pad.market.mvp.data

import com.wujia.businesslib.data.CardDetailBean

class ServiceDto {


    /**
     * bannerList : [{"cover":"string","createDate":"2019-06-20T01:27:28.267Z","id":0,"moduleId":0,"url":"string"}]
     * page : {"content":[{"category":0,"cover":"string","createDate":"2019-06-20T01:27:28.267Z","flag":0,"id":0,"isSubscribe":0,"memo":"string","packageName":"string","status":0,"subscribeNum":0,"title":"string","type":0,"url":"string"}],"first":true,"last":true,"number":0,"numberOfElements":0,"pageable":{"offset":0,"pageNumber":0,"pageSize":0,"paged":true,"sort":{"sorted":true,"unsorted":true},"unpaged":true},"size":0,"sort":{"sorted":true,"unsorted":true},"totalElements":0,"totalPages":0}
     */

    var page: PageBean? = null
    var bannerList: List<BannerListBean>? = null

    class PageBean {
        /**
         * content : [{"category":0,"cover":"string","createDate":"2019-06-20T01:27:28.267Z","flag":0,"id":0,"isSubscribe":0,"memo":"string","packageName":"string","status":0,"subscribeNum":0,"title":"string","type":0,"url":"string"}]
         * first : true
         * last : true
         * number : 0
         * numberOfElements : 0
         * pageable : {"offset":0,"pageNumber":0,"pageSize":0,"paged":true,"sort":{"sorted":true,"unsorted":true},"unpaged":true}
         * size : 0
         * sort : {"sorted":true,"unsorted":true}
         * totalElements : 0
         * totalPages : 0
         */

        var isFirst: Boolean = false
        var last: Boolean = false
        var number: Int = 0
        var numberOfElements: Int = 0
        var pageable: PageableBean? = null
        var size: Int = 0
        var sort: SortBeanX? = null
        var totalElements: Int = 0
        var totalPages: Int = 0
        var content: List<CardDetailBean.ServicesBean>? = null

        class PageableBean {
            /**
             * offset : 0
             * pageNumber : 0
             * pageSize : 0
             * paged : true
             * sort : {"sorted":true,"unsorted":true}
             * unpaged : true
             */

            var offset: Int = 0
            var pageNumber: Int = 0
            var pageSize: Int = 0
            var isPaged: Boolean = false
            var sort: SortBean? = null
            var isUnpaged: Boolean = false

            class SortBean {
                /**
                 * sorted : true
                 * unsorted : true
                 */

                var isSorted: Boolean = false
                var isUnsorted: Boolean = false
            }
        }

        class SortBeanX {
            /**
             * sorted : true
             * unsorted : true
             */

            var isSorted: Boolean = false
            var isUnsorted: Boolean = false
        }

        //        public static class ContentBean {
        //            /**
        //             * category : 0
        //             * cover : string
        //             * createDate : 2019-06-20T01:27:28.267Z
        //             * flag : 0
        //             * id : 0
        //             * isSubscribe : 0
        //             * memo : string
        //             * packageName : string
        //             * status : 0
        //             * subscribeNum : 0
        //             * title : string
        //             * type : 0
        //             * url : string
        //             */
        //
        //            private int category;
        //            private String cover;
        //            private String createDate;
        //            private int flag;
        //            private int id;
        //            private int isSubscribe;
        //            private String memo;
        //            private String packageName;
        //            private int status;
        //            private int subscribeNum;
        //            private String title;
        //            private int type;
        //            private String url;
        //
        //            public int getCategory() {
        //                return category;
        //            }
        //
        //            public void setCategory(int category) {
        //                this.category = category;
        //            }
        //
        //            public String getCover() {
        //                return cover;
        //            }
        //
        //            public void setCover(String cover) {
        //                this.cover = cover;
        //            }
        //
        //            public String getCreateDate() {
        //                return createDate;
        //            }
        //
        //            public void setCreateDate(String createDate) {
        //                this.createDate = createDate;
        //            }
        //
        //            public int getFlag() {
        //                return flag;
        //            }
        //
        //            public void setFlag(int flag) {
        //                this.flag = flag;
        //            }
        //
        //            public int getId() {
        //                return id;
        //            }
        //
        //            public void setId(int id) {
        //                this.id = id;
        //            }
        //
        //            public int getIsSubscribe() {
        //                return isSubscribe;
        //            }
        //
        //            public void setIsSubscribe(int isSubscribe) {
        //                this.isSubscribe = isSubscribe;
        //            }
        //
        //            public String getMemo() {
        //                return memo;
        //            }
        //
        //            public void setMemo(String memo) {
        //                this.memo = memo;
        //            }
        //
        //            public String getPackageName() {
        //                return packageName;
        //            }
        //
        //            public void setPackageName(String packageName) {
        //                this.packageName = packageName;
        //            }
        //
        //            public int getStatus() {
        //                return status;
        //            }
        //
        //            public void setStatus(int status) {
        //                this.status = status;
        //            }
        //
        //            public int getSubscribeNum() {
        //                return subscribeNum;
        //            }
        //
        //            public void setSubscribeNum(int subscribeNum) {
        //                this.subscribeNum = subscribeNum;
        //            }
        //
        //            public String getTitle() {
        //                return title;
        //            }
        //
        //            public void setTitle(String title) {
        //                this.title = title;
        //            }
        //
        //            public int getType() {
        //                return type;
        //            }
        //
        //            public void setType(int type) {
        //                this.type = type;
        //            }
        //
        //            public String getUrl() {
        //                return url;
        //            }
        //
        //            public void setUrl(String url) {
        //                this.url = url;
        //            }
        //        }
    }

    class BannerListBean {
        /**
         * cover : string
         * createDate : 2019-06-20T01:27:28.267Z
         * id : 0
         * moduleId : 0
         * url : string
         */

        var cover: String? = null
        var createDate: String? = null
        var id: Int = 0
        var moduleId: Int = 0
        var url: String? = null
    }
}
