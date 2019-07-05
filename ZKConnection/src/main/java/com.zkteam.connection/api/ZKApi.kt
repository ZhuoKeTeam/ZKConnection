package com.zkteam.connection.api

import com.zkteam.connection.bean.ZKBean
import com.zkteam.connection.bean.ZKTestBean
import retrofit2.Call
import retrofit2.http.GET

interface ZKApi {

    @GET(ZKUrlConfig.ZK_TEST_URL)
    fun requestTest(): Call<ZKBean<MutableList<ZKTestBean>>>


}