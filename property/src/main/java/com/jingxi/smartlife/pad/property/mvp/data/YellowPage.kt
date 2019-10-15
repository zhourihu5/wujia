package com.jingxi.smartlife.pad.property.mvp.data

import com.google.gson.annotations.SerializedName

data class YellowPage(@SerializedName("area")
                      val area: String = "",
                      @SerializedName("address")
                      val address: String = "",
                      @SerializedName("province")
                      val province: String = "",
                      @SerializedName("city")
                      val city: String = "",
                      @SerializedName("phone")
                      val phone: String = "",
                      @SerializedName("name")
                      val name: String = "",
                      @SerializedName("id")
                      val id: Int = 0,
                      @SerializedName("communityId")
                      val communityId: String = "",
                      @SerializedName("type")
                      val type: String = "",
                      @SerializedName("communityCode")
                      val communityCode: String = "",
                      @SerializedName("createDate")
                      val createDate: String = "")