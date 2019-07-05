package com.zkteam.connection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zkteam.connection.api.ZKApi
import com.zkteam.connection.api.ZKUrlConfig
import com.zkteam.sdk.ZKBase
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class ZKConnectionManager {

    companion object {
        // 双重校验锁式（Double Check)
        val instance: ZKConnectionManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ZKConnectionManager()
        }
    }

    //http://blog.csdn.net/u014695188/article/details/52985514
    private val builder = OkHttpClient.Builder()


    private fun getGson(): Gson {
        return GsonBuilder()
            //配置你的Gson
            .setDateFormat("yyyy-MM-dd hh:mm:ss")
            .create()
    }

    fun getBuilder(): OkHttpClient.Builder {
        return builder
    }

    private fun getRetrofit(): Retrofit {
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(10, TimeUnit.SECONDS)
        builder.writeTimeout(10, TimeUnit.SECONDS)

        if (ZKBase.isDebug) {
            //log信息拦截器
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            //设置Debug Log模式
            builder.addInterceptor(httpLoggingInterceptor)
        }

        builder.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()

                // TODO: 2018/3/6  添加统一的 header
                val requestBuilder = original.newBuilder()

                //                Map<String, String> map = KDRequestUtils.getHeaderMaps();
                //                for (Map.Entry<String, String> entry:
                //                        map.entrySet()) {
                //                    requestBuilder.header(entry.getKey(), entry.getValue());
                //                }

                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        })

        return Retrofit.Builder()
            .baseUrl(ZKUrlConfig.ZK_TEST_DOMAIN_URL)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun getZKApi(): ZKApi {
        return getRetrofit().create(ZKApi::class.java)
    }

    fun getApi(cls: Class<*>): Any {
        return getRetrofit().create(cls)
    }

    fun setBaseUrl(baseUrl: String) {
        ZKUrlConfig.ZK_TEST_DOMAIN_URL = baseUrl
    }

    fun setDebug(isDebug: Boolean) {
        ZKBase.isDebug = isDebug
    }

}