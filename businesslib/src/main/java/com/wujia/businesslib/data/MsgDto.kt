package com.wujia.businesslib.data

class MsgDto {


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

    var isFirst: Boolean = false
    var last: Boolean = false
    var number: Int = 0
    var numberOfElements: Int = 0
    var pageable: PageableBean? = null
    var size: Int = 0
    var sort: SortBeanX? = null
    var totalElements: Int = 0
    var totalPages: Int = 0
    var content: List<ContentBean>? = null


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

    class ContentBean {
        /**
         * content : string
         * createDate : 2019-06-18T08:31:50.129Z
         * id : 0
         * status : 未读
         * title : string
         * type : 系统通知
         * userId : 0
         */

        var content: String? = null
        var createDate: String? = null
        var id: Int = 0
        //        private String status;
        //        public String getStatus() {
        //            return status;
        //        }

        //        public void setStatus(String status) {
        //            this.status = status;
        //        }

        var title: String? = null
        var type: String? = null
        var userId: Int = 0
        var isRead: Int = 0
    }

    companion object {

        val TYPE_PROPERTY = "1"//物业消息
        val TYPE_NOTIFY = "2"//社区公告
        val TYPE_SYSTEM = "0"//系统通知

        //    SY("系统通知"),
        //    WY("物业通知"),
        //    SQ("社区通知");


        val STATUS_READ = 1
        val STATUS_UNREAD = 0

        fun getTypeText(data: MsgDto.ContentBean): String {
            when (data.type) {
                TYPE_NOTIFY -> return "社区通知"
                TYPE_PROPERTY -> return "物业通知"
                TYPE_SYSTEM -> return "系统通知"
            }
            return ""
        }
    }
}
