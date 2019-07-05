# ZKConnection
[![](https://jitpack.io/v/ZhuoKeTeam/ZKConnection.svg)](https://jitpack.io/#ZhuoKeTeam/ZKConnection)

## 使用说明

### 配置通用的全局的域名

```
var url = "http://www.gdky005.com"
ZKConnectionManager.instance.setBaseUrl(url)
```

### 建议在 Application 里面配置

```
ZKBase.init(applicationContext)
```

在 APP 的任何位置都可以直接使用上下文
- `ZKBase.context()` 
- `ZKBase.isDebug`
- `Utils.getApp()`


### 可以添加需要的接口地址

类似 `ZKUrlConfig.kt` 里面，将需要的接口写到里面，然后在 ZKXXXApi.kt 中使用。

### 添加 App 中的接口类

可添加 ZKDemoApi.kt (XXXXApi.kt), 继承 ZKApi, 就可以使用默认的测试接口数据。

```
package com.zkteam.connection.demo

import com.zkteam.connection.api.ZKApi

interface ZKDemoApi : ZKApi {
    //这里写需要的接口
     @GET(ZKUrlConfig.ZK_TEST_URL)
     fun requestTest(): Call<ZKBean<MutableList<ZKTestBean>>>
}
```

### 默认可用测试接口
```
    val zkApi = ZKConnectionManager.instance.getApi(ZKDemoApi::class.java) as ZKDemoApi
    zkApi.requestTest().enqueue(object : Callback<ZKBean<MutableList<ZKTestBean>>> {
        override fun onFailure(call: Call<ZKBean<MutableList<ZKTestBean>>>, t: Throwable) {
            tv.text = t.message
        }
    
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
            
```

## retrofit

http://square.github.io/retrofit/

## okhttp

http://square.github.io/okhttp/

