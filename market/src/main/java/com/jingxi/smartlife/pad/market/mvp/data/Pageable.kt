package com.jingxi.smartlife.pad.market.mvp.data

data class Pageable(val paged: Boolean = false,
                    val pageNumber: Int = 0,
                    val offset: Int = 0,
                    val pageSize: Int = 0,
                    val unpaged: Boolean = false,
                    val sort: Sort)