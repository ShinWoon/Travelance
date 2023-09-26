package com.moneyminions.data.interceptor

import android.util.Log
import com.moneyminions.data.datasource.local.PreferenceDataSource
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

private const val TAG = "RequestInterceptor D210"
class RequestInterceptor @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        Log.d(TAG, "intercept.... ${preferenceDataSource.getJwtToken()}")
        try{
//            preferenceDataSource.getJwtToken().let{ token ->
//                token?.let {
//                    builder.addHeader("Authorization", "Bearer ${token.accessToken}")
//                    Log.d(TAG, "intercept: JWT AccessToken 헤더에 담았습니다. ${token.accessToken}")
////                    Log.d(TAG, "intercept: body. ${builder.}")
//                    return chain.proceed(builder.build())
//                }
//            }
            builder.addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQ0NFU1MiLCJpYXQiOjE2OTU3MDY0MjAsImV4cCI6MTY5NTcwODIyMCwiZW1haWwiOiJ0anNnaDE1MTJAbmF2ZXIuY29tIiwicm9sZSI6Ik1FTUJFUiJ9.XknLNqiI3untr67GF9x_UwO0u6nMc9Sx9FdcoPvDlJFjX8jRAlpmJ1bOsiiO-cWrlmNSL0DLl4mdzkCjsFILuw")
            return chain.proceed(builder.build())

        }catch (e: Exception){
            Log.d(TAG, "intercept: $e")
            return chain.proceed(chain.request())
        }
    }
}