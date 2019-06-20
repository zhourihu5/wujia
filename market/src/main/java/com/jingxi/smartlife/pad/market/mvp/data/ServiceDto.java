package com.jingxi.smartlife.pad.market.mvp.data;

import com.wujia.businesslib.data.CardDetailBean;

import java.util.List;

public class ServiceDto {


    /**
     * bannerList : [{"cover":"string","createDate":"2019-06-20T01:27:28.267Z","id":0,"moduleId":0,"url":"string"}]
     * page : {"content":[{"category":0,"cover":"string","createDate":"2019-06-20T01:27:28.267Z","flag":0,"id":0,"isSubscribe":0,"memo":"string","packageName":"string","status":0,"subscribeNum":0,"title":"string","type":0,"url":"string"}],"first":true,"last":true,"number":0,"numberOfElements":0,"pageable":{"offset":0,"pageNumber":0,"pageSize":0,"paged":true,"sort":{"sorted":true,"unsorted":true},"unpaged":true},"size":0,"sort":{"sorted":true,"unsorted":true},"totalElements":0,"totalPages":0}
     */

    private PageBean page;
    private List<BannerListBean> bannerList;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<BannerListBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerListBean> bannerList) {
        this.bannerList = bannerList;
    }

    public static class PageBean {
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

        private boolean first;
        private boolean last;
        private int number;
        private int numberOfElements;
        private PageableBean pageable;
        private int size;
        private SortBeanX sort;
        private int totalElements;
        private int totalPages;
        private List<CardDetailBean.ServicesBean> content;

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public PageableBean getPageable() {
            return pageable;
        }

        public void setPageable(PageableBean pageable) {
            this.pageable = pageable;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public SortBeanX getSort() {
            return sort;
        }

        public void setSort(SortBeanX sort) {
            this.sort = sort;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public List<CardDetailBean.ServicesBean> getContent() {
            return content;
        }

        public void setContent(List<CardDetailBean.ServicesBean> content) {
            this.content = content;
        }

        public static class PageableBean {
            /**
             * offset : 0
             * pageNumber : 0
             * pageSize : 0
             * paged : true
             * sort : {"sorted":true,"unsorted":true}
             * unpaged : true
             */

            private int offset;
            private int pageNumber;
            private int pageSize;
            private boolean paged;
            private SortBean sort;
            private boolean unpaged;

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public int getPageNumber() {
                return pageNumber;
            }

            public void setPageNumber(int pageNumber) {
                this.pageNumber = pageNumber;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public boolean isPaged() {
                return paged;
            }

            public void setPaged(boolean paged) {
                this.paged = paged;
            }

            public SortBean getSort() {
                return sort;
            }

            public void setSort(SortBean sort) {
                this.sort = sort;
            }

            public boolean isUnpaged() {
                return unpaged;
            }

            public void setUnpaged(boolean unpaged) {
                this.unpaged = unpaged;
            }

            public static class SortBean {
                /**
                 * sorted : true
                 * unsorted : true
                 */

                private boolean sorted;
                private boolean unsorted;

                public boolean isSorted() {
                    return sorted;
                }

                public void setSorted(boolean sorted) {
                    this.sorted = sorted;
                }

                public boolean isUnsorted() {
                    return unsorted;
                }

                public void setUnsorted(boolean unsorted) {
                    this.unsorted = unsorted;
                }
            }
        }

        public static class SortBeanX {
            /**
             * sorted : true
             * unsorted : true
             */

            private boolean sorted;
            private boolean unsorted;

            public boolean isSorted() {
                return sorted;
            }

            public void setSorted(boolean sorted) {
                this.sorted = sorted;
            }

            public boolean isUnsorted() {
                return unsorted;
            }

            public void setUnsorted(boolean unsorted) {
                this.unsorted = unsorted;
            }
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

    public static class BannerListBean {
        /**
         * cover : string
         * createDate : 2019-06-20T01:27:28.267Z
         * id : 0
         * moduleId : 0
         * url : string
         */

        private String cover;
        private String createDate;
        private int id;
        private int moduleId;
        private String url;

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getModuleId() {
            return moduleId;
        }

        public void setModuleId(int moduleId) {
            this.moduleId = moduleId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
