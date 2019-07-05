package com.zkteam.connection.bean

import com.google.gson.annotations.SerializedName

data class ZKBean<T>(
    @SerializedName("code")
    var code: Int,
    @SerializedName("message")
    var message: String,
    @SerializedName("total")
    var total: Int,
    @SerializedName("result")
    var result: T
)

data class ZKTestBean(
    @SerializedName("id")
    var id: Int,
    @SerializedName("mid2")
    var mid2: String,
    @SerializedName("mid")
    var mid: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("picUrl")
    var picUrl: String,
    @SerializedName("newPageName")
    var newPageName: String,
    @SerializedName("mhUrl")
    var mhUrl: String,
    @SerializedName("mhNewUrl")
    var mhNewUrl: String
) {
}