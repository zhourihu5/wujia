package com.jingxi.smartlife.pad.family.mvp.data

import androidx.annotation.IdRes
import java.util.*

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-23
 * description ：
 */
class EquipmentBean {

    var type: EquipmentType
    var title: String?=null
    @IdRes
    var icon: Int = 0
    var menus: MutableList<Menu>? = null

    constructor(type: EquipmentType, title: String, icon: Int) {
        this.type = type
        this.title = title
        this.icon = icon
    }

    fun addMenu(menu: Menu): EquipmentBean {
        if (null == menus)
            menus = ArrayList()

        menus!!.add(menu)
        return this
    }

    class Menu(var title: String, @field:IdRes
    var icon: Int)
}
