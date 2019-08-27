package com.jingxi.smartlife.pad.market.mvp.data

data class OrderVo(val number: Int = 0,
                   val last: Boolean = false,
                   val numberOfElements: Int = 0,
                   val size: Int = 0,
                   val totalPages: Int = 0,
                   val pageable: Pageable,
                   val sort: Sort,
                   val content: List<OrderItemVo>?,
                   val first: Boolean = false,
                   val totalElements: Int = 0)