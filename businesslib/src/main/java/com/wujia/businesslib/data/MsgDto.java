package com.wujia.businesslib.data;

import java.util.List;

public class MsgDto {

    public static final String TYPE_PROPERTY = "1";//物业消息
    public static final String TYPE_NOTIFY = "2";//社区公告
    public static final String TYPE_SYSTEM = "0";//系统通知

    public enum MessageType {

        SY("系统通知"),
        WY("物业通知"),
        SQ("社区通知");

        public String getName() {
            return name;
        }

        private final String name;

        MessageType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

//    SY("系统通知"),
//    WY("物业通知"),
//    SQ("社区通知");


    public static final int STATUS_READ = 1;
    public static final int STATUS_UNREAD = 0;

    public static final String getTypeText(MsgDto.ContentBean data) {
        switch (data.getType()) {
            case TYPE_NOTIFY:
                return MessageType.SQ.getName();
            case TYPE_PROPERTY:
                return MessageType.WY.getName();
            case TYPE_SYSTEM:
                return MessageType.SY.getName();

        }
        return "";
    }


    /**
     * content : [{"content":"string","createDate":"2019-06-18T08:31:50.129Z","id":0,"status":"未读","title":"string","type":"系统通知","userId":0}]
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
    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
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

    public static class ContentBean {
        /**
         * content : string
         * createDate : 2019-06-18T08:31:50.129Z
         * id : 0
         * status : 未读
         * title : string
         * type : 系统通知
         * userId : 0
         */

        private String content;
        private String createDate;
        private int id;
        //        private String status;
        private String title;
        private String type;
        private int userId;
        private int isRead;

        public int getIsRead() {
            return isRead;
        }

        public void setIsRead(int isRead) {
            this.isRead = isRead;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

//        public String getStatus() {
//            return status;
//        }

//        public void setStatus(String status) {
//            this.status = status;
//        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
