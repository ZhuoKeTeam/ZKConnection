package com.zkteam.connection.demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zkteam.connection.ZKConnectionManager
import com.zkteam.connection.bean.ZKBean
import com.zkteam.connection.bean.ZKTestBean
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        bt.setOnClickListener {
            val zkApi = ZKConnectionManager.instance.getApi(ZKDemoApi::class.java) as ZKDemoApi
            zkApi.requestTest().enqueue(object : Callback<ZKBean<MutableList<ZKTestBean>>> {
                override fun onFailure(call: Call<ZKBean<MutableList<ZKTestBean>>>, t: Throwable) {
                    tv.text = t.message
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<ZKBean<MutableList<ZKTestBean>>>,
                    response: Response<ZKBean<MutableList<ZKTestBean>>>
                ) {
                    val response = response.body()
                    if (response != null) {

                        tv.text = "返回数据格式为：\n code=${response.code} \n message=${response.message} \n" +
                                " result=${response.result}"
                    }
                }

            })



        }
    }
}
